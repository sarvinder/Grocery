/**
 * For the test case only we will be inflating only one item
 */

package com.example.login.grocery.ui.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.login.grocery.R;
import com.example.login.grocery.model.ShoppingList;

public class ActiveListAdapter extends RecyclerView.Adapter<ActiveListAdapter.MyViewHolder> {
    Context context;
    ShoppingList shoppingList;

    public ActiveListAdapter(Context context,ShoppingList list) {
        this.context=context;
        this.shoppingList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutIdForListItem = R.layout.single_active_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        MyViewHolder holder = new MyViewHolder(view);


        return holder;


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        //this is where the data binding is called
        myViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

    TextView textViewListName;
    TextView createdBy;
    TextView textViewCreatedByUser;
    TextView textViewPeopleShoppingCount;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewListName = itemView.findViewById(R.id.text_view_list_name);
        createdBy = itemView.findViewById(R.id.created_by);
        textViewCreatedByUser = itemView.findViewById(R.id.text_view_created_by_user);
        textViewPeopleShoppingCount = itemView.findViewById(R.id.text_view_people_shopping_count);

    }

    /**
     * Binds the data to there respective views
     * @param position
     */
    public void onBind(int position){

        //in this section we will bind the text view to the data
        //but this will be done by firebase
        String listname = shoppingList.getListName();
        String username = shoppingList.getOwner();
        textViewListName.setText(listname);
        textViewCreatedByUser.setText(username);
        textViewPeopleShoppingCount.setText("6");

    }


}
}
