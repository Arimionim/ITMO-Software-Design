package ru.akirakozov.sd.refactoring;

public class Product {
    private final String name;
    private final int price;
    private static int number = 0;

    Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public static Product getRandomProduct() {
        number++;
        return new Product("name" + number, (42 + number * 123) % 543);
    }
}
