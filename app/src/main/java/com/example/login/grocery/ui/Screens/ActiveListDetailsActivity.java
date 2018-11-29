package com.example.login.grocery.ui.Screens;

import android.animation.StateListAnimator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.grocery.R;
import com.example.login.grocery.model.ShoppingList;
import com.example.login.grocery.model.ShoppingListItem;
import com.example.login.grocery.model.User;
import com.example.login.grocery.ui.Adapters.ActiveListItemAdapter;
import com.example.login.grocery.ui.Adapters.ListItemViewHolder;
import com.example.login.grocery.ui.Adapters.MyActiveListItemAdapter;
import com.example.login.grocery.ui.Fragments.ShoppingListsFragment;
import com.example.login.grocery.ui.Widgets.AddListItemDialogFragment;
import com.example.login.grocery.ui.Widgets.EditListNameDialogFragment;
import com.example.login.grocery.utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
    private static final String LOG = "ActiveListDetails";
    public static  String KEY ;

    private static final String LOG_TAG = ActiveListDetailsActivity.class.getSimpleName();
    private ActiveListItemAdapter mActiveListItemAdapter;
    private Button mButtonShopping;
    private TextView mTextViewPeopleShopping;
    private RecyclerView mListView;
    private User mCurrentUser;
    private ArrayList<ShoppingListItem>listitems = new ArrayList<>();
    /* Stores whether the current user is shopping */
    /* Stores whether the current user is the owner */
    //private ValueEventListener mCurrentUserRefListener, mCurrentListRefListener, mSharedWithListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_list_details);

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        *//* Common toolbar setup *//*
        setSupportActionBar(toolbar);

        *//* Add back button to the action bar *//*
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/
        initializeScreen();
        //* Get the push ID from the extra passed by ShoppingListFragment *//*
        Intent intent = this.getIntent();
        mListId = intent.getStringExtra(Constants.KEY_LIST_ITEM_ID);
        Toast.makeText(this, "got the title to set up: "+mListId, Toast.LENGTH_SHORT).show();
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

        reference = ShoppingListsFragment.searchDatabaseReference;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ShoppingList list = dataSnapshot.getValue(ShoppingList.class);

                mShoppingList = list;
                setTitle(list.getListName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //setup the adapter for the recyclerview
        //first we need to setup the query
       /* DatabaseReference ref = ShoppingListsFragment.searchDatabaseReference;
        String key = ref.getKey();
        Log.e(LOG,"the key is : "+key);
        DatabaseReference reff =Constants.REFERENCE.child("shoppingListItems").child(key);

        Query query = reff
                .limitToLast(50);

        FirebaseRecyclerOptions<ShoppingListItem> options =
                new FirebaseRecyclerOptions.Builder<ShoppingListItem>()
                        .setQuery(query,ShoppingListItem.class)
                        .build();

        mActiveListItemAdapter = new ActiveListItemAdapter(options,"",this);
        mListView.setAdapter(mActiveListItemAdapter);
*/

        DatabaseReference ref = ShoppingListsFragment.searchDatabaseReference;
        String key = ref.getKey();
        Log.e(LOG,"the key is : "+key);

        DatabaseReference reff =Constants.REFERENCE.child("shoppingListItems").child(key);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(LOG,"the number of items are : "+dataSnapshot.getChildrenCount());
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds !=null) {
                        ShoppingListItem item = ds.getValue(ShoppingListItem.class);
                        Log.e(LOG, "The item name is : " + item.getItemName());
                        listitems.add(item);
                        setUpRecyclerView();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*DatabaseReference databaseReference = Constants.REFERENCE.child("shoppingListItems");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(LOG,"the listener is attached");
                Log.e(LOG,"the snapshot is : "+dataSnapshot);
                for(DataSnapshot ds :dataSnapshot.getChildren()){
                    Log.e(LOG,"the second snapshot is : "+ds);
                    for(DataSnapshot ds1 :ds.getChildren()){
                        Log.e(LOG,"the third snapshot is : "+ds1);

                        ShoppingListItem item = ds1.getValue(ShoppingListItem.class);
                        Log.e(LOG,item.getItemName());

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }

    private void setUpRecyclerView() {
        Log.e(LOG,"the size of list is : "+listitems.size());
        MyActiveListItemAdapter adapter = new MyActiveListItemAdapter(this,listitems);
        mListView.setAdapter(adapter);
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
        //Toast.makeText(this, "the edit button is clicked", Toast.LENGTH_SHORT).show();
    }


    /*
    *//**
     * Remove current shopping list and its items from all nodes
     *//*
    public void removeList() {
        *//* Create an instance of the dialog fragment and show it *//*
        DialogFragment dialog = RemoveListDialogFragment.newInstance(mShoppingList, mListId,
                mSharedWithUsers);
        dialog.show(getFragmentManager(), "RemoveListDialogFragment");
    }*/
    /**
     * Show the add list item dialog when user taps "Add list item" fab
     * The items will be added to ShoppingListItems  and push and then
     * add the item to that push id
     */
    public void showAddListItemDialog(View view) {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = AddListItemDialogFragment.newInstance(mShoppingList, mListId,
                mEncodedEmail, mSharedWithUsers);
        dialog.show(getSupportFragmentManager(), "AddListItemDialogFragment");

        Toast.makeText(this, "can add the item to the shoping list ", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "the id to be used to add the item is the  : "+reference.getKey(), Toast.LENGTH_LONG).show();
        KEY = reference.getKey();
    }


    /**
     * Link layout elements from XML and setup the toolbar
     */
    private void initializeScreen() {
        mListView = (RecyclerView) findViewById(R.id.list_view_shopping_list_items);
        mListView.setHasFixedSize(true);
        mListView.setStateListAnimator(new StateListAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mListView.setLayoutManager(manager);
        mTextViewPeopleShopping = (TextView) findViewById(R.id.text_view_people_shopping);
        mButtonShopping = (Button) findViewById(R.id.button_shopping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        /* Common toolbar setup */
        setSupportActionBar(toolbar);
        /* Add back button to the action bar */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        /* Inflate the footer, set root layout to null*/
        //View footer = getLayoutInflater().inflate(R.layout.footer_empty, null);

    }
}
