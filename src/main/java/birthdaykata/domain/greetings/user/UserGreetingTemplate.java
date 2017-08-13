package birthdaykata.domain.greetings.user;

import birthdaykata.domain.greetings.Greeting;
import birthdaykata.domain.greetings.GreetingTemplate;
import birthdaykata.domain.models.User;

public class UserGreetingTemplate implements GreetingTemplate<User> {

    private static final String FIRST_NAME_TAG = "{USER_FIRST_NAME}";

    private final String title;
    private final String message;

    public UserGreetingTemplate(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public Greeting personalize(User user) {

        final String personalizedTitle = title.replace(FIRST_NAME_TAG, user.getFirstName());
        final String personalizedMessage = message.replace(FIRST_NAME_TAG, user.getFirstName());

        return Greeting.builder().withTitle(personalizedTitle).withMessage(personalizedMessage).build();
    }


    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder implements GreetingTemplate.Builder<User> {
        private String title;
        private String message;

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public UserGreetingTemplate build() {
            return new UserGreetingTemplate(title, message);
        }
    }

}
