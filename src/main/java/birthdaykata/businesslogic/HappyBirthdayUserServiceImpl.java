package birthdaykata.businesslogic;

import birthdaykata.domain.greetings.HappyBirthdayChannel;
import birthdaykata.domain.models.User;
import birthdaykata.domain.repositories.UserRepository;
import com.lambdista.util.Try;

import java.util.Calendar;
import java.util.Date;
import java.util.function.Predicate;

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
                        .stream()
                        .filter(byBirthdayIsToday())
                        .forEach(user ->
                                Try.apply(() -> channel.congratulations(user))
                                .map(reportToFill::success)
                                .recover(error->reportToFill.failed(user, error))
                        );
    }

    private Predicate<User> byBirthdayIsToday() {
        return user -> birthdayIsToday(user.getBirthday());
    }

    private boolean birthdayIsToday(Date birthday) {
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthday);
        final Calendar today = Calendar.getInstance();

        return birth.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                birth.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH);
    }

}
