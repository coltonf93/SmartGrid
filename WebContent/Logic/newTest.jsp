<%@ page import = "smartgrid.web.bridge.SmartGridDriver" %>
<%@ page import = "smartgrid.web.bridge.Configuration" %>

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
String[] cCons = cConsumption.split(",");
double[] consumerConsumption = new double[cCons.length];
for(int i = 0; i < cCons.length; i++) {
   consumerConsumption[i] =  Double.parseDouble(cCons[i]);
   if(consumerConsumption[i]==0){
	   consumerConsumption[i]=.001;
   }
}

String[] wGen = wGeneration.split(",");
double[] windGeneration = new double[wGen.length];
for(int i = 0; i < wGen.length; i++) {
   windGeneration[i] =  Double.parseDouble(wGen[i]);
   if(windGeneration[i]==0){
	   windGeneration[i]=.001;
   }
}

String[] sGen = sGeneration.split(",");
double[] solarGeneration = new double[sGen.length];
for(int i = 0; i < sGen.length; i++) {
   solarGeneration[i] =  Double.parseDouble(sGen[i]);
   if(solarGeneration[i]==0){
	  solarGeneration[i]=.001;
   }
}

Configuration configs = new Configuration(testName, description, daysS, connectivity, mainBuy, mainSell, storageCount, stCap, stDecay,stCapVar, consumerCount, consumerConsumption, cConsVar, solarCount, solarGeneration, sGenVar, windCount, windGeneration, wGenVar);
SmartGridDriver sg= new SmartGridDriver(configs);
%>
<script type="text/javascript">
window.location.href = "/SmartGrid/index.jsp?page=home";
</script>