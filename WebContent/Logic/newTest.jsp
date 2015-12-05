<%@ page import = "java.util.Map" %>
<%
Map<String, String[]> parameters = request.getParameterMap();
for(String parameter : parameters.keySet()) {
	//out.print(parameters.get(parameter)+"</br>");
   		out.print(parameter+":"+(parameters.get(parameter.toString()).toString())+"<br/>");
}

%>