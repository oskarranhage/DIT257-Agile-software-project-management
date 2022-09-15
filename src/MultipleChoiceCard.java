public class MultipleChoiceCard {

    private String question;
    private final String answer;
    private String[] alternatives;

    public MultipleChoiceCard (String question, String[] alternatives, String answer) {
        this.question = question;
        this.answer = answer;
        this.alternatives = alternatives;
    }

    public String setQuestion (String newQuestion) {
        question = newQuestion;
        return question;
    }

    public String[] getAlternatives() {return alternatives;}

    public String getQuestion () {
        return question;
    }

    public String getAnswer () {
        return answer;
    }
}
