//Authors : Alexander Lisborg, Lucas, Anthon
package Model;

import java.util.*;

/**
 * A set object represents a collection of cards with a name and a type.
 */
public class Set {
    /**
     * The name of the set represented by a string.
     */
    String name;
    /**
     * The list of cards.
     */
    ArrayList<Card> cards = new ArrayList<Card>();
    /**
     * The type of the set, this type is represented by the enum setType.
     */
    private setType thisSetType;

    /**
     * Enum setType. (FlashCard, Spelling, MultipleChoice, Null)
     */
    public enum setType{FlashCard,Spelling,MultipleChoice,Null}

    /**
     * Sets thisSetType to FlashCard.
     */
    public void setTypeFlashCard(){thisSetType = setType.FlashCard;}
    /**
     * Sets thisSetType to Spelling.
     */
    public void setTypeSpelling(){thisSetType = setType.Spelling;}
    /**
     * Sets thisSetType to MultipleChoice.
     */
    public void setTypeMultipleChoice(){thisSetType = setType.MultipleChoice;}
    /**Getter for setType*/
    public setType getThisSetType() {return thisSetType;}

    /**
     * Constructor for creating a set object with a given name, setType is left as null.
     * @param name The name of the set.
     */
    public Set(String name){
        this.name = name;
    }

    /**
     * Constructor for creating a set object with a given name and a given setType.
     * @param name The name of the set.
     * @param type The type of the set.
     */
    public Set(String name, setType type){
        this.name = name;
        this.thisSetType = type;
    }

    /**
     * Constructor for creating a set object with a given name, a given setType and a given list of cards.
     * @param name The name of the set.
     * @param cardList The list of cards that should be included in the set.
     * @param type The type of the set.
     */
    public Set(String name, List<Card> cardList, setType type){
        this.name = name;
        cards.addAll(cardList);
        this.thisSetType = type;
    }

    /**
     * Adds a card to the list of cards.
     * @param c The card that should be added.
     */
    public void addCard(Card c){this.cards.add(c);}

    /**
     * Removes the given card from the set.
     * @param c The card that should be removed.
     */
    public void removeCard(Card c){
        this.cards.remove(c);
    }

    /**
     * Updates the card at the given index.
     * @param id The id of the card that should be updated.
     * @param card The new card.
     */
    public void updateCard(int id, Card card){
        cards.remove(id-1);
        cards.add(id-1,card);
    }

    /**Getter*/
    public String getName() {return name;}
    /**Setter*/
    public void setName(String name) {this.name = name;}
    /**Getter*/
    public ArrayList<Card> getCards() {return cards;}
    /**Setter*/
    public void setCards(ArrayList<Card> cards) {this.cards = cards;}

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

