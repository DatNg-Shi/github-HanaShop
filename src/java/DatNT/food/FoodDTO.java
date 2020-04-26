/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.food;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author nguye
 */
public class FoodDTO implements Serializable {

    private String name;
    private String image;
    private String description;
    private float price;
    private Timestamp createDate;  
    private String category;
    private int quantity;
    private String source;
    private String statusFood;
    private Timestamp updateDate;

    public FoodDTO() {
    }

    public FoodDTO(String name, String image, String description, float price, Timestamp createDate, String category, int quantity, String source, String statusFood, Timestamp updateDate) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.category = category;
        this.quantity = quantity;
        this.source = source;
        this.statusFood = statusFood;
        this.updateDate = updateDate;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the createDate
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the statusFood
     */
    public String getStatusFood() {
        return statusFood;
    }

    /**
     * @param statusFood the statusFood to set
     */
    public void setStatusFood(String statusFood) {
        this.statusFood = statusFood;
    }

    /**
     * @return the updateDate
     */
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    
    
}
