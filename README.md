#Grails Reset Password Plugin

This plugin allow users to change/reset their password after
answering a series of personal questions.

The plugin is independent of security implementation used by the hosting
Grails application. It uses a delegate class to perform the actual password
change. However the plugin takes care of verifying that the user really is
the right user.

The plugin includes an extendable set of questions that users can choose from.

A security code can be sent via email as part of the verification workflow.
