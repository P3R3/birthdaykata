package birthdaykata.infrastructure.flatFile.domain.models;

import birthdaykata.domain.models.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Date;

public class FlatFileUserTest {

    @Test
    public void newUser_from_factory() {

        User.Factory factory = new FlatFileUserFactory();

        final Date birthday = new Date();

        //test
        User user = factory.builder()
                .withLastName("Doe")
                .withFirstName("John")
                .withBithday(birthday)
                .withEmail("john.doe@foobar.com")
                .build();

        //validate
        Assertions.assertThat(user).isNotNull().isInstanceOf(FlatFileUser.class)
                .extracting(User::getLastName, User::getFirstName, User::getBirthday, User::getEmail)
                .containsExactly("Doe", "John", birthday, "john.doe@foobar.com");
    }

}
