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

class BootStrap {

    def resetPasswordService

    def init = { servletContext ->

        // For test purpose only!
        def username = "test@test.com"
        resetPasswordService.addAnswer(username, "security.question.birth.city", "New York")
        resetPasswordService.addAnswer(username, "security.question.family.wedding.honeymoon.city", "Venice")
        resetPasswordService.addAnswer(username, "security.question.family.child.oldest.name.middle", "Adam")
    }

    def destroy = {
    }

}