package Core;

import java.util.ArrayList;
import java.lang.StringBuilder;

public class Game{
    private ArrayList<Player> playerList;
    private DeckList deckList;
    private int currPlayerTurn;
    private boolean gameStarted;

    public Game(){
        playerList = new ArrayList<Player>();
        deckList = new DeckList();
        currPlayer = 0;
        gameStarted = false;
    }

    private void incrementPlayer(){
        currPlayerTurn++;
        if(currPlayer == getNumPlayers()) currPlayerTurn = 0;
    }

    public void addPlayer(Player P){
        //Consider what happens when you try to add a player to a game which already started
        //Consider what happens when you try to add a player with the same name
        playerList.add(P);
    }

    public void removePlayer(String s){
        for(Player p : playerList){
            if(p.getName().equals(s)){
                playerList.remove(p);
                break;
            }
        }
    }

    public boolean hasStarted(){
        return gameStarted;
    }

    public int getNumPlayers(){
        return playerList.size();
    }

    public Player currPlayer(){
        return playerList.get(currPlayerTurn);
    }

    public boolean currPlayerBroke(){
        return currPlayer().getChips() == 0;
    }

    public void take(){
        Player p = currPlayer();
        p.gainCard(deckList.getCard());
        p.incrementChips(getChips());
        p.removeCard();
    }

    public void pass(){
        Player p = currPlayer();
        p.decrementChips();
        deckList.addChip();
        incrementPlayer();
    }

    public String getStatus(){
        StringBuilder Info = new StringBuilder();
        for(Player p : playerList) Info.append(p.toString());
        Info.append("\nIt is <b>" + currPlayer().getName() + "'s turn</b>\n");
        Info.append(deckList.toString());
        return Info.toString();
    }

    //Code Winning Player Display + End Game/Reset Game(?)

    public boolean gameOver(){
        return deckList.getDeckSize() == 0;
    }

    public void startGame(){
        gameStarted = true;
        deckList = new DeckList();
        Collections.shuffle(playerList);
        for(Player p : playerList) p.setChips(11);
    }
}
