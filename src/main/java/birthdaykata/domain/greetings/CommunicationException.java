package birthdaykata.domain.greetings;

import org.apache.log4j.Logger;

public class CommunicationException extends Exception {

    private static final Logger LOGGER = Logger.getLogger(CommunicationException.class);

    public CommunicationException(String message, Throwable cause) {
        super(message, cause);
        LOGGER.error(message, cause);
    }
}
