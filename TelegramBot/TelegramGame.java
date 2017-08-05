package TelegramBot;

import Core.Game;

public class TelegramGame extends Game{
    private long chat_ID, msg_ID;
    public TelegramGame(long ID){
        super();
        chat_ID = ID;
        msg_ID = -1L;
    }

    public long getChatID(){
        return chat_ID;
    }

    public long getMsgID(){
        return msg_ID;
    }

    public void setMsgID(long newID){
        msg_ID = newID;
    }
}
