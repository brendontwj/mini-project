package vttp2022.batch2b.miniproject.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class GameDiscount {
    private static final Logger logger = LoggerFactory.getLogger(GameDiscount.class);

    private String plain;
    private String title;
    private String currency;
    private List<Discount> discountList = new ArrayList<Discount>();
    private Discount[] discountArr;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Discount> getDiscountList() {
        return discountList;
    }

    public void setDiscountList(List<Discount> discountList) {
        this.discountList = discountList;
    }

    public Discount[] getDiscountArr() {
        return discountArr;
    }

    public void setDiscountArr(Discount[] discountArr) {
        this.discountArr = discountArr;
    }

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }
    
    public static GameDiscount createListOfDiscounts(String json, String plainName) throws IOException {
        logger.info("Inside createListOfDiscounts code block");
        logger.info("plain name > " + plainName);

        GameDiscount listOfDiscounts = new GameDiscount();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            logger.info("Object read > " + o.toString());
            if(o.containsKey(".meta")) {
                logger.info("inside contains .meta key block");
                JsonObject currency = o.getJsonObject(".meta");
                JsonObject data = o.getJsonObject("data");
                JsonObject plain = data.getJsonObject(plainName);
                JsonArray tempDiscountArr = plain.getJsonArray("list");
                listOfDiscounts.setCurrency(currency.getString("currency"));
                for (int i = 0; i < tempDiscountArr.size(); i++) {
                    Discount tempDiscount = new Discount();
                    JsonObject prices = tempDiscountArr.getJsonObject(i);
                    JsonObject shop = prices.getJsonObject("shop");
                    tempDiscount.setCurrency(currency.getString("currency"));
                    tempDiscount.setShopName(shop.getString("name"));
                    tempDiscount.setShopLink(prices.getString("url"));
                    tempDiscount.setPrice_new(prices.getJsonNumber("price_new").doubleValue());
                    tempDiscount.setPrice_old(prices.getJsonNumber("price_old").doubleValue());
                    tempDiscount.setPrice_cut(prices.getJsonNumber("price_cut").doubleValue());
                    listOfDiscounts.discountList.add(tempDiscount);
                }
            }
            logger.info(listOfDiscounts.discountList.toString());
            listOfDiscounts.discountArr = listOfDiscounts.discountList.toArray(new Discount[0]);
            return listOfDiscounts;
        }
    }
}
