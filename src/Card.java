public class Card {
    private String question;
    private String answer;

    Card(String question, String answer) {
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
