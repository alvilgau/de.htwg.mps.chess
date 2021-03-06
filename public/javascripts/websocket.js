$(function () {
    connect();
});

// Connect with WebSocket
function connect() {
    var protocol = location.protocol == "http:" ? "ws://" : "wss://";
    var socket = new WebSocket(protocol + location.host + "/socket");

    socket.onmessage = function (msg) {
        try {
            var data = JSON.parse(msg.data);
        } catch (e) {
            console.warn(e);
            return;
        }

        if (data.type == "reloadLobby") {
            $("#pageContent").load("/play #pageContent > *");
        } else if (data.type == "start") {
            $("#pageContent").load("/chess");
        }
        else {
            // refresh game content
            var game = $("chess-game")[0];
            if (game == null) {
                setTimeout(function () {
                    var game = $("chess-game")[0];
                    if (game != null) {
                        game.setData(data);
                        refreshStatusMessages(data);
                    }
                }, 200);
            } else {
                game.setData(data);
                refreshStatusMessages(data);
            }
        }

        if (data.type == "won") {
            showModalGameInfo("Won", "Congratulation you have won!");
        } else if (data.type == "lost") {
            showModalGameInfo("Lost", "You have lost!");
        }
    }

}