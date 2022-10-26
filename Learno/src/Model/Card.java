// Authors : Alexander Lisborg
package Model;

/**
 * Card represents a card. This class is used for cards inside all set types.
 */
public class Card {
    /**
     * The question as a string.
     */
    private String question;
    /**
     * The answer as a String, this is used only when playing multiple choice.
     */
    private String correctAnswer;
    /**
     * A list of answers where the first element is the correct answer and all others are dummy answers.
     * This list is used in all set types, when playing flash or spelling sets, the first string in the array
     * is the answer, and the rest are left as null. When playing a multiple choice set, the array is filled out.
     */
    private String[] answers = new String[4];

    /**
     * Initializes the card with a question and an answer. Used when playing flash and spelling sets.
     * @param question The question as a string.
     * @param answer The answer as a string.
     */
    public Card(String question, String answer) {
        this.question = question;
        this.answers[0] = answer;
        correctAnswer = answer;
    }

    /**
     * Initializes the card with a question and a list of answers. Used when playing multiple choice sets.
     * @param question The question as a string.
     * @param answers The answers as a list of strings where the first item is the answer.
     */
    public Card(String question, String[] answers) {
        this.question = question;
        this.answers[0] = answers[0];
        this.answers[1] = answers[1];
        this.answers[2] = answers[2];
        this.answers[3] = answers[3];
        correctAnswer = answers[0];
    }

    /**Getter*/
    public String getQuestion() {
        return question;
    }
    /**Getter*/
    public String[] getAnswers() {
        return answers;
    }
    /**Getter*/
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    /**Setter*/
    public String[] setAnswers(String[] newAlt) {
        answers = newAlt;
        return answers;
    }
}
