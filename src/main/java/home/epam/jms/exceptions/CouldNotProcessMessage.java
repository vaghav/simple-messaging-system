package home.epam.jms.exceptions;

/**
 * Created by vagha_000 on 02-Aug-15.
 */
public class CouldNotProcessMessage extends RuntimeException {

    public CouldNotProcessMessage(String message) {
        super(message);
    }
}
