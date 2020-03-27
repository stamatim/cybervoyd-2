import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import io.sentry.context.Context;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.UserBuilder;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {

    private static SentryClient sentry;

    public static void main(String[] args) throws LoginException {

        /* Initialize Sentry */
        /* ---------------------------------- */
        Sentry.init();

        // You can also manually provide the DSN to the ``init`` method.
        // Sentry.init("https://9cbabf1704124dcc99eed9dd4d33ba7f@sentry.io/5177215");

        sentry = SentryClientFactory.sentryClient();
        Main m = new Main();
        m.logWithStaticAPI();
        m.logWithInstanceAPI();
        /* ---------------------------------- */

        /* Java Discord API Builder */
        /* ---------------------------------- */
        JDABuilder builder = new JDABuilder(AccountType.BOT); // Create JDA instance
        String token = "NjkyOTY4NDIxNjkxODgzNjAw.Xn2QOA.QFveCRTHzTPJEY8HQmwepf1_phc"; // Get Discord application bot token
        builder.setToken(token);

        // Add necessary event listeners here
        builder.addEventListener(new Main()); // this class
        // TODO: Add more listeners for more features here
        builder.buildAsync();
        /* ---------------------------------- */
    }

    /**
     * Examples using the (recommended) static API.
     */
    private void logWithStaticAPI() {
        // Note that all fields set on the context are optional. Context data is copied onto
        // all future events in the current context (until the context is cleared).

        // Record a breadcrumb in the current context. By default the last 100 breadcrumbs are kept.
        Sentry.getContext().recordBreadcrumb(
                new BreadcrumbBuilder().setMessage("User made an action").build()
        );

        // Set the user in the current context.
        Sentry.getContext().setUser(
                new UserBuilder().setEmail("hello@sentry.io").build()
        );

        // Add extra data to future events in this context.
        Sentry.getContext().addExtra("extra", "thing");

        // Add an additional tag to future events in this context.
        Sentry.getContext().addTag("tagName", "tagValue");

        /*
         This sends a simple event to Sentry using the statically stored instance
         that was created in the ``main`` method.
         */
        Sentry.capture("This is a test");

        try {
            unsafeMethod();
        } catch (Exception e) {
            // This sends an exception event to Sentry using the statically stored instance
            // that was created in the ``main`` method.
            Sentry.capture(e);
        }
    }

    private void unsafeMethod() {
        throw new UnsupportedOperationException("You shouldn't call this!");
    }

    /**
     * Examples that use the SentryClient instance directly.
     */
    void logWithInstanceAPI() {
        // Retrieve the current context.
        Context context = sentry.getContext();

        // Record a breadcrumb in the current context. By default the last 100 breadcrumbs are kept.
        context.recordBreadcrumb(new BreadcrumbBuilder().setMessage("User made an action").build());

        // Set the user in the current context.
        context.setUser(new UserBuilder().setEmail("hello@sentry.io").build());

        // This sends a simple event to Sentry.
        sentry.sendMessage("This is a test");

        try {
            unsafeMethod();
        } catch (Exception e) {
            // This sends an exception event to Sentry.
            sentry.sendException(e);
        }
    }

    /**
     * Base functionality for bot
     * @param event
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("HEY, We received a message from " +
                event.getAuthor().getName() + ": '" +
                event.getMessage().getContentDisplay() + "'"
        );
        // If the message i s">ping", reply with "pong!"
        if (event.getMessage().getContentRaw().equals("hibot")) {
            String r1 = "Hello, human! Did I end up on the wrong planet again?";
            String r2 = "Hey, Cybervoyd 2.0 here, just checking in!";
            String r3 = "Greetings :)";
            String r4 = "Wanna know a secret? Stamati built me and deployed me to this server. He's honestly kind of a genius";
            int s = (int) (1 + (Math.random() * (4 - 1)));
            String toSend = "Hello, human!";

            event.getChannel().sendMessage(toSend).queue(); // Remember to add message to queue
        }
        // If the message i s">histam", reply with message
        if (event.getMessage().getContentRaw().equals("histam")) {
            String histam = "Hello Stamati - A.K.A Supreme Leader, DJ SHMECKLEBOTTOM, Professor Timmi, The Great Stromboli.net";
            event.getChannel().sendMessage(histam).queue(); // Remember to add message to queue
        }
    }
}
