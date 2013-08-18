/*global $, jQuery, window, console, APP*/

// utility functions
function disableMove() {
    APP.Me.can_move = false;
} // disableMove()

function enableMove() {
    APP.Me.can_move = true;
} // enableMove()

function canMove(target) {
    if (isLocationHallway(target, 'h') && APP.Hallways.Horizontal[target].people.length) {
        return false;
    } else if (isLocationHallway(target, 'v') && APP.Hallways.Vertical[target].people.length) {
        return false;
    }
    return isValidNeighbor(getMyLocation(), target);
} // canMove()

function isLocationHallway(location, type) {
    if (type === undefined) {
        return APP.Hallways.Vertical[location] || APP.Hallways.Horizontal[location];
    } else if (type === 'v') {
        return APP.Hallways.Vertical[location];
    } else if (type === 'h') {
        return APP.Hallways.Horizontal[location];
    }
} // isLocationHallway()

function isLocationRoom(location) {
    return APP.Rooms[location];
} // isLocationRoom()

function disableSuggest() {
    APP.Me.can_suggest = false;
    $('#btn-accuse-suggest').text('Accuse');
    $('#game-accuse-suggest').find('select[name="action"] option[value="suggest"]').remove();
} // disableSuggest()

function enableSuggest() {
    APP.Me.can_suggest = true;
    $('#submit-suggest').show();
    $('#game-accuse-suggest').find('select[name="action"]').prepend('<option value="suggest">Suggest</option>');
} // enableSuggest()

function closeModal() {
    $.fancybox.close();
} // closeModal()

function getDateTime() {
    var date = new Date(),
        hour = date.getHours(),
        min  = date.getMinutes();


    if (hour < 10) {
        hour = '0' + hour;
    }

    if (min < 10) {
        min = '0' + min;
    }

    return hour + ':' + min;
}

function addSystemNotification(message) {
    var key = null;

    for (key in APP.Characters) {
        while (message.indexOf(APP.Characters[key].alias) >= 0) {
            message = message.replace(APP.Characters[key].alias, '<span style="color: ' + APP.Characters[key]['color'] + '">' + APP.Characters[key].alias + '</span>');
        }
    }

    message = '<p><b>' + getDateTime() + '</b> - ' + message + '</p>';
    $notification_system.prepend(message);
} // addSystemNotification()

function addChatNotification(message) {
    var character = message.substring(1, message.indexOf(')', 1)),
        key = null;

    message = (message.replace('(' + character + ')', '')).trim();

    for (key in APP.Characters) {
        if (APP.Characters[key].alias === character) {
            character = '<span style="color: ' + APP.Characters[key]['color'] + '">' + character + '</span>';
            break;
        }
    }

    message = '<p><b>' + character + ' @ ' + getDateTime() + '</b> - ' + message + '</p>';
    $notification_chat.prepend(message);
} // addChatNotification()

function init() {

    // Get list of characters and their location
    // For this character, also get the cards

    APP.Players = {
        'Professor Plum': {
            location: 'Ballroom'
        },
        'Mr. Green': {
            location: 'Study'
        },
        'Ms. Peacock': {
            location: 'Lounge'
        },
        'Mrs. White': {
            location: 'Billiard'
        },
        'Miss Scarlet': {
            location: 'Kitchen'
        },
        'Colonel Mustard': {
            location: 'Library'
        }
    };

    moveCharacter(APP.Me.character, APP.Me.location);

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

    if (isLocationRoom(old_location)) {
        APP.Rooms[old_location].people.splice(
            APP.Rooms[old_location].people.indexOf(character),
            1
        );
    } else if (isLocationHallway(old_location, 'h')) {
        APP.Hallways.Horizontal[old_location].people.splice(
            APP.Hallways.Horizontal[old_location].people.indexOf(character),
            1
        );
    } else if (isLocationHallway(old_location, 'v')) {
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
    if (isLocationRoom(location1)) {
        index = APP.Rooms[location1].neighbors.indexOf(location2);
    } else if (isLocationHallway(location1, 'h')) {
        index = APP.Hallways.Horizontal[location1].neighbors.indexOf(location2);
    } else if (isLocationHallway(location1, 'v')) {
        index = APP.Hallways.Vertical[location1].neighbors.indexOf(location2);
    }
    return index >= 0;
} // isValidNeighbor()

function moveCharacter(character, target) {
    var total_people = 0, remainder = null,
        x = 0, y = 0;

    if (isLocationRoom(target)) {
        x = 20; y = 30; // initial position

        // calculate the offset
        total_people += APP.Rooms[target]['people'].length;
        if (total_people < 3) {
            x += (total_people * 25);
        } else {
            x += (total_people - 3) * 25;
            y = 60;
        }

        x += APP.Rooms[target]['x'];
        y += APP.Rooms[target]['y'];
        APP.Rooms[target]['people'].push(character);
    } else if (isLocationHallway(target, 'h')) {
        x = 5; y = 5; // initial position

        // calculate the offset
        total_people += APP.Hallways.Horizontal[target]['people'].length;
        if (total_people < 3) {
            x += (total_people * 20);
        } else {
            x += (total_people - 3) * 20;
            y = 15;
        }

        x += APP.Hallways.Horizontal[target]['x'];
        y += APP.Hallways.Horizontal[target]['y'];
        APP.Hallways.Horizontal[target]['people'].push(character);
    } else if (isLocationHallway(target, 'v')) {
        // calculate the offset
        total_people += APP.Hallways.Vertical[target]['people'].length;
        total_people = parseInt(total_people / 2, 10);
        y = 3 + (total_people * 20);
        x = (APP.Hallways.Vertical[target]['people'].length % 2 === 1) ? 15 : 3;
        x += APP.Hallways.Vertical[target]['x'];
        y += APP.Hallways.Vertical[target]['y'];
        APP.Hallways.Vertical[target]['people'].push(character);
    }

    APP.Characters[character]['svg'].animate(1000).move(x, y);
} // moveCharacter()






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


