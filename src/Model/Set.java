//Authors : Alexander Lisborg
package Model;

import java.util.ArrayList;

public class Set {
    String name;
    ArrayList<Card> cards = new ArrayList<Card>();
    setType thisSetType;

    public enum setType{FlashCard,Spelling,MultipleChoice}
    public void setTypeFlashCard(){thisSetType = setType.FlashCard;}
    public void setTypeSpelling(){thisSetType = setType.Spelling;}
    public void setTypeMultipleChoice(){thisSetType = setType.MultipleChoice;}
    public setType getThisSetType() {return thisSetType;}

    public Set(String name){
        this.name = name;
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
}

