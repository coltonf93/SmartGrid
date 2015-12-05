
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="css/theme.css" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<meta charset=utf-8 />
<meta name="viewport"
	content="user-scalable=no, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, minimal-ui">
</head>
<body>

	<div id="container">
		<div id="sideMenu">
			<div id="sideMenuFixed"></div>
			<div id="sidecontent">
				
					<h1>SMART GRID</h1>
					<%if(request.getParameter("testName")!=null){
						session.setAttribute( "testName",request.getParameter("testName"));%>
						<h1><%= session.getAttribute("testName")%></h1>
					<%} else if(session.getAttribute("testName")!=null){%>
						<h1><%= session.getAttribute("testName")%></h1>
					<%} %>
					
				<div class="sideCount">
					<div class="dayCount">
						<span id="dCount"></span> Days
					</div>
				</div>
				<div class="sideCount">
					<div class="timeCount">
						<span id="tCount"></span>:00 Hours
					</div>
				</div>
				<ul class="menu">
					<li>Options</li>
					<li><a href="index.jsp?page=create">New</a></li>
					<li><a href="index.jsp?page=load">Load</a></li>
					<li><a href="index.jsp?page=singleTest">Single</a></li>
					<li><a href="index.jsp?page=data">Global</a></li>
				</ul>
			</div>
		</div>
		<div id="content">
		<%if(request.getParameter("page")==null || request.getParameter("page").equals("singleTest")){%>
			<%@include file="Pages/singleTest.jsp"%>
		<%}else if(request.getParameter("page").equals("create")){%>
			<%@include file="Pages/create.jsp"%>
		<%}else if(request.getParameter("page").equals("load")){%>
			<%@include file="Pages/load.jsp"%>
		<%}else if(request.getParameter("page").equals("data")){%>
			<%@include file="Pages/data.jsp"%>
		<%}%>
		</div>
	</div>
	</div>
</body>
</html>
