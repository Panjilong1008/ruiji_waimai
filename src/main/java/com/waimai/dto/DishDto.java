package com.waimai.dto;

import com.waimai.entity.Dish;
import com.waimai.entity.DishFlavor;

import java.util.List;

public class DishDto extends Dish {
    private List<DishFlavor> flavors;
    private String categoryName;
    private Integer copies;

    public List<DishFlavor> getFlavors() {
        return flavors;
    }

    public void setFlavors(List<DishFlavor> flavors) {
        this.flavors = flavors;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    @Override
    public String toString() {
        return "DishDto{" +
                "flavors=" + flavors.toString() +
                ", categoryName='" + categoryName + '\'' +
                ", copies=" + copies +
                '}';
    }
}
