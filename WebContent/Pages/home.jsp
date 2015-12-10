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
						<i class="ion ion-cash"></i> Average Prices
					</h3>
				</div>
				<ul class="pagination avgPrice">
					<li><a  onclick='updateAvgPrice(0)'>1</a></li>
					<li><a onclick='updateAvgPrice(1)'>2</a></li>
					<li><a onclick='updateAvgPrice(2)'>3</a></li>
					<li><a onclick='updateAvgPrice(3)'>4</a></li>
					<li><a onclick='updateAvgPrice(4)'>5</a></li>
					<li><a onclick='updateAvgPrice(5)'>6</a></li>
					<li><a onclick='updateAvgPrice(6)'>7</a></li>
					<li><a onclick='updateAvgPrice(7)'>8</a></li>
					<li><a onclick='updateAvgPrice(8)'>9</a></li>
					<li><a onclick='updateAvgPrice(9)'>10</a></li>
					<li><a onclick='updateAvgPrice(10)'>11</a></li>
					<li><a onclick='updateAvgPrice(11)'>12</a></li>
					<li><a onclick='updateAvgPrice(12)'>13</a></li>
					<li><a onclick='updateAvgPrice(13)'>14</a></li>
					<li><a onclick='updateAvgPrice(14)'>15</a></li>
					<li><a onclick='updateAvgPrice(15)'>16</a></li>
					<li><a onclick='updateAvgPrice(16)'>17</a></li>
					<li><a onclick='updateAvgPrice(17)'>18</a></li>
					<li><a onclick='updateAvgPrice(18)'>19</a></li>
					<li><a onclick='updateAvgPrice(19)'>20</a></li>
					<li><a onclick='updateAvgPrice(20)'>21</a></li>
					<li><a onclick='updateAvgPrice(21)'>22</a></li>
					<li><a onclick='updateAvgPrice(22)'>23</a></li>
					<li><a onclick='updateAvgPrice(23)'>24</a></li>
					<li class='active'><a onclick='updateAvgPrice(24)'>Day</a></li>
				</ul>
				<div id="avgPrice" style="height: 250px;"></div>
			</div>
		</div>
		
		<div class="col-md-5">
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
							<th>Total Profit</th>
							<th>Total Expense</th>
							<th>Daily Net</th>
							<th>Total Net </th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</div>

		</div>
		
		
		
		<div class='col-md-7'>
			<div class="box box-default color-palette-box">
				<div class="box-header with-border">
					<h3 class="box-title">
						<i class="ion ion-pricetags"></i> Average Bid
					</h3>
				</div>
				<ul class="pagination avgBid">
					<li><a  onclick='updateAvgBid(0)'>1</a></li>
					<li><a onclick='updateAvgBid(1)'>2</a></li>
					<li><a onclick='updateAvgBid(2)'>3</a></li>
					<li><a onclick='updateAvgBid(3)'>4</a></li>
					<li><a onclick='updateAvgBid(4)'>5</a></li>
					<li><a onclick='updateAvgBid(5)'>6</a></li>
					<li><a onclick='updateAvgBid(6)'>7</a></li>
					<li><a onclick='updateAvgBid(7)'>8</a></li>
					<li><a onclick='updateAvgBid(8)'>9</a></li>
					<li><a onclick='updateAvgBid(9)'>10</a></li>
					<li><a onclick='updateAvgBid(10)'>11</a></li>
					<li><a onclick='updateAvgBid(11)'>12</a></li>
					<li><a onclick='updateAvgBid(12)'>13</a></li>
					<li><a onclick='updateAvgBid(13)'>14</a></li>
					<li><a onclick='updateAvgBid(14)'>15</a></li>
					<li><a onclick='updateAvgBid(15)'>16</a></li>
					<li><a onclick='updateAvgBid(16)'>17</a></li>
					<li><a onclick='updateAvgBid(17)'>18</a></li>
					<li><a onclick='updateAvgBid(18)'>19</a></li>
					<li><a onclick='updateAvgBid(19)'>20</a></li>
					<li><a onclick='updateAvgBid(20)'>21</a></li>
					<li><a onclick='updateAvgBid(21)'>22</a></li>
					<li><a onclick='updateAvgBid(22)'>23</a></li>
					<li><a onclick='updateAvgBid(23)'>24</a></li>
					<li class='active'><a onclick='updateAvgBid(24)'>Day</a></li>
				</ul>
				<div id="avgBid" style="height: 250px;"></div>
			</div>
		</div>
	</div>
	<div class="row">
		
	</div>
<div id="singleAgent" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Agent Details</h4>
      </div>
      <div class="modal-body">
      	<div id="capacity"></div>
      	<h2 class="singlecgenlabel"></h2>
        <div id="consGen" style="height: 250px;"></div>
       	<h2 class="singlepricelabel"></h2>
        <div id="priceDay" style="height: 250px;"></div>
        <h2 class="singlebidlabel"></h2>
        <div id="bidDay" style="height: 250px;"></div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

	<script
		src="http://cytoscape.github.io/cytoscape.js/api/cytoscape.js-latest/cytoscape.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.10.10/js/jquery.dataTables.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.10.10/js/dataTables.bootstrap.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
	</div>
</section>