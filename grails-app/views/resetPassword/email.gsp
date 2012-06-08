<%@ page contentType="text/html;charset=UTF-8" %>
<g:applyLayout name="${layout}">
<html>
<head>
    <title><g:message code="security.question.email.title" default="Reset Password Security Code"/></title>
</head>

<body>
<h1><g:message code="security.question.email.title" default="Reset Password Security Code"/></h1>

<p>
    <g:message code="security.question.email.body"
               default="A request to reset your password has been initiated. You need the following code to process the request:"/>
</p>

<h2>${code.encodeAsHTML()}</h2>

</body>
</html>
</g:applyLayout>