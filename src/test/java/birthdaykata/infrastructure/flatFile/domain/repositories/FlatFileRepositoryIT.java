package birthdaykata.infrastructure.flatFile.domain.repositories;

import birthdaykata.domain.models.User;
import birthdaykata.domain.repositories.RepositoryNotAvailableException;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class FlatFileRepositoryIT {

    @Test
    public void getAll_when_file_not_exists_then_error() throws IOException {
        try {
            //test
            new FlatFileRepository("/notExistingFile.txt").getAll();
            Assertions.fail("exception must be thrown");
        }catch (RepositoryNotAvailableException e) {
            //validate
            Assertions.assertThat(e.getMessage()).isEqualTo("Repo not available");
        }
    }

    @Test
    public void getAll_when_extra_spaces_in_file_then_remove_it() throws IOException {
        final List<String> fileContent = Arrays.asList(
                "    \tDoe, John wick  , 1982/10/08,       john.doe@foobar.com      "
        );

        File tempFile = File.createTempFile("tmp", "userRepo.txt");

        //prepare content
        Files.write(tempFile.toPath(), fileContent);

        //test
        List<User> users = new FlatFileRepository(tempFile.getAbsolutePath()).getAll();

        //validate
        final Date johnBirthDay = new GregorianCalendar(1982,9,8).getTime();

        Assertions.assertThat(users).hasSize(1)
                .extracting(User::getLastName, User::getFirstName, User::getBirthday, User::getEmail)
                .contains(
                        Tuple.tuple("Doe", "John wick", johnBirthDay, "john.doe@foobar.com")
                );
    }

    @Test
    public void getAll_when_header_in_file_then_ignore_it() throws IOException {
        final List<String> fileContent = Arrays.asList(
                "#last_name, first_name, date_of_birth, email",
                "Doe, John, 1982/10/08, john.doe@foobar.com"
        );

        File tempFile = File.createTempFile("tmp", "userRepo.txt");

        //prepare content
        Files.write(tempFile.toPath(), fileContent);

        //test
        List<User> users = new FlatFileRepository(tempFile.getAbsolutePath()).getAll();

        //validate
        final Date johnBirthDay = new GregorianCalendar(1982,9,8).getTime();

        Assertions.assertThat(users).hasSize(1)
                .extracting(User::getLastName, User::getFirstName, User::getBirthday, User::getEmail)
                .contains(
                        Tuple.tuple("Doe", "John", johnBirthDay, "john.doe@foobar.com")
                );

    }

    @Test
    public void getAll_when_extra_lines_in_file_then_ignore_it() throws IOException {
        final List<String> fileContent = Arrays.asList(
                "#last_name, first_name, date_of_birth, email",
                "",
                "Doe, John, 1982/10/08, john.doe@foobar.com",
                "",
                "Ann, Mary, 1975/09/11, mary.ann@foobar.com"
        );

        File tempFile = File.createTempFile("tmp", "userRepo.txt");

        //prepare content
        Files.write(tempFile.toPath(), fileContent);

        //test
        List<User> users = new FlatFileRepository(tempFile.getAbsolutePath()).getAll();

        //validate
        final Date johnBirthDay = new GregorianCalendar(1982,9,8).getTime();
        final Date maryBirthDay = new GregorianCalendar(1975,8,11).getTime();

        Assertions.assertThat(users).hasSize(2)
                .extracting(User::getLastName, User::getFirstName, User::getBirthday, User::getEmail)
                .contains(
                        Tuple.tuple("Doe", "John", johnBirthDay, "john.doe@foobar.com"),
                        Tuple.tuple("Ann", "Mary", maryBirthDay, "mary.ann@foobar.com")
                );

    }


}
