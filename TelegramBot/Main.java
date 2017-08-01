package TelegramBot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.swing.JOptionPane;

public class Main{
    public static void main( String[] args ){

        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try{
            botsApi.registerBot( new TelegramBot() );
            JOptionPane.showMessageDialog( null, "No Thanks! bot has started. \nClick OK to terminate." );
            System.out.println( "The bot will terminate." );
            System.exit( 0 );
        } catch( TelegramApiException e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog( null, "Please check your internet connection and try again.", "Error", JOptionPane.ERROR_MESSAGE );
        }
    }
}
