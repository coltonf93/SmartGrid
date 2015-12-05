<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Smart Grid</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
    <link rel="stylesheet" href="css/theme.css">
    <link rel="stylesheet" href="dist/css/skin-blue.min.css">
  </head>
  <body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper">
      <!-- Main Header -->
      <header class="main-header">
        <!-- Logo -->
        <a href="index.jsp" class="logo">
          <!-- mini logo for sidebar mini 50x50 pixels -->
          <span class="logo-mini"><b>S</b>G</span>
          <!-- logo for regular state and mobile devices -->
          <span class="logo-lg"><b>Smart</b>Grid</span>
        </a>

        <!-- Header Navbar -->
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
            <li><a href="index.jsp?page=load"><i class="fa fa-link"></i> <span>Load</span></a></li>
          </ul>
        </section>
      </aside>

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->

        <!-- Main content -->
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
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
    </div><!-- ./wrapper -->

    <!-- REQUIRED JS SCRIPTS -->

    <!-- jQuery 2.1.4 -->
    <script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <!-- Bootstrap 3.3.5 -->
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <!-- AdminLTE App -->
    <script src="dist/js/app.min.js"></script>

    <!-- Optionally, you can add Slimscroll and FastClick plugins.
         Both of these plugins are recommended to enhance the
         user experience. Slimscroll is required when using the
         fixed layout. -->
  </body>
</html>
