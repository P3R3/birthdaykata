package birthdaykata.domain.greetings;

import birthdaykata.domain.models.User;


public interface HappyBirthdayChannel {

    User congratulations(User user) throws CommunicationException;

}
