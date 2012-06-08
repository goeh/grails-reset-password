<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title><g:message code="resetPassword.step1.title"/></title>
</head>

<body>

<header class="page-header">
    <h1><g:message code="resetPassword.step1.title"/></h1>
</header>

<g:render template="flash"/>

<g:form action="step1">

    <g:render template="form"/>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">
            <i class="icon-ok icon-white"></i>
            <g:message code="resetPassword.button.next.label" default="Next"/>
        </button>
    </div>

</g:form>

</body>

</html>