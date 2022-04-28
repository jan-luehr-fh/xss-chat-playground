// Note: Instead of a stored XSS-attack that is harder to model-server-side,
// One could also go for an refelected XSS-Attach
// E.g.
/*
var room_name = location.href // Source, but is there actually a way to decode a room-name with propagators?
 */

const url = new URL(location.href);
const room_name = url.searchParams.get("room");

//var room_name = "lounge";

function send_msg() {
    let inputBox = document.getElementById("message").value;
    let send_request = new XMLHttpRequest();
    send_request.open('POST', 'messages', false);
    send_request.send(inputBox);
}

// Load messages initially
// ... one could skip this code if msgs are loaded initially. That'd required transforming setInterval( () => {}) to a regular call
// I don't know if scan can do so yet
let request = new XMLHttpRequest();
request.open('GET', 'messages/' + room_name , false);
request.send(null);

// Initial-load
let response = request.responseText // Note: When using a sanitizing backend, this is tagged html-escaped
let returned_data = JSON.parse(response);
let sink_messages = document.getElementById('chatMessages')
let sink_room = document.getElementById('roomName')
let sink_help = document.getElementById('helpText')
sink_room.innerHTML = returned_data.room // Reflected XSS - An attacker controls the room-name
sink_messages.innerHTML = returned_data.msgs // Stored XSS: An attack can send evil messages
sink_help.innerHTML = returned_data.help // No taint flow, help_text is always static at the server

//Go on loading messages
setInterval(() => {
    let request = new XMLHttpRequest();
    request.open('GET', 'messages/' + room_name , false);
    request.send(null);
    let response = request.responseText // Note: When using a sanitizing backend, this is tagged html-escaped
    let returned_data = JSON.parse(response);
    let sink_messages = document.getElementById('chatMessages')
    let sink_room = document.getElementById('roomName')
    let sink_help = document.getElementById('helpText')
    sink_room.innerHTML = returned_data.room // Reflected XSS
    sink_messages.innerHTML = returned_data.msgs // Stored XSS: An attack controls the room-name
    sink_help.innerHTML = returned_data.help // No taint flow, help_text is always static at the server

},2000)
