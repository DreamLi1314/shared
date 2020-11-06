var stompClient = null;

function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	$("#name").prop("disabled", connected);

	if(connected) {
		$("conversation").show();
	}
	else {
		$("conversation").hide();
	}
	$("#greetings").html("");
}

function connect() {
	var socket = new SockJS("/chat-channel");// endpoint
	stompClient = Stomp.over(socket);

	stompClient.connect({}, function (frame) {
		setConnected(true);
		console.log("==========Connected==frame===" + frame);
		stompClient.subscribe("/topic/broadcast", function(greeting) {
			console.log("====topic===greeting====", greeting);
			showMsg(JSON.parse(greeting.body).content);
		});
	});
}

function disconnect() {
	if(!!stompClient) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("=====Disconnected!==");
}

function sendWords() {
	stompClient.send("/chat/all", {}, JSON.stringify({
		'name': $("#name").val(),
		'words': $("#words").val()
	}));
}

function showMsg(message) {
    $("#msgs").append("<tr><td>" + message + "</td></tr>");

	 document.getElementById("msgs-container").scrollTop
		 = document.getElementById("msgs-container").scrollHeight;
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() {
    	if(!!!$("#name").val()) {
    		alert("先设置你的名字!");
    		return;
		}
    	connect();
    });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendWords(); });
});

