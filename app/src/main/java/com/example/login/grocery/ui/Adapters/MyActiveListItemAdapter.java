package com.example.login.grocery.ui.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.login.grocery.R;
import com.example.login.grocery.model.ShoppingListItem;

import java.util.ArrayList;

public class MyActiveListItemAdapter extends RecyclerView.Adapter<MyActiveListItemAdapter.ListItemViewHolder> {
    Context context;
    ArrayList<ShoppingListItem> shoppingListItem;

    public MyActiveListItemAdapter(Context context,ArrayList<ShoppingListItem>shoppingLists){
        this.context = context;
        this.shoppingListItem=shoppingLists;
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
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int i) {


        holder.onBindItemName(shoppingListItem.get(i).getItemName());
        holder.onBindBoughtBy(shoppingListItem.get(i).getBoughtBy());
        holder.onBindBoughtByUser(shoppingListItem.get(i).getOwner());

    }

    @Override
    public int getItemCount() {
        return shoppingListItem.size();
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder{
        public ImageButton buttonRemoveItem;
        public TextView textViewItemName;
        public TextView textViewBoughtByUser;
        public TextView textViewBoughtBy;

        public ListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            buttonRemoveItem = itemView.findViewById(R.id.button_remove_item);
            textViewItemName = itemView.findViewById(R.id.text_view_active_list_item_name);
            textViewBoughtByUser = itemView.findViewById(R.id.text_view_bought_by_user);
            textViewBoughtBy=itemView.findViewById(R.id.text_view_bought_by);
        }

        /**
         * here we will bind the view with the data
         * @param itemName
         */
        public void onBindItemName(String itemName){
            if(itemName !=null){
                textViewItemName.setText(itemName);
            }

        }

        public void onBindBoughtByUser(String name){
            if(name !=null){
                textViewBoughtByUser.setText(name);
            }

        }

        public void onBindBoughtBy(String name){
            if(name !=null){
                textViewBoughtBy.setText(name);
            }

        }
    }
}
