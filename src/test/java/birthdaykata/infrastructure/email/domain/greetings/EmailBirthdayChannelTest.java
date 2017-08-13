package birthdaykata.infrastructure.email.domain.greetings;

import birthdaykata.domain.greetings.CommunicationException;
import birthdaykata.domain.greetings.Greeting;
import birthdaykata.domain.greetings.HappyBirthdayChannel;
import birthdaykata.domain.greetings.user.UserGreetingTemplate;
import birthdaykata.domain.models.User;
import birthdaykata.infrastructure.flatFile.domain.models.FlatFileUserFactory;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.GregorianCalendar;

@RunWith(MockitoJUnitRunner.class)
public class EmailBirthdayChannelTest {

    @Mock
    private EmailGreetingSender emailSender;

    @Test
    public void test_email_properly_sended() throws CommunicationException {

        final Date johnBirthDay = new GregorianCalendar(1982,9,8).getTime();

        final User user = new FlatFileUserFactory().builder()
                .withLastName("Doe")
                .withFirstName("John")
                .withBithday(johnBirthDay)
                .withEmail("john.doe@foobar.com")
                .build();

        final UserGreetingTemplate template = UserGreetingTemplate.builder()
                .withTitle("Happy birthday!")
                .withMessage("Happy birthday, dear {USER_FIRST_NAME}!")
                .build();

        HappyBirthdayChannel happyBirthdayChannel = new EmailBirthdayChannel(template, emailSender);

        //test
        User congratulated = happyBirthdayChannel.congratulations(user);

        //validate
        Assertions.assertThat(congratulated).isEqualTo(user);

        //verify
        Greeting userGreeting = Greeting.builder()
                .withTitle("Happy birthday!")
                .withMessage("Happy birthday, dear John!")
                .build();
        Mockito.verify(emailSender).sendEmail("john.doe@foobar.com", userGreeting);

    }

}
