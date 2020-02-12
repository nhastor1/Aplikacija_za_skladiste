package ba.unsa.etf.rpr;

import java.time.LocalDateTime;

public class ProductOrder {
    int id;
    Product product;
    int amount;
    Invoice invoice;
    double discount = 0;
    LocalDateTime timeOfOrder = LocalDateTime.now();

    public ProductOrder(int id, Product product, int amount, Invoice invoice) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.invoice = invoice;
    }

    public ProductOrder(int id, Product product, int amount, Invoice invoice, double discount) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.invoice = invoice;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice(){
        double price = product.getPrice() * amount;
        return  price - price * discount;
    }

    public LocalDateTime getTimeOfOrder() {
        return timeOfOrder;
    }
}
