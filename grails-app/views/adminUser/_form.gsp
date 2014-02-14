<%@ page import="org.openmrs.modulus.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
	<label for="username">
		<g:message code="user.username.label" default="Username" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="username" required="" value="${userInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'fullname', 'error')} ">
	<label for="fullname">
		<g:message code="user.fullname.label" default="Fullname" />
		
	</label>
	<g:textField name="fullname" value="${userInstance?.fullname}"/>
</div>

