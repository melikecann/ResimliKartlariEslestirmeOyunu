import java.awt.*;
    import java.util.ArrayList;
    import java.util.Collections;
    import javax.swing.*;
    
public class GameBoard {
    
        private ArrayList<ICard> cards;
        private final int cardWidth;
        private final int cardHeight;

        //constructor metot
        public GameBoard(
            String[] cardNames, int cardWidth, int cardHeight) {
            this.cardWidth = cardWidth;
            this.cardHeight = cardHeight;
            // listeyi boş oluşturduk, ileride kartları ekleyeceğiz
            this.cards = new ArrayList<>();
            //kart isimlerine göre kartları hazırlayıp listeye ekleyecek
            setupCards(cardNames);
            shuffleCards();
        }
    
        private void setupCards(String[] names) {
            //names dizisindeki her isim için döngü başlatılır
            for (String name : names) {
                //kartın görselini yüklüyoruz
                Image img = new ImageIcon(getClass().getResource("/img/" + name + ".jpg")).getImage();
                //görsel yeniden boyutlandırılır kaliteli küçültülür
                ImageIcon icon = new ImageIcon(img.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
                // ICard türünde nesne olarak Card oluşturuluyor
                ICard card = new Card(name, icon);
                //Card isminde yeni bir nesne (kart) oluşturulur ve cards listesine eklenir
                cards.add(new Card(name, icon));
            }
            //cards listesinin bir kopyası alınır ve orijinal cards listesine eklenir
            cards.addAll(new ArrayList<>(cards)); // çiftleri oluştur
        }
    
        public void shuffleCards() {
            //bu method karıştırma işlemini otomatik yapar (swing)
            Collections.shuffle(cards);
        }
    
        public ArrayList<ICard> getCards() {
            //başka sınıflar bu metto sayesinde cards listesine erişebilir
            return cards;
        }
    }
