package birthdaykata.businesslogic;

import birthdaykata.domain.greetings.HappyBirthdayChannel;
import birthdaykata.domain.repositories.UserRepository;
import com.lambdista.util.Try;

public class HappyBirthdayUserServiceImpl implements HappyBirthdayUserService {

    private final UserRepository userRepository;
    private final HappyBirthdayChannel channel;

    public HappyBirthdayUserServiceImpl(UserRepository userRepository, HappyBirthdayChannel channel) {
        this.userRepository = userRepository;
        this.channel = channel;
    }

    @Override
    public void congratulatesEveryone(CongratulationsReport reportToFill) {
                userRepository.getAll()
                        .forEach(user ->
                                Try.apply(() -> channel.congratulations(user))
                                .map(reportToFill::success)
                                .recover(error->reportToFill.failed(user, error))
                        );
    }


}
