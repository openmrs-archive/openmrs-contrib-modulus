<!DOCTYPE html>
<html>
	<head>
		<title><g:if env="development">Grails Runtime Exception</g:if><g:else>Server Error</g:else></title>
		<meta name="layout" content="main">
		<g:if env="development"><link rel="stylesheet" href="${resource(dir: 'css', file: 'errors.css')}" type="text/css"></g:if>
	</head>
	<body>
		<g:if env="development">
			<g:renderException exception="${exception}" />
		</g:if>
		<g:else>
			<ul class="errors">
				<li>An error has occurred</li>
			</ul>
		</g:else>
	</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: herooftime
  Date: 3/11/14
  Time: 12:56 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Modulus Runtime Exception</title>

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'app.css')}">

</head>
<body>

<div class="container">

    <header class="row">

        <h1>An internal error has occurred.</h1>

    </header>

    <section>
        <g:renderException exception="${exception}" />

        <p class="lead">For additional assistance, please contact <a
                href="mailto:help@openmrs.org">help@openmrs.org</a>.</p>
    </section>

</div>

</body>
</html>

