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

import javax.servlet.http.HttpServletResponse

class ResetPasswordController {

    def grailsApplication
    def resetPasswordService

    def index() {
        redirect(action: "step1")
    }

    def step1() {
        def httpSession = request.session
        def retries = grailsApplication.config.reset.password.step1.retries ?: 5
        if ((httpSession.resetPasswordStep1Retries ?: 0) >= retries) {
            redirect(action: "unauthorized")
            return
        }
        def fields = grailsApplication.config.reset.password.step1.fields ?: ['email', 'postalCode']
        switch (request.method) {
            case "GET":
                break
            case "POST":
                try {
                    def user = resetPasswordService.verifyAccount(params)
                    if (user) {
                        httpSession.resetPasswordUser = user
                        httpSession.resetPasswordStep1 = true
                        if (grailsApplication.config.reset.password.email.enabled) {
                            httpSession.resetPasswordCode = sendSecurityCode(params.email)
                        }
                        redirect(action: "step2")
                        return
                    } else {
                        httpSession.resetPasswordStep1 = false
                        flash.error = message(code: 'resetPassword.error.invalid.data', default: 'Invalid data')
                        httpSession.resetPasswordStep1Retries = (httpSession.resetPasswordStep1Retries ?: 0) + 1
                    }
                } catch (Exception e) {
                    log.error(e)
                    flash.error = message(code: 'resetPassword.error.server', default: 'Server error')
                }
                break
        }
        def model = params.subMap(fields)
        model.fields = fields
        return model
    }

    def step2() {
        def httpSession = request.session
        if (!httpSession.resetPasswordStep1) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN)
            return
        }
        def retries = grailsApplication.config.reset.password.step2.retries ?: 10
        if ((httpSession.resetPasswordStep2Retries ?: 0) >= retries) {
            redirect(action: "unauthorized")
            return
        }
        def username = httpSession.resetPasswordUser
        if (!username) {
            redirect(action: "unauthorized")
            return
        }
        def questions = resetPasswordService.getQuestionsForUser(username)
        if (httpSession.resetPasswordCode) {
            questions << 'security.question.email.code'
        }
        if (!questions) {
            redirect(action: "unsupported")
            return
        }

        def answers = params.findAll {it.key.startsWith('security.question')}

        switch (request.method) {
            case "GET":
                return [questions: questions, answers: answers]
            case "POST":
                try {
                    def user = resetPasswordService.verifyAnswers(username, answers)
                    def codeOk = httpSession.resetPasswordCode == null ||
                            (httpSession.resetPasswordCode == params['security.question.email.code'])
                    if (user && codeOk) {
                        httpSession.resetPasswordStep2 = true
                        redirect(action: "step3")
                    } else {
                        httpSession.resetPasswordStep2 = false
                        httpSession.resetPasswordStep2Retries = (httpSession.resetPasswordStep2Retries ?: 0) + 1
                        flash.error = message(code: 'resetPassword.error.invalid.data', default: 'Invalid data')
                        return [questions: questions, answers: answers]
                    }
                } catch (Exception e) {
                    log.error(e)
                    flash.error = message(code: 'resetPassword.error.server', default: 'Server error')
                }
                break
        }
    }

    /**
     * Security questions answered correct. Allow user to set new password.
     * @return
     */
    def step3() {
        def httpSession = request.session
        if (!(httpSession.resetPasswordStep1 && httpSession.resetPasswordStep2)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN)
            return
        }
        switch (request.method) {
            case "GET":
                break
            case "POST":
                try {
                    if (params.password1 == params.password2) {
                        def username = httpSession.resetPasswordUser
                        def user = resetPasswordService.changePassword(username, params.password1)
                        if (user) {
                            httpSession.resetPasswordStep3 = true
                            redirect(action: "success")
                        } else {
                            httpSession.resetPasswordStep3 = false
                            redirect(action: "unsupported")
                        }
                    } else {
                        flash.error = message(code: 'resetPassword.error.password.mismatch')
                    }
                } catch (Exception e) {
                    log.error(e)
                    flash.error = message(code: 'resetPassword.error.server', default: 'Server error')
                }
                break
        }
    }

    def success() {
        resetSession()
        def config = grailsApplication.config
        def url = config.reset.password.success.url ?: config.grails.serverURL
        if (!url) {
            url = request.contextPath
        }
        [url: url]
    }

    def unsupported() {
        resetSession()
    }

    def unauthorized() {
        def username = request.session?.resetPasswordUser
        if (username) {
            resetPasswordService.disableAccount(username)
        }
        resetSession()
    }

    private String sendSecurityCode(String email) {
        def config = grailsApplication.config.reset.password.email
        def subject = message(code: "security.question.email.subject", default: "Password Reset Security Code")
        def text = message(code: "security.question.email.body", default: "Security code:")
        def code = resetPasswordService.getSecurityCode()
        sendMail {
            multipart true
            to email
            if (config.sender) {
                from config.sender
            }
            if (config.cc) {
                cc config.cc
            }
            if (config.bcc) {
                bcc config.bcc
            }
            delegate.subject subject
            delegate.text "$text\n\n$code\n".toString()
            html(view: "email", model: [code: code, email: email, layout: (config.layout ?: 'email')])
        }
        return code
    }

    private void resetSession() {
        def httpSession = request.session
        httpSession.removeAttribute('resetPasswordUser')
        httpSession.removeAttribute('resetPasswordStep1')
        httpSession.removeAttribute('resetPasswordStep2')
        httpSession.removeAttribute('resetPasswordCode')
    }
}
