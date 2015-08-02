import home.epam.jms.common.MessagingSystem;
import home.epam.jms.common.MessagingSystemLocksImpl;
import home.epam.jms.common.MessagingSystemQueueImpl;
import org.junit.Test;

/**
 * Created by vagha_000 on 02-Aug-15.
 */
public class MessagingSystemTest {

    private MessagingSystem messagingSystem;

    @Test
    public void testSendMessageQueueOk() throws InterruptedException {
        messagingSystem = new MessagingSystemQueueImpl();
        messagingSystem.sendMessage("vvvvv");
        messagingSystem.receiveMessage();
    }


    @Test
    public void testMessagingWithReentrantLocksOk(){
        messagingSystem = new MessagingSystemLocksImpl();
        messagingSystem.sendMessage("ttt");
        messagingSystem.receiveMessage();
    }
}
