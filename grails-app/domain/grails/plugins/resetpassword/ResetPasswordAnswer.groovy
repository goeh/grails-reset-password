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

class ResetPasswordAnswer {

    String username
    int orderIndex
    String question
    String answer

    static constraints = {
        username(maxSize:80, blank:false)
        question(maxSize:80, unique:'username')
        answer(maxSize:255, blank:false)
    }
    static mapping = {
        sort 'orderIndex'
    }
    String toString() {
        "$username:$question"
    }
}
