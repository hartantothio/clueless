/*global $, jQuery, window, console, APP*/

function init() {

    // Get list of characters and their location
    // For this character, also get the cards

    APP.Players = {
        'Plum': {
            location: 'ballroom'
        },
        'Green': {
            location: 'study'
        },
        'Peacock': {
            location: 'lounge'
        },
        'White': {
            location: 'billiard'
        },
        'Scarlet': {
            location: 'kitchen'
        },
        'Mustard': {
            location: 'library'
        }
    };

    APP.Me = {
        character: 'Plum',
    };

    moveCharacter('Plum', APP.Me.location);

    for (var key in APP.Players) {
        moveCharacter(key, APP.Players[key].location);
    }

} // init()

function getMyLocation() {
    return APP.Players[APP.Me.character].location;
} // getMyLocation()

function updateCharacter(character, new_location) {
    var old_location = APP.Players[character].location;
    APP.Players[character].location = new_location;
    if (APP.Rooms[old_location]) {
        APP.Rooms[old_location].people.splice(
            APP.Rooms[old_location].people.indexOf(character),
            1
        );
    } else if (APP.Hallways.Horizontal[old_location]) {
        APP.Hallways.Horizontal[old_location].people.splice(
            APP.Hallways.Horizontal[old_location].people.indexOf(character),
            1
        );
    } else if (APP.Hallways.Vertical[old_location]) {
        APP.Hallways.Vertical[old_location].people.splice(
            APP.Hallways.Vertical[old_location].people.indexOf(character),
            1
        );
    }
} // updateCharacter()

function getHiddenPassageDestination(passage) {
    switch (passage) {
        case 'top-left':
            return 'kitchen';
        case 'top-right':
            return 'conservatory';
        case 'bottom-left':
            return 'lounge';
        case 'bottom-right':
            return 'study';
    }
    return passage;
} // getHiddenPassageDestination()

function isValidNeighbor(location1, location2) {
    var index = -1;
    if (APP.Rooms[location1]) {
        index = APP.Rooms[location1].neighbors.indexOf(location2);
    } else if (APP.Hallways.Horizontal[location1]) {
        index = APP.Hallways.Horizontal[location1].neighbors.indexOf(location2);
    } else if (APP.Hallways.Vertical[location1]) {
        index = APP.Hallways.Vertical[location1].neighbors.indexOf(location2);
    }
    return index >= 0;
} // isValidNeighbor()

function moveCharacter(character, target) {
    var total_people = 0, remainder = null,
        x = 0, y = 0;

    if (APP.Rooms[target]) {
        x = 20; y = 30; // initial position

        // calculate the offset
        total_people = APP.Rooms[target]['people'].length;
        if (total_people < 3) {
            x += (total_people * 25);
        } else {
            x += (total_people - 3) * 25;
            y = 60;
        }

        x += APP.Rooms[target]['x'];
        y += APP.Rooms[target]['y'];
        APP.Rooms[target]['people'].push(character);
    } else if (APP.Hallways.Horizontal[target]) {
        x = 5; y = 5; // initial position

        // calculate the offset
        total_people = APP.Hallways.Horizontal[target]['people'].length;
        if (total_people < 3) {
            x += (total_people * 20);
        } else {
            x += (total_people - 3) * 20;
            y = 15;
        }

        x += APP.Hallways.Horizontal[target]['x'];
        y += APP.Hallways.Horizontal[target]['y'];
        APP.Hallways.Horizontal[target]['people'].push(character);
    } else if (APP.Hallways.Vertical[target]) {
        // calculate the offset
        total_people = APP.Hallways.Vertical[target]['people'].length;
        total_people = parseInt(total_people / 2, 10);
        y = 3 + (total_people * 20);
        x = (APP.Hallways.Vertical[target]['people'].length % 2 === 1) ? 15 : 3;
        x += APP.Hallways.Vertical[target]['x'];
        y += APP.Hallways.Vertical[target]['y'];
        APP.Hallways.Vertical[target]['people'].push(character);
    }

    APP.Characters[character]['svg'].animate(1000).move(x, y);
} // moveCharacter()

function accuse(accusation) {

} // accuse()

function suggest(suggestion) {

} // suggest()

function disprove(card) {

} // disprove()

function quit() {

} // quit()

function sendMessage(message) {

} // sendMessage()

function changeLanguage(language) {

} // changeLanguage()

function toggleSound(enable) {

} // toggleSound()

function receiveNotifcation(notification) {

} // receiveNotification()

function displayDetectivePad() {

} // displayDetectivePad()


init();

$body.on('click', 'rect', function () {
    var $this = $(this),
        type = $this.data('type'),
        room = $this.data('id');

    if (type === 'passage') {
        room = getHiddenPassageDestination(room);
    }

    if (!isValidNeighbor(room, getMyLocation())) {
        alert('Invalid move');
        return false;
    } else {
        updateCharacter(APP.Me.character, room);
        moveCharacter(APP.Me.character, room);
    }

});

/* suggest/accuse */
$('#submit-suggest').on('click', function () {
    alert('Continue with suggestion!');
    return false;
});

$('#submit-accuse').on('click', function () {
    alert('Continue with accusation!');
    return false;
});





/*
$(function () {

    $('body').on('click', 'rect', function () {
        var $this = $(this),
            x = $this.attr('x'),
            y = $this.attr('y'),
            type = $this.data('type'),
            id = $this.data('id'),
            target = id;

        if (type === 'passage') {
            switch (id) {
            }

            x = APP.Rooms[id]['x'];
            y = APP.Rooms[id]['y'];
        }

        x = +x; y = +y;

        if (type === 'room' && APP.Rooms[id]) {
            console.log(x + ' ' + y);
            start = { add_x: 10, add_y: 30, inc: 30 };
            x += start['add_x'];
            y += start['add_y'];
            console.log(x + ' ' + y);

        } else if (APP.Hallways.Horizontal[id]) {

        } else if (APP.Hallways.Vertical[id]) {

        }

        APP.Char.Green.animate(1000).move(x, y);
        //console.dir($(this).attr('x'));
    });

    // top1.click(function () {
    //     c_plum.animate(1000)
    //         .move(400 * Math.random(), 400 * Math.random());
    // });

    // APP.Char.Plum.mouseover(function () {
    //     this.animate(1000)
    //         .move(400 * Math.random(), 400 * Math.random());
    // });


});
*/


