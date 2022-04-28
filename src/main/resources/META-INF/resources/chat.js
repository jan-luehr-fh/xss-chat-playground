// Note: Instead of a stored XSS-attack that is harder to model-server-side,
// One could also go for an refelected XSS-Attach
// E.g.
/*
var room_name = location.href // Source, but is there actually a way to decode a room-name with propagators?
 */

var room_name = "lounge";

function send_msg() {
    let inputBox = document.getElementById("message").value;
    let send_request = new XMLHttpRequest();
    send_request.open('POST', 'messages/' + room_name, false);
    send_request.send(inputBox);
}

// Load messages initially
// ... one could skip this code if msgs are loaded initially. That'd required transforming setInterval( () => {}) to a regular call
// I don't know if scan can do so yet
let request = new XMLHttpRequest();
request.open('GET', 'messages/' + room_name , false);
request.send(null);





// Propagation on JSON.parse .... or sth
let source = request.responseText // Note: When using a sanitizing backend, this is tagged html-escaped
let sink = document.getElementById('chatMessages')
let sink2 = document.getElementById('rootTitle')
let sink3 = document.getElementById('helpText')
sink.innerHTML = source.room_name
sink2.innerHTML = source.messages
sink3.innerHTML = source.help_text // We not it's save!

// Go on loading messages
setInterval(() => {
    // Load messages initially
    let request = new XMLHttpRequest();
    request.open('GET', 'messages/' + room_name, false);
    request.send(null);

    let source = request.responseText
    let sink = document.getElementById('chatMessages')
    sink.innerHTML = source

},1000)
