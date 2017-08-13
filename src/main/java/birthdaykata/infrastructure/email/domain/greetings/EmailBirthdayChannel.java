package birthdaykata.infrastructure.email.domain.greetings;

import birthdaykata.domain.greetings.CommunicationException;
import birthdaykata.domain.greetings.Greeting;
import birthdaykata.domain.greetings.HappyBirthdayChannel;
import birthdaykata.domain.greetings.user.UserGreetingTemplate;
import birthdaykata.domain.models.User;


public class EmailBirthdayChannel implements HappyBirthdayChannel {

    private final UserGreetingTemplate template;
    private final EmailGreetingSender sender;

    public EmailBirthdayChannel(UserGreetingTemplate template) {
        this(template, new EmailGreetingSender());
    }

    EmailBirthdayChannel(UserGreetingTemplate template, EmailGreetingSender sender) {
        this.template = template;
        this.sender = sender;
    }

    @Override
    public User congratulations(User user) throws CommunicationException {
        final Greeting greeting = template.personalize(user);

        sender.sendEmail(user.getEmail(), greeting);

        return user;
    }


    public static class Factory implements HappyBirthdayChannel.Factory {
        @Override
        public HappyBirthdayChannel newOne(UserGreetingTemplate greetingTemplate) {
            return new EmailBirthdayChannel(greetingTemplate);
        }
    }

}
