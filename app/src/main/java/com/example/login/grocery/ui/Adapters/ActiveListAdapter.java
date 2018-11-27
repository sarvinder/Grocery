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

import java.util.ArrayList;
import java.util.List;

public class ActiveListAdapter extends RecyclerView.Adapter<ActiveListAdapter.MyViewHolder> {
    Context context;
    ArrayList<ShoppingList> shoppingList;
    ListItemClickListener listItemClickListener;

    public ActiveListAdapter(Context context,ArrayList<ShoppingList>list,ListItemClickListener listItemClickListener) {
        this.context=context;
        this.shoppingList = list;
        this.listItemClickListener = listItemClickListener;
    }

    public interface ListItemClickListener{
        void onListNameClickListener(int clickedItemIndex ,TextView textView);
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
        return shoppingList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
        String listname = shoppingList.get(position).getListName();
        String username = shoppingList.get(position).getOwner();
        textViewListName.setText(listname);
        textViewCreatedByUser.setText(username);
        textViewPeopleShoppingCount.setText("6");
        textViewListName.setOnClickListener(this);

    }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listItemClickListener.onListNameClickListener(clickedPosition,textViewListName);
        }
    }
}
