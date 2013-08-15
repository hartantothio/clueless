APP.Size = {
    Room               : {w: 100, h: 100},
    Hallway_Horizontal : {w: 80,  h: 30},
    Hallway_Vertical   : {w: 30,  h: 80}
};

APP.Rooms = {
    'Study'        : {x: 20,  y: 20,  people: [], neighbors: ['Hallway1', 'Hallway3', 'Kitchen']},
    'Hall'         : {x: 170, y: 20,  people: [], neighbors: ['Hallway1', 'Hallway2', 'Hallway4']},
    'Lounge'       : {x: 320, y: 20,  people: [], neighbors: ['Hallway2', 'Hallway5', 'Conservatory']},
    'Library'      : {x: 20,  y: 170, people: [], neighbors: ['Hallway3', 'Hallway6', 'Hallway8']},
    'Billiard'     : {x: 170, y: 170, people: [], neighbors: ['Hallway4', 'Hallway6', 'Hallway7', 'Hallway9']},
    'Dining'       : {x: 320, y: 170, people: [], neighbors: ['Hallway5', 'Hallway7', 'Hallway10']},
    'Conservatory' : {x: 20,  y: 320, people: [], neighbors: ['Hallway8', 'Hallway11', 'Lounge']},
    'Ballroom'     : {x: 170, y: 320, people: [], neighbors: ['Hallway9', 'Hallway11', 'Hallway12']},
    'Kitchen'      : {x: 320, y: 320, people: [], neighbors: ['Hallway10', 'Hallway12', 'Study']}
};

APP.Hallways = {
    Horizontal: {
        'Hallway1'  : {x: 105, y: 55,  people: [], neighbors: ['Study', 'Hall']},
        'Hallway2'  : {x: 255, y: 55,  people: [], neighbors: ['Hall', 'Lounge']},
        'Hallway6'  : {x: 105, y: 205, people: [], neighbors: ['Library', 'Billiard']},
        'Hallway7'  : {x: 255, y: 205, people: [], neighbors: ['Billiard', 'Dining']},
        'Hallway11' : {x: 105, y: 355, people: [], neighbors: ['Conservatory', 'Ballroom']},
        'Hallway12' : {x: 255, y: 355, people: [], neighbors: ['Ballroom', 'Kitchen']}
    },
    Vertical: {
        'Hallway3'  : {x: 55,  y: 105, people: [], neighbors: ['study', 'Library']},
        'Hallway4'  : {x: 205, y: 105, people: [], neighbors: ['Hall', 'Billiard']},
        'Hallway5'  : {x: 355, y: 105, people: [], neighbors: ['Lounge', 'Dining']},
        'Hallway8'  : {x: 55,  y: 255, people: [], neighbors: ['Library', 'Conservatory']},
        'Hallway9'  : {x: 205, y: 255, people: [], neighbors: ['Billiard', 'Ballroom']},
        'Hallway10' : {x: 355, y: 255, people: [], neighbors: ['Dining', 'Kitchen']}
    }
};

// draw rooms
for (var key in APP.Rooms) {
    APP.Draw.rect(APP.Size.Room['w'], APP.Size.Room['h'])
        .attr('data-type', 'room')
        .attr('data-id', key)
        .move(APP.Rooms[key]['x'], APP.Rooms[key]['y'])
        .fill('#fff')
        .stroke({color: '#000', width: 2});
}

// draw hallways

for (var key in APP.Hallways.Horizontal) {
    APP.Draw.rect(APP.Size.Hallway_Horizontal['w'], APP.Size.Hallway_Horizontal['h'])
        .attr('data-type', 'hallway')
        .attr('data-id', key)
        .move(APP.Hallways.Horizontal[key]['x'], APP.Hallways.Horizontal[key]['y'])
        .fill('#fff')
        .stroke({color: '#000', width: 1});
}

for (var key in APP.Hallways.Vertical) {
    APP.Draw.rect(APP.Size.Hallway_Vertical['w'], APP.Size.Hallway_Vertical['h'])
        .attr('data-type', 'hallway')
        .attr('data-id', key)
        .move(APP.Hallways.Vertical[key]['x'], APP.Hallways.Vertical[key]['y'])
        .fill('#fff')
        .stroke({color: '#000', width: 1});
}

// room texts
APP.Draw.text('Study').fill('#999').move(30, 20).font({family: 'Arial', size: 14});
APP.Draw.text('Hall').fill('#999').move(180, 20).font({family: 'Arial', size: 14});
APP.Draw.text('Lounge').fill('#999').move(330, 20).font({family: 'Arial', size: 14});
APP.Draw.text('Library').fill('#999').move(30, 180).font({family: 'Arial', size: 14});
APP.Draw.text('Billiard Room').fill('#999').move(180, 180).font({family: 'Arial', size: 14});
APP.Draw.text('Dining Room').fill('#999').move(330, 180).font({family: 'Arial', size: 14});
APP.Draw.text('Conservatory').fill('#999').move(30, 390).font({family: 'Arial', size: 14});
APP.Draw.text('Ballroom').fill('#999').move(180, 390).font({family: 'Arial', size: 14});
APP.Draw.text('Kitchen').fill('#999').move(330, 390).font({family: 'Arial', size: 14});

// hidden pathways
APP.Passages['1'] = APP.Draw.rect(20, 20)
    .attr('data-type', 'passage')
    .attr('data-id', 'top-left')
    .move(99, 99)
    .fill('#ccc')
    .stroke('#000');
APP.Passages['2'] = APP.Draw.rect(20, 20)
    .attr('data-type', 'passage')
    .attr('data-id', 'top-right')
    .move(321, 99)
    .fill('#ccc')
    .stroke('#000');
APP.Passages['3'] = APP.Draw.rect(20, 20)
    .attr('data-type', 'passage')
    .attr('data-id', 'bottom-left')
    .move(99, 321)
    .fill('#ccc')
    .stroke('#000');
APP.Passages['4'] = APP.Draw.rect(20, 20)
    .attr('data-type', 'passage')
    .attr('data-id', 'bottom-right')
    .move(321, 321)
    .fill('#ccc')
    .stroke('#000');

// characters
APP.Characters.Green = {
    alias: 'Mr. Green',
    svg: APP.Draw.circle(10).move(30, 50).fill('#66CD00') // +10, +30
};

APP.Characters.Scarlet = {
    alias: 'Miss Scarlet',
    svg: APP.Draw.circle(10).move(60, 50).fill('#f00') // +40, +30
};

APP.Characters.Plum = {
    alias: 'Professor Plum',
    svg: APP.Draw.circle(10).move(90, 50).fill('#9400A5') // +70, +30
};

APP.Characters.Peacock = {
    alias: 'Mrs. Peacock',
    svg: APP.Draw.circle(10).move(30, 80).fill('#007FFF') // +10, +60
};

APP.Characters.White = {
    alias: 'Mrs. White',
    svg: APP.Draw.circle(10).move(60, 80).fill('#fff').stroke('#000') // +40, +60
};

APP.Characters.Mustard =  {
    alias: 'Colonel Mustard',
    svg: APP.Draw.circle(10).move(90, 80).fill('#e5e500') // +70, +60
};


