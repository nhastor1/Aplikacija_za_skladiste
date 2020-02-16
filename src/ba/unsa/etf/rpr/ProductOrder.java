package ba.unsa.etf.rpr;


import java.sql.Date;
import java.util.Objects;

public class ProductOrder {
    private int id;
    private Product product;
    private int amount;
    private Invoice invoice;
    private double discount = 0;
    private Date timeOfOrder = new Date(System.currentTimeMillis());;

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

    public ProductOrder(int id, Product product, int amount, Invoice invoice, double discount, Date timeOfOrder) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.invoice = invoice;
        this.discount = discount;
        this.timeOfOrder = timeOfOrder;
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

    public Date getTimeOfOrder() {
        return timeOfOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOrder that = (ProductOrder) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
