/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatNT.food;

import java.io.Serializable;

/**
 *
 * @author nguye
 */
public class CreateFoodError implements Serializable{
    private String nameEmpty;
    private String imageEmpty;
    private String descriptionEmpty;
    private String priceEmpty;
    private String categoryEmpty;
    private String quantityEmpty;
    private String sourceEmpty;
    private String nameIsExisted;
    private String messCreateSucc;
    
    public CreateFoodError() {
    }

    /**
     * @return the nameEmpty
     */
    public String getNameEmpty() {
        return nameEmpty;
    }

    /**
     * @param nameEmpty the nameEmpty to set
     */
    public void setNameEmpty(String nameEmpty) {
        this.nameEmpty = nameEmpty;
    }

    /**
     * @return the imageEmpty
     */
    public String getImageEmpty() {
        return imageEmpty;
    }

    /**
     * @param imageEmpty the imageEmpty to set
     */
    public void setImageEmpty(String imageEmpty) {
        this.imageEmpty = imageEmpty;
    }

    /**
     * @return the descriptionEmpty
     */
    public String getDescriptionEmpty() {
        return descriptionEmpty;
    }

    /**
     * @param descriptionEmpty the descriptionEmpty to set
     */
    public void setDescriptionEmpty(String descriptionEmpty) {
        this.descriptionEmpty = descriptionEmpty;
    }

    /**
     * @return the priceEmpty
     */
    public String getPriceEmpty() {
        return priceEmpty;
    }

    /**
     * @param priceEmpty the priceEmpty to set
     */
    public void setPriceEmpty(String priceEmpty) {
        this.priceEmpty = priceEmpty;
    }

    /**
     * @return the categoryEmpty
     */
    public String getCategoryEmpty() {
        return categoryEmpty;
    }

    /**
     * @param categoryEmpty the categoryEmpty to set
     */
    public void setCategoryEmpty(String categoryEmpty) {
        this.categoryEmpty = categoryEmpty;
    }

    /**
     * @return the quantityEmpty
     */
    public String getQuantityEmpty() {
        return quantityEmpty;
    }

    /**
     * @param quantityEmpty the quantityEmpty to set
     */
    public void setQuantityEmpty(String quantityEmpty) {
        this.quantityEmpty = quantityEmpty;
    }

    /**
     * @return the sourceEmpty
     */
    public String getSourceEmpty() {
        return sourceEmpty;
    }

    /**
     * @param sourceEmpty the sourceEmpty to set
     */
    public void setSourceEmpty(String sourceEmpty) {
        this.sourceEmpty = sourceEmpty;
    }

    /**
     * @return the nameIsExisted
     */
    public String getNameIsExisted() {
        return nameIsExisted;
    }

    /**
     * @param nameIsExisted the nameIsExisted to set
     */
    public void setNameIsExisted(String nameIsExisted) {
        this.nameIsExisted = nameIsExisted;
    }

    /**
     * @return the messCreateSucc
     */
    public String getMessCreateSucc() {
        return messCreateSucc;
    }

    /**
     * @param messCreateSucc the messCreateSucc to set
     */
    public void setMessCreateSucc(String messCreateSucc) {
        this.messCreateSucc = messCreateSucc;
    }

    
    
    
    
}
