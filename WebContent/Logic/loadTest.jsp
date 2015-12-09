<%if(request.getParameter("file")!=null){
	 session.setAttribute( "testName",request.getParameter("file"));
	 %>
	<script type="text/javascript">
	window.location.href = "/SmartGrid/index.jsp?page=home";
	</script>
	<%
}
else{
	 %>
		<script type="text/javascript">
		window.location.href = "/SmartGrid/index.jsp?page=load";
		</script>
		<%
}
 %>
 
 