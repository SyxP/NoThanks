package TelegramBot;

import Core.Player;

public class TelegramPlayer extends Player{
    private long chat_ID;

    public TelegramPlayer(String s, long ID){
        super(s);
        chat_ID = ID;
    }

    public long getChatID(){
        return chat_ID;
    }
}
