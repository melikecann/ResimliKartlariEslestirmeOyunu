import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class MatchCards {
    class Card{
        String cardName;
        ImageIcon cardImageIcon;
        
        Card(String cardName, ImageIcon cardImageIcon) {
            this.cardName = cardName;
            this.cardImageIcon = cardImageIcon;

        }

        public String toString(){
            return cardName;
        }
    }

    String[] cardList = {   //track cardNames
        "country cousin",
        "donna duck",
        "mickey mouse",
        "mickeys amateur",
        "moth and flame",
        "postman",
        "snow white",
        "the giant",
        "three little pigs",
        "witch"
    };

    int rows =4;
    int columns = 5;
    int cardWidth = 90;
    int cardHeight = 128;

    ArrayList<Card> cardSet; // create a deck of cards with cardNames and cardImageIcons
    ImageIcon cardBackImageIcon;

    MatchCards(){
        setupCards();
        shuffleCards();
    }

    void setupCards(){
        cardSet = new ArrayList<Card>();
        for  (String cardName : cardList)
        {
            // load each card img
            Image cardImg = new ImageIcon(getClass().getResource("./img/" + cardName + ".jpg")). getImage();
            ImageIcon carImageIcon = new ImageIcon(cardImg. getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH ));

            // create card object and add to cardSet
            Card card = new Card(cardName, carImageIcon);
            cardSet.add(card);
        }
        cardSet.addAll(cardSet); // listedeki kartlari double yapiyo 20 kart olcak 

        //load the back card img
        Image cardBackImg = new ImageIcon(getClass().getResource("./img/back.jpg")).getImage();
        cardBackImageIcon = new ImageIcon(cardBackImg.getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH));
    }

    void shuffleCards(){
        System.out.println(cardSet);
    }
}
