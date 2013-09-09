<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.config.reset.password.layout ?: 'main'}"/>
    <title><g:message code="resetPassword.step2.title"/></title>
</head>

<body>

<header class="page-header">
    <h1><g:message code="resetPassword.step2.title"/></h1>
</header>

<g:render template="flash"/>

<g:form action="step2">

    <g:each in="${questions}" var="q" status="i">
        <div class="control-group">
            <label class="control-label">
                ${message(code: q, default: q)}
            </label>
            <div class="controls">
                <input type="text" name="${q}" value="${answers[q]?.encodeAsHTML()}" ${i ? '' : 'autofocus=""'}
                             placeholder="${message(code: q + '.placeholder', default:'')}"/>
            </div>
        </div>
    </g:each>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">
            <i class="icon-ok icon-white"></i>
            <g:message code="resetPassword.button.next.label" default="Next"/>
        </button>
    </div>

</g:form>

</body>

</html>