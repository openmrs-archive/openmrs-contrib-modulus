
<%@ page import="org.openmrs.modulus.Screenshot" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'screenshot.label', default: 'Screenshot')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-screenshot" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-screenshot" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>

                        <g:sortableColumn property="id" title="${message(code: 'screenshot.id.label', default: 'ID')}" />

                        <g:sortableColumn property="description" title="${message(code: 'screenshot.description.label', default: 'Description')}" />
					
						<th><g:message code="screenshot.uploadedBy.label" default="Uploaded By" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${screenshotInstanceList}" status="i" var="screenshotInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                        <td><g:link action="show" id="${screenshotInstance.id}">${fieldValue(bean: screenshotInstance, field: "id")}</g:link></td>
					
						<td>${fieldValue(bean: screenshotInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: screenshotInstance, field: "uploadedBy")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${screenshotInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
