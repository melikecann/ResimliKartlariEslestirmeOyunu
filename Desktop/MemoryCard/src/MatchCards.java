import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
    private ImageIcon cardBackImageIcon;

    private JLabel textLabel;
    private JPanel boardPanel;
    private JButton restartButton;

    private int errorCount = 0;
    private final ArrayList<JButton> boardButtons = new ArrayList<>();
    private JButton card1Selected;
    private JButton card2Selected;
    private Timer hideCardTimer;
    private boolean gameReady = false;

    public MatchCards() {
        super("Disney Match Cards"); // JFrame başlığı
        initializeCardBack();
        gameBoard = new GameBoard(cardList, cardWidth, cardHeight);
        setupUI();
        setupGameBoard();
    }

    private void initializeCardBack() {
        String path = "resources/img/back.jpg";
        Image backImg = new ImageIcon(path).getImage();
        cardBackImageIcon = new ImageIcon(backImg.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
    }

    private void setupUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(columns * cardWidth, rows * cardHeight + 60);
        setLocationRelativeTo(null);
        setResizable(false);

        textLabel = new JLabel("Errors: " + errorCount, SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(textLabel, BorderLayout.NORTH);

        boardPanel = new JPanel(new GridLayout(rows, columns));
        add(boardPanel, BorderLayout.CENTER);

        restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 16));
        restartButton.setEnabled(false);
        restartButton.addActionListener(_ -> restartGame());
        add(restartButton, BorderLayout.SOUTH);

        hideCardTimer = new Timer(1500, _ -> hideUnmatchedCards());
        hideCardTimer.setRepeats(false);

        setVisible(true);
    }

    private void setupGameBoard() {
        boardButtons.clear();
        boardPanel.removeAll();

        ArrayList<Card> cards = gameBoard.getCards();

        for (int i = 0; i < cards.size(); i++) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(cardWidth, cardHeight));
            button.setIcon(cardBackImageIcon);
            final int index = i;

            button.addActionListener(_ -> handleCardClick(index));

            boardButtons.add(button);
            boardPanel.add(button);
        }

        revalidate();
        repaint();

        gameReady = false;
        hideCardTimer.start(); // başta hepsini saklayacak
    }

    private void handleCardClick(int index) {
        if (!gameReady) return;

        JButton clicked = boardButtons.get(index);
        Card card = gameBoard.getCards().get(index);

        if (clicked.getIcon() != cardBackImageIcon) return; // zaten açık

        clicked.setIcon(card.getImageIcon());

        if (card1Selected == null) {
            card1Selected = clicked;
        } else if (card2Selected == null && clicked != card1Selected) {
            card2Selected = clicked;

            if (!card1Selected.getIcon().equals(card2Selected.getIcon())) {
                errorCount++;
                textLabel.setText("Errors: " + errorCount);
                hideCardTimer.start();
            } else {
                card1Selected = null;
                card2Selected = null;
            }
        }
    }

    private void hideUnmatchedCards() {
        if (card1Selected != null && card2Selected != null) {
            card1Selected.setIcon(cardBackImageIcon);
            card2Selected.setIcon(cardBackImageIcon);
            card1Selected = null;
            card2Selected = null;
        } else {
            for (JButton btn : boardButtons) {
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