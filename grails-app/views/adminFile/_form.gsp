<%@ page import="org.openmrs.modulus.File" %>



<div class="fieldcontain ${hasErrors(bean: fileInstance, field: 'rawFile', 'error')} required">
	<label for="rawFile">
		<g:message code="file.rawFile.label" default="Raw File" />
		<span class="required-indicator">*</span>
	</label>
	<input type="file" id="rawFile" name="rawFile" />
</div>

<div class="fieldcontain ${hasErrors(bean: fileInstance, field: 'uploadedBy', 'error')} required">
	<label for="uploadedBy">
		<g:message code="file.uploadedBy.label" default="Uploaded By" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="uploadedBy" name="uploadedBy.id" from="${org.openmrs.modulus.User.list()}" optionKey="id" required="" value="${fileInstance?.uploadedBy?.id}" class="many-to-one"/>
</div>

