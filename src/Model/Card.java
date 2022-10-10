// Authors : Alexander Lisborg
package Model;

public class Card {
    private String question;
    private String[] answers = new String[4];

    public Card(String question, String answer) {
        this.question = question;
        this.answers[0] = answer;
    }
    public Card(String question, String[] answers) {
        this.question = question;
        this.answers[0] = answers[0];
        this.answers[1] = answers[1];
        this.answers[2] = answers[2];
        this.answers[3] = answers[3];
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public String[] setAnswers(String[] newAlt) {
        answers = newAlt;
        return answers;
    }
}
