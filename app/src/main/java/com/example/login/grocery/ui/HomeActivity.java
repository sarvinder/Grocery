package com.example.login.grocery.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.login.grocery.R;
import com.example.login.grocery.ui.Fragments.MealsFragment;
import com.example.login.grocery.ui.Fragments.ShoppingListsFragment;
import com.example.login.grocery.ui.Widgets.AddListDialogFragment;
import com.example.login.grocery.ui.Widgets.AddMealDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    String mEncodedEmail = "s";
    private static final String LOG_TAG = HomeActivity.class.getSimpleName();

    //Firebase setup
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /**
         * Link layout elements from XML and setup the toolbar
         */
        initializeScreen();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Override onOptionsItemSelected to add action_settings only to the MainActivity
     *
     * @param item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        /**
         * Open SettingsActivity with sort options when Sort icon was clicked
         */
        if (id == R.id.action_sort) {
           // startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Link layout elements from XML and setup the toolbar
     */
    public void initializeScreen() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        /**
         * Create SectionPagerAdapter, set it as adapter to viewPager with setOffscreenPageLimit(2)
         **/
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        /**
         * Setup the mTabLayout with view pager
         */
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * This will be called when in shoping fragement the
     * add button is clicked
     * @param view
     */

    public void showAddListDialog(View view) {

        DialogFragment dialog = AddListDialogFragment.newInstance(mEncodedEmail);
        dialog.show(this.getSupportFragmentManager(),"AddListDialogFragment");
    }

    /**
     * Shows the add meal dialog
     * @param view
     */
    public void showAddMealDialog(View view)
    {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = AddMealDialogFragment.newInstance();
        dialog.show(this.getSupportFragmentManager(), "AddMealDialogFragment");
    }

    public class SectionPagerAdapter extends FragmentStatePagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            /**
             * Set fragment to different fragments depending on position in ViewPager
             */
            switch (position) {
                case 0:
                    fragment = ShoppingListsFragment.newInstance("sa","chel");
                    break;
                case 1:
                    fragment = MealsFragment.newInstance();
                    break;
                default:
                    fragment = ShoppingListsFragment.newInstance("sarvinder","chelsea");
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.pager_title_shopping_lists);
                case 1:
                default:
                    return getString(R.string.pager_title_meals);
            }
        }
    }

}
