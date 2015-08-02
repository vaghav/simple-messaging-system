package home.epam.jms.common;

/**
 * Created by vagha_000 on 02-Aug-15.
 */
public interface MessagingSystem {

    /**
     * Implement simple producer-consumer pattern for messaging
     */
    void sendMessage(String messages);

    String receiveMessage();

}
