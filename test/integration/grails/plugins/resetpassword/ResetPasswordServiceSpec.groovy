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
        resetPasswordService.isCorrect(username, "security.question.family.wedding.honeymoon.city", "Venice")
        resetPasswordService.isCorrect(username, "security.question.family.child.oldest.name.middle", "Adam")
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

}
