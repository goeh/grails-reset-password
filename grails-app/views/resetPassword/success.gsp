<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.reset.password.layout ?: 'main'}"/>
    <title><g:message code="resetPassword.success.title"/></title>
</head>

<body>

<header class="page-header">
    <h1><g:message code="resetPassword.success.title"/></h1>
</header>

<g:render template="flash"/>

<p><g:message code="resetPassword.success.message"/></p>

</body>

</html>