package project.pkg3;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Ettienne Martinez
 */
public class Project3 extends Application {
    
    private static Deck deck = new Deck();
    private static ArrayList<Card> player = new ArrayList<>();
    private static ArrayList<Card> discCards = new ArrayList<>();
    private static ArrayList<Card> comp = new ArrayList<>();
    private static int turn = 0;
    private static Scanner input = new Scanner(System.in);
    
    private static int winnerCount = 0;
    private static boolean winner = false;
    static String green = "\u001B[32m";
    
    private static boolean firstTurn = true;
    
    ObservableList playerList;// = FXCollections.observableArrayList(player);
    ObservableList discardList;
    ObservableList compList;// = FXCollections.observableArrayList(comp);
    
    int selectedIndices;
    
    public static void main(String[] args) {
        
        Application.launch(args);
    }
    
    private static void checkDeck() {
        if (deck.getDrawDeck().isEmpty()) {
            int z = 44;
            for (int i = 0; i < z; i++) {
                deck.getDrawDeck().add(deck.getDiscardDeck().get(i));
            }
            deck.getDiscardDeck().clear();
            deck.shuffleDeck();
        }
    }

    private static void test(ArrayList<Card> hand) {
        winnerCount = 0;
        
        if(hand.get(0).cardValue.equals(hand.get(1).cardValue)){
            winnerCount++;
            if(hand.get(0).cardValue.equals(hand.get(2).cardValue)){
                winnerCount++;
                if(hand.get(0).cardValue.equals(hand.get(3).cardValue)){
                    winnerCount++;
                    if(hand.get(0).cardValue.equals(hand.get(4).cardValue)){
                        winnerCount++;
                        if(winnerCount == 3){
                            System.out.println("You HAVE WON");
                            winner = true;
                        }
                    }
                }
            }
        }
        
        winnerCount = 0;
        if(hand.get(1).cardValue.equals(hand.get(0).cardValue)){
            winnerCount++;
            if(hand.get(1).cardValue.equals(hand.get(2).cardValue)){
                winnerCount++;
                if(hand.get(1).cardValue.equals(hand.get(3).cardValue)){
                    winnerCount++;
                    if(hand.get(1).cardValue.equals(hand.get(4).cardValue)){
                        winnerCount++;
                        if(winnerCount == 3){
                            System.out.println("You HAVE WON");
                            winner = true;
                        }
                    }
                }
            }
        }
        
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        deck.shuffleDeck();
        deck.firstDeal(player, comp);
        
        primaryStage.setTitle("Card Game");
        
        VBox mainPane = new VBox();
        HBox containerPane = new HBox();
        
        VBox leftPane = new VBox();
        Label leftHandlbl = new Label("Your Hand");
        ListView handView = new ListView();
        
        playerList = FXCollections.observableArrayList(player);
        handView.setItems(playerList);
        
        handView.setMaxHeight(300);
        leftPane.getChildren().addAll(leftHandlbl, handView);
        leftPane.setAlignment(Pos.CENTER);
        
        VBox centerPane = new VBox();
        ListView discardPileView = new ListView();
        discardPileView.setMaxHeight(100);
        Button drawCardButton = new Button("Start by Drawing Card");
        Button removeCardButton = new Button("Remove and Pick Up Card");
        centerPane.getChildren().addAll(discardPileView, drawCardButton, removeCardButton);
        centerPane.setAlignment(Pos.CENTER);
        
        discardList = FXCollections.observableArrayList(deck.getDiscardDeck());
        discardPileView.setItems(discardList);
        
        VBox rightPane = new VBox();
        Label rightHandlbl = new Label("Opponent Hand");
        ListView computerCardView = new ListView();
        computerCardView.setMaxHeight(300);
        rightPane.getChildren().addAll(rightHandlbl, computerCardView);
        rightPane.setAlignment(Pos.CENTER);
        
        compList = FXCollections.observableArrayList(comp);
        computerCardView.setItems(compList);
        
        Label generalLbl = new Label("ATTENTION:   Draw Card");
        
        containerPane.getChildren().addAll(leftPane, centerPane, rightPane);
        mainPane.getChildren().addAll(containerPane, generalLbl);
        
        //********************************************************************
        
        drawCardButton.setOnAction((ActionEvent e) -> {

            try{
                if (deck.getDiscardDeck().empty()) {

                    System.out.println("A");
                    if (turn==0 && playerList.size() <= 5 && firstTurn==true) {//Player turn
                        System.out.println("The discard deck is empty -- you must draw a new card");

                        deck.drawCard(player);

                        playerList = FXCollections.observableArrayList(player);
                        handView.setItems(playerList);

                        System.out.println(playerList.size());
                        drawCardButton.setText("Opponent Turn");

                        test(player);
                        turn++;
                        System.out.println(turn);
                        firstTurn = false;

                    } else if(turn==1 && compList.size() <= 4) {//Opponent Turn

                        System.out.println("B");
                        drawCardButton.setText("Discard and Draw New Card");

                        deck.drawCard(comp);
                        compList = FXCollections.observableArrayList(comp);
                        computerCardView.setItems(compList);
                        System.out.println(compList);

                        if(deck.getDiscardDeck().empty()){
                            int cpRand = (int) (Math.random() * (5 - 1) + 1);
                            deck.discardCard(comp, cpRand);
                        }

                        compList = FXCollections.observableArrayList(comp);
                        computerCardView.setItems(compList);
                        System.out.println(compList);


                        discardList = FXCollections.observableArrayList(deck.getDiscardDeck());
                        System.out.println(discardList);
                        discardPileView.setItems(discardList);

                        test(comp);
                        turn--;
                        System.out.println(turn);
                    }
            }else if(!deck.getDiscardDeck().empty() && turn == 0) { //discard not empty
                
                try{
                    if(turn == 0 && player.size() == 5 && handView.getSelectionModel().getSelectedIndex() > -1){    //robot  card in discard deck               
                        selectedIndices = handView.getSelectionModel().getSelectedIndex();

                        System.out.println("Before " + playerList);


                        player.remove(selectedIndices);
                        deck.drawCard(player);

                        playerList = FXCollections.observableArrayList(player);
                        handView.setItems(playerList);

                        drawCardButton.setText("Opponent Turn");
                        System.out.println(turn);
                        //*********************


                            System.out.println("After " + playerList);


                        turn++;
                    }else{
                        generalLbl.setText("Must Select a Card to Remove in order to Draw a new Card");
                    }
                    
            }catch(Exception ex){
                System.out.println(ex);
            }finally{
                test(player);
                checkDeck();
            }
                
            }else if(turn == 1 && compList.size() < 5 && !deck.getDiscardDeck().empty()) {    //robot  card in discard deck
                drawCardButton.setText("Discard and Draw New Card");
                
                System.out.println("D");
                int cpRand = (int) (Math.random() * (3 - 1) + 1);
                int cpRand2 = (int) (Math.random() * (5 - 1) + 1);
                if (cpRand == 1) {
                    System.out.println("chose to draw then discard");
                    
                    deck.discardCard(comp, cpRand2);
                    discardList = FXCollections.observableArrayList(deck.getDiscardDeck());
                    discardPileView.setItems(discardList);
                    
                    deck.drawCard(comp);
                    compList = FXCollections.observableArrayList(comp);
                    computerCardView.setItems(compList);
                    
                    System.out.println("Your opponet discarded the " + deck.getDiscardDeck().peek().toString());
                }
                
                if (cpRand == 2) {
                    System.out.println("Chose to pop and replace");
                    
                    comp.add(deck.getDiscardDeck().pop());
                    deck.discardCard(comp, cpRand2);
                    
                    compList = FXCollections.observableArrayList(comp);
                    computerCardView.setItems(compList);
                    
                    
                    discardList = FXCollections.observableArrayList(deck.getDiscardDeck());
                    discardPileView.setItems(discardList);
                    
                    System.out.println("Your opponet discarded the " + deck.getDiscardDeck().peek().toString());
                }
                
                test(comp);
                turn--;
            }
            
            
        }catch(Exception ex){
            System.out.println(ex);
        }finally{
            test(player);
            test(comp);
            checkDeck();
        }
            
        });
        
        removeCardButton.setOnAction((ActionEvent e) -> {
            try{
                if(turn == 0 && player.size() == 5 && handView.getSelectionModel().getSelectedIndex() > -1){    //robot  card in discard deck               
                    
                    selectedIndices = handView.getSelectionModel().getSelectedIndex();
                    player.add(deck.getDiscardDeck().pop());
                    
                    deck.discardCard(player, selectedIndices);

                    playerList = FXCollections.observableArrayList(player);
                    handView.setItems(playerList);

                    discardList = FXCollections.observableArrayList(deck.getDiscardDeck());
                    System.out.println(discardList);
                    discardPileView.setItems(discardList);

                    drawCardButton.setText("Opponent Turn");
                    turn++;
                }else{
                    generalLbl.setText("You cannot Remove");
                }
            }catch(Exception ex){
                System.out.println(ex);
            }finally{
                test(player);
                test(comp);
                checkDeck();
            }
        });
        
        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}