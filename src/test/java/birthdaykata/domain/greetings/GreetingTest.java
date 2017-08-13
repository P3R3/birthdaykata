package birthdaykata.domain.greetings;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class GreetingTest {

    @Test
    public void newUserGreeting_from_builder() {
        //test
        Greeting userGreeting = Greeting.builder()
                .withTitle("Happy birthday!")
                .withMessage("Happy birthday, dear John!")
                .build();

        //validate
        Assertions.assertThat(userGreeting).isNotNull()
                .extracting(Greeting::getTitle, Greeting::getMessage)
                .contains("Happy birthday!", "Happy birthday, dear John!");
    }

}
