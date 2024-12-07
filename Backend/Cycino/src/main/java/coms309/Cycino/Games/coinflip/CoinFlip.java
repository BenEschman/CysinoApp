package coms309.Cycino.Games.coinflip;

import coms309.Cycino.Games.Lobby.GameChat;

import java.util.Map;

public class CoinFlip {
    private static final GameChat gameChat = new GameChat();
    public void gameAction(Long lobbyId){
        gameChat.broadcast(lobbyId, "CoinFlip: Your lobby has " + gameChat.getLobbyMembers(lobbyId) + " members.");
    }
}
