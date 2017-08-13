package birthdaykata.domain.repositories;


import org.apache.log4j.Logger;

public class RepositoryNotAvailableException extends  RuntimeException {

    private static final Logger LOGGER = Logger.getLogger(RepositoryNotAvailableException.class);

    public RepositoryNotAvailableException(String message) {
        super(message);
        LOGGER.error(message);
    }

    public RepositoryNotAvailableException(String message, Throwable cause) {
        super(message, cause);
        LOGGER.error(message, cause);
    }
}
