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

class ResetPasswordGrailsPlugin {
    def version = "0.9.4"
    def grailsVersion = "2.4 > *"
    def dependsOn = [:]
    def pluginExcludes = [
        "grails-app/views/error.gsp",
        "src/groovy/grails/plugins/resetpassword/TestDelegate.groovy"
    ]
    def title = "Reset Password Plugin"
    def author = "Goran Ehrsson"
    def authorEmail = "goran@technipelago.se"
    def description = '''
A secure "forgot password" feature that uses personal security questions.
The user is allowed to reset his/her own password by answering a series of
questions. The web application doesn’t need to use email, display passwords,
or set any temporary passwords. It's flexible and can be customized for
different requirements using application event listeners.
'''
    def documentation = "https://github.com/goeh/grails-reset-password"
    def license = "APACHE"
    def organization = [ name: "Technipelago AB", url: "http://www.technipelago.se/" ]
    def issueManagement = [ system: "github", url: "https://github.com/goeh/grails-reset-password/issues" ]
    def scm = [ url: "https://github.com/goeh/grails-reset-password" ]

    /**
     * This feature declaration enables the "reset my password" feature in GR8 CRM.
     */
    def features = {
        password {
            description "Reset Password"
            link controller: 'resetPassword'
            enabled true
            hidden true
        }
    }
}
