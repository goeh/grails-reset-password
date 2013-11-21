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

import edu.vt.middleware.password.AlphabeticalSequenceRule
import edu.vt.middleware.password.CharacterCharacteristicsRule
import edu.vt.middleware.password.DigitCharacterRule
import edu.vt.middleware.password.LengthRule
import edu.vt.middleware.password.LowercaseCharacterRule
import edu.vt.middleware.password.MessageResolver
import edu.vt.middleware.password.NonAlphanumericCharacterRule
import edu.vt.middleware.password.NumericalSequenceRule
import edu.vt.middleware.password.Password
import edu.vt.middleware.password.PasswordData
import edu.vt.middleware.password.PasswordValidator
import edu.vt.middleware.password.QwertySequenceRule
import edu.vt.middleware.password.RepeatCharacterRegexRule
import edu.vt.middleware.password.Rule
import edu.vt.middleware.password.UppercaseCharacterRule
import edu.vt.middleware.password.UsernameRule
import edu.vt.middleware.password.WhitespaceRule

/**
 * This service uses vt-password (edu.vt.middleware) to validate password strength.
 */
class PasswordValidatorService {

    static transactional = false

    def grailsApplication
    def messageSource

    /**
     * Setup password rules.
     *
     * @todo make this configurable (with a DSL?)
     * @param username optional username to validate password with
     * @return a list of password rules
     */
    private List<Rule> getPasswordRules(boolean username) {
        // group all rules together in a List
        def ruleList = []

        // password must be between 8 and 32 chars long
        ruleList << new LengthRule(8, 32)

        // don't allow whitespace
        //ruleList << new WhitespaceRule()

        // control allowed characters
        def charRule = new CharacterCharacteristicsRule()
        // require at least 1 digit in passwords
        charRule.getRules().add(new DigitCharacterRule(1))
        // require at least 1 non-alphanumeric char
        charRule.getRules().add(new NonAlphanumericCharacterRule(1))
        // require at least 1 upper case char
        charRule.getRules().add(new UppercaseCharacterRule(1))
        // require at least 1 lower case char
        charRule.getRules().add(new LowercaseCharacterRule(1))
        // require at least 3 of the previous rules be met
        charRule.setNumberOfCharacteristics(3)

        ruleList << charRule

        // don't allow alphabetical sequences
        ruleList << new AlphabeticalSequenceRule()

        // don't allow numerical sequences of length 3
        ruleList << new NumericalSequenceRule(3, true)

        // don't allow qwerty sequences
        ruleList << new QwertySequenceRule()

        // don't allow 4 repeat characters
        ruleList << new RepeatCharacterRegexRule(4)

        if (username) {
            // don't allow username, even backwards.
            ruleList << new UsernameRule(true, true)
        }

        ruleList
    }

    private PasswordData createPasswordData(String username, String password) {
        def data = new PasswordData(new Password(password))
        data.username = username
        data
    }

    /**
     * Return a message resolver that get messages from the standard Spring messageSource bean.
     *
     * @param locale optional Locale instance
     * @return the resolver
     */
    private MessageResolver getMessageResolver(Locale locale) {
        Properties props = new Properties()
        List keys = [
                'HISTORY_VIOLATION',
                'ILLEGAL_WORD',
                'ILLEGAL_WORD_REVERSED',
                'ILLEGAL_MATCH',
                'ILLEGAL_CHAR',
                'ILLEGAL_SEQUENCE',
                'ILLEGAL_USERNAME',
                'ILLEGAL_USERNAME_REVERSED',
                'ILLEGAL_WHITESPACE',
                'INSUFFICIENT_CHARACTERS',
                'INSUFFICIENT_CHARACTERISTICS',
                'SOURCE_VIOLATION',
                'TOO_LONG',
                'TOO_SHORT'
        ]

        // Get all messages for the specified Locale.
        for (String key in keys) {
            props.setProperty(key, messageSource.getMessage('passwordValidator.' + getMessageKey(key), null, key, locale))
        }

        new MessageResolver(props)
    }

    /**
     * Convert native uppercase message key format to camelcase.
     * Example: 'ILLEGAL_WORD_REVERSED' becomes 'illegalWordReversed'.
     * @param key upper case key to convert
     * @return same key but in camelcase format
     */
    private String getMessageKey(String key) {
        boolean uppercase
        StringBuilder s = new StringBuilder()
        key.each { c ->
            if (c == '_') {
                uppercase = true
            } else {
                if (uppercase) {
                    s << c.toUpperCase()
                    uppercase = false
                } else {
                    s << c.toLowerCase()
                }
            }
        }
        s
    }

    /**
     * Validate password.
     *
     * @param password password to validate
     * @param username if specified will validate that username is not in password
     * @param locale locale for message lookup
     * @return a list of validation errors or null if password is valid
     */
    List<String> validatePassword(String password, String username = null, Locale locale = null) {

        if (!grailsApplication.config.reset.password.validate) {
            log.warn "Password validation disabled, skipping validation for username [$username]"
            return null
        }

        if (locale == null) {
            locale = Locale.default
        }

        def resolver = getMessageResolver(locale)
        def validator = new PasswordValidator(resolver, getPasswordRules(username != null))
        def result = validator.validate(createPasswordData(username, password))

        return result.valid ? null : validator.getMessages(result)
    }
}
