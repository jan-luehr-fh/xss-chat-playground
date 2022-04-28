
// BEGIN - relevant for scanning
const url = new URL(location.href);
const room_name = url.searchParams.get("room");
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

// END relevant for scanning

// Begin: Boilerplate for demo

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

function send_msg() {
    let inputBox = document.getElementById("message").value;
    let send_request = new XMLHttpRequest();
    send_request.open('POST', 'messages', false);
    send_request.send(inputBox);
}
