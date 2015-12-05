
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="css/theme.css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script
	src="http://cytoscape.github.io/cytoscape.js/api/cytoscape.js-latest/cytoscape.min.js"></script>
<script
	src="js/gridBuilder.js?version=<%out.println((int)(Math.random()*99999));%>"></script>
<meta charset=utf-8 />
<meta name="viewport"
	content="user-scalable=no, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, minimal-ui">
</head>
<body>

	<div id="container">
		<div id="sideMenu">
			<div id="sideMenuFixed"></div>
			<div id="sidecontent">
				<h1>
					SMART GRID</br>
					<%= request.getParameter("first_name")%></h1>
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
					<li class="play"><a href="#"><img src="Images/play.png">Play</a></li>
					<li class="pause"><a href="#"><img src="Images/pause.png" />Pause</a></li>
					<li><a href="#"><img src="Images/skipHour.png" />Next Hour</a></li>
					<li><a href="#"><img src="Images/skipDay.png" />Next Day</a></li>
					<li><a href="#"><img src="Images/refresh.png" />Reset</a></li>
					<li><img src="Images/skip.png" />Skip to
						<form class="skipTo">
							<input type="number" min=0 max=9999 name="day" placeholder="Day" />
							<input type="number" min=0 max=23 name="hour" placeholder="Hour" /><br />
							<input type="submit" value="Skip" align="center" />
						</form></li>
				</ul>
			</div>
		</div>
		<div id="content">
			<div class="mainChart">
				<div id="cy"></div>
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

		</div>
	</div>
	</div>
</body>
</html>
