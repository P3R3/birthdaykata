package birthdaykata.domain.models;

import java.util.Date;

public interface User {

    String getLastName();

    String getFirstName();

    Date getBirthday();

    String getEmail();

    interface Builder  {
        Builder withLastName(String lastName);
        Builder withFirstName (String firstName);
        Builder withBithday (Date date);
        Builder withEmail(String email);
        User build();
    }

    interface Factory {
        Builder builder();
    }

}
