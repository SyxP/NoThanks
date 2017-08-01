package TelegramBot;

import java.util.ArrayList;
import java.util.List;
import Core.*;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot{
    private ArrayList<TelegramGame> gameList;
    private boolean DebugMode = false;

    public NoThanks(){
        super();
        gameList = new ArrayList<TelegramGame>();
    }

    @Override
    public String getBotUsername(){
        return "No_Thanks_Bot";
    }

    @Override
    public String getBotToken(){
        String BotToken = null; //Replace with your Bot Token
        return BotToken;
    }

    public void sendMessage(String text, long chat_ID){
        sendMessage(text, chat_ID, false, false);
    }

    public void sendMassage(String text, long chat_ID, boolean type, boolean bankrupted){
        SendMessage message = new SendMessage();
        message.setChatId(chat_ID);
        message.setText(text);

        if(type){
            message.setReplyMarkup(inline(bankrupted));
        }

        message.setParseMode("HTML");
        try{
            Message m = sendMessage(message);
        }catch( TelegramApiException e ){
            e.printStackTrace();
        }
    }

    public EditMessageText editMessage(String text, long chat_ID,  long message_ID, boolean type, boolean bankrupted){
        EditMessageText edited = new EditMessageText();
        edited.setChatId(chat_ID);
        edited.setMessageId(Math.toIntExact(message_ID));
        edited.setText(text);
        edited.setParseMode("HTML");

        if( type ){
            edited.setReplyMarkup(inline(bankrupted));
        }
        return edited;
    }

    private InlineKeyboardMarkup inline(boolean bankrupted){ //1 row, 2 buttons
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<List<InlineKeyboardButton>>();
        List<InlineKeyboardButton> firstRow = new ArrayList<InlineKeyboardButton>();

        firstRow.add( new InlineKeyboardButton().setText("Take").setCallbackData("/take"));
       	if(!bankrupted){
            firstRow.add(new InlineKeyboardButton().setText("No Thanks!").setCallbackData("/no"));
       	}

       	rowsInline.add(firstRow);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public boolean isGroup(Chat c){
        return ( c.isGroupChat() || c.isSuperGroupChat() );
    }

    public TelegramGame getGame(long l){ //Returns null if no game found
        TelegramGame game = null;
        for(TelegramGame g : gameList){
            if(g.getChatID() == l){
                game = g; break;
            }
        }
        return game;
    }

    private void sendHello(long chat_ID){
        sendMessage( "Hi there! I'm @No_Thanks_Bot, and I moderate games of <b>No Thanks!</b> To find out more about the game, use /help.", chat_ID, false);
    }

    private void sendHelp(long chat_ID){
        sendMessage( "No Thanks! is a simple game for 3-5 players. \n\n<b>Setup</b>\nThe deck is numbered 3 to 35, and 9 cards are removed randomly at the start. Each player begins with 11 chips. \n\n<b>Gameplay</b>\nThe first player reveals the top card and either takes it or passes on the card by playing a chip (on the card). \n\nIf a player takes a card, he also gains all the chips on it. The same player then reveals the next card and decides if he wants it. Play goes around in a pre-determined order and continues until the deck runs out. \n\n<b>Scoring</b>\nCards give points according to their value, but cards in a row only score with the lowest value (e.g. 29, 28, 27 count as 27 points.) Chips are worth -1 point each. The player with the lowest points wins.", chat_ID, false);
    }

    private void newGame(Chat c, User u){
        boolean newGroup = true;
        if(isGroup(c)){
            //Check if there is a game in progress in the group.
            for(TelegramGame g : gameList){
                if(g.getChatID() == c.getChatID()){
                    newGroup = false;
                    break;
                }
            }

            if(newGroup){
                //No games in progress
                TelegramGame game = new TelegramGame(c.getChatID());
                gameList.add(game);
                sendMessage("A new game has been started by <b>" + u.getFirstName() + "</b>, /join now!", c.getChatID(), false, false);
                game.addPlayer(new TelegramPlayer(u.getFirstName(), u.getID()));
            } else {
                //Have games in progress
                sendMessage("There is already a game in progress, /join now or wait for the next round.", c.getChatID(), false, false);
            }
        } else {
            //Ensures that it is played only in a group.
            sendMessage("Please play No Thanks! in a group.", c.getChatID(), false, false);
        }
    }

    @Override
    public void onUpdateReceived(Update update){
        long user_ID, chat_ID, msg_ID;
        String Name;
        Message m;
        Chat c;
        User u;

        if(update.hasMessage() && update.getMessage.hasText()){
            m = update.getMessage();
            chat_ID = m.getChatId();
            u = m.getFrom();
            Name = u.getFirstName();
            user_ID = u.getID();
            c = m.getChat();

            String command = m.getText();

            if(DebugMode) System.out.println( Name + "(" + chat_ID + "): " + command);

            switch(command){
            case "/start":
            case "/start@No_Thanks_Bot":
                sendHello(chat_ID);
                break;

            case "/help":
            case "/help@No_Thanks_Bot":
                sendHelp(chat_ID);
                break;

            case "/new":
            case "/new@No_Thanks_Bot":
                newGame(c);
                break;

            case "/join":
            case "/join@No_Thanks_Bot":

            case "/flee":
            case "/flee@No_Thanks_Bot":

            case "/begin":
            case "/begin@No_Thanks_Bot":

            case "/abort":
            case "/abort@No_Thanks_Bot":

            default:
                if (DebugMode) System.out.println("Unknown Command.");
            }
        }else if(update.hasCallbackQuery()){
            //TODO
        }
    }
