package birthdaykata.infrastructure.email.domain.greetings;

import birthdaykata.domain.greetings.Greeting;
import org.apache.log4j.Logger;


public class EmailGreetingSender {


    private static final Logger LOGGER = Logger.getLogger(EmailGreetingSender.class);


    public void sendEmail(String email, Greeting greeting)  {
        //todo: send by email this greeting
        LOGGER.info("SENDED mail to>> "+email+" with BODY>>"+greeting.toString());
    }

}
