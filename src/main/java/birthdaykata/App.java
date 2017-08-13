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

    //use case for communicate a happy birthday to all users
    private final HappyBirthdayUserService service ;

    //aplicacion component to inform to the user interface
    private final CongratulationsReport report;

    public App() {
        //transform a text with tags to a personalized greeting
        final UserGreetingTemplate  happyBirthdayTemplate = UserGreetingTemplate.builder()
                .withTitle("Happy birthday!")
                .withMessage("Happy birthday, dear {USER_FIRST_NAME}!")
                .build();

        //make a happy birthday communication
        final HappyBirthdayChannel channel = new EmailBirthdayChannel(happyBirthdayTemplate);

        //component for retrieve users
        final UserRepository repository = new FlatFileRepository(getFileRepositoryPath());

        //intances the bussiness logic
        service = new HappyBirthdayUserServiceImpl(repository, channel);

        //set a console report informer
        report = new AppCongratulationsReport();
    }

    private String getFileRepositoryPath() {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResource("users.txt").getPath();
    }

    public App(HappyBirthdayUserService service, CongratulationsReport report) {
        this.service = service;
        this.report = report;
    }

    public void run() {
        service.congratulatesEveryone(report);

        report.print();
    }

    public static void main(String[] args )
    {
        new App().run();
    }
}
