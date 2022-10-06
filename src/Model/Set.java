//Authors : Alexander Lisborg
package Model;

import java.util.*;

public class Set {
    String name;
    ArrayList<Card> cards = new ArrayList<Card>();
    setType thisSetType;
    static Random rand = new Random();
    Scanner sc = new Scanner(System.in);

    public enum setType{FlashCard,Spelling,MultipleChoice,Null}
    public void setTypeFlashCard(){thisSetType = setType.FlashCard;}
    public void setTypeSpelling(){thisSetType = setType.Spelling;}
    public void setTypeMultipleChoice(){thisSetType = setType.MultipleChoice;}
    public setType getThisSetType() {return thisSetType;}

    public Set(String name){
        this.name = name;
    }

    public Set(String name, List<Card> cardList, setType type){
        this.name = name;
        cards.addAll(cardList);
        this.thisSetType = type;
    }

    public void addCard(int id,Card c){this.cards.add(id,c);}
    public void addCard(Card c){this.cards.add(c);}
    public void removeCard(Card c){
        this.cards.remove(c);
    }
    public void updateCard(int id, Card card){
        cards.remove(id-1);
        cards.add(id-1,card);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public ArrayList<Card> getCards() {return cards;}

    public void setCards(ArrayList<Card> cards) {this.cards = cards;}

    public static Model.Set shuffleSet(Model.Set set) {
        //ArrayList<Model.Card> cardList = flashSet.cards;
        //Model.Set.cards;
        //ArrayList<Model.Card> cardList = db.getFlashSet("tja").cards;
        //flashSet.getCards();
        ArrayList<Model.Card> cardList = new ArrayList<>();
        ArrayList<Model.Card> tmp = set.cards;
        int amountOfCards = tmp.size();
        int int_random;
        int int_random2;
        Model.Card pickedCard;

        String[] alt;
        String[] newAlt;
        List<String> l;
        //for (Card card : tmp) {
        for (int i = 0; i < amountOfCards;) {
            int_random = rand.nextInt(amountOfCards);
            pickedCard = tmp.get(int_random);

            //shuffle alternatives
            alt = pickedCard.getAnswers();
            newAlt = new String[alt.length];
            l = Arrays.asList(alt);

            ArrayList<String> alternatives = new ArrayList<>(l);

            for (int j = 0; j < alt.length; j++) {
                int_random2 = rand.nextInt(alternatives.size());

                String pickedAlternative = alternatives.get(int_random2);
                newAlt[j] = pickedAlternative;
                alternatives.remove(pickedAlternative);
            }

            tmp.remove(pickedCard);
            cardList.add(pickedCard);
            amountOfCards--;

            System.out.println(int_random);
            System.out.println(pickedCard.getQuestion());
            System.out.println(Arrays.toString(newAlt));
            System.out.println(Arrays.toString(pickedCard.getAnswers()) + "\n");
            pickedCard.setAnswers(newAlt);
            /*Model.Set h = db.getFlashSet("tja");
            System.out.println(h.getCards());
            Model.Set g = db.getFlashSet("tjo");
            System.out.println(g.getCards());*/

        }
        System.out.println(cardList);
        set.setCards(cardList);
        return set;
    }

    public void runSpellingSet(Set set){
        int rightAnswers = 0;
        int wrongAnswers = 0;
        int amountOfQuestions = (set.getCards()).size();
        ArrayList<Card> questionsRight = new ArrayList<>();
        ArrayList<Card> questionsWrong = new ArrayList<>();
        ArrayList<String> rightInputs = new ArrayList<>();
        ArrayList<String> wrongInputs = new ArrayList<>();
        String input = "";
        int count = 1;
        System.out.println("You are running " + set.getName() + " as a spelling set.");
        for (Card card : set.getCards()) {
            System.out.println("Press enter to view next card.");
            sc.nextLine();
            System.out.println("Card " + count + " :");
            System.out.println(card.getQuestion());
            System.out.println("Type out the answer:");
            input = sc.nextLine();
            if(input.toLowerCase().equals(card.getAnswers()[0].toLowerCase())) {
                System.out.println("Your answer was correct!");
                rightAnswers++;
                questionsRight.add(card);
                rightInputs.add(input);
            }
            else {
                System.out.println("Your answer was incorrect!");
                System.out.println("You answered " + input + ", the correct answer is : " + card.getAnswers()[0]);
                wrongAnswers++;
                questionsWrong.add(card);
                wrongInputs.add(input);
            }
            count++;
        }
        System.out.println("\nCongratulations! You reached the end of the set. You gave " + rightAnswers + " correct answers out of " + amountOfQuestions);
        //System.out.println("You answered " + inputs.get(0) + "the correct was " + );
        System.out.println("These are the questions you got right: " + questionsRight);
        System.out.println("This is what you answered: " + rightInputs);
        System.out.println("\nYou got " + wrongAnswers + " question/s wrong.");
        System.out.println("These are the questions you got wrong: " + questionsWrong);
        System.out.println("This is what you answered: " + wrongInputs);
        System.out.println("Press enter to continue.");
        sc.nextLine();
    }

    /**
     * Runs a multiple choice set in the terminal view.
     * @param set The set that will be run
     */
    public void runMultipleChoiceSet(Set set){
        int rightAnswers = 0;
        int wrongAnswers = 0;
        int amountOfQuestions = (set.getCards()).size();
        ArrayList<String> questionsRight = new ArrayList<>();
        ArrayList<String> questionsWrong = new ArrayList<>();
        ArrayList<String> rightInputs = new ArrayList<>();
        ArrayList<String> wrongInputs = new ArrayList<>();
        String input = "";
        int count = 1;
        System.out.println("You are running " + set.getName() + " as a multiple choice set.");
        for (Card card : set.getCards()) {
            System.out.println("Press enter to view next card.");
            sc.nextLine();
            System.out.println("Card " + count + " :");
            System.out.println(card.getQuestion());
            String correctAnswer = card.getAnswers()[0];
            List<String> answersShuffled = Arrays.asList(card.getAnswers());
            Collections.shuffle(answersShuffled);
            System.out.println(answersShuffled);
            System.out.println("Please enter the answer :");
            input = sc.nextLine();
            if (input.toLowerCase().equals(correctAnswer.toLowerCase())) {
                System.out.println("Your answer was correct!");
                rightAnswers++;
                questionsRight.add(card.getQuestion());
            }
            else {
                System.out.println("Your answer was incorrect!");
                System.out.println("The correct answer was : " + correctAnswer);
                wrongAnswers++;
                questionsWrong.add(card.getQuestion());
            }
            count++;
        }
        System.out.println("\nCongratulations! You reached the end of the set. You gave " + rightAnswers + " correct answers out of " + amountOfQuestions);
        //System.out.println("You answered " + inputs.get(0) + "the correct was " + );
        System.out.println("These are the questions you got right: " + questionsRight);
        System.out.println("This is what you answered: " + rightInputs);
        System.out.println("\nYou got " + wrongAnswers + " question/s wrong.");
        System.out.println("These are the questions you got wrong: " + questionsWrong);
        System.out.println("This is what you answered: " + wrongInputs);
        System.out.println("Press enter to continue.");
        sc.nextLine();
    }
}

