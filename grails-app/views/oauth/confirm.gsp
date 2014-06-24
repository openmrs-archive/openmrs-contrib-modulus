<%@ page import="org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException" %>
<head>
<meta name='layout' content='main' />
<title>Login</title>
<style type='text/css' media='screen'>
#login {
	margin:15px 0px; padding:0px;
	text-align:center;
}
#login .inner {
	width:260px;
	margin:0px auto;
	text-align:left;
	padding:10px;
	border-top:1px dashed #499ede;
	border-bottom:1px dashed #499ede;
	background-color:#EEF;
}
#login .inner .fheader {
	padding:4px;margin:3px 0px 3px 0;color:#2e3741;font-size:14px;font-weight:bold;
}
#login .inner .cssform p {
	clear: left;
	margin: 0;
	padding: 5px 0 8px 0;
	padding-left: 105px;
	border-top: 1px dashed gray;
	margin-bottom: 10px;
	height: 1%;
}
#login .inner .cssform input[type='text'] {
	width: 120px;
}
#login .inner .cssform label {
	font-weight: bold;
	float: left;
	margin-left: -105px;
	width: 100px;
}
#login .inner .login_message {color:red;}
#login .inner .text_ {width:120px;}
#login .inner .chk {height:12px;}
</style>
</head>

<body>

    <g:if test="${lastException && !(lastException instanceof UnapprovedClientAuthenticationException)}">
        <header class="row">
            <h1>Access could not be granted.</h1>

        </header>

        <section>
            <p>${lastException?.message}</p>

            <p class="lead">For additional assistance, please contact <a
                    href="mailto:helpdesk@openmrs.org">helpdesk@openmrs.org</a>.</p>
        </section>
    </g:if>

    <g:else>
        <g:if test='${flash.message}'>
        <div class='alert alert-info'>${flash.message}</div>
        </g:if>

        <header class="row">
            <h1>Please Confirm.</h1>
        </header>

        <section>
            <p>
                <b>${applicationContext.getBean('clientDetailsService')?.loadClientByClientId(params.client_id)?.additionalInformation?.name ?: applicationContext.getBean('clientDetailsService')?.loadClientByClientId(params.client_id)?.clientId ?: 'n/a'}</b>
                is requesting access to your data stored within Modulus.
            </p>
            <p>
                Do you authorize this?
            </p>
        </section>

        <section>

            <form method='POST' id='confirmationForm' class='cssform'>
                <p>
                    <input name='user_oauth_approval' type='hidden' value='true' />
                    <button type="submit" class="btn btn-primary btn-lg">Authorize and Continue</button>
                </p>
            </form>

            <form method='POST' id='denialForm' class='cssform'>
                <p>
                    <input name='user_oauth_approval' type='hidden' value='false' />
                    <button type="submit" class="btn btn-default btn-lg">Deny</button>
                </p>
            </form>

        </section>

    </g:else>
</body>
