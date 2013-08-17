if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function (searchElement /*, fromIndex */ ) {
        'use strict';
        if (this == null) {
            throw new TypeError();
        }
        var n, k, t = Object(this),
            len = t.length >>> 0;

        if (len === 0) {
            return -1;
        }
        n = 0;
        if (arguments.length > 1) {
            n = Number(arguments[1]);
            if (n != n) { // shortcut for verifying if it's NaN
                n = 0;
            } else if (n != 0 && n != Infinity && n != -Infinity) {
                n = (n > 0 || -1) * Math.floor(Math.abs(n));
            }
        }
        if (n >= len) {
            return -1;
        }
        for (k = n >= 0 ? n : Math.max(len - Math.abs(n), 0); k < len; k++) {
            if (k in t && t[k] === searchElement) {
                return k;
            }
        }
        return -1;
    };
}

if (!String.prototype.trim) {
    String.prototype.trim = function () {
        return this.replace(/^\s+|\s+$/g, '');
    };
}

if (!Array.prototype.remove) {
    Array.prototype.remove = function() {
        var what, a = arguments, L = a.length, ax;
        while (L && this.length) {
            what = a[--L];
            while ((ax = this.indexOf(what)) !== -1) {
                this.splice(ax, 1);
            }
        }
        return this;
    };
}


var $body = $('body');
$body.on('click', '[data-toggle="modal"]', function () {
    $.fancybox({
        'centerOnScroll': true,
        'content': $($(this).attr('href'))[0].outerHTML
    });
    return false;
});

$body.on('click', '.modal-close', function () {
    $.fancybox.close();
});

function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}




