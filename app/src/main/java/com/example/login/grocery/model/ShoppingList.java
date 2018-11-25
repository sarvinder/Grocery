/**
 * This is the POJO class
 *
 */

package com.example.login.grocery.model;

import com.example.login.grocery.utils.Constants;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class ShoppingList {

    String listName;
    String owner;
    //private HashMap<String, Object> timestampLastChanged;
    //private HashMap<String, Object> timestampCreated;
    //private HashMap<String, Object> timestampLastChangedReverse;
    //private HashMap<String, User> usersShopping;
    /**
     * Required public constructor
     */
    public ShoppingList() {
    }

    /**
     * Use this constructor to create new ShoppingLists.
     * Takes shopping list listName and owner. Set's the last
     * changed time to what is stored in ServerValue.TIMESTAMP
     *
     * @param listName
     * @param owner
     */
    public ShoppingList(String listName, String owner ) {
        this.listName = listName;
        this.owner = owner;
        //this.timestampCreated = timestampCreated;
       // HashMap<String, Object> timestampNowObject = new HashMap<String, Object>();
       // timestampNowObject.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
       // this.timestampLastChanged = timestampNowObject;
        //this.timestampLastChangedReverse = null;
        //this.usersShopping = new HashMap<>();
    }

    public String getListName() {
        return listName;
    }

    public String getOwner() {
        return owner;
    }


    @Exclude
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("listName", listName);
        result.put("owner", owner);
        return result;
    }

    /*
    public HashMap<String, Object> getTimestampLastChanged() {
        return timestampLastChanged;
    }

    public HashMap<String, Object> getTimestampCreated() {
        return timestampCreated;
    }

    public HashMap<String, Object> getTimestampLastChangedReverse() {
        return timestampLastChangedReverse;
    }

    public long getTimestampLastChangedLong() {

        return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }


    public long getTimestampCreatedLong() {
        return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }

    public long getTimestampLastChangedReverseLong() {

        return (long) timestampLastChangedReverse.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }

    public HashMap getUsersShopping() {
        return usersShopping;
    }

    public void setTimestampLastChangedToNow() {
        HashMap<String, Object> timestampNowObject = new HashMap<String, Object>();
        timestampNowObject.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
        this.timestampLastChanged = timestampNowObject;
    }*/


}
