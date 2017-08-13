package birthdaykata;

import birthdaykata.businesslogic.CongratulationsReport;
import birthdaykata.businesslogic.HappyBirthdayUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AppTest
{

    @Mock
    private HappyBirthdayUserService service ;

    @Mock
    private CongratulationsReport report;

    @InjectMocks
    private App application;

    @Test
    public void testApp()
    {
        //test
        application.run();

        //verify
        Mockito.verify(service).congratulatesEveryone(report);
        Mockito.verify(report).print();
    }
}

