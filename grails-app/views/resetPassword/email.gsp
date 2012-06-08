<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="email"/>
    <title><g:message code="security.question.email.title" default="Reset Password Security Code"/></title>
</head>

<body>
<h1><g:message code="security.question.email.title" default="Reset Password Security Code"/></h1>

<p>
    <g:message code="security.question.email.body"
               default="A request to reset your password has been initiated. You need the following code to process the request:"/>
</p>

<p><strong>${code.encodeAsHTML()}</strong></p>

</body>
</html>