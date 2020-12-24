package project.pkg3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Ettienne Martinez
 */
public class Deck {
    
    public static Queue<Card> drawDeck = new LinkedList<Card>();
    public static Stack<Card> discardDeck = new Stack();
    public static ArrayList<Card> player = new ArrayList();

    public Deck() {
        String[] cardSuit = {"Diamond", "Heart", "Club", "Spade"};
        String[] cardValues = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
        for (int i = 0; i < cardSuit.length; i++) {
            for (int j = 0; j < cardValues.length; j++) {
                drawDeck.add(new Card(cardValues[j], cardSuit[i]));
            }
        }
    }
    
    public void displayDeck() {
        ArrayList<Card> adeck = new ArrayList(drawDeck);
        for (int i = 0; i < drawDeck.size(); i++) {
            System.out.println(adeck.get(i));
        }
        System.out.println("Cards in drawDeck: " + adeck.size());
    }

    public void shuffleDeck() {
        Collections.shuffle((List<?>) drawDeck);
    }

    public void firstDeal(ArrayList<Card> Player, ArrayList<Card> Comp) {
        for (int i = 0; i < 4; i++) {
            Player.add(drawDeck.poll());
//            player.add(drawDeck.poll());
            Comp.add(drawDeck.poll());
        }
    }

    public void drawCard(ArrayList<Card> hand){
        if (drawDeck.peek() != null) {
            hand.add(drawDeck.poll());
        } else {
            System.out.println("THE DRAW DECK IS EMPTY");
        }
    }

    public void drawdisCard(ArrayList<Card> hand) {
        if(!discardDeck.empty()){
            System.out.println("Draw a card my dude");
        }else{
            System.out.println("Empty try again later");
        }
    }
    
    public void discardCard(ArrayList<Card> hand, int sel) {
        discardDeck.add(hand.get(sel));
        hand.remove(sel);
    }
    
    public Queue<Card> getDrawDeck() {
        return drawDeck;
    }

    public void setDrawDeck(Queue<Card> drawDeck) {
        Deck.drawDeck = drawDeck;
    }

    public Stack<Card> getDiscardDeck() {
        return discardDeck;
    }

    public void setDiscardDeck(Stack<Card> discardDeck) {
        Deck.discardDeck = discardDeck;
    }
}