package birthdaykata.domain.greetings.user;

import birthdaykata.domain.greetings.Greeting;
import birthdaykata.domain.greetings.GreetingTemplate;
import birthdaykata.domain.models.User;
import birthdaykata.infrastructure.flatFile.domain.models.FlatFileUserFactory;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

public class UserGreetingTemplateTest {

    @Test
    public void userGreetingTemplate_personalize() {


        final Date johnBirthDay = new GregorianCalendar(1982,9,8).getTime();

        final User user = new FlatFileUserFactory().builder()
                .withLastName("Doe")
                .withFirstName("John")
                .withBithday(johnBirthDay)
                .withEmail("john.doe@foobar.com")
                .build();

        final GreetingTemplate template = UserGreetingTemplate.builder()
                .withTitle("Happy birthday!")
                .withMessage("Happy birthday, dear {USER_FIRST_NAME}!")
                .build();

        //test
        Greeting userGreeting = template.personalize(user);

        //validate
        Assertions.assertThat(userGreeting).isNotNull()
                .extracting(Greeting::getTitle, Greeting::getMessage)
                .contains("Happy birthday!", "Happy birthday, dear John!");
    }


}
