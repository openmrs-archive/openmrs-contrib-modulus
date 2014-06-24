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

    <title>Modulus</title>

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'app.css')}">

</head>
<body>

<div class="container">

    <header class="row">

        <h1>Modulus.</h1>

    </header>

    <section>
        <p class="lead">
            <a href="https://github.com/openmrs/openmrs-contrib-modulus">
                https://github.com/openmrs/openmrs-contrib-modulus
            </a>
        </p>
        <p class="lead">
            <a href="https://modules.openmrs.org/">
                https://modules.openmrs.org/
            </a>
        </p>
    </section>

    <section>
        <p><strong>Connection Info:</strong></p>
        <p>
            <oauth:connect provider="openmrsid" id="openmrsid-connect-link">openmrsid</oauth:connect>
            Logged in with openmrsid?
            <s2o:ifLoggedInWith provider="openmrsid">yes</s2o:ifLoggedInWith>
            <s2o:ifNotLoggedInWith provider="openmrsid">no</s2o:ifNotLoggedInWith>
        </p>
    </section>

    <section>
        <sec:ifLoggedIn>
            Hello, <sec:loggedInUserInfo field="username"/>.
        </sec:ifLoggedIn>
    </section>

</div>

</body>
</html>
