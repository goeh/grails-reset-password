<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.reset.password.layout ?: 'main'}"/>
    <title><g:message code="resetPassword.step3.title"/></title>
</head>

<body>

<header class="page-header">
    <h1><g:message code="resetPassword.step3.title"/></h1>
</header>

<g:render template="flash"/>

<g:hasErrors bean="${cmd}">
    <div class="alert alert-error">
        <ul>
            <g:eachError bean="${cmd}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                        error="${error}"/></li>
            </g:eachError>
        </ul>
    </div>
</g:hasErrors>

<g:form action="step3">

    <div class="control-group">
        <label class="control-label">
            <g:message code="resetPassword.password1.label" default="Password"/>
        </label>

        <div class="controls">
            <g:passwordField name="password1" autofocus=""
                             placeholder="${message(code:'resetPassword.password1.placeholder', default:'')}"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">
            <g:message code="resetPassword.password2.label" default="Password"/>
        </label>

        <div class="controls">
            <g:passwordField name="password2"
                             placeholder="${message(code:'resetPassword.password2.placeholder', default:'')}"/>
        </div>
    </div>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">
            <i class="icon-ok icon-white"></i>
            <g:message code="resetPassword.button.next.label" default="Next"/>
        </button>
    </div>

</g:form>

</body>

</html>