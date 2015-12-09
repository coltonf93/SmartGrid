var agents;

function gridConnection(data){
	/*Updates the primary numbers*/
	$('#cCount').text(data.cCount);
	$('#gCount').text((data.wCount+data.sCount));$
	('#sCount').text(data.stCount);
	$('#dCount').text(data.days);
	/*Creates the nodes*/
	for (i = 0; i < data.agents.length; i++) { 
	    
	}
	/*Creates the edges*/
	
	
}

function avgGraph(testData){
	var graphData=[];
	for(dy=0;dy<testData.agents[0].avgPrices[0].length;dy++){
		var priceSum=0;
		for(hour=0;hour<testData.agents[0].avgPrices.length;hour++){
			for(agent=0;agent<testData.agents.length;agent++){
				priceSum+=testData.agents[agent].avgPrices[hour][dy];
			}
		}
		graphData.push({t:dy,price:(priceSum/(testData.agents.length*24))});
	}
	
new Morris.Area({
	  element: 'avgBidPrices',
	  data: graphData,
	  xkey: 't',
	  ykeys: ['price'],
	  labels: ['price'],
      parseTime: false
	});	
}

function updateAvgHourPrice(hour){
	$('#avgHourPrice').text("");
	var graphData2=[];
	for(dy=0;dy<testData.agents[0].avgPrices[0].length;dy++){
		var solarPriceSum=0;
		var windPriceSum=0;
		var consumerPriceSum=0;
		var storagePriceSum=0;
		for(agent=0;agent<testData.agents.length;agent++){
			if(testData.agents[agent].name.indexOf("So") > -1){
				solarPriceSum+=testData.agents[agent].avgPrices[hour][dy];
			}
			else if(testData.agents[agent].name.indexOf("Wi") > -1){
				windPriceSum+=testData.agents[agent].avgPrices[hour][dy];
			}
			else if(testData.agents[agent].name.indexOf("St") > -1){
				storagePriceSum+=testData.agents[agent].avgPrices[hour][dy];
			}
			else if(testData.agents[agent].name.indexOf("Co") > -1){
				consumerPriceSum+=testData.agents[agent].avgPrices[hour][dy];
			}
			else{
				console.log('error: '+testData.agents[agent].name+" not found.")
			}
		}
		graphData2.push({t:dy,sop:(solarPriceSum/(testData.agents.length)),stp:(storagePriceSum/(testData.agents.length)),wip:(windPriceSum/(testData.agents.length)),cop:(consumerPriceSum/(testData.agents.length))});
	}	
	
	$('.pagination li').removeClass('active');
	$('.pagination li').eq(hour).addClass('active');
	new Morris.Area({
		  element: 'avgHourPrice',
		  data: graphData2,
		  xkey: 't',
		  ykeys: ['sop','wip','cop','stp'],
		  labels: ['Solar','Wind','Consumer','Storage'],
		  parseTime: false
		});
}

function loadJson(jsonString) {
	    testData=jQuery.parseJSON(jsonString); 
	    console.log(testData);
	    $( document ).ready(function() {
	    	gridConnection(testData);	
	 	    avgGraph(testData);
	 	    updateAvgHourPrice(0);
	    });
	   
}