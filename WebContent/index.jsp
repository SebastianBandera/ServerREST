<%@ taglib prefix = "tag" uri = "WEB-INF/generic.tld"%>

<!doctype html>

<html>
<head>
<title>Servidor REST</title>
<link rel="stylesheet" href="./css/style.css">
</head>
<body>
	<h1 align="center">Servidor REST</h1>
	<hr>
	<p class="subTitle">Servicios GET</p>
	<tag:ListServices type = "GET"/>
	
	<br>
	<hr>
	<br>
	
	<p class="subTitle">Servicios POST</p>
	<tag:ListServices type = "POST"/>
	
	
</body>
</html>
