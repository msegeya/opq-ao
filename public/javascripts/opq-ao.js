function addDynamicCss (location) {
    var cssRef = document.createElement("link");
    cssRef.setAttribute("rel", "stylesheet");
    cssRef.setAttribute("media", "screen");
    cssRef.setAttribute("href", location);
    document.getElementsByTagName("head")[0].appendChild(cssRef);
}

function addDynamicJs (location) {
    var jsRef = document.createElement("script");
    jsRef.setAttribute("src", location);
    jsRef.setAttribute("type", "text/javascript");
    document.getElementsByTagName("head")[0].appendChild(jsRef);
}


// It would be nice to eventually use the following code, but having multiple levels of menus on some pages
// makes this difficult.
/*
function makeActiveClass() {
  $('ul.nav a').filter(function() {
    return this.href == document.location;
  }).parent().addClass('active');
}

$(document).ready(makeActiveClass);
*/

function millisecondsFromTimeUnit(timeUnit) {
  switch(timeUnit) {
    case "Minute"   : return 60000;
    case "Hour"     : return 3600000;
    case "Day"      : return 86400000;
    case "Week"     : return 604800000;
    case "Month"    : return 2592000000;
    case "Year"     : return 31536000000;
  }
}

function currentTimeInMillis() {
  return Date.now();
}

function getPastDate(timeUnit) {
  return currentTimeInMillis() - millisecondsFromTimeUnit(timeUnit);
}