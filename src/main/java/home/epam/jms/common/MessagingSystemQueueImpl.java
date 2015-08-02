package home.epam.jms.common;

import home.epam.jms.exceptions.CouldNotProcessMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

/**
 * Created by vagha_000 on 03-Aug-15.
 */
public class MessagingSystemQueueImpl implements MessagingSystem {

    private final Logger logger = Logger.getLogger(MessagingSystemQueueImpl.class.getName());
    private BlockingQueue messageQueue = new LinkedBlockingQueue();

    @Override
    public void sendMessage(String message) {
        try {
            messageQueue.put(message);
            logger.info("Message produces : " + message);
        } catch (InterruptedException ex) {
            logger.info("Thread interruption occurs" + ex.getMessage());
            throw new CouldNotProcessMessage("Could not send message. Please try again");
        }
    }

    @Override
    public String receiveMessage() {
        String message;
        try {
            message = (String) messageQueue.take();
            logger.info("Message consumed : " + message);
        } catch (InterruptedException ex) {
            logger.info("Thread interruption occurs" + ex.getMessage());
            throw new CouldNotProcessMessage("Could not receive message");
        }
        return message;
    }

}
