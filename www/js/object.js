/*global $, jQuery, window, console, APP*/

/**** objects ****/

Clueless.Weapon.Candlestick = new Clueless.Class.Weapon('Candlestick');
Clueless.Weapon.Dagger      = new Clueless.Class.Weapon('Dagger');
Clueless.Weapon.Pipe        = new Clueless.Class.Weapon('Lead Pipe');
Clueless.Weapon.Revolver    = new Clueless.Class.Weapon('Revolver');
Clueless.Weapon.Rope        = new Clueless.Class.Weapon('Rope');
Clueless.Weapon.Wrench      = new Clueless.Class.Weapon('Wrench');

Clueless.Character.Scarlet = new Clueless.Class.Character('Miss Scarlet');
Clueless.Character.Mustard = new Clueless.Class.Character('Colonel Mustard');
Clueless.Character.White   = new Clueless.Class.Character('Mrs. White');
Clueless.Character.Green   = new Clueless.Class.Character('Mr. Green');
Clueless.Character.Peacock = new Clueless.Class.Character('Mrs. Peacock');
Clueless.Character.Plum    = new Clueless.Class.Character('Professor Plum');

Clueless.Room.Study        = new Clueless.Class.Room('Study',         new Clueless.Class.Position(1,1));
Clueless.Room.Hall         = new Clueless.Class.Room('Hall',          new Clueless.Class.Position(3,1));
Clueless.Room.Lounge       = new Clueless.Class.Room('Lounge',        new Clueless.Class.Position(5,1));
Clueless.Room.Library      = new Clueless.Class.Room('Library',       new Clueless.Class.Position(1,3));
Clueless.Room.Billiard     = new Clueless.Class.Room('Billiard Room', new Clueless.Class.Position(3,3));
Clueless.Room.Dining       = new Clueless.Class.Room('Dining Room',   new Clueless.Class.Position(5,3));
Clueless.Room.Conservatory = new Clueless.Class.Room('Conservatory',  new Clueless.Class.Position(1,5));
Clueless.Room.Ball         = new Clueless.Class.Room('Ballroom',      new Clueless.Class.Position(3,5));
Clueless.Room.Kitchen      = new Clueless.Class.Room('Kitchen',       new Clueless.Class.Position(5,5));

Clueless.Hall.Hall1  = new Clueless.Class.Hall('Hall_1',  new Clueless.Class.Position(2,1));
Clueless.Hall.Hall2  = new Clueless.Class.Hall('Hall_2',  new Clueless.Class.Position(4,1));
Clueless.Hall.Hall3  = new Clueless.Class.Hall('Hall_3',  new Clueless.Class.Position(1,2));
Clueless.Hall.Hall4  = new Clueless.Class.Hall('Hall_4',  new Clueless.Class.Position(3,2));
Clueless.Hall.Hall5  = new Clueless.Class.Hall('Hall_5',  new Clueless.Class.Position(5,2));
Clueless.Hall.Hall6  = new Clueless.Class.Hall('Hall_6',  new Clueless.Class.Position(2,3));
Clueless.Hall.Hall7  = new Clueless.Class.Hall('Hall_7',  new Clueless.Class.Position(4,3));
Clueless.Hall.Hall8  = new Clueless.Class.Hall('Hall_8',  new Clueless.Class.Position(1,4));
Clueless.Hall.Hall9  = new Clueless.Class.Hall('Hall_9',  new Clueless.Class.Position(3,4));
Clueless.Hall.Hall10 = new Clueless.Class.Hall('Hall_10', new Clueless.Class.Position(5,4));
Clueless.Hall.Hall11 = new Clueless.Class.Hall('Hall_11', new Clueless.Class.Position(2,5));
Clueless.Hall.Hall12 = new Clueless.Class.Hall('Hall_12', new Clueless.Class.Position(4,5));


