<%@ page import = "java.util.Map" %>
<%@ page import = "smartgrid.web.bridge.SmartGridDriver" %>
<%
String testName=request.getParameter("testName");
String description=request.getParameter("description");
int daysS=Integer.parseInt(request.getParameter("days"));
double connectivity=Double.parseDouble(request.getParameter("connectivity"));
double mainBuy=Double.parseDouble(request.getParameter("mBuy"));
double mainSell=Double.parseDouble(request.getParameter("mSell"));
int storageCount=Integer.parseInt(request.getParameter("stCount"));;
double stCap=Double.parseDouble(request.getParameter("stCap"));
double stDecay=Double.parseDouble(request.getParameter("stDecay"));
double stCapVar=Double.parseDouble(request.getParameter("stCapVar"));
int consumerCount=Integer.parseInt(request.getParameter("cCount"));;
String cConsumption=request.getParameter("cGen");
double cConsVar=Double.parseDouble(request.getParameter("cVar"));
int solarCount=Integer.parseInt(request.getParameter("sCount"));;
String sGeneration=request.getParameter("sGen");
double sGenVar=Double.parseDouble(request.getParameter("sVar"));
int windCount=Integer.parseInt(request.getParameter("wCount"));;
String wGeneration=request.getParameter("wGen");
double wGenVar=Double.parseDouble(request.getParameter("wVar"));
SmartGridDriver sg= new SmartGridDriver(testName, description, daysS, connectivity, mainBuy, mainSell, storageCount, stCap, stDecay,stCapVar, consumerCount, cConsumption, cConsVar, solarCount, sGeneration, sGenVar, windCount, wGeneration, wGenVar);
%>
<script type="text/javascript">
window.location.href = "/SmartGrid/index.jsp?page=home";
</script>