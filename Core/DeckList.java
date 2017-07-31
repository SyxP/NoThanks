package Core;

import java.util.ArrayList;
import java.util.Collections;
import java.lang.StringBuilder;

public class DeckList{
    private ArrayList<Integer> deckList;
    private int Counters;

    public DeckList(){
        this(3,36,9);
    }

    public DeckList(int Min, int Max, int Removed){
        Counters = 0;

        deckList = new ArrayList<Integer>();
        for(int i = Min; i < Max; i++) deckList.add(i);
        Collections.shuffle(deckList);
        for(int i = 0; i < Removed; i++) removeCard();
    }

    public int getDeckSize(){
        return deckList.size();
    }

    public void addChip(){
        addChip(1);
    }

    public void addChip(int k){
        Counters += k;
    }

    public void removeCard(){
        Counters = 0;
        deckList.remove(getDeckSize() - 1);
    }

    public int getCard(){
        return deckList.get(getDeckSize() - 1);
    }

    public int getCounter(){
        return Counters;
    }

    @Override
    public String toString(){
        StringBuilder Info = new StringBuilder();
        Info.append("[");
        for(int i = getDeckSize() - 1; i >= 0; i--){
            Info.append(deckList.get(i));
            if(i != 0) Info.append(",");
        }
        Info.append("]");
        return Info.toString();
    }
}
