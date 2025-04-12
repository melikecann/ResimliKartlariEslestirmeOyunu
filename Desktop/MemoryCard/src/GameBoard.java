import javax.swing.*;
    import java.awt.*;
    import java.util.ArrayList;
    import java.util.Collections;
    
public class GameBoard {
    
        private ArrayList<Card> cards;
        private final int cardWidth;
        private final int cardHeight;
    
        public GameBoard(String[] cardNames, int cardWidth, int cardHeight) {
            this.cardWidth = cardWidth;
            this.cardHeight = cardHeight;
            this.cards = new ArrayList<>();
            setupCards(cardNames);
            shuffleCards();
        }
    
        private void setupCards(String[] names) {
            for (String name : names) {
                Image img = new ImageIcon(getClass().getResource("/img/" + name + ".jpg")).getImage();
                ImageIcon icon = new ImageIcon(img.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
                cards.add(new Card(name, icon));
            }
            cards.addAll(new ArrayList<>(cards)); // çiftleri oluştur
        }
    
        public void shuffleCards() {
            Collections.shuffle(cards);
        }
    
        public ArrayList<Card> getCards() {
            return cards;
        }
    }
