
import javax.swing.*;

//diğer sınıflardan erişilebilir public
public class Card {
    private String name;
    //ön yüz görseli imageIcon
    private ImageIcon imageIcon;

    //constructor metot, nesnenin adını ve görselini belirliyor
    public Card(String name, ImageIcon imageIcon) {
        //thisler sayesinde gelen değer sınıfa kaydedilir
        this.name = name;
        this.imageIcon = imageIcon;
    }

    //name private tanımlandığı için kartın ismini dışardan bu getter metotu ile öğreniyoruz
    public String getName() {
        return name;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    //bir nesneyi yazdırmak istediğimizde ne gözükeceğini toString() belirler
    //normalde System.out.println(card) yazarsak karışık bir ifade çıkar
    @Override
    public String toString() {
        return name;
    }
}

