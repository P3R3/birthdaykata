package birthdaykata.infrastructure.flatFile.domain.models;

import birthdaykata.domain.models.User;
import birthdaykata.domain.repositories.RepositoryNotAvailableException;
import birthdaykata.infrastructure.flatFile.domain.repositories.MalformedFileException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlatFileUserFactory implements User.Factory {
    private static final DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public User.Builder builder() {
        return new FlatFileUser.Buider();
    }

    public User fromRecord(String[] record) throws RepositoryNotAvailableException {
        if (record.length!=4) {throw new MalformedFileException("required: last_name, first_name, date_of_birth, email");}

        return builder()
                .withLastName(extractString(record[0]))
                .withFirstName(extractString(record[1]))
                .withBithday(extractDate(record[2]))
                .withEmail(extractString(record[3]))
                .build();
    }

    private String extractString(String lastName) {
        return lastName.replaceAll("^\\s+", "").replaceAll("\\s+$", "");
    }

    private Date extractDate(String source) throws MalformedFileException {
        try {
            return df.parse(source);
        } catch (ParseException e) {
            throw new MalformedFileException("wrong date", e);
        }
    }

}
