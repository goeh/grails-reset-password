package grails.plugins.resetpassword

/**
 * Tests for PasswordValidatorService
 */
class PasswordValidatorServiceSpec {

    def passwordValidatorService

    def "test password validation"() {
        expect:
        // These passwords should not validate
        !passwordValidatorService.validatePassword("hello", "hello")
        !passwordValidatorService.validatePassword("hello world", "hello")
        !passwordValidatorService.validatePassword("Hello 1 World!", "hello")
        !passwordValidatorService.validatePassword("Hello 1111 World!", "hello")

        // These passwords are ok
        passwordValidatorService.validatePassword("Hello 1 World!", "bart")
    }
}
