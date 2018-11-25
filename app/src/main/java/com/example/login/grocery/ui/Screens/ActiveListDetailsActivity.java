package com.example.login.grocery.ui.Screens;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.login.grocery.R;
import com.example.login.grocery.model.ShoppingList;
import com.example.login.grocery.model.User;
import com.example.login.grocery.ui.Widgets.EditListNameDialogFragment;
import com.example.login.grocery.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ActiveListDetailsActivity extends AppCompatActivity {

    private boolean mShopping = true;
    /* Stores whether the current user is the owner */
    private boolean mCurrentUserIsOwner = true;
    private String mListId;
    private ShoppingList mShoppingList;
    private String mEncodedEmail = "";
    private HashMap<String, User> mSharedWithUsers =new HashMap<>();
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_list_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        /* Common toolbar setup */
        setSupportActionBar(toolbar);

        /* Add back button to the action bar */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //* Get the push ID from the extra passed by ShoppingListFragment *//*
        Intent intent = this.getIntent();
        mListId = intent.getStringExtra(Constants.KEY_LIST_ITEM_ID);
        Toast.makeText(this, "got the text : "+mListId, Toast.LENGTH_SHORT).show();
        if (mListId == null) {
            /* No point in continuing without a valid ID. */
            finish();
            return;
        }
        else
        {
            //setting the appropriate title based on the list name
            setTitle(mListId);
        }

        reference = Constants.REFERENCE.child("activeList");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ShoppingList list = dataSnapshot.getValue(ShoppingList.class);
                if (list == null) {
                    finish();
                    /**
                     * Make sure to call return, otherwise the rest of the method will execute,
                     * even after calling finish.
                     */
                    return;
                }
                mShoppingList = list;
                String listname = mShoppingList.getListName();
                setTitle(listname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_details, menu);

        /**
         * Get menu items
         */
        MenuItem remove = menu.findItem(R.id.action_remove_list);
        MenuItem edit = menu.findItem(R.id.action_edit_list_name);
        MenuItem share = menu.findItem(R.id.action_share_list);
        MenuItem archive = menu.findItem(R.id.action_archive);

        /* Only the edit and remove options are implemented */
        remove.setVisible(mCurrentUserIsOwner);
        edit.setVisible(mCurrentUserIsOwner);
        share.setVisible(mCurrentUserIsOwner);
        archive.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        /**
         * Show edit list dialog when the edit action is selected
         */
        if (id == R.id.action_edit_list_name) {
            showEditListNameDialog();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * Show edit list name dialog when user selects "Edit list name" menu item
     */
    public void showEditListNameDialog() {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = EditListNameDialogFragment.newInstance(mShoppingList, mListId,
                mEncodedEmail, mSharedWithUsers);
        dialog.show(this.getSupportFragmentManager(), "EditListNameDialogFragment");
    }

}
