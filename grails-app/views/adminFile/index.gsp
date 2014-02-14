
<%@ page import="org.openmrs.modulus.File" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'file.label', default: 'File')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-file" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-file" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="id" title="${message(code: 'file.id.label', default: 'ID')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'file.name.label', default: 'Name')}" />
					
						<th><g:message code="file.uploadedBy.label" default="Uploaded By" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${fileInstanceList}" status="i" var="fileInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${fileInstance.id}">${fieldValue(bean: fileInstance, field: "id")}</g:link></td>
					
						<td>${fieldValue(bean: fileInstance, field: "filename")}</td>
					
						<td>${fieldValue(bean: fileInstance, field: "uploadedBy")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${fileInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
