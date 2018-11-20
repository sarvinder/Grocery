/**
 * This is the POJO class
 *
 */

package com.example.login.grocery.model;

public class ShoppingList {

    String listName;
    String owner;

    /**
     * Constructor overloading
     */

    public ShoppingList() {

    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ShoppingList(String listName, String owner) {
        this.listName = listName;
        this.owner = owner;
    }


}
