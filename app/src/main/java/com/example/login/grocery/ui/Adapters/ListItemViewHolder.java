package com.example.login.grocery.ui.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.login.grocery.R;

public class ListItemViewHolder extends RecyclerView.ViewHolder{

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
