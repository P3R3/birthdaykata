package birthdaykata.infrastructure.flatFile.domain.models;

import birthdaykata.domain.models.User;
import birthdaykata.domain.repositories.RepositoryNotAvailableException;
import birthdaykata.infrastructure.flatFile.domain.repositories.MalformedFileException;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

public class FlatFileUserFactoryTest {

    @Test
    public void newUser_from_lineRecord() throws RepositoryNotAvailableException {

        final String[] lineRecord  = {"Doe", "John", "1982/10/08", "john.doe@foobar.com"};

        //test
        User user = new FlatFileUserFactory().fromRecord(lineRecord);

        //validate
        final Date expectedBirhday = new GregorianCalendar(1982,9,8).getTime();

        Assertions.assertThat(user).isNotNull()
                .extracting(User::getLastName, User::getFirstName, User::getEmail)
                .containsExactly("Doe", "John", "john.doe@foobar.com");
        Assertions.assertThat(user.getBirthday()).isInSameDayAs(expectedBirhday);
    }

    @Test
    public void newUser_from_lineRecord_with_extra_spaces() throws RepositoryNotAvailableException {

        final String[] lineRecord  = {"\tDoe D. ", "   John   ", "1982/10/08", "john.doe@foobar.com"};

        //test
        User user = new FlatFileUserFactory().fromRecord(lineRecord);

        //validate
        final Date expectedBirhday = new GregorianCalendar(1982,9,8).getTime();

        Assertions.assertThat(user).isNotNull()
                .extracting(User::getLastName, User::getFirstName, User::getEmail)
                .containsExactly("Doe D.", "John", "john.doe@foobar.com");
        Assertions.assertThat(user.getBirthday()).isInSameDayAs(expectedBirhday);
    }

    @Test
    public void newUser_from_incomplete_lineRecord() throws RepositoryNotAvailableException {

        final String[] lineRecord  = {"Doe", "John"};

        try {
            //test
            User user = new FlatFileUserFactory().fromRecord(lineRecord);
            Assertions.fail("exception must be thrown");
        }catch (MalformedFileException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("required: last_name, first_name, date_of_birth, email");
        }
    }

    @Test
    public void newUser_from_wrong_date_in_lineRecord() throws RepositoryNotAvailableException {

        final String[] lineRecord  = {"Doe", "John", "19XX/60/08", "john.doe@foobar.com"};

        try {
            //test
            User user = new FlatFileUserFactory().fromRecord(lineRecord);
            Assertions.fail("exception must be thrown");
        }catch (MalformedFileException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("wrong date");
        }
    }

}
