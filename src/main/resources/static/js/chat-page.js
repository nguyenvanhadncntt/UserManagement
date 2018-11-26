$(document).ready(function() {
	$('#action_menu_btn').click(function() {
		$('.action_menu').toggle();
	});
	var socket = new SockJS('/wschat');
    stompClient = Stomp.over(socket);

//    stompClient.connect({}, onConnected, onError);
});
