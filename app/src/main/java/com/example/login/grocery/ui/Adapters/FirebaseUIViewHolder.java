package com.example.login.grocery.ui.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.login.grocery.R;

public class FirebaseUIViewHolder extends RecyclerView.ViewHolder {
    TextView textViewListName;
    TextView createdBy;
    TextView textViewCreatedByUser;
    TextView textViewPeopleShoppingCount;

    public FirebaseUIViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewListName = itemView.findViewById(R.id.text_view_list_name);
        createdBy = itemView.findViewById(R.id.created_by);
        textViewCreatedByUser = itemView.findViewById(R.id.text_view_created_by_user);
        textViewPeopleShoppingCount = itemView.findViewById(R.id.text_view_people_shopping_count);

    }

    public void textViewListName(String text){
          textViewListName.setText(text);
    }
    public void textViewCreatedByUser(String username){
        textViewCreatedByUser.setText(username);
    }
    public void textViewPeopleShoppingCount(){
        textViewPeopleShoppingCount.setText("6");
    }



}
