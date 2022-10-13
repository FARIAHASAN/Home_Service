package com.example.splashscreen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link detailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class detailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
     String name,email,mobile,description,image,ServiceType,s1day,s1week,s1month,s6month;
     Integer price1day,price1week,price1month,price6month;
     RadioButton day,week,month1,month6;

    public detailsFragment() {
        // Required empty public constructor
    }

    public detailsFragment(String name, String email, String mobile, String description,String image,String ServiceType) {
        this.name=name;
        this.email=email;
        this.mobile=mobile;
        this.description=description;
        this.image=image;
        this.ServiceType=ServiceType;




    }


    // TODO: Rename and change types and number of parameters
    public static detailsFragment newInstance(String param1, String param2) {
        detailsFragment fragment = new detailsFragment();
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
     View view= inflater.inflate(R.layout.fragment_details, container, false);
        ImageView imgHolder=view.findViewById(R.id.imagegholder);
       TextView nameHolder=view.findViewById(R.id.name);
        TextView mobileHolder=view.findViewById(R.id.mobile);
        TextView emailHolder=view.findViewById(R.id.email);
        TextView descriptionHolder=view.findViewById(R.id.description);
        TextView dayHolder=view.findViewById(R.id.price1day);
        TextView weekHolder=view.findViewById(R.id.price1week);
        TextView month1Holder=view.findViewById(R.id.price1month);
        TextView month6Holder=view.findViewById(R.id.price6month);

         day=view.findViewById(R.id.radio1day);
        week=view.findViewById(R.id.radio1week);
        month1=view.findViewById(R.id.radio1month);
       month6=view.findViewById(R.id.radio6month);


        nameHolder.setText(name);
        mobileHolder.setText(mobile);
        emailHolder.setText(email);
        descriptionHolder.setText(description);
        if(ServiceType.equals("Baby Sitting")) {
            price1day=1000;
            price1week=5000;
            price1month=20000;
            price6month=75000;
            //convert to string
            s1day= price1day.toString();
            s1week= price1week.toString();
            s1month= price1month.toString();
            s6month= price6month.toString();



          dayHolder.setText(s1day);
            weekHolder.setText(s1week);
            month1Holder.setText(s1month);
        month6Holder.setText(s6month);



        }

        else if(ServiceType.equals("Senior Care")) {
            price1day=800;
            price1week=5000;
            price1month=20000;
            price6month=75000;
            //convert to string
            s1day= price1day.toString();
            s1week= price1week.toString();
            s1month= price1month.toString();
            s6month= price6month.toString();



            dayHolder.setText(s1day);
            weekHolder.setText(s1week);
            month1Holder.setText(s1month);
            month6Holder.setText(s6month);
        }
        else if(ServiceType.equals("Sign Language"))
        {
            price1day=1200;
            price1week=5000;
            price1month=20000;
            price6month=75000;
            //convert to string
            s1day= price1day.toString();
            s1week= price1week.toString();
            s1month= price1month.toString();
            s6month= price6month.toString();



            dayHolder.setText(s1day);
            weekHolder.setText(s1week);
            month1Holder.setText(s1month);
            month6Holder.setText(s6month);
        }
        else if(ServiceType.equals("Special Child"))
        {
            price1day=1500;
            price1week=5000;
            price1month=20000;
            price6month=75000;
            //convert to string
            s1day= price1day.toString();
            s1week= price1week.toString();
            s1month= price1month.toString();
            s6month= price6month.toString();



            dayHolder.setText(s1day);
            weekHolder.setText(s1week);
            month1Holder.setText(s1month);
            month6Holder.setText(s6month);
        }
        else if(ServiceType.equals("Cylinder Provider"))
        {
            price1day=1300;
            price1week=5000;
            price1month=20000;
            price6month=75000;
            //convert to string
            s1day= price1day.toString();
            s1week= price1week.toString();
            s1month= price1month.toString();
            s6month= price6month.toString();



            dayHolder.setText(s1day);
            weekHolder.setText(s1week);
            month1Holder.setText(s1month);
            month6Holder.setText(s6month);
        }
        else if(ServiceType.equals("PhysioTherapy"))
        {
            price1day=1500;
            price1week=5000;
            price1month=20000;
            price6month=75000;
            //convert to string
            s1day= price1day.toString();
            s1week= price1week.toString();
            s1month= price1month.toString();
            s6month= price6month.toString();



            dayHolder.setText(s1day);
            weekHolder.setText(s1week);
            month1Holder.setText(s1month);
            month6Holder.setText(s6month);
        }
        else if(ServiceType.equals("Diabetic and pressure measure"))
        {
            price1day=1400;
            price1week=5000;
            price1month=20000;
            price6month=75000;
            //convert to string
            s1day= price1day.toString();
            s1week= price1week.toString();
            s1month= price1month.toString();
            s6month= price6month.toString();



            dayHolder.setText(s1day);
            weekHolder.setText(s1week);
            month1Holder.setText(s1month);
            month6Holder.setText(s6month);
        }

        Glide.with(getContext()).load(image).into(imgHolder);




     return view;
    }
//    public  void onBackPressed()
//    {
//
//        AppCompatActivity activity=(AppCompatActivity)getContext();
//        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new ).addToBackStack(null).commit();
//    }
}