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

/**
 * Tests for ResetPasswordServiceSpec.
 */
class ResetPasswordServiceSpec extends grails.plugin.spock.IntegrationSpec {

    def grailsApplication
    def resetPasswordService

    def "available questions configured"() {
        expect:
        !resetPasswordService.getAvailableQuestions().isEmpty()
    }

    def "add and test correct answers"() {
        given:
        def username = "test"

        when:
        resetPasswordService.addAnswer(username, "security.question.birth.city", "New York")
        resetPasswordService.addAnswer(username, "security.question.family.wedding.honeymoon.city", "Venice")
        resetPasswordService.addAnswer(username, "security.question.family.child.oldest.name.middle", "Adam")

        then:
        !resetPasswordService.isCorrect(username, "security.question.birth.city", "Paris")
        resetPasswordService.isCorrect(username, "security.question.birth.city", "New York")
        resetPasswordService.isCorrect(username, "security.question.birth.city", "NewYork") // Case+Space insensitve
        resetPasswordService.isCorrect(username, "security.question.birth.city", "NEW    YORK")
        resetPasswordService.isCorrect(username, "security.question.family.wedding.honeymoon.city", "Venice")
        !resetPasswordService.isCorrect(username, "security.question.family.wedding.honeymoon.city", "Venise")
        resetPasswordService.isCorrect(username, "security.question.family.child.oldest.name.middle", "Adam")
        resetPasswordService.isCorrect(username, "security.question.family.child.oldest.name.middle", "Adam ")
        resetPasswordService.isCorrect(username, "security.question.family.child.oldest.name.middle", " Adam")
        !resetPasswordService.isCorrect(username, "security.question.family.child.oldest.name.middle", "Sadam")
    }

    def "set answers from a map"() {
        given:
        def username = "joe"
        def map = ['security.question.birth.city': "London",
                'security.question.family.wedding.honeymoon.city': "Paris",
                'security.question.family.child.oldest.name.middle': "Marie"
        ]

        when:
        resetPasswordService.setAnswers(username, map)

        then:
        resetPasswordService.getQuestionsForUser(username).size() == 3
        resetPasswordService.isCorrect(username, "security.question.birth.city", "London")
        resetPasswordService.isCorrect(username, "security.question.family.wedding.honeymoon.city", "Paris")
        resetPasswordService.isCorrect(username, "security.question.family.child.oldest.name.middle", "Marie")

        when:
        resetPasswordService.setAnswers(username, ['security.question.birth.city': "Stockholm"])

        then:
        resetPasswordService.getQuestionsForUser(username).size() == 1
        !resetPasswordService.isCorrect(username, "security.question.birth.city", "London")
        resetPasswordService.isCorrect(username, "security.question.birth.city", "Stockholm")
    }

    def "empty answer"() {
        when:
        resetPasswordService.addAnswer("test", "security.question.birth.city", "")

        then:
        thrown(IllegalArgumentException)
    }

    def "invalid question"() {
        when:
        resetPasswordService.addAnswer("test", "security.question.my.name", "test")

        then:
        thrown(IllegalArgumentException)
    }

    def "question not answered"() {
        given:
        resetPasswordService.addAnswer("test", "security.question.birth.city", "New York")

        when:
        resetPasswordService.isCorrect("test", "security.question.family.wedding.honeymoon.city", "Venice")

        then:
        thrown(IllegalArgumentException)
    }

    def "verifyAnswers with no questions defined should return truth"() {
        given:
        resetPasswordService.addAnswer("anna", "security.question.birth.city", "Oslo")

        when:
        def answers = ['security.question.birth.city': "Oslo",
                'security.question.family.wedding.honeymoon.city': "Paris",
                'security.question.family.child.oldest.name.middle': "Tone"
        ]
        then:
        resetPasswordService.verifyAnswers("laura", answers) // Laura has no questions defined.
    }

    def "remove question"() {
        given:
        def username = "test"

        when:
        resetPasswordService.addAnswer(username, "security.question.birth.city", "New York")
        resetPasswordService.addAnswer(username, "security.question.family.wedding.honeymoon.city", "Venice")
        resetPasswordService.addAnswer(username, "security.question.family.child.oldest.name.middle", "Adam")

        then:
        resetPasswordService.getQuestionsForUser(username).size() == 3

        when: "remove a question"
        resetPasswordService.removeAnswer(username, "security.question.family.wedding.honeymoon.city")

        then: "only two left"
        resetPasswordService.getQuestionsForUser(username).size() == 2


        when: "remove non-existing question"
        resetPasswordService.removeAnswer(username, "security.question.foo.bogus")

        then: "nothing happens"
        resetPasswordService.getQuestionsForUser(username).size() == 2
    }
}
