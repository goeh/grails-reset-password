<%@ page contentType="text/html;charset=UTF-8" defaultCodec="html" %>

<g:if test="${flash.message}">
    <div class="alert-info">
        ${flash.message}
    </div>
</g:if>
<g:if test="${flash.success}">
    <div class="alert-success">
        ${flash.success}
    </div>
</g:if>
<g:if test="${flash.warning}">
    <div class="alert-warning">
        ${flash.warning}
    </div>
</g:if>
<g:if test="${flash.error}">
    <div class="alert-error">
        ${flash.error}
    </div>
</g:if>