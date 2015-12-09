<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.FileReader"%>
<script src="js/testLoad.js"></script>
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.10/css/dataTables.bootstrap.min.css">
<link rel="stylesheet"
	href="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css">
<script
	src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.min.js"></script>
<section class="content-header">
	<h1>
		<%
			if (session.getAttribute("testName") == null) {
				response.sendRedirect("index.jsp?page=load&loadLast=true");
		%>
		<%
			} else if (session.getAttribute("testName") != null) {
		%>
		<script>
		<%
		/*This is to override the browser security setting for refrencing local files*/
			String jsonString="";
			BufferedReader reader = new BufferedReader(new FileReader("C:/tests/"+session.getAttribute("testName")+".json"));
			String line;
			while ((line = reader.readLine()) != null) {
				jsonString+=line;
			}
			out.print("loadJson('"+jsonString+"');");
		%>
		</script>
		<%=session.getAttribute("testName")%>
		&nbsp;
		<%
			}
		%>
		Dashboard <small>Data and Analytics</small></h1>
	
</section>

<section class="content">
	<div class="row">
		<div class="col-md-3 col-sm-6 col-xs-12">
			<div class="info-box">
				<span class="info-box-icon bg-aqua"><i
					class="ion ion-battery-charging"></i></span>
				<div class="info-box-content">
					<span class="info-box-number"><h2>
							<span id='sCount'></span> Storage
						</h2></span>
				</div>
			</div>
		</div>
		<!-- /.col -->
		<div class="col-md-3 col-sm-6 col-xs-12">
			<div class="info-box">
				<span class="info-box-icon bg-red"><i class="fa ion-home"></i></span>
				<div class="info-box-content">
					<span class="info-box-number"><h2>
							<span id='cCount'></span> Consumers
						</h2></span>
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
					<span class="info-box-number"><h2>
							<span id='gCount'></span> Generators
						</h2></span>
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
					<span class="info-box-number"><h2>
							<span id='dCount'></span> Days
						</h2></span>
				</div>
			</div>
		</div>
	</div>
	<div class="box box-default color-palette-box">
		<div class="box-header with-border">
			<h3 class="box-title">
				<i class="ion ion-link"></i> Smart Grid Connections
			</h3>
		</div>
		<div class="box-body">
			<div id="cy"></div>
		</div>
	</div>
	<div class="row">
		<div class='col-md-7'>
			<div class="box box-default color-palette-box">
				<div class="box-header with-border">
					<h3 class="box-title">
						<i class="ion ion-cash"></i> Average Price by Hour
					</h3>
				</div>
				<ul class="pagination dayavg">
					<li class='active'><a  onclick='updateAvgHourPrice(0)'>1</a></li>
					<li><a onclick='updateAvgHourPrice(1)'>2</a></li>
					<li><a onclick='updateAvgHourPrice(2)'>3</a></li>
					<li><a onclick='updateAvgHourPrice(3)'>4</a></li>
					<li><a onclick='updateAvgHourPrice(4)'>5</a></li>
					<li><a onclick='updateAvgHourPrice(5)'>6</a></li>
					<li><a onclick='updateAvgHourPrice(6)'>7</a></li>
					<li><a onclick='updateAvgHourPrice(7)'>8</a></li>
					<li><a onclick='updateAvgHourPrice(8)'>9</a></li>
					<li><a onclick='updateAvgHourPrice(9)'>10</a></li>
					<li><a onclick='updateAvgHourPrice(10)'>11</a></li>
					<li><a onclick='updateAvgHourPrice(11)'>12</a></li>
					<li><a onclick='updateAvgHourPrice(12)'>13</a></li>
					<li><a onclick='updateAvgHourPrice(13)'>14</a></li>
					<li><a onclick='updateAvgHourPrice(14)'>15</a></li>
					<li><a onclick='updateAvgHourPrice(15)'>16</a></li>
					<li><a onclick='updateAvgHourPrice(16)'>17</a></li>
					<li><a onclick='updateAvgHourPrice(17)'>18</a></li>
					<li><a onclick='updateAvgHourPrice(18)'>19</a></li>
					<li><a onclick='updateAvgHourPrice(19)'>20</a></li>
					<li><a onclick='updateAvgHourPrice(20)'>21</a></li>
					<li><a onclick='updateAvgHourPrice(21)'>22</a></li>
					<li><a onclick='updateAvgHourPrice(22)'>23</a></li>
					<li><a onclick='updateAvgHourPrice(23)'>24</a></li>
				</ul>
				<div id="avgHourPrice" style="height: 250px;"></div>
			</div>
		</div>
		<div class='col-md-5'>
			<div class="box box-default color-palette-box">
				<div class="box-header with-border">
					<h3 class="box-title">
						<i class="ion ion-cash"></i> Average Price by Day
					</h3>
				</div>
				<div id="avgBidPrices" style="height: 250px;"></div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-7">
			<div class="box box-default color-palette-box">
				<div class="box-header with-border">
					<h3 class="box-title">
						<i class="ion ion-ios-eye"></i> Agent Details
					</h3>
				</div>
				<table id="agentData" class="table table-striped table-bordered"
					cellspacing="0" width="100%">
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

		</div>

	</div>

	<script
		src="http://cytoscape.github.io/cytoscape.js/api/cytoscape.js-latest/cytoscape.min.js"></script>
	<script
		src="js/gridBuilder.js?version=<%out.println((int) (Math.random() * 99999));%>"></script>
	<script
		src="https://cdn.datatables.net/1.10.10/js/jquery.dataTables.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.10.10/js/dataTables.bootstrap.min.js"></script>
	<script>
	$(document).ready(function() {
	    $('#agentData').DataTable();
	} );
	</script>
	</div>
</section>