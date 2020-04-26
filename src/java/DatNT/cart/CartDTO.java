/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.cart;

import java.io.Serializable;

/**
 *
 * @author nguye
 */
public class CartDTO implements Serializable{
    private String userID;
    private String foodID;
    private int quantity;

    public CartDTO(String userID, String foodID, int quantity) {
        this.userID = userID;
        this.foodID = foodID;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
 
}
