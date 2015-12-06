document.addEventListener('DOMContentLoaded', function(){ var cy = cytoscape({ container: document.querySelector('#cy'), boxSelectionEnabled: false, zoomingEnabled: false, panningEnabled: true, autounselectify: true, style: cytoscape.stylesheet() .selector('node') .css({ 'content': 'data(id)', width: '60px', height: '60px', 'text-valign': 'center', 'color': '#333', 'text-outline-width': 2, 'text-outline-color': 'white', 'pie-size': '80%', 'pie-1-background-color': '#E8747C', 'pie-1-background-size': 'mapData(red, 0, 10, 0, 100)', 'pie-2-background-color': '#74E883', 'pie-2-background-size': 'mapData(green, 0, 10, 0, 100)' }) .selector('edge') .css({ 'target-arrow-shape': 'triangle' }) .selector(':selected') .css({ 'background-color': 'black', 'line-color': 'black', 'target-arrow-color': 'green', 'source-arrow-color': 'green' }) .selector('.faded') .css({ 'opacity': 0.08, 'text-opacity': 0 }),
elements: {nodes:[{ data: { id: 'MainGrid', red: 5, green: 5 } },
{ data: { id: 'Consumer 1', red: 10, green: 0 } },
{ data: { id: 'Consumer 2', red: 10, green: 0 } },
{ data: { id: 'Consumer 3', red: 10, green: 0 } },
{ data: { id: 'Consumer 4', red: 10, green: 0 } },
{ data: { id: 'Consumer 5', red: 10, green: 0 } },
{ data: { id: 'Solar 1', red: 0, green: 10 } },
{ data: { id: 'Solar 2', red: 0, green: 10 } },
{ data: { id: 'Solar 3', red: 0, green: 10 } },
{ data: { id: 'Solar 4', red: 0, green: 10 } },
{ data: { id: 'Solar 5', red: 0, green: 10 } },
{ data: { id: 'Solar 6', red: 0, green: 10 } },
{ data: { id: 'Solar 7', red: 0, green: 10 } },
{ data: { id: 'Solar 8', red: 0, green: 10 } },
{ data: { id: 'Solar 9', red: 0, green: 10 } },
{ data: { id: 'Solar 10', red: 0, green: 10 } },
],
edges:[{ data: { id: 'link00', weight: 1, source: 'Solar 6', target: 'MainGrid' } },
{ data: { id: 'link01', weight: 1, source: 'Solar 7', target: 'MainGrid' } },
{ data: { id: 'link02', weight: 1, source: 'Solar 2', target: 'MainGrid' } },
{ data: { id: 'link03', weight: 1, source: 'Solar 5', target: 'MainGrid' } },
{ data: { id: 'link04', weight: 1, source: 'Solar 4', target: 'MainGrid' } },
{ data: { id: 'link05', weight: 1, source: 'Solar 3', target: 'MainGrid' } },
{ data: { id: 'link06', weight: 1, source: 'Solar 8', target: 'MainGrid' } },
{ data: { id: 'link07', weight: 1, source: 'Solar 1', target: 'MainGrid' } },
{ data: { id: 'link08', weight: 1, source: 'Solar 10', target: 'MainGrid' } },
{ data: { id: 'link09', weight: 1, source: 'Solar 9', target: 'MainGrid' } },
{ data: { id: 'link10', weight: 1, source: 'Solar 6', target: 'Consumer 1' } },
{ data: { id: 'link11', weight: 1, source: 'Solar 1', target: 'Consumer 1' } },
{ data: { id: 'link12', weight: 1, source: 'MainGrid', target: 'Consumer 1' } },
{ data: { id: 'link20', weight: 1, source: 'Solar 7', target: 'Consumer 2' } },
{ data: { id: 'link21', weight: 1, source: 'Solar 2', target: 'Consumer 2' } },
{ data: { id: 'link22', weight: 1, source: 'MainGrid', target: 'Consumer 2' } },
{ data: { id: 'link30', weight: 1, source: 'Solar 3', target: 'Consumer 3' } },
{ data: { id: 'link31', weight: 1, source: 'Solar 8', target: 'Consumer 3' } },
{ data: { id: 'link32', weight: 1, source: 'MainGrid', target: 'Consumer 3' } },
{ data: { id: 'link40', weight: 1, source: 'Solar 4', target: 'Consumer 4' } },
{ data: { id: 'link41', weight: 1, source: 'Solar 9', target: 'Consumer 4' } },
{ data: { id: 'link42', weight: 1, source: 'MainGrid', target: 'Consumer 4' } },
{ data: { id: 'link50', weight: 1, source: 'Solar 5', target: 'Consumer 5' } },
{ data: { id: 'link51', weight: 1, source: 'Solar 10', target: 'Consumer 5' } },
{ data: { id: 'link52', weight: 1, source: 'MainGrid', target: 'Consumer 5' } },
]},
layout: { /*ring, grid and*/ name: 'grid', padding: 10 } }); cy.on('tap', 'node', function(e){ var node = e.cyTarget; var neighborhood = node.neighborhood().add(node); cy.elements().addClass('faded'); neighborhood.removeClass('faded'); }); cy.on('tap', function(e){ if( e.cyTarget === cy ){ cy.elements().removeClass('faded'); } }); });
$( document ).ready(function() {$('#agentData tbody').append('<tr id="tidMainGrid"><td>MainGrid</td><td>$0.01</td><td>$1</td><td>$49.54</td><td>$532.87</td></tr>');
$('#agentData tbody').append('<tr id="tidConsumer 1"><td>Consumer 1</td><td>$0.25</td><td>$0.00</td><td>-$14.49</td><td>-$155.78</td></tr>');
$('#agentData tbody').append('<tr id="tidConsumer 2"><td>Consumer 2</td><td>$0.25</td><td>$0.00</td><td>-$14.54</td><td>-$162.67</td></tr>');
$('#agentData tbody').append('<tr id="tidConsumer 3"><td>Consumer 3</td><td>$0.4</td><td>$0.00</td><td>-$17.4</td><td>-$165.59</td></tr>');
$('#agentData tbody').append('<tr id="tidConsumer 4"><td>Consumer 4</td><td>$0.36</td><td>$0.00</td><td>-$16.54</td><td>-$165.61</td></tr>');
$('#agentData tbody').append('<tr id="tidConsumer 5"><td>Consumer 5</td><td>$0.32</td><td>$0.00</td><td>-$14.65</td><td>-$164.39</td></tr>');
$('#agentData tbody').append('<tr id="tidSolar 1"><td>Solar 1</td><td>$0.00</td><td>$0.81</td><td>$3.3</td><td>$34.44</td></tr>');
$('#agentData tbody').append('<tr id="tidSolar 2"><td>Solar 2</td><td>$0.00</td><td>$0.7</td><td>$2.86</td><td>$32.04</td></tr>');
$('#agentData tbody').append('<tr id="tidSolar 3"><td>Solar 3</td><td>$0.00</td><td>$0.75</td><td>$1.61</td><td>$12.73</td></tr>');
$('#agentData tbody').append('<tr id="tidSolar 4"><td>Solar 4</td><td>$0.00</td><td>$0.74</td><td>$1.47</td><td>$24.06</td></tr>');
$('#agentData tbody').append('<tr id="tidSolar 5"><td>Solar 5</td><td>$0.00</td><td>$0.74</td><td>$2.41</td><td>$34.7</td></tr>');
$('#agentData tbody').append('<tr id="tidSolar 6"><td>Solar 6</td><td>$0.00</td><td>$0.67</td><td>$1.29</td><td>$24.4</td></tr>');
$('#agentData tbody').append('<tr id="tidSolar 7"><td>Solar 7</td><td>$0.00</td><td>$0.7</td><td>$3.77</td><td>$27.68</td></tr>');
$('#agentData tbody').append('<tr id="tidSolar 8"><td>Solar 8</td><td>$0.00</td><td>$0.78</td><td>$3.87</td><td>$40.85</td></tr>');
$('#agentData tbody').append('<tr id="tidSolar 9"><td>Solar 9</td><td>$0.00</td><td>$0.81</td><td>$3.15</td><td>$30.54</td></tr>');
$('#agentData tbody').append('<tr id="tidSolar 10"><td>Solar 10</td><td>$0.00</td><td>$0.81</td><td>$4.35</td><td>$19.73</td></tr>');
$('#cCount').text('5');$('#gCount').text('10');$('#sCount').text('0');$('#dCount').text('9');});
