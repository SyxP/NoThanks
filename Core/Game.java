package Core;

import java.util.ArrayList;
import java.util.Collections;
import java.lang.StringBuilder;

public class Game{
    protected ArrayList<Player> playerList;
    protected DeckList deckList;
    protected int currPlayerTurn;
    protected boolean gameStarted;

    public Game(){
        playerList = new ArrayList<Player>();
        deckList = new DeckList();
        currPlayerTurn = 0;
        gameStarted = false;
    }

    private void incrementPlayer(){
        currPlayerTurn++;
        if(currPlayerTurn == getNumPlayers()) currPlayerTurn = 0;
    }

    public boolean inGame(String s){
        for(Player p : playerList){
            if(p.getName().equals(s)) return true;
        }
        return false;
    }

    public void addPlayer(Player P){
        if(gameStarted == true){
            System.out.println("Unable to add player. Game has started.");
            return;
        }
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
        if(gameStarted == false){
            System.out.println("Unable to Pass. Game has not started.");
            return;
        }
        Player p = currPlayer();
        p.gainCard(deckList.getCard());
        p.incrementChips(deckList.getChips());
        deckList.removeCard();
    }

    public void pass(){
        if(gameStarted == false){
            System.out.println("Unable to Pass. Game has not started.");
            return;
        }
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

    public boolean gameOver(){
        return deckList.getDeckSize() == 0;
    }

    public void startGame(){
        gameStarted = true;
        deckList = new DeckList();
        Collections.shuffle(playerList);
        for(Player p : playerList) p.setChips(11);
    }

    private ArrayList<Player> winner(){
        ArrayList<Player> winPlayers = new ArrayList<Player>();
        for(Player p : playerList){
            if(!winPlayers.isEmpty() && winPlayers.get(0).getScore() < p.getScore()){
                winPlayers.clear();
            }
            if(!winPlayers.isEmpty() && !(winPlayers.get(0).getScore() > p.getScore())){
                winPlayers.add(p);
            }
        }
        return winPlayers;
    }

    public String getWinStatus(){ //Also Ends the current Game
        gameStarted = false;
        StringBuilder Info = new StringBuilder();
        Info.append("<b>Score</b>\n\n");
        for(Player p : playerList){
            Info.append(p.getName() + ": " + p.getScore() + " points.\n");
        }
        ArrayList<Player> winPlayers = winner();
        if(winPlayers.size() == 1){
            Info.append("\n\n<b>" + winPlayers.get(0).getName() + "</b> won with " + winPlayers.get(0).getScore() + " points.");
        }else{
            Info.append("The following players ");
            for(Player p : winPlayers) Info.append(p.getName() + " ");
            Info.append("</b> have won win " + winPlayers.get(0).getScore() + " points.");
        }
        Info.append(" \n\nPlay a /new game?");
        return Info.toString();
    }
}
