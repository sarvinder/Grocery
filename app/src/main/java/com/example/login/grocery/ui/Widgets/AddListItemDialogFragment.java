package com.example.login.grocery.ui.Widgets;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import com.example.login.grocery.R;
import com.example.login.grocery.model.ShoppingList;
import com.example.login.grocery.model.ShoppingListItem;
import com.example.login.grocery.model.User;
import com.example.login.grocery.ui.Screens.ActiveListDetailsActivity;
import com.example.login.grocery.utils.Constants;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddListItemDialogFragment extends EditListDialogFragment {

    private static final String LOG = "ActiveListDialog";

    /**
     * Public static constructor that creates fragment and passes a bundle with data into it when adapter is created
     */
    public static AddListItemDialogFragment newInstance(ShoppingList shoppingList, String listId,
                                                        String encodedEmail,
                                                        HashMap<String, User> sharedWithUsers) {
        AddListItemDialogFragment addListItemDialogFragment = new AddListItemDialogFragment();
        Bundle bundle = EditListDialogFragment.newInstanceHelper(shoppingList,
                R.layout.dialog_add_item, listId, encodedEmail, sharedWithUsers);
        addListItemDialogFragment.setArguments(bundle);

        return addListItemDialogFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /** {@link EditListDialogFragment#createDialogHelper(int)} is a
         * superclass method that creates the dialog
         **/
        return super.createDialogHelper(R.string.positive_button_add_list_item);
    }


    /**
     * Adds new item to the current shopping list
     */
    @Override
    protected void doListEdit() {
        String mItemName = mEditTextForList.getText().toString();
        /**
         * Adds list item if the input name is not empty
         */
        if (!mItemName.equals("")) {

            Log.e(LOG,"the item to be entered into the list is : "+mItemName);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference().child(Constants.FIREBASE_LOCATION_SHOPPING_LIST_ITEMS).child(ActiveListDetailsActivity.KEY);

            HashMap<String, Object> updatedItemToAddMap = new HashMap<String, Object>();


            DatabaseReference newRef = reference.push();
            String itemId = newRef.getKey();
            ShoppingListItem itemToAddObject = new ShoppingListItem(mItemName, "Anonomus");
            HashMap<String, Object> itemToAdd = itemToAddObject.toMap();
            updatedItemToAddMap.put("/"+ itemId + "/",itemToAddObject);

            reference.updateChildren(updatedItemToAddMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                }
            });

            /**
             * Close the dialog fragment when done
             */
            AddListItemDialogFragment.this.getDialog().cancel();
        }
    }



}
