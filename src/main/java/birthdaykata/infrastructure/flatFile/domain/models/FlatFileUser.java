package birthdaykata.infrastructure.flatFile.domain.models;

import birthdaykata.domain.models.User;
import birthdaykata.domain.repositories.RepositoryNotAvailableException;
import birthdaykata.infrastructure.flatFile.domain.repositories.MalformedFileException;
import com.lambdista.util.Try;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlatFileUser implements User {

    private String lastName;
    private String firstName;
    private Date birthday;
    private String email;

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public static class Buider implements User.Builder {
        FlatFileUser model = new FlatFileUser();

        @Override
        public Builder withLastName(String lastName) {
            model.lastName = lastName;
            return this;
        }

        @Override
        public Builder withFirstName(String firstName) {
            model.firstName = firstName;
            return this;
        }

        @Override
        public Builder withBithday(Date date) {
            model.birthday = date;
            return this;
        }

        @Override
        public Builder withEmail(String email) {
            model.email = email;
            return this;
        }

        @Override
        public User build() {
            return model;
        }
    }

}
