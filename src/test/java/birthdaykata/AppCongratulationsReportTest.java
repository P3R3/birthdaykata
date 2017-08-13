package birthdaykata;

import birthdaykata.domain.models.User;
import birthdaykata.infrastructure.flatFile.domain.models.FlatFileUserFactory;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

public class AppCongratulationsReportTest {

    @Test
    public void reportFailedTest() {

        final Date johnBirthDay = new GregorianCalendar(1982,9,8).getTime();

        final User user = new FlatFileUserFactory().builder()
                .withLastName("Doe")
                .withFirstName("John")
                .withBithday(johnBirthDay)
                .withEmail("john.doe@foobar.com")
                .build();

        final AppCongratulationsReport report = new AppCongratulationsReport();

        //test
        report.failed(user, new Exception("failed to send"));

        //validate
        Assertions.assertThat(report.prepareReport()).isEqualTo("\n" +
                "HAPPY BIRTHDAY REPORT\n" +
                "#Success: 0\n" +
                "#Failed : 1\n" +
                "\n" +
                "HAPPY BIRTHDAY DETAILED REPORT\n" +
                "Users Failed:\n" +
                "john.doe@foobar.com>>failed to send");
    }
    @Test
    public void reportSuccesfulTest() {

        final Date johnBirthDay = new GregorianCalendar(1982,9,8).getTime();

        final User user = new FlatFileUserFactory().builder()
                .withLastName("Doe")
                .withFirstName("John")
                .withBithday(johnBirthDay)
                .withEmail("john.doe@foobar.com")
                .build();

        final AppCongratulationsReport report = new AppCongratulationsReport();

        //test
        report.success(user);

        //validate
        Assertions.assertThat(report.prepareReport()).isEqualTo("\n" +
                "HAPPY BIRTHDAY REPORT\n" +
                "#Success: 1\n" +
                "#Failed : 0\n" +
                "\n" +
                "HAPPY BIRTHDAY DETAILED REPORT\n" +
                "Users Success:[john.doe@foobar.com]");
    }
}
