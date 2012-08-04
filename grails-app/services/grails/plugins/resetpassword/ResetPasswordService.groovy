/*
 * Copyright (c) 2012 Goran Ehrsson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package grails.plugins.resetpassword

import java.security.MessageDigest

/**
 * Reset password service.
 */
class ResetPasswordService {

    def grailsApplication
    def resetPasswordDelegate

    List getAvailableQuestions() {
        grailsApplication.config.reset.password.questions ?: []
    }

    List getQuestionsForUser(String username) {
        def questions = ResetPasswordAnswer.createCriteria().list() {
            projections {
                property('question')
            }
            eq('username', username)
            order 'orderIndex', 'asc'
        }
        resetPasswordDelegate.getQuestions(username, questions)
    }

    /**
     * Set all security answers for a user.
     * Any existing answers will be replaced.
     * @param username the user to set answers for
     * @param answers a Map with answers keyed by question i18n key
     */
    void setAnswers(String username, Map answers) {
        removeAllAnswers(username)
        answers.each{q, a->
            addAnswer(username, q, a)
        }
    }

    /**
     * Store answer to a question.
     *
     * @param username the user key (username or email).
     * @param questionKey i18n key for the question
     * @param answer the answer to store
     */
    void addAnswer(String username, String question, String answer) {
        if (!username) {
            throw new IllegalArgumentException("empty username is not allowed")
        }
        // Remove all white space and lowercase answer.
        answer = answer?.replaceAll(/\s*/, '')?.toLowerCase()
        if (!answer) {
            throw new IllegalArgumentException("empty answer is not allowed")
        }
        if (!getAvailableQuestions().contains(question)) {
            throw new IllegalArgumentException("question [$question] is not a valid security question")
        }
        def hashedAnswer = toHex(hash(answer.getBytes("UTF-8"), username.getBytes("UTF-8")))
        def usa = ResetPasswordAnswer.findByUsernameAndQuestion(username, question)
        if (!usa) {
            usa = new ResetPasswordAnswer(username: username, question: question)
        }
        usa.answer = hashedAnswer
        usa.save(failOnError: true)
    }

    void removeAnswer(String username, String questionKey) {
        if (!username) {
            throw new IllegalArgumentException("empty username is not allowed")
        }
        def question = getQuestionNumber(questionKey)
        if (question == -1) {
            throw new IllegalArgumentException("question [$questionKey] is not a valid security question")
        }
        ResetPasswordAnswer.findByUsernameAndQuestion(username, question)?.delete()
    }

    void removeAllAnswers(String username) {
        ResetPasswordAnswer.findAllByUsername(username)*.delete()
    }

    /**
     * Compare if supplied answer is the same as stored answer for a question.
     * Note: White space and case are ignored when verifying the answer.
     *
     * @param username the user key (username or email).
     * @param question i18n key for the question
     * @param answer the answer to test
     * @return true if the answer is the same as stored
     */
    boolean isCorrect(String username, String question, String answer) {
        if (!username) {
            throw new IllegalArgumentException("empty username is not allowed")
        }
        answer = answer?.replaceAll(/\s*/, '')?.toLowerCase()
        if (!answer) {
            throw new IllegalArgumentException("empty answer is not allowed")
        }
        if (!getAvailableQuestions().contains(question)) {
            throw new IllegalArgumentException("question [$question] is not a valid security question")
        }
        def hashedAnswer = toHex(hash(answer.getBytes("UTF-8"), username.getBytes("UTF-8")))
        def usa = ResetPasswordAnswer.findByUsernameAndQuestion(username, question)
        if (!usa) {
            throw new IllegalArgumentException("User [$username] has no answer for for question [$question]")
        }
        return hashedAnswer == usa.answer
    }

    def verifyAccount(Map params) {
        resetPasswordDelegate.verifyAccount(params)
    }

    def disableAccount(String username) {
        resetPasswordDelegate.disableAccount(username)
    }

    String getSecurityCode() {
        (new Random(System.currentTimeMillis()).nextInt(800000) + 123456).toString()
    }

    /**
     * Verify that all questions defined for a user are answered correct.
     * Note: If no questions are defined, this method still returns the username.
     * @param username username of use answering questions
     * @param answers answers keyed by question i18n key
     * @return username if questions answered correct, null otherwise.
     */
    def verifyAnswers(String username, Map answers) {
        int correct = 0
        def questions = getQuestionsForUser(username)
        for (q in questions) {
            if (isCorrect(username, q, answers[q])) {
                correct++
            }
        }
        return correct == questions.size() ? username : null
    }

    def changePassword(String username, String password) {
        event(for:"app", topic:"resetPassword", data:[username: username, password: password])
        resetPasswordDelegate.resetPassword(username, password)
    }

    private byte[] hash(byte[] bytes, byte[] salt) {
        def algorithm = grailsApplication.config.reset.password.hashAlgorithm ?: 'SHA-256'
        MessageDigest digest = MessageDigest.getInstance(algorithm)
        if (salt != null) {
            digest.reset()
            digest.update(salt)
        }
        byte[] hashed = digest.digest(bytes)
        int iterations = (grailsApplication.config.reset.password.hashIterations ?: 1000) - 1 //already hashed once above
        //iterate remaining number:
        for (int i = 0; i < iterations; i++) {
            digest.reset()
            hashed = digest.digest(hashed)
        }
        return hashed
    }

    private static final char[] DIGITS = [
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    ].toArray()

    private String toHex(byte[] bytes) {

        int l = bytes.length

        char[] out = new char[l << 1]

        int j = 0
        for (int i = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & bytes[i]) >>> 4]
            out[j++] = DIGITS[0x0F & bytes[i]]
        }

        return new String(out)
    }
}
