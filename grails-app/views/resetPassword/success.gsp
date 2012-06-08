<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title><g:message code="resetPassword.success.title"/></title>
</head>

<body>

<header class="page-header">
    <h1><g:message code="resetPassword.success.title"/></h1>
</header>

<g:render template="flash"/>

<p><g:message code="resetPassword.success.message"/></p>

<div class="form-actions">
    <a href="${url}" class="btn btn-primary">
        <i class="icon-ok icon-white"></i>
        <g:message code="resetPassword.button.login.label" default="Proceed to login"/>
    </a>
</div>

</body>

</html>