package com.example.login.grocery.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;

public class ShoppingListItem {

    private String itemName;
    private String owner;
    private String boughtBy;
    private boolean bought;

    /**
     * Required public constructor
     */
    public ShoppingListItem() {
    }

    /**
     * Use this constructor to create new ShoppingListItem.
     * Takes shopping list item name and list item owner email as params
     *
     * @param itemName
     * @param owner
     */
    public ShoppingListItem(String itemName, String owner) {
        this.itemName = itemName;
        this.owner = owner;
        this.boughtBy = null;
        this.bought = false;

    }

    public String getItemName() { return itemName; }

    public String getOwner() {
        return owner;
    }

    public String getBoughtBy() {
        return boughtBy;
    }

    public boolean isBought() {
        return bought;
    }

    @Exclude
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ItemName", getItemName());
        result.put("Owner", getOwner());
        result.put("BoughtBy", getBoughtBy());
        result.put("isBought", isBought());

        return result;
    }

}
