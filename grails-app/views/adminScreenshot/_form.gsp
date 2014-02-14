<%@ page import="org.openmrs.modulus.Screenshot" %>



<div class="fieldcontain ${hasErrors(bean: screenshotInstance, field: 'rawFile', 'error')} required">
	<label for="rawFile">
		<g:message code="screenshot.rawFile.label" default="Raw File" />
		<span class="required-indicator">*</span>
	</label>
	<input type="file" id="rawFile" name="rawFile" />
</div>

<div class="fieldcontain ${hasErrors(bean: screenshotInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="screenshot.description.label" default="Description" />
		
	</label>
	<g:textField name="description" maxlength="140" value="${screenshotInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: screenshotInstance, field: 'uploadedBy', 'error')} required">
	<label for="uploadedBy">
		<g:message code="screenshot.uploadedBy.label" default="Uploaded By" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="uploadedBy" name="uploadedBy.id" from="${org.openmrs.modulus.User.list()}" optionKey="id" required="" value="${screenshotInstance?.uploadedBy?.id}" class="many-to-one"/>
</div>

