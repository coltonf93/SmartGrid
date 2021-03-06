var maxSamples=100;
var agents;
var sRate=1;
function basicData(){
	/*Updates the primary numbers*/
	$('#cCount').text(testData.configs.consumerCount);
	$('#gCount').text((testData.configs.windCount+testData.configs.solarCount));
	$('#sCount').text(testData.configs.storageCount);
	$('#dCount').text(testData.configs.daysS);//check this
}

function gridConnection(){	
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
		agentEdges.push({ data: { id: "link"+link, weight: 1, source: testData.links[link][1], target: testData.links[link][0] } });
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

function avgGraph(){

}

function updateAvgPrice(hour){
	$('#avgPrice').text("");
		var graphData2=[];
		console.log(sRate);
		for(dy=0;dy<testData.agents[0].avgPrices[0].length;dy+=sRate){
			var solarPriceSum=0;
			var windPriceSum=0;
			var consumerPriceSum=0;
			var storagePriceSum=0;
			var mainPriceSum=0;
			var solarCount=0;
			var windCount=0;
			var storageCount=0
			var consumerCount=0;
			var mainCount=0;
			for(agent=0;agent<testData.agents.length;agent++){
				if(hour<24){//average by hour
					if(testData.agents[agent].name.indexOf("So") > -1){
						solarPriceSum+=testData.agents[agent].avgPrices[hour][dy];
						solarCount++;
					}
					else if(testData.agents[agent].name.indexOf("Wi") > -1){
						windPriceSum+=testData.agents[agent].avgPrices[hour][dy];
						windCount++;
					}
					else if(testData.agents[agent].name.indexOf("St") > -1){
						storagePriceSum+=testData.agents[agent].avgPrices[hour][dy];
						storageCount++;
					}
					else if(testData.agents[agent].name.indexOf("Co") > -1){
						consumerPriceSum+=testData.agents[agent].avgPrices[hour][dy];
						consumerCount++;
					}
					else if(testData.agents[agent].name.indexOf("Ma") > -1){
						mainPriceSum+=testData.agents[agent].avgPrices[hour][dy];
						mainCount++;
					}
				}else{//average for day
					for(hour=0;hour<testData.agents[0].avgPrices.length;hour++){
						if(testData.agents[agent].name.indexOf("So") > -1){
							solarPriceSum+=testData.agents[agent].avgPrices[hour][dy];
							solarCount++;
						}
						else if(testData.agents[agent].name.indexOf("Wi") > -1){
							windPriceSum+=testData.agents[agent].avgPrices[hour][dy];
							windCount++;
						}
						else if(testData.agents[agent].name.indexOf("St") > -1){
							storagePriceSum+=testData.agents[agent].avgPrices[hour][dy];
							storageCount++;
						}
						else if(testData.agents[agent].name.indexOf("Co") > -1){
							consumerPriceSum+=testData.agents[agent].avgPrices[hour][dy];
							consumerCount++;
						}
						else if(testData.agents[agent].name.indexOf("Ma") > -1){
							mainPriceSum+=testData.agents[agent].avgPrices[hour][dy];
							mainCount++;
						}
					}	
				}
			}
			solarPriceSum/=solarCount;
			windPriceSum/=windCount;
			consumerPriceSum/=consumerCount;
			storagePriceSum/=storageCount;
			mainPriceSum/=mainCount;
			
			graphData2.push({t:dy,sop:(solarPriceSum),wip:(windPriceSum),cop:(consumerPriceSum),stp:(storagePriceSum),main:(mainPriceSum)});
		}
		
		new Morris.Line({
			  element: 'avgPrice',
			  data: graphData2,
			  xkey: 't',
			  ykeys: ['sop','wip','cop','stp','main'],
			  labels: ['Solar','Wind','Consumer','Storage','Main'],
			  parseTime: false
			});
		$('.pagination.avgPrice li').removeClass('active');
		$('.pagination.avgPrice li').eq(hour).addClass('active');
}

function updateAvgBid(hour){
	$('#avgBid').text("");
		var graphData=[];
		for(dy=0;dy<testData.agents[0].avgPrices[0].length;dy+=sRate){
			var solarBidSum=0;
			var windBidSum=0;
			var consumerBidSum=0;
			var storageBuyBidSum=0;
			var mainBuyBidSum=0;
			var storageSellBidSum=0;
			var mainSellBidSum=0;
			var solarCount=0;
			var windCount=0;
			var storageCount=0
			var consumerCount=0;
			var mainCount=0;
			for(agent=0;agent<testData.agents.length;agent++){
				if(hour<24){//average by hour
					if(testData.agents[agent].name.indexOf("So") > -1){
						solarBidSum+=testData.agents[agent].sellBids[hour][dy];
						solarCount++;
					}
					else if(testData.agents[agent].name.indexOf("Wi") > -1){
						windBidSum+=testData.agents[agent].sellBids[hour][dy];
						windCount++;
					}
					else if(testData.agents[agent].name.indexOf("St") > -1){
						storageBuyBidSum+=testData.agents[agent].buyBids[hour][dy];
						storageSellBidSum+=testData.agents[agent].sellBids[hour][dy];
						storageCount++;
					}
					else if(testData.agents[agent].name.indexOf("Co") > -1){
						consumerBidSum+=testData.agents[agent].buyBids[hour][dy];
						consumerCount++;
					}
					else if(testData.agents[agent].name.indexOf("Ma") > -1){
						mainBuyBidSum+=testData.agents[agent].buyBids[hour][dy];
						mainSellBidSum+=testData.agents[agent].sellBids[hour][dy];
						mainCount++;
					}
				}else{//average for day
					for(hour=0;hour<testData.agents[0].avgPrices.length;hour++){
						if(testData.agents[agent].name.indexOf("So") > -1){
							solarBidSum+=testData.agents[agent].sellBids[hour][dy];
							solarCount++;
						}
						else if(testData.agents[agent].name.indexOf("Wi") > -1){
							windBidSum+=testData.agents[agent].sellBids[hour][dy];
							windCount++;
						}
						else if(testData.agents[agent].name.indexOf("St") > -1){
							storageBuyBidSum+=testData.agents[agent].buyBids[hour][dy];
							storageSellBidSum+=testData.agents[agent].sellBids[hour][dy];
							storageCount++;
						}
						else if(testData.agents[agent].name.indexOf("Co") > -1){
							consumerBidSum+=testData.agents[agent].buyBids[hour][dy];
							consumerCount++;
						}
						else if(testData.agents[agent].name.indexOf("Ma") > -1){
							mainBuyBidSum+=testData.agents[agent].buyBids[hour][dy];
							mainSellBidSum+=testData.agents[agent].sellBids[hour][dy];
							mainCount++;
						}
					}	
				}
			}
			solarBidSum/=solarCount;
			windBidSum/=windCount;
			consumerBidSum/=consumerCount;
			storageBuyBidSum/=storageCount;
			storageSellBidSum/=storageCount;
			mainBuyBidSum/=mainCount;
			mainSellBidSum/=mainCount;
			graphData.push({t:dy,sob:(solarBidSum),wib:(windBidSum),cob:(consumerBidSum), stbb:(storageBuyBidSum),stsb:(storageSellBidSum),mains:(mainSellBidSum),mainb:(mainBuyBidSum)});
		}
		
		new Morris.Line({
			  element: 'avgBid',
			  data: graphData,
			  xkey: 't',
			  ykeys: ['sob','wib','cob','stsb','stbb','mains','mainb'],
			  labels: ['Solar Sell','Wind Sell','Consumer Buy','Storage Sell','Storage Buy','Main Sell','Main Buy'],
			  parseTime: false
			});
		$('.pagination.avgBid li').removeClass('active');
		$('.pagination.avgBid li').eq(hour).addClass('active');
}

//populates the on page table with data of agents
function updateTableData(){
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
		if(testData.agents[agent].name.indexOf("Co") > -1 || testData.agents[agent].name.indexOf("St") > -1 || testData.agents[agent].name.indexOf("Ma") > -1){
			//finds the avgFinalBuyBid for buy agents
			for(i=0;i<testData.agents[agent].buyBids[23].length;i++){
				avgFinalBuyBid+=testData.agents[agent].buyBids[23][i];
			}
			avgFinalBuyBid=avgFinalBuyBid/testData.agents[agent].buyBids[23].length;
			expense=testData.agents[agent].expense;
			finalDailyExpense=testData.agents[agent].dailyExpense;
		}
		$('#agentData tbody').append('<tr id="tid'+agent+'"><td><a href="#" onclick="agentDetails('+agent+')">'+testData.agents[agent].name+'</a></td><td>$'+parseFloat(profit).toFixed(3)+'</td><td>$'+parseFloat(expense).toFixed(3)+'</td><td>$'+parseFloat(finalDailyProfit-finalDailyExpense).toFixed(3)+'</td><td>$'+parseFloat(profit-expense).toFixed(3)+'</td></tr>');
	}
	$('#agentData').DataTable();
}

