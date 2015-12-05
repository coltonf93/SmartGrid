<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Smart Grid</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">

    <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
    <link rel="stylesheet" href="css/theme.css">
    <link rel="stylesheet" href="dist/css/skin-blue.min.css">
    <script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>
  </head>
  <body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper">
      <header class="main-header">
        <a href="index.jsp" class="logo">
          <span class="logo-mini"><b>S</b>G</span>
          <span class="logo-lg"><b>Smart</b>Grid</span>
        </a>
        <nav class="navbar navbar-static-top" role="navigation">
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
          </a>
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
            </ul>
          </div>
        </nav>
      </header>
      <aside class="main-sidebar">
        <section class="sidebar">
          <ul class="sidebar-menu">
            <li class="header">
          <%if(request.getParameter("testName")!=null){
            session.setAttribute( "testName",request.getParameter("testName"));%>
            <h2><%= session.getAttribute("testName")%></h2>
          <%} else if(session.getAttribute("testName")!=null){%>
            <h2><%= session.getAttribute("testName")%></h2>
          <%} %></li>
            <li><a href="index.jsp?page=home"><i class="fa ion-ios-speedometer"></i> <span>Dashboard</span></a></li>
            <li><a href="index.jsp?page=create"><i class="fa ion-plus"></i> <span>New</span></a></li>
            <li><a href="index.jsp?page=load"><i class="fa ion-ios-download"></i> <span>Load</span></a></li>
          </ul>
        </section>
      </aside>
      <div class="content-wrapper">
        <section class="content">
			<%if(request.getParameter("page")==null || request.getParameter("page").equals("home")){%>
		      <%@include file="Pages/home.jsp"%>
		    <%}else if(request.getParameter("page").equals("create")){%>
		      <%@include file="Pages/create.jsp"%>
		    <%}else if(request.getParameter("page").equals("load")){%>
		      <%@include file="Pages/load.jsp"%>
		    <%}else if(request.getParameter("page").equals("data")){%>
		      <%@include file="Pages/data.jsp"%>
		    <%}%>
        </section>
      </div>
    </div>
    <script src="dist/js/app.min.js"></script>
  </body>
</html>
