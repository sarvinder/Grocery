package com.example.login.grocery.ui.Widgets;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.login.grocery.R;
import com.example.login.grocery.model.ShoppingList;
import com.example.login.grocery.model.User;
import com.example.login.grocery.ui.Screens.ActiveListDetailsActivity;
import com.example.login.grocery.utils.Constants;
import com.example.login.grocery.utils.Utils;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public class EditListNameDialogFragment extends EditListDialogFragment {

    private static final String LOG_TAG = ActiveListDetailsActivity.class.getSimpleName();
    String mListName;

    /**
     * Public static constructor that creates fragment and passes a bundle with data into it when adapter is created
     */
    public static EditListNameDialogFragment newInstance(ShoppingList shoppingList, String listId,
                                                         String encodedEmail,
                                                         HashMap<String, User> sharedWithUsers) {
        EditListNameDialogFragment editListNameDialogFragment = new EditListNameDialogFragment();
        Bundle bundle = EditListDialogFragment.newInstanceHelper(shoppingList,
                R.layout.dialog_edit_list, listId, encodedEmail, sharedWithUsers);
        bundle.putString(Constants.KEY_LIST_NAME, shoppingList.getListName());
        editListNameDialogFragment.setArguments(bundle);
        return editListNameDialogFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListName = getArguments().getString(Constants.KEY_LIST_NAME);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /** {@link EditListDialogFragment#createDialogHelper(int)} is a
         * superclass method that creates the dialog
         **/
        Dialog dialog = super.createDialogHelper(R.string.positive_button_edit_item);
        /**
         * {@link EditListDialogFragment#helpSetDefaultValueEditText(String)} is a superclass
         * method that sets the default text of the TextView
         */
        helpSetDefaultValueEditText(mListName);
        return dialog;
    }

    /**
     * Changes the list name in all copies of the current list
     */
    protected void doListEdit() {
        final String inputListName = mEditTextForList.getText().toString();
        /**
         * Check that the user inputted list name is not empty, has changed the original name
         * and that the dialog was properly initialized with the current name and id of the list.
         */
        if (!inputListName.equals("") && mListName != null &&
                mListId != null && !inputListName.equals(mListName)) {

            DatabaseReference reference = Constants.REFERENCE.child("activeList");
            ShoppingList list = new ShoppingList(inputListName, "Anonomus owner");
                //reference.child("activeList").setValue(list);

            DatabaseReference newReference = reference.push();
            final String listID = newReference.getKey();

            HashMap<String, Object> updateShoppingListData = new HashMap<>();
            HashMap<String, Object> shoppingListMap = list.toMap();
            updateShoppingListData.put( "/"+ listID + "/",shoppingListMap);

            reference.updateChildren(updateShoppingListData, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    Toast.makeText(getContext(), "saved the list in database", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
