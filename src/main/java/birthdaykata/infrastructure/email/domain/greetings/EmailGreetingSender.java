package birthdaykata.infrastructure.email.domain.greetings;

import birthdaykata.domain.greetings.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailGreetingSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailBirthdayChannel.class);

    public void sendEmail(String email, Greeting greeting)  {
        //todo: send by email this greeting
        LOGGER.info("SEND MAIL TO {} with body {}", email, greeting);
    }

}
