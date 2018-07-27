package com.talabaty.swever.admin.Mabi3at.Delevry.Capital;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.talabaty.swever.admin.Mabi3at.DoneTalabat.Talabat;
import com.talabaty.swever.admin.Montagat.ControlMontag.ControlMontagAdapter;
import com.talabaty.swever.admin.R;

import java.util.ArrayList;
import java.util.List;

public class WithCapital extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<CapitalModel> models;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delevry_capital,container,false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rec);
        recyclerView.setLayoutManager(layoutManager);
        models = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        for (int x=0; x<10; x++){
            CapitalModel item = new CapitalModel("zagazig","20");
            models.add(item);
        }

        adapter = new CapitalAdapter(models,getActivity());
        recyclerView.setAdapter(adapter);
    }
}
