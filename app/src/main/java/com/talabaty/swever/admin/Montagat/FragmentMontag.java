package com.talabaty.swever.admin.Montagat;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.talabaty.swever.admin.Home;
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.Montagat.ControlUnitAndDepartment.FragmentControlUnitAndDepartment;
import com.talabaty.swever.admin.Montagat.UnitAndDepartment.Department.FragmentDepartment;
import com.talabaty.swever.admin.Montagat.UnitAndDepartment.Unit.FragmentUnit;
import com.talabaty.swever.admin.R;
import com.talabaty.swever.admin.SystemDatabase;
import com.talabaty.swever.admin.SystemPermission;

import java.util.ArrayList;
import java.util.List;

public class FragmentMontag extends Fragment {

    Button add_montage, control_montage;
    Button add_unit, control_unit;
    Button add_dep, control_dep;
    FragmentManager fragmentManager;
    LinearLayout unit, dep;

    int type;
    LoginDatabae loginDatabae;
    Cursor cursor;
    String permession = "";

    SystemDatabase systemDatabase;
    Cursor sysCursor;
    List<SystemPermission> permissions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_montagat,container,false);
        add_montage = view.findViewById(R.id.add_montage);
        add_unit = view.findViewById(R.id.add_unit);
        add_dep = view.findViewById(R.id.add_dep);
        control_dep = view.findViewById(R.id.control_dep);
        control_montage = view.findViewById(R.id.control_montag);
        control_unit = view.findViewById(R.id.control_unit);
        unit = view.findViewById(R.id.unit);
        dep = view.findViewById(R.id.dep);
        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ((Home) getActivity())
                .setActionBarTitle("المنتجات");
        fragmentManager = getFragmentManager();

        systemDatabase = new SystemDatabase(getActivity());
        sysCursor = systemDatabase.ShowData();
        permissions = new ArrayList<>();
        while (sysCursor.moveToNext()) {
            SystemPermission systemPermission = new SystemPermission();
            systemPermission.setCreate(Boolean.valueOf(sysCursor.getString(1)));
            systemPermission.setDelete(Boolean.valueOf(sysCursor.getString(2)));
            systemPermission.setView(Boolean.valueOf(sysCursor.getString(3)));
            systemPermission.setUpdate(Boolean.valueOf(sysCursor.getString(4)));
            systemPermission.setScreensId(Integer.parseInt(sysCursor.getString(5)));
            permissions.add(systemPermission);
        }

        while (cursor.moveToNext()) {
            permession = cursor.getString(6);
        }

        if (!permession.equals("1")) {
            unit.setVisibility(View.GONE);
            dep.setVisibility(View.GONE);

        }

//        fragmentManager.beginTransaction().replace(R.id.home_montag_frame,new FragmentHomeMontag()).commit();
        add_montage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                type = 1;

                if (permissions.get(1).isCreate()) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.frame_mabi3at, new FragmentChooseAdd().setType(1));
                    transaction.addToBackStack("FragmentChooseAdd");
                    transaction.commit();
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText("لا يمكن عرض الصفحه");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }

            }
        });

        add_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissions.get(1).isCreate()) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.frame_mabi3at, new FragmentUnit());
                    transaction.addToBackStack("FragmentUnit");
                    transaction.commit();
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText("لا يمكن عرض الصفحه");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        add_dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissions.get(1).isCreate()) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.frame_mabi3at, new FragmentDepartment());
                    transaction.addToBackStack("FragmentDepartment");
                    transaction.commit();
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText("لا يمكن عرض الصفحه");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        control_montage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                type = 0;
                if (permissions.get(1).isUpdate()) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.frame_mabi3at, new FragmentChooseAdd().setType(0));
                    transaction.addToBackStack("FragmentChooseAdd");
                    transaction.commit();
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText("لا يمكن عرض الصفحه");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }

            }
        });

        control_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissions.get(1).isUpdate()) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.frame_mabi3at, new FragmentControlUnitAndDepartment().setType("5"));
                    transaction.addToBackStack("FragmentControlUnit");
                    transaction.commit();
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText("لا يمكن عرض الصفحه");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        control_dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissions.get(1).isUpdate()) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.frame_mabi3at, new FragmentControlUnitAndDepartment().setType("6"));
                    transaction.addToBackStack("FragmentControlDepartment");
                    transaction.commit();
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText("لا يمكن عرض الصفحه");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });


    }
}
