package Core;

import java.util.ArrayList;
import java.lang.StringBuilder;

public class Player{
    protected String Name;
    protected int chipCount;
    protected ArrayList<Integer> cardList;

    public Player(String s){
        setName(s);
        setChips(11);
        cardList = new ArrayList<Integer>();
    }

    private void setName(String s){
        Name = s;
    }

    public void setChips(int i){
        if(i < 0){
            System.out.println("Cannot assign negative chips!");
        } else {
            chipCount = i;
        }
    }

    public void decrementChips(){
        decrementChips(1);
    }

    public void decrementChips(int k){
        setChips(getChips() - k);
    }

    public void incrementChips(){
        incrementChips(1);
    }

    public void incrementChips(int k){
        setChips(getChips() + k);
    }

    public void gainCard(int card){
        for(int i = 0; i < cardList.size(); i++){
            if(cardList.get(i) < card) continue;
            cardList.add(i,card);
        }
        cardList.add(card);
    }

    public String getName(){
        return Name;
    }

    public int getChips(){
        return chipCount;
    }

    public int getScore(){
        int score = -getChips();
        for(int i = 0; i < cardList.size(); i++){
            if(i > 0 && (cardList.get(i) - cardList.get(i-1) == 1)) continue;
            score += cardList.get(i);
        }
        return score;
    }

    @Override
    public String toString(){
        StringBuilder Info = new StringBuilder();
        Info.append("Player " + Name + " has currently " + getChips() + " chip(s).\n");
        Info.append("They has taken the cards [");
        for(int i = 0; i < cardList.size(); i++){
            Info.append(cardList.get(i));
            if(i != cardList.size() - 1) Info.append(",");
        }
        Info.append("].\n");
        Info.append("Their current total score is " + getScore() + ".\n");
        return Info.toString();
    }
}
