package com.talabaty.swever.admin.Montagat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.talabaty.swever.admin.Home;
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.Montagat.AddMontag.AddMontag;
import com.talabaty.swever.admin.Montagat.AddReturanteMontage.AddReturanteMontage;
import com.talabaty.swever.admin.Montagat.Additions.FragmentAdditions;
import com.talabaty.swever.admin.Montagat.Market.AddMarketMontage;
import com.talabaty.swever.admin.Montagat.NewFood.FragmentAddNewFood;
import com.talabaty.swever.admin.R;

public class FragmentChooseAdd extends Fragment {

    FragmentManager fragmentManager;

    Button restaurant, market, other, base_food, additions;

    static int type;

    LoginDatabae loginDatabae;
    Cursor cursor;
    String permession = "";

    public static FragmentChooseAdd setType(int type1) {
        FragmentChooseAdd chooseAdd = new FragmentChooseAdd();
        type = type1;
        return chooseAdd;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_add, container, false);
        other = view.findViewById(R.id.other);
        market = view.findViewById(R.id.market);
        restaurant = view.findViewById(R.id.restaurant);
        additions = view.findViewById(R.id.additions);
        base_food = view.findViewById(R.id.base_food);
        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ((Home) getActivity())
                .setActionBarTitle("أختر مجالا");
        fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.home_montag_frame,new FragmentHomeMontag()).commit();

        while (cursor.moveToNext()) {
            permession = cursor.getString(6);
        }

        if (permession.equals("1")) {
            //super market
            other.setVisibility(View.GONE);
            restaurant.setVisibility(View.GONE);
            additions.setVisibility(View.GONE);
            base_food.setVisibility(View.GONE);
        } else if (permession.equals("2")) {
            //restaurant
            other.setVisibility(View.GONE);
            market.setVisibility(View.GONE);
        } else if (permession.equals("3")) {
            //other
            restaurant.setVisibility(View.GONE);
            additions.setVisibility(View.GONE);
            base_food.setVisibility(View.GONE);
            market.setVisibility(View.GONE);
        }

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new AddMontag()).addToBackStack("AddMontag").commit();
                } else {
                    Intent intent = new Intent(getActivity(), Mabi3atNavigator.class);
                    intent.putExtra("fragment", "control");
                    startActivity(intent);
                }
            }
        });

        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new AddMarketMontage()).addToBackStack("AddMarketMontage").commit();
                } else {
                    Intent intent = new Intent(getActivity(), Mabi3atNavigator.class);
                    intent.putExtra("fragment", "control1");
                    startActivity(intent);
                }
            }
        });

        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new AddReturanteMontage()).addToBackStack("AddMarketMontage").commit();
                } else {
                    Intent intent = new Intent(getActivity(), Mabi3atNavigator.class);
                    intent.putExtra("fragment", "control2");
                    startActivity(intent);
                }
            }
        });

        base_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new FragmentAddNewFood()).addToBackStack("FragmentAddNewFood").commit();
                } else {
                    Intent intent = new Intent(getActivity(), Mabi3atNavigator.class);
                    intent.putExtra("fragment", "control3");
                    startActivity(intent);
                }
            }
        });

        additions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new FragmentAdditions()).addToBackStack("FragmentAdditions").commit();
                } else {
                    Intent intent = new Intent(getActivity(), Mabi3atNavigator.class);
                    intent.putExtra("fragment", "control4");
                    startActivity(intent);
                }
            }
        });


    }
}
