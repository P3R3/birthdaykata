package birthdaykata.businesslogic;

import birthdaykata.domain.models.User;

public interface CongratulationsReport {

    User success(User user);

    User failed(User user, Throwable error);

    void print();

}
