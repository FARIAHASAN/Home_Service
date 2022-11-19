package com.example.splashscreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class fragmentNewOrder extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView orderrec;
    String name;
    myorderadapter adapter;

    public fragmentNewOrder() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static fragmentNewOrder newInstance(String param1, String param2) {
        fragmentNewOrder fragment = new fragmentNewOrder();
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

        //session value
        SessionManager sessionManager = new SessionManager(getContext());
        HashMap<String,String> userDetails=sessionManager.getUserInfo();
        name=userDetails.get(SessionManager.KEY_NAME);
        // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_new_order, container, false);

    orderrec=(RecyclerView) view.findViewById(R.id.orderrec);
   orderrec.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<orderModel> options =
                new FirebaseRecyclerOptions.Builder<orderModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("orders").orderByChild("Customer_name").equalTo(name), orderModel.class)
                        .build();
        adapter = new myorderadapter(options);
        orderrec.setAdapter(adapter);
    return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}