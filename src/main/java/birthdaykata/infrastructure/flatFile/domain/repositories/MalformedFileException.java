package birthdaykata.infrastructure.flatFile.domain.repositories;

import birthdaykata.domain.repositories.RepositoryNotAvailableException;

public class MalformedFileException extends RepositoryNotAvailableException  {
    public MalformedFileException(String message) {
        super(message);
    }

    public MalformedFileException(String message, Exception e) {
        super(message, e);
    }
}
