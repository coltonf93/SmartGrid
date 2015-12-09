var agents;

function basicData(testData){
	/*Updates the primary numbers*/
	$('#cCount').text(testData.cCount);
	$('#gCount').text((testData.wCount+testData.sCount));
	$('#sCount').text(testData.stCount);
	$('#dCount').text(testData.days);
}

function gridConnection(testData){	
	/*Creates the nodes*/
	var agentNodes=[];
	for (agent = 0; agent < testData.agents.length; agent++) { 
		if(testData.agents[agent].name.indexOf("So") > -1 || testData.agents[agent].name.indexOf("Wi") > -1){
			agentNodes.push({data:{id:testData.agents[agent].name,red:0,green:10}});
		}
		else if(testData.agents[agent].name.indexOf("St") > -1 || testData.agents[agent].name.indexOf("Ma") > -1){
			agentNodes.push({data:{id:testData.agents[agent].name,red:5,green:5}});
		}
		else if(testData.agents[agent].name.indexOf("Co") > -1){
			agentNodes.push({data:{id:testData.agents[agent].name,red:10,green:0}});
		}
		else{
			console.log('error: '+testData.agents[agent].name+" not found.")
		}
	    
	}
	
	/*Creates the edges*/
	var agentEdges=[];
	for (link = 0; link < testData.links.length; link++) { 
		agentEdges.push({ data: { id: "link"+link, weight: 1, source: testData.links[link][0], target: testData.links[link][1] } });
	}
	
	
		var cy = cytoscape({ 
			container: document.querySelector('#cy'), 
			boxSelectionEnabled: false, zoomingEnabled: false, 
			panningEnabled: true, 
			autounselectify: true,
			style: cytoscape.stylesheet().selector('node').css({ 
				'content': 'data(id)', 
				width: '60px',
				height: '60px', 
				'text-valign':'center',
				'color': '#333', 
				'text-outline-width': 2, 
				'text-outline-color': 'white',
				'pie-size': '80%',
				'pie-1-background-color': '#E8747C',
				'pie-1-background-size': 'mapData(red, 0, 10, 0, 100)',
				'pie-2-background-color': '#74E883',
				'pie-2-background-size': 'mapData(green, 0, 10, 0, 100)' 
				}) .selector('edge') .css({ 
					'target-arrow-shape': 'triangle' 
				}) .selector(':selected') .css({ 
					'background-color': 'black', 
					'line-color': 'black', 
					'target-arrow-color': 'green',
					'source-arrow-color': 'green' 
				}) .selector('.faded') .css({ 
					'opacity': 0.08,
					'text-opacity': 0 
				}),
				elements: {
					nodes:agentNodes,
					edges:agentEdges
				},
		        layout: { 
		        	/*ring, grid and*/ 
		        	name: 'grid', padding: 10 
		        }
			}); 
			//ON node click hide other edges and nodes 
			cy.on('tap', 'node', function(e){
				var node = e.cyTarget; 
				var neighborhood = node.neighborhood().add(node); 
				cy.elements().addClass('faded'); 
				neighborhood.removeClass('faded'); 
			}); 
			cy.on('tap', function(e){
				if( e.cyTarget === cy ){
					cy.elements().removeClass('faded'); 
				} 
			}); 
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
		var mainPriceSum=0;
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
			else if(testData.agents[agent].name.indexOf("Ma") > -1){
				mainPriceSum+=testData.agents[agent].avgPrices[hour][dy];
			}
			else{
				console.log('error: '+testData.agents[agent].name+" not found.")
			}
		}
		graphData2.push({t:dy,sop:(solarPriceSum/(testData.agents.length)),stp:(storagePriceSum/(testData.agents.length)),wip:(windPriceSum/(testData.agents.length)),cop:(consumerPriceSum/(testData.agents.length)),main:(mainPriceSum/testData.agents.length)});
	}	
	
	$('.pagination li').removeClass('active');
	$('.pagination li').eq(hour).addClass('active');
	new Morris.Area({
		  element: 'avgHourPrice',
		  data: graphData2,
		  xkey: 't',
		  ykeys: ['sop','wip','cop','stp','main'],
		  labels: ['Solar','Wind','Consumer','Storage','main'],
		  parseTime: false
		});
}

//populates the on page table with data of agents
function updateTableData(testData){
	for (agent = 0; agent < testData.agents.length; agent++) { 
		var avgFinalBuyBid=0;
		var avgFinalSellBid=0;
		var expense=0;
		var profit=0;
		var finalDailyProfit=0;
		var finalDailyExpense=0;
		//Sellers
		if(testData.agents[agent].name.indexOf("So") > -1 || testData.agents[agent].name.indexOf("Wi") > -1 || testData.agents[agent].name.indexOf("St") > -1 || testData.agents[agent].name.indexOf("Ma") > -1){
			//finds the avgFinalSellBid for sell agents
			for(i=0;i<testData.agents[agent].sellBids[23].length;i++){
				avgFinalSellBid+=testData.agents[agent].sellBids[23][i];
			}
			avgFinalSellBid=avgFinalSellBid/testData.agents[agent].sellBids[23].length;
			profit=testData.agents[agent].profit;
			finalDailyProfit=testData.agents[agent].dailyProfit;
			
		}
		//Buyers
		else if(testData.agents[agent].name.indexOf("Co") > -1 || testData.agents[agent].name.indexOf("St") > -1 || testData.agents[agent].name.indexOf("Ma") > -1){
			//finds the avgFinalBuyBid for buy agents
			for(i=0;i<testData.agents[agent].buyBids[23].length;i++){
				avgFinalBuyBid+=testData.agents[agent].buyBids[23][i];
			}
			avgFinalBuyBid=avgFinalBuyBid/testData.agents[agent].buyBids[23].length;
			expense=testData.agents[agent].expense;
			finalDailyExpense=testData.agents[agent].dailyExpense;
		}
		$('#agentData tbody').append('<tr id="tid'+agent+'"><td>'+testData.agents[agent].name+'</td><td>$'+parseFloat(avgFinalSellBid).toFixed(3)+'</td><td>$'+parseFloat(avgFinalBuyBid).toFixed(3)+'</td><td>$'+parseFloat(finalDailyProfit-finalDailyExpense).toFixed(3)+'</td><td>$'+parseFloat(profit-expense).toFixed(3)+'</td></tr>');
	}
	$('#agentData').DataTable();
}


function loadJson(jsonString) {
	    testData=jQuery.parseJSON(jsonString); 
	    console.log(testData);
	    document.addEventListener('DOMContentLoaded', function(){ 
	    	gridConnection(testData);	
	 	    avgGraph(testData);
	 	    basicData(testData);
	 	    updateTableData(testData);
	 	    updateAvgHourPrice(0);
	    });
	   
}