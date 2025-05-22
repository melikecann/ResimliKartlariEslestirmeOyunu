import javax.swing.ImageIcon;

public class Card implements ICard {    //INHERITANCE Kalıtım
    private String name;    //ENCAPSULATION Kapsülleme
    private ImageIcon imageIcon;    //ENCAPSULATION Kapsülleme

    //constructor metot
    public Card(String name, ImageIcon imageIcon) {
        //parametreleri sınıf içindeki değişkenlere aktarır
        this.name = name;
        this.imageIcon = imageIcon;
    }

    //ICard arayüzünde zorunlu olan getName'in gövdesidir
    //kartın ismini döndürür
    @Override
    public String getName() {
        return name;
    }

    //diğer zorunlu metot, görsel döndürür
    @Override
    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    //toString() metodu javada nesne metin olarak yazdırıldığında ne çıkacağını tanımlar
    //kartın ismi yani
    //System.out.println(card) şeklinde yazarsak karışık şeyler yazabilir
    @Override
    public String toString() {
        return name;
    }
}
