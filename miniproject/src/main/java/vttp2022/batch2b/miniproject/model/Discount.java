package vttp2022.batch2b.miniproject.model;

public class Discount {
    private double price_new;
    private double price_old;
    private double price_cut;
    private String shopLink;
    private String shopName;
    private String currency;

    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public double getPrice_new() {
        return price_new;
    }
    public void setPrice_new(double price_new) {
        this.price_new = price_new;
    }
    public double getPrice_old() {
        return price_old;
    }
    public void setPrice_old(double price_old) {
        this.price_old = price_old;
    }
    public double getPrice_cut() {
        return price_cut;
    }
    public void setPrice_cut(double price_cut) {
        this.price_cut = price_cut;
    }
    public String getShopLink() {
        return shopLink;
    }
    public void setShopLink(String shopLink) {
        this.shopLink = shopLink;
    }

}