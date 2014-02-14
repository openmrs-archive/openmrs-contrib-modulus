
<%@ page import="org.openmrs.modulus.Screenshot" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'screenshot.label', default: 'Screenshot')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-screenshot" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-screenshot" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list screenshot">
			
				<g:if test="${screenshotInstance?.rawFile}">
				<li class="fieldcontain">
					<span id="rawFile-label" class="property-label"><g:message code="screenshot.rawFile.label" default="Raw File" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${screenshotInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="screenshot.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${screenshotInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${screenshotInstance?.uploadedBy}">
				<li class="fieldcontain">
					<span id="uploadedBy-label" class="property-label"><g:message code="screenshot.uploadedBy.label" default="Uploaded By" /></span>
					
						<span class="property-value" aria-labelledby="uploadedBy-label"><g:link controller="adminUser" action="show" id="${screenshotInstance?.uploadedBy?.id}">${screenshotInstance?.uploadedBy?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:screenshotInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${screenshotInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
