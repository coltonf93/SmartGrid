<script src="http://cytoscape.github.io/cytoscape.js/api/cytoscape.js-latest/cytoscape.min.js"></script>
	<script src="js/gridBuilder.js?version=<%out.println((int)(Math.random()*99999));%>"></script>
<section class="content-header">
	<h1>
		<%if(request.getParameter("testName")!=null){
            session.setAttribute( "testName",request.getParameter("testName"));%>
		<%= session.getAttribute("testName")%>
		<%} else if(session.getAttribute("testName")!=null){%>
		<%= session.getAttribute("testName")%>
		<%} else{%>
		Dashboard
		<%} %>
		<small>Data and Analytics</small>
	</h1>
</section>

<section class="content">
<div class="row">
	<div class="col-md-3 col-sm-6 col-xs-12">
		<div class="info-box">
			<span class="info-box-icon bg-aqua"><i
				class="ion ion-battery-charging"></i></span>
			<div class="info-box-content">
				<span class="info-box-number"><h2>99 Storage</h2></span>
			</div>
		</div>
	</div>
	<!-- /.col -->
	<div class="col-md-3 col-sm-6 col-xs-12">
		<div class="info-box">
			<span class="info-box-icon bg-red"><i
				class="fa ion-home"></i></span>
			<div class="info-box-content">
				<span class="info-box-number"><h2>99 Consumers</h2></span>
			</div>
		</div>
		<!-- /.info-box -->
	</div>
	<!-- /.col -->

	<!-- fix for small devices only -->
	<div class="clearfix visible-sm-block"></div>

	<div class="col-md-3 col-sm-6 col-xs-12">
		<div class="info-box">
			<span class="info-box-icon bg-green"><i
				class="ion ion-ios-bolt"></i></span>
			<div class="info-box-content">
				<span class="info-box-number"><h2>99 Generators</h2></span>
			</div>
			<!-- /.info-box-content -->
		</div>
		<!-- /.info-box -->
	</div>
	<!-- /.col -->
	<div class="col-md-3 col-sm-6 col-xs-12">
		<div class="info-box">
			<span class="info-box-icon bg-yellow"><i
				class="ion ion-android-calendar"></i></span>
			<div class="info-box-content">
				<span class="info-box-number"><h2>500 Days</h2></span>
			</div>
		</div>
	</div>
</div>
<div class="box box-default color-palette-box">
  <div class="box-header with-border">
              <h3 class="box-title"><i class="ion ion-link"></i>Smart Grid Connections</h3>
            </div>
      <div class="box-body">
        <div id="cy"></div>
      </div>
  </div>
<div class="mainChart">
	<pre class="darkfill">

</pre>
	<table id="agentData">
		<thead>
			<tr>
				<th>Agent</th>
				<th>Buy Price</th>
				<th>Sell Price</th>
				<th>Daily Net Profit</th>
				<th>Total Net Profit</th>
			</tr>
		</thead>
		<tbody>

		</tbody>
	</table>
</div>
</section>