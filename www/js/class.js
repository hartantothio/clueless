/*global $, jQuery, window, console, APP*/

Clueless.Class.Card = Class.create({

    initialize: function (name) {
        this.name = name;
    }

});

Clueless.Class.Position = Class.create({

    initialize: function (x, y) {
        this.x = x;
        this.y = y;
    }

});

Clueless.Class.Weapon    = Class.create(Clueless.Class.Card, {});
Clueless.Class.Character = Class.create(Clueless.Class.Card, {});
Clueless.Class.Location  = Class.create(Clueless.Class.Card, {

    initialize: function ($super, name, position) {
        $super(name);
        this.position = position;
    }

});

Clueless.Class.Hall = Class.create(Clueless.Class.Location, {});
Clueless.Class.Room = Class.create(Clueless.Class.Location, {});


// ClientGame

Clueless.ClientGame = function (name, style, password) {


};


// ClientPlayer

Clueless.ClientPlayer = function () {
    this.status = 1; // active by default
};

Clueless.ClientPlayer.prototype.setCharacter = function (character) {
    this.character = character;
};

Clueless.ClientPlayer.prototype.setCards = function (cards) {
    this.cards = cards;
};

Clueless.ClientPlayer.prototype.setLocation = function (location) {
    this.location = location;
};

Clueless.ClientPlayer.prototype.move = function (location) {

};

Clueless.ClientPlayer.prototype.suggest = function (room, weapon, character) {

};

Clueless.ClientPlayer.prototype.accuse = function (room, weapon, character) {

};

Clueless.ClientPlayer.prototype.disprove = function (card) {

};

Clueless.ClientPlayer.prototype.quit = function () {

};

Clueless.ClientPlayer.prototype.sendMessage = function (message) {

};

Clueless.ClientPlayer.prototype.changeLanguage = function (language) {

};

Clueless.ClientPlayer.prototype.toggleSound = function (active) {

};

Clueless.ClientPlayer.prototype.ignorePlayer = function (character, active) {

};

Clueless.ClientPlayer.prototype.receiveNotification = function (message) {

};

Clueless.ClientPlayer.prototype.displayDetectivePad = function () {

};

Clueless.ClientPlayer.prototype.setNotes = function (notes) {

};

Clueless.ClientPlayer.prototype.isActive = function () {

};








