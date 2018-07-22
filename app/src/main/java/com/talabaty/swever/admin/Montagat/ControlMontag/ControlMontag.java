package com.talabaty.swever.admin.Montagat.ControlMontag;

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
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.R;

import java.util.ArrayList;
import java.util.List;

public class ControlMontag extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Talabat> talabats;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_montag,container,false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rec);
        recyclerView.setLayoutManager(layoutManager);
        talabats = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ((Mabi3atNavigator) getActivity())
                .setActionBarTitle("عمليات المنتجات");

        for (int x=0; x<10; x++){
            Talabat item = new Talabat(x,"admin","v","rr","rr","rr","rr","dsd","15");
            talabats.add(item);
        }

        adapter = new ControlMontagAdapter(talabats,getActivity());
        recyclerView.setAdapter(adapter);

    }

}
