package com.example.login.grocery.ui.Fragments;

import android.animation.StateListAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.login.grocery.R;
import com.example.login.grocery.model.ShoppingList;
import com.example.login.grocery.ui.Adapters.ActiveListAdapter;
import com.example.login.grocery.ui.Adapters.FirebaseUIViewHolder;
import com.example.login.grocery.ui.Screens.ActiveListDetailsActivity;
import com.example.login.grocery.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShoppingListsFragment} interface
 * to handle interaction events.
 * Use the {@link ShoppingListsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingListsFragment extends Fragment implements ActiveListAdapter.ListItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final String LOG = "ShoppingListFragment";
    View view;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ActiveListAdapter mAdapter;
    private DatabaseReference reference;
    private FirebaseUIViewHolder mViewHolder;
    // private OnFragmentInteractionListener mListener;
    private ArrayList<ShoppingList> listHashMap = new ArrayList<>();
    private ArrayList<DatabaseReference>references = new ArrayList<>();
    public static DatabaseReference searchDatabaseReference ;

    public ShoppingListsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingListsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingListsFragment newInstance(String param1, String param2) {
        ShoppingListsFragment fragment = new ShoppingListsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shopping_lists, container, false);


        //**********************retriving data from the firebase*********************************
        reference = Constants.REFERENCE.child("activeList");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listHashMap.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    ShoppingList list = ds.getValue(ShoppingList.class);

                    Log.e(LOG,"the length of the list is :  "+list.getListName());
                    Log.e(LOG,"the length of the list is :  "+list.getOwner());
                    references.add(ds.getRef());
                    listHashMap.add(list);


                }
                setUpList(listHashMap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //***************************************************************************************

        return view;
    }


   public void setUpList(ArrayList<ShoppingList> list){
       //lets attach the recycler view here
       mRecyclerView = view.findViewById(R.id.shopping_list_view);

       // use this setting to improve performance if you know that changes
       // in content do not change the layout size of the RecyclerView
       mRecyclerView.setHasFixedSize(true);
       mRecyclerView.setStateListAnimator(new StateListAnimator());
       // use a linear layout manager
       mLayoutManager = new LinearLayoutManager(getContext());
       mRecyclerView.setLayoutManager(mLayoutManager);
       mAdapter = new ActiveListAdapter(getContext(),list,this);

       mRecyclerView.setAdapter(mAdapter);

       //lets use the firebaseui to populate the list


   }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
      //  mListener = null;
    }

    @Override
    public void onListNameClickListener(int clickedItemIndex, TextView textView) {

        //Toast.makeText(getContext(), textView.getText(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), ActiveListDetailsActivity.class);
        String data = textView.getText().toString();
        intent.putExtra(Constants.KEY_LIST_ITEM_ID,data);
        searchDatabaseReference = references.get(clickedItemIndex);
        startActivity(intent);

    }


}
