package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.Person.Person;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Invoice {
    private int id;
    private Person customer;
    private double price;
    private double discount = 0;
    private Date timeOfOrder = new Date(System.currentTimeMillis());;

    public Invoice(int id, Person customer, List<ProductOrder> orders) {
        this.id = id;
        this.customer = customer;
    }

    public Invoice(int id, Person customer, double price, double discount) {
        this.id = id;
        this.customer = customer;
        this.price = price;
        this.discount = discount;
    }

    public Invoice(int id, Person customer, double price, double discount, Date timeOfOrder) {
        this.id = id;
        this.customer = customer;
        this.price = price;
        this.discount = discount;
        this.timeOfOrder = timeOfOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getCustomer() {
        return customer;
    }

    public void setCustomer(Person customer) {
        this.customer = customer;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getTimeOfOrder() {
        return timeOfOrder;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return id == invoice.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
