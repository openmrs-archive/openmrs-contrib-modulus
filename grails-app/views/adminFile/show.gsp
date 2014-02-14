
<%@ page import="org.openmrs.modulus.File" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'file.label', default: 'File')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-file" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-file" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list file">

                <li class="fieldcontain">
                    <span id="id-label" class="property-label"><g:message code="file.id.label" default="ID" /></span>

                        <span class="property-value" aria-labelledby="id-label"><g:fieldValue field="id" bean="${fileInstance}"/></span>
                </li>

				<g:if test="${fileInstance?.rawFile}">
				<li class="fieldcontain">
					<span id="rawFile-label" class="property-label"><g:message code="file.rawFile.label" default="Raw File" /></span>

                        <span class="property-value" aria-labelledby="rawFile-label"><g:link controller="adminFile" action="download" id="${fileInstance.id}"><g:message code="file.download.label" default="Download" /></g:link></span>
				</li>
				</g:if>
			
				<g:if test="${fileInstance?.uploadedBy}">
				<li class="fieldcontain">
					<span id="uploadedBy-label" class="property-label"><g:message code="file.uploadedBy.label" default="Uploaded By" /></span>
					
						<span class="property-value" aria-labelledby="uploadedBy-label"><g:link controller="adminUser" action="show" id="${fileInstance?.uploadedBy?.id}">${fileInstance?.uploadedBy?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:fileInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${fileInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
