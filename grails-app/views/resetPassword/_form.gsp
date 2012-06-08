<%@ page contentType="text/html;charset=UTF-8" defaultCodec="html" %>

<g:set var="focus" value="${true}"/>

<g:if test="${fields.contains('username')}">
    <div class="control-group">
        <label class="control-label">
            <g:message code="resetPassword.username.label" default="Username"/>
        </label>

        <div class="controls">
            <input type="text" name="username" ${focus ? 'autofocus=""' : ''}
                         placeholder="${message(code:'resetPassword.username.placeholder', default:'')}"/>
        </div>
    </div>
    <g:set var="focus" value="${false}"/>
</g:if>

<g:if test="${fields.contains('email')}">
    <div class="control-group">
        <label class="control-label">
            <g:message code="resetPassword.email.label" default="Email"/>
        </label>

        <div class="controls">
            <input type="text" name="email" value="${email}" ${focus ? 'autofocus=""' : ''}
                         placeholder="${message(code:'resetPassword.email.placeholder', default:'')}"/>
        </div>
    </div>
    <g:set var="focus" value="${false}"/>
</g:if>

<g:if test="${fields.contains('postalCode')}">
    <div class="control-group">
        <label class="control-label">
            <g:message code="resetPassword.postalCode.label" default="Postal Code"/>
        </label>

        <div class="controls">
            <g:textField name="postalCode" value="${postalCode}" class="span2"
                         placeholder="${message(code:'resetPassword.postalCode.placeholder', default:'')}"/>
        </div>
    </div>
</g:if>

<g:if test="${fields.contains('birthDate')}">
    <div class="control-group">
        <label class="control-label">
            <g:message code="resetPassword.birthDate.label" default="Date of birth"/>
        </label>

        <div class="controls">
            <g:textField name="birthYear" value="${birthYear}"
                         placeholder="${message(code:'resetPassword.birthYear.placeholder', default:'YYYY')}"
                         class="span1"/>
            <g:textField name="birthMonth" value="${birthMonth}"
                         placeholder="${message(code:'resetPassword.birthMonth.placeholder', default:'MM')}"
                         class="span1"/>
            <g:textField name="birthDay" value="${birthDay}"
                         placeholder="${message(code:'resetPassword.birthDay.placeholder', default:'DD')}"
                         class="span1"/>
        </div>
    </div>
</g:if>