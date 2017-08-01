package TelegramBot;

import Core.*

public class TelegramGame extends Game{
    private long chat_ID;
    public TelegramGame(long ID){
        super();
        chat_ID = ID;
    }

    public long getChatID(){
        return chat_ID;
    }
}
