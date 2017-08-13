package birthdaykata.businesslogic;

import birthdaykata.domain.greetings.CommunicationException;
import birthdaykata.domain.greetings.HappyBirthdayChannel;
import birthdaykata.domain.models.User;
import birthdaykata.domain.repositories.UserRepository;
import birthdaykata.infrastructure.flatFile.domain.models.FlatFileUserFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

@RunWith(MockitoJUnitRunner.class)
public class HappyBirthdayUserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HappyBirthdayChannel channel;

    @Mock
    private CongratulationsReport report;

    @InjectMocks
    private HappyBirthdayUserServiceImpl service;


    @Test
    public void congratulatesEveryone_all_success() throws CommunicationException {
        final Date johnBirthDay = new GregorianCalendar(1982,9,8).getTime();

        final User user = new FlatFileUserFactory().builder()
                .withLastName("Doe")
                .withFirstName("John")
                .withBithday(johnBirthDay)
                .withEmail("john.doe@foobar.com")
                .build();

        //stub retrieve users
        Mockito.when(userRepository.getAll()).thenReturn(Collections.singletonList(user));

        //stub nicely sended
        Mockito.when(channel.congratulations(user)).thenReturn(user);

        //test
        service.congratulatesEveryone(report);

        //verify
        Mockito.verify(channel).congratulations(user);
        Mockito.verify(report).success(user);
    }

    @Test
    public void congratulatesEveryone_all_failed() throws CommunicationException {
        final Date johnBirthDay = new GregorianCalendar(1982,9,8).getTime();

        final User user = new FlatFileUserFactory().builder()
                .withLastName("Doe")
                .withFirstName("John")
                .withBithday(johnBirthDay)
                .withEmail("john.doe@foobar.com")
                .build();

        //stub retrieve users
        Mockito.when(userRepository.getAll()).thenReturn(Collections.singletonList(user));

        //stub nicely sended
        CommunicationException mailError = new CommunicationException("mail error", new Exception());
        Mockito.when(channel.congratulations(user)).thenThrow(mailError);

        //test
        service.congratulatesEveryone(report);

        //verify
        Mockito.verify(channel).congratulations(user);
        Mockito.verify(report).failed(user, mailError);
    }



}
