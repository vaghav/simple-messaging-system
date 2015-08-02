package home.epam.jms.common;

import home.epam.jms.exceptions.CouldNotProcessMessage;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Created by vagha_000 on 03-Aug-15.
 */
public class MessagingSystemLocksImpl implements MessagingSystem {

    private final Logger logger = Logger.getLogger(MessagingSystemLocksImpl.class.getName());
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean isMessageProduced = false;
    private String message;


    @Override
    public void sendMessage(String message) {
        lock.lock();
        try {
            while (isMessageProduced) {
                condition.await();
            }
        } catch (InterruptedException e) {
            logger.info("Interruption occurs" + e.getMessage());
        }
        this.message = message;
        logger.info("Message produced with id: " + message);
        isMessageProduced = true;
        condition.signalAll();
        lock.unlock();
    }


    @Override
    public String receiveMessage() {
        lock.lock();
        try {
            while (!isMessageProduced) {
                condition.await();
            }
            logger.info("Message consumed with id: " + message);
            isMessageProduced = false;
            condition.signalAll();
            return message;
        } catch (InterruptedException e) {
            logger.info("Interruption occurs" + e.getMessage());
            throw new CouldNotProcessMessage("Could not receive message");
        } finally {
            lock.unlock();
        }
    }
}
