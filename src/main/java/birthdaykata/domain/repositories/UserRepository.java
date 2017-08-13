package birthdaykata.domain.repositories;

import birthdaykata.domain.models.User;

import java.util.List;

public interface UserRepository {

    List<User> getAll() throws RepositoryNotAvailableException;

}
