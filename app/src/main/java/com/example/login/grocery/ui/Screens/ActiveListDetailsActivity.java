package com.example.login.grocery.ui.Screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.login.grocery.R;
import com.example.login.grocery.utils.Constants;

public class ActiveListDetailsActivity extends AppCompatActivity {

    private boolean mShopping = true;
    /* Stores whether the current user is the owner */
    private boolean mCurrentUserIsOwner = true;
    private String mListId;

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


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
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



        return super.onOptionsItemSelected(item);
    }
}