function agentDetails(a){
	var agent=testData.agents[a];
	$('#singleAgent').modal('show');

	$('#singleAgent').on('shown.bs.modal', function (e) {
		$('#capacity, #consGen, #priceDay, #bidDay, .singlecgenlabel, .singlebidlabel, .singlepricelabel ').text(" ");
		$('#myModalLabel').text(agent.name+" details");
	var consGenData=[];
	var priceDayData=[];
	var bidDayData=[];
	var agentBuyBidAvg=0;
	var agentSellBidAvg=0;
	var agentPriceAvg=0;
	if(agent.name.indexOf("So") > -1 || agent.name.indexOf("Wi") > -1){
		for(day=0;day<testData.configs.daysS;day+=sRate){
			for(hour=0;hour<24;hour++){
				agentSellBidAvg+=agent.sellBids[hour][day];
				agentPriceAvg+=agent.avgPrices[hour][day];
			}
			agentSellBidAvg/=24;
			agentPriceAvg/=24;
			priceDayData.push({t:day,price:agentPriceAvg});
			bidDayData.push({t:day,bid:agentSellBidAvg});
		}
		for(hour=0;hour<24;hour++){
			if(agent.name.indexOf("So")>-1){
				var cmax=testData.configs.sGeneration[hour]+testData.configs.sGenVar;
				consGenData.push({t:hour,c:testData.configs.sGeneration[hour],cm:cmax});
				$(".singlecgenlabel").text("Solar Generation");
				$(".singlebidlabel").text("Solar Average Sell Bids");
				$(".singlepricelabel").text("Solar Average Prices");
			}
			if(agent.name.indexOf("Wi")>-1){
				var cmax=testData.configs.wGeneration[hour]+testData.configs.wGenVar;
				consGenData.push({t:hour,c:testData.configs.wGeneration[hour],cm:cmax});
				$(".singlecgenlabel").text("Wind Generation");
				$(".singlebidlabel").text("Wind Average Sell Bids");
				$(".singlepricelabel").text("Wind Average Prices");
			}
		}
	}
	else if(agent.name.indexOf("St") > -1 || agent.name.indexOf("Ma") > -1){
		for(day=0;day<testData.configs.daysS;day+=sRate){
			for(hour=0;hour<24;hour++){
				agentSellBidAvg+=agent.sellBids[hour][day];
				agentBuyBidAvg+=agent.buyBids[hour][day];
				agentPriceAvg+=agent.avgPrices[hour][day];
			}
			agentSellBidAvg/=24;
			agentBuyBidAvg/=24;
			agentPriceAvg/=24;
			priceDayData.push({t:day,price:agentPriceAvg});
			bidDayData.push({t:day,buybid:agentBuyBidAvg,sellBid:agentSellBidAvg});
		}
		$(".singlebidlabel").text(agent.name+" Average Buy/ Sell Bids");
		$(".singlepricelabel").text(agent.name+" Average Prices");
	}
	else if(agent.name.indexOf("Co") > -1){
		for(day=0;day<testData.configs.daysS;day+=sRate){
			for(hour=0;hour<24;hour++){
				agentBuyBidAvg+=agent.buyBids[hour][day];
				agentPriceAvg+=agent.avgPrices[hour][day];
			}
			agentBuyBidAvg/=24;
			agentPriceAvg/=24;
			priceDayData.push({t:day,price:agentPriceAvg})
			bidDayData.push({t:day,bid:agentBuyBidAvg})
		}
		for(hour=0;hour<24;hour++){
			consGenData.push({t:hour,c:(testData.configs.cConsumption[hour]),cm:(testData.configs.cConsumption[hour]+testData.configs.cConsVar)});
		}
		$(".singlecgenlabel").text("Consumer Consumption");
		$(".singlebidlabel").text("Consumer Average Buy Bids");
		$(".singlepricelabel").text("Consumer Average Prices");
	}
	if(agent.name.indexOf("So") > -1 || agent.name.indexOf("Wi") > -1 || agent.name.indexOf("Co") > -1){
		$('#consGen').css('display','initial');
		new Morris.Bar({
			  element: 'consGen',
			  data: consGenData,
			  xkey: 't',
			  ykeys: ['c','cm'],
			  labels: ['Consumption/Generation Min','Consumption/Generation Max'],
			  parseTime: false
			});
		new Morris.Line({
			  element: 'bidDay',
			  data: bidDayData,
			  xkey: 't',
			  ykeys: ['bid'],
			  labels: ['Agent Buy/Sell Bid Average'],
			  parseTime: false
			});
		new Morris.Line({
			  element: 'priceDay',
			  data: priceDayData,
			  xkey: 't',
			  ykeys: ['price'],
			  labels: ['Agent Daily Price Average'],
			  parseTime: false
			});
		
	}
	else{
		if(agent.name.indexOf("Ma")>-1){
			$('#capacity').text("Capacity: INFINITE");
		}
		else{
			$('#capacity').text("Capacity: "+testData.configs.stCap+"(+"+testData.configs.stCapVar+")");
		}
		console.log("StCapVar: "+testData.configs.stCapVar)
		$('#consGen').css('display','none');
		new Morris.Line({
			  element: 'bidDay',
			  data: bidDayData,
			  xkey: 't',
			  ykeys: ['buybid','sellbid'],
			  labels: ['Agent Buy/Sell Bid Average'],
			  parseTime: false
			});
		new Morris.Line({
			  element: 'priceDay',
			  data: priceDayData,
			  xkey: 't',
			  ykeys: ['price'],
			  labels: ['Agent Daily Price Average'],
			  parseTime: false
			});
	}	
	});
}


function loadJson(jsonString) {
	    testData=jQuery.parseJSON(jsonString); 
	    console.log(testData);
	    $( document ).ready(function() {
	        sRate=1;
			if(testData.configs.daysS>100){
				sRate=Math.round(testData.configs.daysS/maxSamples);
			}
	    	gridConnection();	
	 	    avgGraph();
	 	    basicData();
	 	    updateTableData();
	 	    updateAvgPrice(24);
	 	    updateAvgBid(24);
	 	
	    });
	   
}