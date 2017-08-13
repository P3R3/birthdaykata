package birthdaykata.domain.greetings;

import birthdaykata.domain.greetings.user.UserGreetingTemplate;
import birthdaykata.domain.models.User;


public interface HappyBirthdayChannel {

    User congratulations(User user) throws CommunicationException;

    interface Factory {
        HappyBirthdayChannel newOne(UserGreetingTemplate greetingTemplate);
    }

}
