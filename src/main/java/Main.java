import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT); // Create JDA instance
        String token = "NjkyOTY4NDIxNjkxODgzNjAw.Xn2QOA.QFveCRTHzTPJEY8HQmwepf1_phc"; // Get Discord application bot token
        builder.setToken(token);

        /* Add necessary event listeners here */
        /* ---------------------------------- */
        builder.addEventListener(new Main()); // this class
        // TODO: Add more listeners for more features here
        /* ---------------------------------- */

        builder.buildAsync();
    }

    /**
     * Base functionality for bot
     * @param event
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " +
                event.getMessage().getContentDisplay()
        );
        // If the message i s">ping", reply with "pong!"
        if (event.getMessage().getContentRaw().equals(">ping")) {
            String pong = "pong :)";
            event.getChannel().sendMessage(pong).queue(); // Remember to add message to queue
        } // If the message i s">histam", reply with message
        if (event.getMessage().getContentRaw().equals(">histam")) {
            String histam = "Hello Stamati - A.K.A Supreme Leader, DJ SHMECKLEBOTTOM, Professor Timmi, The Great Stromboli.net";
            event.getChannel().sendMessage(histam).queue(); // Remember to add message to queue
        }
    }
}
