package com.talabaty.swever.admin.Mabi3at.Delevry;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.talabaty.swever.admin.Mabi3at.Delevry.Capital.WithCapital;
import com.talabaty.swever.admin.Mabi3at.Delevry.Kilo.WithKilo;
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.R;

public class DelevryHome extends Fragment {
    RadioGroup radioGroup;
    FragmentManager fragmentManager;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_delevry_home,container,false);
        radioGroup = view.findViewById(R.id.radio_group);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        fragmentManager = getFragmentManager();
        ((Mabi3atNavigator) getActivity())
                .setActionBarTitle("تعاملات الديلفرى");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.kilo){
                    Log.e("Chosen","Kilo");
                    fragmentManager.beginTransaction().replace(R.id.frame_delevry,new WithKilo()).commit();
                }else {
                    Log.e("Chosen","Capital");
                    fragmentManager.beginTransaction().replace(R.id.frame_delevry,new WithCapital()).commit();
                }
            }
        });

    }
}
