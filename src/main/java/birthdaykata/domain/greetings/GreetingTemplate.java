package birthdaykata.domain.greetings;

public interface GreetingTemplate<Model> {
    Greeting personalize(Model model);

    interface Builder<Model> {
        Builder withTitle(String title);
        Builder withMessage(String message);
        GreetingTemplate<Model> build();
    }

}
