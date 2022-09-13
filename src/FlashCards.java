public class FlashCards {
    private final String question;
    private final String answer;

    FlashCards(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    String getQuestion() {
        return question;
    }

    String getAnswer() {
        return answer;
    }
}
