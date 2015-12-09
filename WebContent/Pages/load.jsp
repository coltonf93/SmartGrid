<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.FileReader"%>


<section class="content-header">
	<h1>
		Load Test <small>Select a test to view it's isolated metrics</small>
	</h1>
</section>
<div class='row'>
	<div class='col-md-6 col-md-offset-3'>
		<div class='info-box'>
			<div class='info-box-content'>
				<table class='table '>
					<thead>
						<th>Name</th>
						<th>Description</th>
						<th>Consumer</th>
						<th>Solar</th>
						<th>Wind</th>
						<th>Storage</th>
						<th>Days</th>
						<th></th>
					</thead>
					<tbody>
						<%
							BufferedReader reader = new BufferedReader(new FileReader("C:/tests/tests.config"));
							String line;
						%>
						<%
							while ((line = reader.readLine()) != null) {
								String[] params = line.split(",");
								if(request.getParameter("loadLast")!=null && request.getParameter("loadLast").equals("true")){
									%>
									<script type="text/javascript">
									window.location.href = "Logic/loadTest.jsp?file=<%=params[0]%>";
									</script>
									<%
								}
								if(params.length==7){
						%>
						
						<tr>
							<td><%=params[0]%></td>
							<td><%=params[1]%></td>
							<td><%=params[2]%></td>
							<td><%=params[3]%></td>
							<td><%=params[4]%></td>
							<td><%=params[5]%></td>
							<td><%=params[6]%></td>
							<td><a href="Logic/loadTest.jsp?file=<%=params[0]%>">Load</a></td>
						</tr>
						<% }} %>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
