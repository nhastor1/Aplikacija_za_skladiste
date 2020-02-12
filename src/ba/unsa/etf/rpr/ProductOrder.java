package ba.unsa.etf.rpr;

public class ProductOrder {
    int id;
    Product product;
    int amount;
    Invoice invoice;
    int discount = 0;

    public ProductOrder(int id, Product product, int amount, Invoice invoice) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.invoice = invoice;
    }

    public ProductOrder(int id, Product product, int amount, Invoice invoice, int discount) {
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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getPrice(){
        double price = product.getPrice() * amount;
        return  price - price * discount;
    }
}
