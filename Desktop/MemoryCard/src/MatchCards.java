import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class MatchCards extends JFrame {
    private final int rows = 4;
    private final int columns = 5;
    private final int cardWidth = 90;
    private final int cardHeight = 128;

    private final String[] cardList = {
        "country cousin", "donna duck", "mickey mouse", "mickeys amateur", "moth and flame",
        "postman", "snow white", "the giant", "three little pigs", "witch"
    };

    private final GameBoard gameBoard;
    private ImageIcon cardBackImageIcon; //ön yüz görselleri

    private JLabel textLabel;
    private JPanel boardPanel;
    private JButton restartButton;

    private int errorCount = 0;
    private final ArrayList<JButton> boardButtons = new ArrayList<>();
    private JButton card1Selected;
    private JButton card2Selected;
    private Timer hideCardTimer;
    // oyun başlangıcında kartlara tıklanmasını engelliyor
    private boolean gameReady = false;

    //constructor metot
    public MatchCards() {
        super("Disney Match Cards"); // JFrame başlığı
        initializeCardBack();
        gameBoard = new GameBoard(cardList, cardWidth, cardHeight);
        //arayüz
        setupUI();
        //kartların ekranda görünür olması
        setupGameBoard();
    }

    private void initializeCardBack() {
        String path = "resources/img/back.jpg";
        //görseli alıp nesne oluşturuyor
        Image backImg = new ImageIcon(path).getImage();
        //görselin boyutunu ayarlıyor
        cardBackImageIcon = new ImageIcon(backImg.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
    }

    private void setupUI() {
        // uygulama kapatıldığında programın kapanması için
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //arayüz 5e ayrılır: north, south, west, east, center.
        setLayout(new BorderLayout());
        //pencere boyutu hesaplanır
        setSize(columns * cardWidth, rows * cardHeight + 60);
        //ekranın ortasında başlar
        setLocationRelativeTo(null);
        //kullanıcı pencere boyutunu değiştiremez
        setResizable(false);

        textLabel = new JLabel("Errors: " + errorCount, SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(textLabel, BorderLayout.NORTH);

        boardPanel = new JPanel(new GridLayout(rows, columns));
        add(boardPanel, BorderLayout.CENTER);

        restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 16));
        //başlangıçta aktif değil
        restartButton.setEnabled(false);
        //lambda işlemi: bahsedilen olay yaşanırsa işlemi gerçekleştiriyor
        restartButton.addActionListener(_ -> restartGame());
        add(restartButton, BorderLayout.SOUTH);

        hideCardTimer = new Timer(1500, _ -> hideUnmatchedCards());
        //otomatik tekrar etmediği için false, her seferinde manuel işlem yapmış gibi oluyoruz
        hideCardTimer.setRepeats(false);

        setVisible(true);
    }

    private void setupGameBoard() {
        boardButtons.clear();
        boardPanel.removeAll();
        
        //kart listesini alıyor
        ArrayList<Card> cards = gameBoard.getCards();
    

        for (int i = 0; i < cards.size(); i++) {
            JButton button = new JButton();
            //dimension ve preferredSize birbirini destekliyor
            //swing kütüphanesinden geliyorlar
            button.setPreferredSize(new Dimension(cardWidth, cardHeight));
    
            // Oyun başında kartı geçici olarak açık gösteriyoruz
            button.setIcon(cards.get(i).getImageIcon());
    
            //javada i'yi direkt kullanamiyoruz o sebeple tanımlıyoruz
            final int index = i;
            button.addActionListener(_ -> handleCardClick(index));
    
            //butonlar panele ve listeye eklenir
            boardButtons.add(button);
            boardPanel.add(button);
        }

        // kartlar çizildikten sonra görünürlük güncellenir
        revalidate(); 
        repaint();
    
        //kartlar kapatılana kadar tıklamalar devre dışı
        gameReady = false;
    
        // 1.5 saniye sonra tüm kartları kapat
        hideCardTimer.start();
    }
    
    private void handleCardClick(int index) {
        //başlangıçta oyun hazır değilse tıklamalar geçersiz
        if (!gameReady) return;
        
        //tıklanan kartın butonunu alıyor
        JButton clicked = boardButtons.get(index); 
        //butona ait olan kartın verisini görselini alıyoruz
        Card card = gameBoard.getCards().get(index);

        // kartın ön yüzü görünürken tekrar tıklayamayız
        if (clicked.getIcon() != cardBackImageIcon) return;

        // kartın görseli butona yerleştirilir, tıklanınca ön yüzü açılır
        clicked.setIcon(card.getImageIcon());

        //kart1 seçilmemişse
        if (card1Selected == null) {
            card1Selected = clicked;
        } 
        //kart2 seçilmemiş ve kart1 seçilmişse
        else if (card2Selected == null && clicked != card1Selected) {
            card2Selected = clicked;

            //görseller farklıysa
            if (!card1Selected.getIcon().equals(card2Selected.getIcon())) {
                errorCount++;
                textLabel.setText("Errors: " + errorCount);
                hideCardTimer.start(); //1 saniye sonra kapatmak için
            } 
            else { //kartlar eşleşiyorsa tekrar seçilmemesi için null
                card1Selected = null;
                card2Selected = null;
            }
        }
    }

    private void hideUnmatchedCards() {
        //iki kart da seçilmiş ve açıksa
        if (card1Selected != null && card2Selected != null) {
            //ikisinin görselini de arka yüzle değiştiriyor
            card1Selected.setIcon(cardBackImageIcon);
            card2Selected.setIcon(cardBackImageIcon);
            card1Selected = null;
            card2Selected = null;
        } 
        else { //kartlar seçilmediyse oyun başlamadıysa
            for (JButton btn : boardButtons) {
                //arka yüz resmi atanıyor
                btn.setIcon(cardBackImageIcon);
            }
            gameReady = true;
            restartButton.setEnabled(true);
        }
    }

    private void restartGame() {
        gameBoard.shuffleCards();
        errorCount = 0;
        textLabel.setText("Errors: " + errorCount);
        card1Selected = null;
        card2Selected = null;
        gameReady = false;
        restartButton.setEnabled(false);
        setupGameBoard();
    }
}