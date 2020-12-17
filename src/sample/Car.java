package sample;

public class Car {
    private String registrationNumber;
    private int yearMade;
    private String[] color = new String[3];
    private String maker;
    private String model;
    private int price;
    private int quantity;

    public Car(String registrationNumber, int yearMade, String color1, String color2, String color3, String maker, String model, int price, int quantity) {
        this.registrationNumber = registrationNumber;
        this.yearMade = yearMade;
        this.color[0] = color1;
        this.color[1] = color2;
        this.color[2] = color3;
        this.maker = maker;
        this.model = model;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return this.registrationNumber + "\t" + this.yearMade + "\t" + this.color[0] + "\t" + this.color[1] + "\t" + this.color[2] + "\t"
                + this.maker + "\t" + this.model + "\t" + this.price + "\t" + this.quantity;
    }

//    @Override
//    public String toString() {
//        return "\n\t\t\t\t\tRegistration Number: " + "\u001B[36m" + this.registrationNumber + "\n" + "\u001B[0m" +
//                "\t\t\t\t\tYear of manufacture: " + "\u001B[32m"  + this.yearMade + "\n" + "\u001B[0m"  +
//                "\t\t\t\t\t           Color(s): " + "\n" +
//                "\t\t\t\t\t\t                   " + this.color[0] + "\n" +
//                "\t\t\t\t\t\t                   " + this.color[1] + "\n" +
//                "\t\t\t\t\t\t                   " + this.color[2] + "\n" +
//                "\t\t\t\t\t              Maker: " + "\u001B[36m"  + this.maker +  "\n" + "\u001B[0m"  +
//                "\t\t\t\t\t              Model: " + "\u001B[36m"  + this.model +  "\n" + "\u001B[0m"  +
//                "\t\t\t\t\t              Price: " + "\u001B[32m"  + this.price + "\u001B[0m" + "\n";
//    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getMaker() {
        return maker;
    }

    public String getModel() {
        return model;
    }

    public int getYearMade() {
        return yearMade;
    }

    public String getColor1() {
        return color[0];
    }
    public String getColor2() {
        return color[1];
    }
    public String getColor3() {
        return color[2];
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantity() {
        this.quantity--;
    }
}
