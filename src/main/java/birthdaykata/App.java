package birthdaykata;

import birthdaykata.businesslogic.CongratulationsReport;
import birthdaykata.businesslogic.HappyBirthdayUserService;
import birthdaykata.businesslogic.HappyBirthdayUserServiceImpl;
import birthdaykata.domain.greetings.HappyBirthdayChannel;
import birthdaykata.domain.greetings.user.UserGreetingTemplate;
import birthdaykata.domain.repositories.UserRepository;
import birthdaykata.infrastructure.email.domain.greetings.EmailBirthdayChannel;
import birthdaykata.infrastructure.flatFile.domain.repositories.FlatFileRepository;


public class App 
{

    private final UserGreetingTemplate happyBirthdayTemplate;

    private final HappyBirthdayChannel channel;

    private final UserRepository repository ;

    private final HappyBirthdayUserService service ;

    private String getFileRepositoryPath() {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResource("users.txt").getPath();
    }

    public App() {
        happyBirthdayTemplate = UserGreetingTemplate.builder()
                .withTitle("Happy birthday!")
                .withMessage("Happy birthday, dear {USER_FIRST_NAME}!")
                .build();

        channel = new EmailBirthdayChannel(happyBirthdayTemplate);

        repository = new FlatFileRepository(getFileRepositoryPath());

        service = new HappyBirthdayUserServiceImpl(repository, channel);
    }



    public static void main(String[] args )
    {

        final CongratulationsReport report = new AppCongratulationsReport();

        new App().service.congratulatesEveryone(report);

        report.print();

    }
}
