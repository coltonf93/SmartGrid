document.addEventListener('DOMContentLoaded', function(){ var cy = cytoscape({ container: document.querySelector('#cy'), boxSelectionEnabled: false, zoomingEnabled: false, panningEnabled: false, autounselectify: true, style: cytoscape.stylesheet() .selector('node') .css({ 'content': 'data(id)', width: '60px', height: '60px', 'text-valign': 'center', 'color': '#333', 'text-outline-width': 2, 'text-outline-color': 'white', 'pie-size': '80%', 'pie-1-background-color': '#E8747C', 'pie-1-background-size': 'mapData(red, 0, 10, 0, 100)', 'pie-2-background-color': '#74E883', 'pie-2-background-size': 'mapData(green, 0, 10, 0, 100)' }) .selector('edge') .css({ 'target-arrow-shape': 'triangle' }) .selector(':selected') .css({ 'background-color': 'black', 'line-color': 'black', 'target-arrow-color': 'green', 'source-arrow-color': 'green' }) .selector('.faded') .css({ 'opacity': 0.15, 'text-opacity': 0 }),
elements: {nodes:[{ data: { id: 'MainGrid', red: 5, green: 5 } },
{ data: { id: 'Consumer 1', red: 10, green: 0 } },
{ data: { id: 'Consumer 2', red: 10, green: 0 } },
{ data: { id: 'Consumer 3', red: 10, green: 0 } },
{ data: { id: 'Consumer 4', red: 10, green: 0 } },
{ data: { id: 'Consumer 5', red: 10, green: 0 } },
{ data: { id: 'Solar 1', red: 0, green: 10 } },
{ data: { id: 'Solar 2', red: 0, green: 10 } },
{ data: { id: 'Wind 1', red: 0, green: 10 } },
{ data: { id: 'Storage 1', red: 5, green: 5 } },
{ data: { id: 'Storage 2', red: 5, green: 5 } },
],
edges:[{ data: { id: 'link00', weight: 1, source: 'Solar 2', target: 'MainGrid' } },
{ data: { id: 'link01', weight: 1, source: 'Solar 1', target: 'MainGrid' } },
{ data: { id: 'link02', weight: 1, source: 'Storage 1', target: 'MainGrid' } },
{ data: { id: 'link03', weight: 1, source: 'Storage 2', target: 'MainGrid' } },
{ data: { id: 'link04', weight: 1, source: 'Wind 1', target: 'MainGrid' } },
{ data: { id: 'link10', weight: 1, source: 'Solar 1', target: 'Consumer 1' } },
{ data: { id: 'link11', weight: 1, source: 'Storage 1', target: 'Consumer 1' } },
{ data: { id: 'link12', weight: 1, source: 'Storage 2', target: 'Consumer 1' } },
{ data: { id: 'link13', weight: 1, source: 'MainGrid', target: 'Consumer 1' } },
{ data: { id: 'link14', weight: 1, source: 'Wind 1', target: 'Consumer 1' } },
{ data: { id: 'link20', weight: 1, source: 'Solar 2', target: 'Consumer 2' } },
{ data: { id: 'link21', weight: 1, source: 'Solar 1', target: 'Consumer 2' } },
{ data: { id: 'link22', weight: 1, source: 'Storage 1', target: 'Consumer 2' } },
{ data: { id: 'link23', weight: 1, source: 'MainGrid', target: 'Consumer 2' } },
{ data: { id: 'link30', weight: 1, source: 'Solar 2', target: 'Consumer 3' } },
{ data: { id: 'link31', weight: 1, source: 'Storage 2', target: 'Consumer 3' } },
{ data: { id: 'link32', weight: 1, source: 'MainGrid', target: 'Consumer 3' } },
{ data: { id: 'link40', weight: 1, source: 'Storage 1', target: 'Consumer 4' } },
{ data: { id: 'link41', weight: 1, source: 'Storage 2', target: 'Consumer 4' } },
{ data: { id: 'link42', weight: 1, source: 'MainGrid', target: 'Consumer 4' } },
{ data: { id: 'link43', weight: 1, source: 'Wind 1', target: 'Consumer 4' } },
{ data: { id: 'link50', weight: 1, source: 'Solar 1', target: 'Consumer 5' } },
{ data: { id: 'link51', weight: 1, source: 'Storage 2', target: 'Consumer 5' } },
{ data: { id: 'link52', weight: 1, source: 'MainGrid', target: 'Consumer 5' } },
{ data: { id: 'link53', weight: 1, source: 'Wind 1', target: 'Consumer 5' } },
{ data: { id: 'link90', weight: 1, source: 'Solar 1', target: 'Storage 1' } },
{ data: { id: 'link91', weight: 1, source: 'MainGrid', target: 'Storage 1' } },
{ data: { id: 'link92', weight: 1, source: 'Wind 1', target: 'Storage 1' } },
{ data: { id: 'link100', weight: 1, source: 'Solar 2', target: 'Storage 2' } },
{ data: { id: 'link101', weight: 1, source: 'MainGrid', target: 'Storage 2' } },
]},
layout: { /*ring, grid and*/ name: 'grid', padding: 10 } }); cy.on('tap', 'node', function(e){ var node = e.cyTarget; var neighborhood = node.neighborhood().add(node); cy.elements().addClass('faded'); neighborhood.removeClass('faded'); }); cy.on('tap', function(e){ if( e.cyTarget === cy ){ cy.elements().removeClass('faded'); } }); });
$( document ).ready(function() {$('#agentData tbody').append('<tr id="tidMainGrid"><td>MainGrid</td><td>$0.01</td><td>$1</td><td>$79.89</td><td>$17446.83</td></tr>');
$('#agentData tbody').append('<tr id="tidConsumer 1"><td>Consumer 1</td><td>$0.53</td><td>$0.00</td><td>-$17.87</td><td>-$3563.41</td></tr>');
$('#agentData tbody').append('<tr id="tidConsumer 2"><td>Consumer 2</td><td>$0.47</td><td>$0.00</td><td>-$18.77</td><td>-$3624.09</td></tr>');
$('#agentData tbody').append('<tr id="tidConsumer 3"><td>Consumer 3</td><td>$0.53</td><td>$0.00</td><td>-$15.5</td><td>-$3574.45</td></tr>');
$('#agentData tbody').append('<tr id="tidConsumer 4"><td>Consumer 4</td><td>$0.67</td><td>$0.00</td><td>-$16.65</td><td>-$3581.96</td></tr>');
$('#agentData tbody').append('<tr id="tidConsumer 5"><td>Consumer 5</td><td>$0.48</td><td>$0.00</td><td>-$15.62</td><td>-$3687.68</td></tr>');
$('#agentData tbody').append('<tr id="tidSolar 1"><td>Solar 1</td><td>$0.00</td><td>$0.23</td><td>$2.29</td><td>$374.45</td></tr>');
$('#agentData tbody').append('<tr id="tidSolar 2"><td>Solar 2</td><td>$0.00</td><td>$0.16</td><td>$2.1</td><td>$358.11</td></tr>');
$('#agentData tbody').append('<tr id="tidWind 1"><td>Wind 1</td><td>$0.00</td><td>$1.6</td><td>$1.92</td><td>$233.81</td></tr>');
$('#agentData tbody').append('<tr id="tidStorage 1"><td>Storage 1</td><td>$0.65</td><td>$0.65</td><td>$-0.95</td><td>$-194.21</td></tr>');
$('#agentData tbody').append('<tr id="tidStorage 2"><td>Storage 2</td><td>$0.74</td><td>$0.74</td><td>$-0.84</td><td>$-187.41</td></tr>');
$('#dCount').text('197');
$('#tCount').text('23');
});
