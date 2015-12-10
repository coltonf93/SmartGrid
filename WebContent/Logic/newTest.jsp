<%@ page import = "java.util.Map" %>
<%@ page import = "smartgrid.web.bridge.SmartGridDriver" %>
<%
String testName=request.getParameter("testName");
String description=request.getParameter("description");
int daysS=Integer.parseInt(request.getParameter("days"));
double connectivity=Double.parseDouble(request.getParameter("connectivity"));
double mainBuy;
if(request.getParameter("mBuy")!="0"){
	mainBuy=Double.parseDouble(request.getParameter("mBuy"));
}
else{
	mainBuy=0.0;
}
double mainSell=Double.parseDouble(request.getParameter("mSell"));
if(request.getParameter("mSell")!="0"){
	mainBuy=Double.parseDouble(request.getParameter("mBuy"));
}
else{
	mainBuy=0.0;
}
int storageCount=Integer.parseInt(request.getParameter("stCount"));;
double stCap;
if(request.getParameter("stCap")!="0"){
	stCap=Double.parseDouble(request.getParameter("stCap"));
}
else{
	stCap=0.0;
}
double stDecay;
if(request.getParameter("stDecay")!="0"){
	stDecay=Double.parseDouble(request.getParameter("stDecay"));
}
else{
	stDecay=0.0;
}
double stCapVar;
if(request.getParameter("stCapVar")!="0"){
	stCapVar=Double.parseDouble(request.getParameter("stCapVar"));
}
else{
	stCapVar=0.0;
}
int consumerCount=Integer.parseInt(request.getParameter("cCount"));;
String cConsumption=request.getParameter("cGen");
double cConsVar;
if(request.getParameter("cVar")!="0"){
	cConsVar=Double.parseDouble(request.getParameter("cVar"));
}
else{
	cConsVar=0.0;
}
int solarCount=Integer.parseInt(request.getParameter("sCount"));;
String sGeneration=request.getParameter("sGen");
double sGenVar;
if(request.getParameter("sVar")!="0"){
	sGenVar=Double.parseDouble(request.getParameter("sVar"));
}
else{
	sGenVar=0.0;
}
int windCount=Integer.parseInt(request.getParameter("wCount"));;
String wGeneration=request.getParameter("wGen");
double wGenVar;
if(request.getParameter("wVar")!="0"){
	wGenVar=Double.parseDouble(request.getParameter("wVar"));
}
else{
	wGenVar=0.0;
}
SmartGridDriver sg= new SmartGridDriver(testName, description, daysS, connectivity, mainBuy, mainSell, storageCount, stCap, stDecay,stCapVar, consumerCount, cConsumption, cConsVar, solarCount, sGeneration, sGenVar, windCount, wGeneration, wGenVar);
%>
<script type="text/javascript">
window.location.href = "/SmartGrid/index.jsp?page=home";
</script>