package birthdaykata;

import birthdaykata.businesslogic.CongratulationsReport;
import birthdaykata.domain.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppCongratulationsReport implements CongratulationsReport {

    private final List<User> successful = new ArrayList<>();
    private final List<User> failed = new ArrayList<>();
    private final List<Throwable> errors = new ArrayList<>();

    @Override
    public User success(User user) {
        this.successful.add(user);
        return user;
    }

    @Override
    public User failed(User user, Throwable error) {
        this.failed.add(user);
        this.errors.add(error);
        return user;
    }

    @Override
    public void print() {
        System.out.println(prepareReport());
    }

    protected String prepareReport() {
        StringBuilder report = new StringBuilder();

        report.append("\nHAPPY BIRTHDAY REPORT");
        report.append("\n#Success: ").append(this.successful.size());
        report.append("\n#Failed : ").append(this.failed.size());

        report.append("\n\nHAPPY BIRTHDAY DETAILED REPORT");
        if (this.successful.size()>0) {
            report.append("\nUsers Success:").append(this.successful.stream().map(User::getEmail).collect(Collectors.toList()));
        }
        if (this.failed.size()>0) {
            report.append("\nUsers Failed:");
            for (int i = 0; i < this.failed.size(); i++) {
                User user = this.failed.get(i);
                Throwable error = this.errors.get(i);
                report.append("\n").append(user.getEmail()).append(">>").append(error.getMessage());
            }
        }

        return report.toString();
    }

}
