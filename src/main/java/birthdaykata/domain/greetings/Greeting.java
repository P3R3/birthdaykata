package birthdaykata.domain.greetings;

public class Greeting {

    private final String title;
    private final String message;

    public Greeting(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Greeting greeting = (Greeting) o;

        if (title != null ? !title.equals(greeting.title) : greeting.title != null) return false;
        return message != null ? message.equals(greeting.message) : greeting.message == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder {
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

        public Greeting build() {
            return new Greeting(title, message);
        }
    }




}
