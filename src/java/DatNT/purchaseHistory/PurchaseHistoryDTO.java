/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.purchaseHistory;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author nguye
 */
public class PurchaseHistoryDTO implements Serializable{
    
    private String userID;
    private String foodID;
    private String image;
    private String name;
    private int quantity;
    private float price;
    private Timestamp date;

    public PurchaseHistoryDTO(String userID, String foodID, String image, String name, int quantity, float price, Timestamp date) {
        this.userID = userID;
        this.foodID = foodID;
        this.image = image;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    
}
