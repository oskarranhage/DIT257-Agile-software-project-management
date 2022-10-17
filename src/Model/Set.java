//Authors : Alexander Lisborg, Lucas, Anthon
package Model;

import java.util.*;

public class Set {
    private String name;
    private ArrayList<Card> cards = new ArrayList<Card>();
    private setType thisSetType;
    private static Random rand = new Random();

    public enum setType{FlashCard,Spelling,MultipleChoice,Null}
    public void setTypeFlashCard(){thisSetType = setType.FlashCard;}
    public void setTypeSpelling(){thisSetType = setType.Spelling;}
    public void setTypeMultipleChoice(){thisSetType = setType.MultipleChoice;}
    public setType getThisSetType() {return thisSetType;}

    public Set(String name){
        this.name = name;
    }

    public Set(String name, setType type){
        this.name = name;
        this.thisSetType = type;
    }

    public Set(String name, List<Card> cardList, setType type){
        this.name = name;
        cards.addAll(cardList);
        this.thisSetType = type;
    }

    public Set(Set set) {
        this.name = set.getName();
        try {this.cards = set.getCards();} catch (Exception e) {}
        try {this.thisSetType = set.getThisSetType();} catch (Exception e) {}
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

    public static Model.Set shuffleSet(Set set) {
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
            pickedCard.setAnswers(newAlt);

        }
        System.out.println(cardList);
        set.setCards(cardList);
        return set;
    }

    /**
     * Gets the list of answers of a specific card.
     * @param cardIndex The index of the card inside the cards list. (Starts at 0)
     * @return Returns the list of answers with length 4.
     */
    public String[] getAnswersOfCardAtIndex(int cardIndex) {
        return cards.get(cardIndex).getAnswers();
    }

    /**
     * Gets the question at the given card index.
     * @param cardIndex The index of the card inside the cards list. (Starts at 0)
     * @return Returns a string representing the answer.
     */
    public String getQuestionAtIndex(int cardIndex) {return cards.get(cardIndex).getQuestion();}

    /**
     * Gets the card at the given index.
     * @param cardIndex The index of the card inside the cards list. (Starts at 0)
     * @return Returns the card at index cardIndex.
     */
    public Card getCardAtIndex(int cardIndex){
        return cards.get(cardIndex);
    }
}

