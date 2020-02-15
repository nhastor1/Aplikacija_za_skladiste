package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.DAO.MainDAO;
import ba.unsa.etf.rpr.Person.Person;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Invoice {
    int id;
    Person customer;
    List<ProductOrder> orders;
    double price;
    double discount = 0;
    Date timeOfOrder = new Date(System.currentTimeMillis());;

    public Invoice(int id, Person customer, List<ProductOrder> orders) {
        this.id = id;
        this.customer = customer;
        this.orders = orders;
    }

    public Invoice(int id, Person customer, List<ProductOrder> orders, double discount) {
        this.id = id;
        this.customer = customer;
        this.orders = orders;
        this.discount = discount;
    }

    public Invoice(int id, Person customer, List<ProductOrder> orders, double price, double discount, Date timeOfOrder) {
        this.id = id;
        this.customer = customer;
        this.orders = orders;
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

    public List<ProductOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<ProductOrder> orders) {
        this.orders = orders;
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

    private void setNewPrice(){
        price = 0;
        for(ProductOrder o : orders){
            price += o.getPrice();
        }
        price = price - discount * price;
    }
}
