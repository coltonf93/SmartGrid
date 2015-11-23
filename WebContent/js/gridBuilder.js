document.addEventListener('DOMContentLoaded', function(){ // on dom ready

    var cy = cytoscape({
        container: document.querySelector('#cy'),

        boxSelectionEnabled: false,
        autounselectify: true,

        style: cytoscape.stylesheet()
            .selector('node')
            .css({
                'content': 'data(id)',
                width: '60px',
                height: '60px',
                'text-valign': 'center',
                'color': 'white',
                'text-outline-width': 2,
                'text-outline-color': '#888',
                'pie-size': '80%',
                'pie-1-background-color': '#E8747C',
                'pie-1-background-size': 'mapData(foo, 0, 10, 0, 100)',
                'pie-2-background-color': '#74CBE8',
                'pie-2-background-size': 'mapData(bar, 0, 10, 0, 100)',
                'pie-3-background-color': '#74E883',
                'pie-3-background-size': 'mapData(baz, 0, 10, 0, 100)'
            })
            .selector('edge')
            .css({
                'target-arrow-shape': 'triangle'
            })
            .selector(':selected')
            .css({
                'background-color': 'black',
                'line-color': 'green',
                'target-arrow-color': 'green',
                'source-arrow-color': 'green'
            })
            .selector('.faded')
            .css({
                'opacity': 0.15,
                'text-opacity': 0
            }),

        elements: {
            nodes: [
                { data: { id: 'st0', foo: 2, bar: 3, baz: 5 } },
                { data: { id: 's0', foo: 0, bar: 0, baz: 10 } },
                { data: { id: 's1', foo: 0, bar: 0, baz: 10 } },
                { data: { id: 's2', foo: 0, bar: 0, baz: 10 } },
                { data: { id: 'w0', foo: 0, bar: 0, baz: 10 } },
                { data: { id: 'st1', foo: 7, bar: 1, baz: 2 } },
                { data: { id: 'w1', foo: 0, bar: 0, baz: 10 } },
                { data: { id: 'w2', foo: 0, bar: 0, baz: 10} },
                { data: { id: 'w3', foo: 0, bar: 0, baz: 10 } },
                { data: { id: 'w4', foo: 0, bar: 0, baz: 10 } },
                { data: { id: 'st2', foo: 2, bar: 3, baz: 5 } },
                { data: { id: 'c0', foo: 10, bar: 0, baz: 0 } },
                { data: { id: 'c1', foo: 10, bar: 0, baz: 0 } },
                { data: { id: 'st3', foo: 2, bar: 3, baz: 5 } },
                { data: { id: 'c2', foo: 10, bar: 0, baz: 0 } },
                { data: { id: 'c3', foo: 10, bar: 0, baz: 0 } },
                { data: { id: 'c4', foo: 10, bar: 0, baz: 0 } },
                { data: { id: 'main', foo: 3, bar: 0, baz: 7 } }
            ],

            edges: [
                /*s0 edges selling to */
                { data: { id: 'link7a', weight: 1, source: 's0', target: 'c0' } },
                { data: { id: 'link8a', weight: 1, source: 's0', target: 'c1' } },
                { data: { id: 'link9a', weight: 1, source: 's0', target: 'c2' } },
                { data: { id: 'link10a', weight: 1, source: 's0', target: 'c3' } },
                { data: { id: 'link11a', weight: 1, source: 's0', target: 'c4' } },
                { data: { id: 'link12a', weight: 1, source: 's0', target: 'st0' } },
                { data: { id: 'link13a', weight: 1, source: 's0', target: 'st1' } },
                { data: { id: 'link14a', weight: 1, source: 's0', target: 'st2' } },
                { data: { id: 'link15a', weight: 1, source: 's0', target: 'st3' } },
                { data: { id: 'link16a', weight: 1, source: 's0', target: 'main' } },

                /*s1 edges selling to*/
                { data: { id: 'link7b', weight: 1, source: 's1', target: 'c0' } },
                { data: { id: 'link8b', weight: 1, source: 's1', target: 'c1' } },
                { data: { id: 'link9b', weight: 1, source: 's1', target: 'c2' } },
                { data: { id: 'link10b', weight: 1, source: 's1', target: 'c3' } },
                { data: { id: 'link11b', weight: 1, source: 's1', target: 'c4' } },
                { data: { id: 'link12b', weight: 1, source: 's1', target: 'st0' } },
                { data: { id: 'link13b', weight: 1, source: 's1', target: 'st1' } },
                { data: { id: 'link14b', weight: 1, source: 's1', target: 'st2' } },
                { data: { id: 'link15b', weight: 1, source: 's1', target: 'st3' } },
                { data: { id: 'link16b', weight: 1, source: 's1', target: 'main' } },

                /*s2 edges selling to*/
                { data: { id: 'link7c', weight: 1, source: 's2', target: 'c0' } },
                { data: { id: 'link8c', weight: 1, source: 's2', target: 'c1' } },
                { data: { id: 'link9c', weight: 1, source: 's2', target: 'c2' } },
                { data: { id: 'link10c', weight: 1, source: 's2', target: 'c3' } },
                { data: { id: 'link11c', weight: 1, source: 's2', target: 'c4' } },
                { data: { id: 'link12c', weight: 1, source: 's2', target: 'st0' } },
                { data: { id: 'link13c', weight: 1, source: 's2', target: 'st1' } },
                { data: { id: 'link14c', weight: 1, source: 's2', target: 'st2' } },
                { data: { id: 'link15c', weight: 1, source: 's2', target: 'st3' } },
                { data: { id: 'link16c', weight: 1, source: 's2', target: 'main' } },

                /*w0 edges selling to*/
                { data: { id: 'link7d', weight: 1, source: 'w0', target: 'c0' } },
                { data: { id: 'link8d', weight: 1, source: 'w0', target: 'c1' } },
                { data: { id: 'link9d', weight: 1, source: 'w0', target: 'c2' } },
                { data: { id: 'link10d', weight: 1, source: 'w0', target: 'c3' } },
                { data: { id: 'link11d', weight: 1, source: 'w0', target: 'c4' } },
                { data: { id: 'link12d', weight: 1, source: 'w0', target: 'st0' } },
                { data: { id: 'link13d', weight: 1, source: 'w0', target: 'st1' } },
                { data: { id: 'link14d', weight: 1, source: 'w0', target: 'st2' } },
                { data: { id: 'link15d', weight: 1, source: 'w0', target: 'st3' } },
                { data: { id: 'link16d', weight: 1, source: 'w0', target: 'main' } },

                /*w1 edges selling to*/
                { data: { id: 'link7e', weight: 1, source: 'w1', target: 'c0' } },
                { data: { id: 'link8e', weight: 1, source: 'w1', target: 'c1' } },
                { data: { id: 'link9e', weight: 1, source: 'w1', target: 'c2' } },
                { data: { id: 'link10e', weight: 1, source: 'w1', target: 'c3' } },
                { data: { id: 'link11e', weight: 1, source: 'w1', target: 'c4' } },
                { data: { id: 'link12e', weight: 1, source: 'w1', target: 'st0' } },
                { data: { id: 'link13e', weight: 1, source: 'w1', target: 'st1' } },
                { data: { id: 'link14e', weight: 1, source: 'w1', target: 'st2' } },
                { data: { id: 'link15e', weight: 1, source: 'w1', target: 'st3' } },
                { data: { id: 'link16e', weight: 1, source: 'w1', target: 'main' } },

                /*w2 edges selling to*/
                { data: { id: 'link7f', weight: 1, source: 'w2', target: 'c0' } },
                { data: { id: 'link8f', weight: 1, source: 'w2', target: 'c1' } },
                { data: { id: 'link9f', weight: 1, source: 'w2', target: 'c2' } },
                { data: { id: 'link10f', weight: 1, source: 'w2', target: 'c3' } },
                { data: { id: 'link11f', weight: 1, source: 'w2', target: 'c4' } },
                { data: { id: 'link12f', weight: 1, source: 'w2', target: 'st0' } },
                { data: { id: 'link13f', weight: 1, source: 'w2', target: 'st1' } },
                { data: { id: 'link14f', weight: 1, source: 'w2', target: 'st2' } },
                { data: { id: 'link15f', weight: 1, source: 'w2', target: 'st3' } },
                { data: { id: 'link16f', weight: 1, source: 'w2', target: 'main' } },

                /*w3 edges selling to*/
                { data: { id: 'link7g', weight: 1, source: 'w3', target: 'c0' } },
                { data: { id: 'link8g', weight: 1, source: 'w3', target: 'c1' } },
                { data: { id: 'link9g', weight: 1, source: 'w3', target: 'c2' } },
                { data: { id: 'link10g', weight: 1, source: 'w3', target: 'c3' } },
                { data: { id: 'link11g', weight: 1, source: 'w3', target: 'c4' } },
                { data: { id: 'link12g', weight: 1, source: 'w3', target: 'st0' } },
                { data: { id: 'link13g', weight: 1, source: 'w3', target: 'st1' } },
                { data: { id: 'link14g', weight: 1, source: 'w3', target: 'st2' } },
                { data: { id: 'link15g', weight: 1, source: 'w3', target: 'st3' } },
                { data: { id: 'link16g', weight: 1, source: 'w3', target: 'main' } },

                /*w4 edges selling to*/
                { data: { id: 'link7h', weight: 1, source: 'w4', target: 'c0' } },
                { data: { id: 'link8h', weight: 1, source: 'w4', target: 'c1' } },
                { data: { id: 'link9h', weight: 1, source: 'w4', target: 'c2' } },
                { data: { id: 'link10h', weight: 1, source: 'w4', target: 'c3' } },
                { data: { id: 'link11h', weight: 1, source: 'w4', target: 'c4' } },
                { data: { id: 'link12h', weight: 1, source: 'w4', target: 'st0' } },
                { data: { id: 'link13h', weight: 1, source: 'w4', target: 'st1' } },
                { data: { id: 'link14h', weight: 1, source: 'w4', target: 'st2' } },
                { data: { id: 'link15h', weight: 1, source: 'w4', target: 'st3' } },
                { data: { id: 'link16h', weight: 1, source: 'w4', target: 'main' } },

                /*st0 edges selling to*/
                { data: { id: 'link7i', weight: 1, source: 'st0', target: 'c0' } },
                { data: { id: 'link8i', weight: 1, source: 'st0', target: 'c1' } },
                { data: { id: 'link9i', weight: 1, source: 'st0', target: 'c2' } },
                { data: { id: 'link10i', weight: 1, source: 'st0', target: 'c3' } },
                { data: { id: 'link11i', weight: 1, source: 'st0', target: 'c4' } },
                { data: { id: 'link13i', weight: 1, source: 'st0', target: 'st1' } },
                { data: { id: 'link14i', weight: 1, source: 'st0', target: 'st2' } },
                { data: { id: 'link15i', weight: 1, source: 'st0', target: 'st3' } },
                { data: { id: 'link16i', weight: 1, source: 'st0', target: 'main' } },

                /*st1 edges selling to*/
                { data: { id: 'link7j', weight: 1, source: 'st1', target: 'c0' } },
                { data: { id: 'link8j', weight: 1, source: 'st1', target: 'c1' } },
                { data: { id: 'link9j', weight: 1, source: 'st1', target: 'c2' } },
                { data: { id: 'link10j', weight: 1, source: 'st1', target: 'c3' } },
                { data: { id: 'link11j', weight: 1, source: 'st1', target: 'c4' } },
                { data: { id: 'link13j', weight: 1, source: 'st1', target: 'st0' } },
                { data: { id: 'link14j', weight: 1, source: 'st1', target: 'st2' } },
                { data: { id: 'link15j', weight: 1, source: 'st1', target: 'st3' } },
                { data: { id: 'link16j', weight: 1, source: 'st1', target: 'main' } },

                /*st2 edges selling to*/
                { data: { id: 'link7k', weight: 1, source: 'st2', target: 'c0' } },
                { data: { id: 'link8k', weight: 1, source: 'st2', target: 'c1' } },
                { data: { id: 'link9k', weight: 1, source: 'st2', target: 'c2' } },
                { data: { id: 'link10k', weight: 1, source: 'st2', target: 'c3' } },
                { data: { id: 'link11k', weight: 1, source: 'st2', target: 'c4' } },
                { data: { id: 'link13k', weight: 1, source: 'st2', target: 'st0' } },
                { data: { id: 'link14k', weight: 1, source: 'st2', target: 'st1' } },
                { data: { id: 'link15k', weight: 1, source: 'st2', target: 'st3' } },
                { data: { id: 'link16k', weight: 1, source: 'st2', target: 'main' } },

                /*st3 edges selling to*/
                { data: { id: 'link7l', weight: 1, source: 'st3', target: 'c0' } },
                { data: { id: 'link8l', weight: 1, source: 'st3', target: 'c1' } },
                { data: { id: 'link9l', weight: 1, source: 'st3', target: 'c2' } },
                { data: { id: 'link10l', weight: 1, source: 'st3', target: 'c3' } },
                { data: { id: 'link11l', weight: 1, source: 'st3', target: 'c4' } },
                { data: { id: 'link13l', weight: 1, source: 'st3', target: 'st0' } },
                { data: { id: 'link14l', weight: 1, source: 'st3', target: 'st1' } },
                { data: { id: 'link15l', weight: 1, source: 'st3', target: 'st2' } },
                { data: { id: 'link16l', weight: 1, source: 'st3', target: 'main' } },

                /*main edges selling to*/
                { data: { id: 'link7m', weight: 1, source: 'main', target: 'c0' } },
                { data: { id: 'link8m', weight: 1, source: 'main', target: 'c1' } },
                { data: { id: 'link9m', weight: 1, source: 'main', target: 'c2' } },
                { data: { id: 'link10m', weight: 1, source: 'main', target: 'c3' } },
                { data: { id: 'link11m', weight: 1, source: 'main', target: 'c4' } },
                { data: { id: 'link13m', weight: 1, source: 'main', target: 'st0' } },
                { data: { id: 'link14m', weight: 1, source: 'main', target: 'st1' } },
                { data: { id: 'link15m', weight: 1, source: 'main', target: 'st2' } },
                { data: { id: 'link16m', weight: 1, source: 'main', target: 'st3' } },
            ]
        },



        layout: {
            /*ring, grid and*/
            name: 'grid',
            padding: 10
        }
    });



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

}); // on dom ready