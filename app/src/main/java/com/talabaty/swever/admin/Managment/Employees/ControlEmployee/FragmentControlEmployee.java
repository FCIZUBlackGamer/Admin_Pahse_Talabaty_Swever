package com.talabaty.swever.admin.Managment.Employees.ControlEmployee;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.talabaty.swever.admin.Home;
import com.talabaty.swever.admin.Mabi3at.DoneTalabat.Talabat;
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.Montagat.ControlMontag.ControlMontagAdapter;
import com.talabaty.swever.admin.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentControlEmployee extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Talabat> talabats;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_control,container,false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rec);
        recyclerView.setLayoutManager(layoutManager);
        talabats = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((Mabi3atNavigator) getActivity())
                .setActionBarTitle("عمليات الموظف");

        for (int x=0; x<10; x++){
            Talabat item = new Talabat(x,"admin","v","rr","rr","rr","rr","dsd");
            talabats.add(item);
        }

        adapter = new ControlEmployeeAdapter(talabats,getActivity());
        recyclerView.setAdapter(adapter);
    }
}
