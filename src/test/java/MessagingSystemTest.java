import home.epam.jms.common.MessagingSystem;
import home.epam.jms.common.MessagingSystemLocksImpl;
import home.epam.jms.common.MessagingSystemQueueImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vagha_000 on 02-Aug-15.
 */
public class MessagingSystemTest {

    private MessagingSystem messagingSystem;

    @Test
    public void testMessagingWithQueueOk() throws InterruptedException {
        messagingSystem = new MessagingSystemQueueImpl();
        messagingSystem.sendMessage("some message");
        String receivedMessage = messagingSystem.receiveMessage();
        Assert.assertNotNull(receivedMessage);
        Assert.assertEquals("some message", receivedMessage);
    }


    @Test(expected = NullPointerException.class)
    public void testMessagingWithQueueExceptionCase() throws InterruptedException {
        messagingSystem = new MessagingSystemQueueImpl();
        messagingSystem.sendMessage(null);
    }


    @Test
    public void testMessagingWithQueueSeveralProducers() throws InterruptedException {
        messagingSystem = new MessagingSystemQueueImpl();

        sendMessages("first message", "second message");

        Assert.assertNotNull(messagingSystem.receiveMessage());
        Assert.assertNotNull(messagingSystem.receiveMessage());
    }


    @Test
    public void testMessagingWithQueueSeveralProducersConsumers() throws InterruptedException {
        messagingSystem = new MessagingSystemQueueImpl();

        sendMessages("first message", "second message");

        List<String> messages = receiveMessages();
        Assert.assertNotNull(messages);
        Assert.assertEquals(2, messages.size());
    }


    @Test
    public void testMessagingWithReentrantLocksOk(){
        messagingSystem = new MessagingSystemLocksImpl();
        messagingSystem.sendMessage("some message");
        String receivedMessage = messagingSystem.receiveMessage();
        Assert.assertNotNull(receivedMessage);
        Assert.assertEquals("some message", receivedMessage);
    }


    @Test
    public void testMessagingReentrantLocksSeveralProducers() throws InterruptedException {
        messagingSystem = new MessagingSystemLocksImpl();

        sendMessages("first message", "second message");

        Assert.assertNotNull(messagingSystem.receiveMessage());
        Assert.assertNotNull(messagingSystem.receiveMessage());
    }


    @Test
    public void testMessagingReentrantLocksSeveralProducersConsumers() throws InterruptedException {
        messagingSystem = new MessagingSystemLocksImpl();

        sendMessages("first message", "second message");

        List<String> messages = receiveMessages();
        Assert.assertNotNull(messages);
        Assert.assertEquals(2, messages.size());
    }


    private void sendMessages(final String firstMessage, final String secondMessage) throws InterruptedException {
        Thread firstProducerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                messagingSystem.sendMessage(firstMessage);
            }
        });
        firstProducerThread.start();

        Thread secondProducerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                messagingSystem.sendMessage(secondMessage);
            }
        });
        secondProducerThread.start();
    }


    private List<String> receiveMessages() throws InterruptedException {
        final List<String> messages = new ArrayList<String>();
        Thread firstProducerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                messages.add(messagingSystem.receiveMessage());
            }
        });
        firstProducerThread.start();

        Thread secondProducerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                messages.add(messagingSystem.receiveMessage());
            }
        });
        secondProducerThread.start();

        firstProducerThread.join();
        secondProducerThread.join();
        return messages;
    }
}
