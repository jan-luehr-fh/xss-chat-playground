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
request.open('GET', 'messages', false);
request.send(null);

let source = request.responseText // Note: When using a sanitizing backend, this is tagged html-escaped
let sink = document.getElementById('chatMessages')
sink.innerHTML = source

// Go on loading messages
setInterval(() => {
    // Load messages initially
    let request = new XMLHttpRequest();
    request.open('GET', 'messages', false);
    request.send(null);

    let source = request.responseText
    let sink = document.getElementById('chatMessages')
    sink.innerHTML = source

},1000)
