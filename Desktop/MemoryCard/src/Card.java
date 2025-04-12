
import javax.swing.*;
public class Card {
    private String name;
    private ImageIcon imageIcon;

    public Card(String name, ImageIcon imageIcon) {
        this.name = name;
        this.imageIcon = imageIcon;
    }

    public String getName() {
        return name;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    @Override
    public String toString() {
        return name;
    }
}

