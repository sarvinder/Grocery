package com.example.login.grocery.ui.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.login.grocery.R;
import com.example.login.grocery.model.ShoppingList;
import com.example.login.grocery.model.ShoppingListItem;
import com.example.login.grocery.model.User;
import com.example.login.grocery.utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class ActiveListItemAdapter extends FirebaseRecyclerAdapter<ShoppingListItem,ListItemViewHolder> {


    private ShoppingList mShoppingList;
    private String mEncodedEmail;
    private HashMap<String, User> mSharedWithUsers;
    private Activity mActivity;
    private static final String LOG = "ActiveListDetails";


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ActiveListItemAdapter(@NonNull FirebaseRecyclerOptions<ShoppingListItem> options, String encodedEmail,Activity activity) {
        super(options);
        this.mActivity = activity;
        this.mEncodedEmail = encodedEmail;
    }
    /**
     * Public method that is used to pass shoppingList object when it is loaded in ValueEventListener
     */
    public void setShoppingList(ShoppingList shoppingList) {
        this.mShoppingList = shoppingList;
        this.notifyDataSetChanged();
    }

    public void setSharedWithUsers(HashMap<String, User> sharedWithUsers) {
        this.mSharedWithUsers = sharedWithUsers;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        int layoutIdForListItem = R.layout.single_active_list_item;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ListItemViewHolder holder = new ListItemViewHolder(view);

        return holder;
    }


    @Override
    protected void onBindViewHolder(@NonNull ListItemViewHolder holder, int position, @NonNull ShoppingListItem model) {

        holder.onBindItemName(model.getItemName());
        holder.onBindBoughtBy(model.getBoughtBy());
        holder.onBindBoughtByUser(model.getOwner());
        /*setItemAppearanceBaseOnBoughtStatus(model.getOwner(), holder.textViewBoughtByUser, holder.textViewBoughtBy, holder.buttonRemoveItem,
                holder.textViewItemName, model);
*/

        /**
         * This is when we want to remove the item at certain index
         */
        //final String itemToRemoveId = this.getRef(position).getKey();

    }


   /* private void removeItem(String itemId) {
        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL);

        *//* Make a map for the removal *//*
        HashMap<String, Object> updatedRemoveItemMap = new HashMap<String, Object>();

        *//* Remove the item by passing null *//*
        updatedRemoveItemMap.put("/" + Constants.FIREBASE_LOCATION_SHOPPING_LIST_ITEMS + "/"
                + mListId + "/" + itemId, null);

        *//* Add the updated timestamp *//*
        Utils.updateMapWithTimestampLastChanged(mSharedWithUsers,
                mListId, mShoppingList.getOwner(), updatedRemoveItemMap);

        *//* Do the update *//*
        firebaseRef.updateChildren(updatedRemoveItemMap, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                Utils.updateTimestampReversed(firebaseError, "ActListItemAdap", mListId,
                        mSharedWithUsers, mShoppingList.getOwner());
            }
        });
    }*/


    private void setItemAppearanceBaseOnBoughtStatus(String owner, final TextView textViewBoughtByUser,
                                                     TextView textViewBoughtBy, ImageButton buttonRemoveItem,
                                                     TextView textViewItemName, ShoppingListItem item) {
        /**
         * If selected item is bought
         * Set "Bought by" text to "You" if current user is owner of the list
         * Set "Bought by" text to userName if current user is NOT owner of the list
         * Set the remove item button invisible if current user is NOT list or item owner
         */
        if (item.isBought() && item.getBoughtBy() != null) {

            textViewBoughtBy.setVisibility(View.VISIBLE);
            textViewBoughtByUser.setVisibility(View.VISIBLE);
            buttonRemoveItem.setVisibility(View.INVISIBLE);

            /* Add a strike-through */
            textViewItemName.setPaintFlags(textViewItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if (item.getBoughtBy().equals(mEncodedEmail)) {
                textViewBoughtByUser.setText(mActivity.getString(R.string.text_you));
            } else {

               /* Firebase boughtByUserRef = new Firebase(Constants.FIREBASE_URL_USERS).child(item.getBoughtBy());
                *//* Get the item's owner's name; use a SingleValueEvent listener for memory efficiency *//*
                boughtByUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            textViewBoughtByUser.setText(user.getName());
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Log.e(mActivity.getClass().getSimpleName(),
                                mActivity.getString(R.string.log_error_the_read_failed) +
                                        firebaseError.getMessage());
                    }
                });*/
            }
        } else {
            /**
             * If selected item is NOT bought
             * Set "Bought by" text to be empty and invisible
             * Set the remove item button visible if current user is owner of the list or selected item
             */

            /* Remove the strike-through */
            textViewItemName.setPaintFlags(textViewItemName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

            textViewBoughtBy.setVisibility(View.INVISIBLE);
            textViewBoughtByUser.setVisibility(View.INVISIBLE);
            textViewBoughtByUser.setText("");
            /**
             * If you are the owner of the item or the owner of the list, then the remove icon
             * is visible.
             */
            if (owner.equals(mEncodedEmail) || (mShoppingList != null && mShoppingList.getOwner().equals(mEncodedEmail))) {
                buttonRemoveItem.setVisibility(View.VISIBLE);
            } else {
                buttonRemoveItem.setVisibility(View.INVISIBLE);
            }
        }
    }
}
