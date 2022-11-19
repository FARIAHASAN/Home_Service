package com.example.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
   int price1day,price1week,price1month,price6month,current_service_price=100;
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
//         see=view.findViewById(R.id.see);

       LinearLayout Purchase = view.findViewById(R.id.purchase);
        Purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Gmap.class);

                intent.putExtra("ServicePrice", Integer.toString(current_service_price));
                intent.putExtra("ProviderName", name);
                intent.putExtra("ProviderPhone", mobile);
                intent.putExtra("ServiceType", ServiceType);
                getActivity().startActivity(intent);


            }
        });

         day=view.findViewById(R.id.radio1day);
        week=view.findViewById(R.id.radio1week);
        month1=view.findViewById(R.id.radio1month);
       month6=view.findViewById(R.id.radio6month);


      day.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              current_service_price=price1day;
              radiotapped(day);

          }
      });
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_service_price=price1week;
                radiotapped(week);

            }
        });
        month1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_service_price=price1month;
                radiotapped(month1);

            }
        });
        month6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_service_price=price6month;
                radiotapped(month6);

            }
        });

        nameHolder.setText(name);
        mobileHolder.setText(mobile);
        emailHolder.setText(email);
        descriptionHolder.setText(description);
        if(ServiceType.equals("Baby Sitting")) {

            price1day=1000;
            current_service_price=price1day;

            price1week=3000;
            price1month=8000;
            price6month=20000;
            //convert to string
            s1day= Integer.toString(price1day);
            //see.setText( "Current price:  "+s1day);
            s1week= Integer.toString(price1week);
            s1month= Integer.toString(price1month);;
            s6month=Integer.toString(price6month);;



            day.setText("For 1 day price:"+s1day);
            week.setText("For 1 week price:"+s1week);
            month1.setText("For 1 month price:"+s1month);
            month6.setText("For 6 month price:"+s6month);




        }

        else if(ServiceType.equals("Senior Care")) {
            price1day=800;
            current_service_price=  price1day;

            price1week=5000;
            price1month=20000;
            price6month=75000;
            //convert to string
            s1day= Integer.toString(price1day);
            //see.setText( "Current price:  "+s1day);
            s1week= Integer.toString(price1week);
            s1month= Integer.toString(price1month);;
            s6month=Integer.toString(price6month);;



            day.setText("For 1 day price:"+s1day);
            week.setText("For 1 week price:"+s1week);
            month1.setText("For 1 month price:"+s1month);
            month6.setText("For 6 month price:"+s6month);
        }
        else if(ServiceType.equals("Sign Language"))
        {
            price1day=1200;
            current_service_price=price1day;

            price1week=5000;
            price1month=20000;
            price6month=75000;
            s1day= Integer.toString(price1day);
            //see.setText( "Current price:  "+s1day);
            s1week= Integer.toString(price1week);
            s1month= Integer.toString(price1month);;
            s6month=Integer.toString(price6month);;



            day.setText("For 1 day price:"+s1day);
            week.setText("For 1 week price:"+s1week);
            month1.setText("For 1 month price:"+s1month);
            month6.setText("For 6 month price:"+s6month);
        }
        else if(ServiceType.equals("Special Child"))
        {
            price1day=1500;
            current_service_price=price1week;

            price1week=5000;
            price1month=20000;
            price6month=75000;
            //convert to string
            s1day= Integer.toString(price1day);
            //see.setText( "Current price:  "+s1day);
            s1week= Integer.toString(price1week);
            s1month= Integer.toString(price1month);;
            s6month=Integer.toString(price6month);;



            day.setText("For 1 day price:"+s1day);
            week.setText("For 1 week price:"+s1week);
            month1.setText("For 1 month price:"+s1month);
            month6.setText("For 6 month price:"+s6month);
        }
        else if(ServiceType.equals("Cylinder Provider"))
        {
            price1day=1300;
            current_service_price=price1day;

            price1week=5000;
            price1month=20000;
            price6month=75000;
            //convert to string
            s1day= Integer.toString(price1day);
            //see.setText( "Current price:  "+s1day);
            s1week= Integer.toString(price1week);
            s1month= Integer.toString(price1month);;
            s6month=Integer.toString(price6month);;



            day.setText("For 1 day price:"+s1day);
            week.setText("For 1 week price:"+s1week);
            month1.setText("For 1 month price:"+s1month);
            month6.setText("For 6 month price:"+s6month);
        }
        else if(ServiceType.equals("PhysioTherapy"))
        {
            price1day=1500;
            current_service_price=price1day;

            price1week=5000;
            price1month=20000;
            price6month=75000;
            //convert to string
            s1day= Integer.toString(price1day);
            //see.setText( "Current price:  "+s1day);
            s1week= Integer.toString(price1week);
            s1month= Integer.toString(price1month);;
            s6month=Integer.toString(price6month);;



            day.setText("For 1 day price:"+s1day);
            week.setText("For 1 week price:"+s1week);
            month1.setText("For 1 month price:"+s1month);
            month6.setText("For 6 month price:"+s6month);
        }
        else if(ServiceType.equals("Diabetic and pressure measure"))
        {
            price1day=1400;
            current_service_price=price1day;

            price1week=5000;
            price1month=20000;
            price6month=75000;
            //convert to string
            s1day= Integer.toString(price1day);
            //see.setText( "Current price:  "+s1day);
            s1week= Integer.toString(price1week);
            s1month= Integer.toString(price1month);;
            s6month=Integer.toString(price6month);;



            day.setText("For 1 day price:"+s1day);
            week.setText("For 1 week price:"+s1week);
            month1.setText("For 1 month price:"+s1month);
            month6.setText("For 6 month price:"+s6month);
        }

        Glide.with(getContext()).load(image).into(imgHolder);






     return view;
    }

    private void updateRadioGroup(RadioButton Selected) {
        day.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radio_off));
        week.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radio_off));
        month1.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radio_off));
        month6.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radio_off));
        Selected.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radio_on));
       // see.setText( "Current price:  "+current_service_price.toString());


    }


    public void radiotapped( RadioButton view) {
        int selectedID= view.getId();
        if(selectedID==R.id.radio1day)
        {
            updateRadioGroup(day);
        }
        else  if(selectedID==R.id.radio1week)
        {
            updateRadioGroup(week);
        }
        else  if(selectedID==R.id.radio1month)
        {
            updateRadioGroup(month1);
        }
        else  if(selectedID==R.id.radio6month)
        {
            updateRadioGroup(month6);
        }

    }




//    public  void onBackPressed()
//    {
//
//        AppCompatActivity activity=(AppCompatActivity)getContext();
//        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new ).addToBackStack(null).commit();
//    }
}