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

import grails.validation.Validateable

/**
 * A command object used in step3 of the reset password process.
 */
@Validateable
class ResetPasswordCommand {

    String username
    String password1
    String password2

    Locale locale

    def passwordValidatorService

    static constraints = {
        username(maxSize: 80, nullable: false, blank: false)
        password1(maxSize: 255, nullable: false, blank: false, validator: { val, obj, errors ->
            try {
                def result = obj.passwordValidatorService.validatePassword(val, obj.username, obj.locale)
                for (code in result) {
                    errors.rejectValue('password1', code)
                }
            } catch (Exception e) {
                errors.rejectValue('password1', e.message)
            }
            return null
        })
        password2(maxSize: 255, blank: false, validator: { val, obj ->
            val == obj.password1 ? null : 'resetPassword.error.password.mismatch'
        })
        locale(nullable: true)
    }

    @Override
    String toString() {
        username.toString()
    }
}
