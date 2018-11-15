package com.talabaty.swever.admin.Managment.Employees;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.talabaty.swever.admin.Home;
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.R;
import com.talabaty.swever.admin.SystemDatabase;
import com.talabaty.swever.admin.SystemPermission;

import java.util.ArrayList;
import java.util.List;

public class EmployeesHome extends Fragment {
    Button control, add;
    Intent intent;

    SystemDatabase systemDatabase;
    Cursor cursor, sysCursor;
    List<SystemPermission> permissions;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_employee,container,false);
        control = view.findViewById(R.id.control);
        add = view.findViewById(R.id.add);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ((Home) getActivity())
                .setActionBarTitle("إداره الموظفين");

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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissions.get(4).isView()) {
                    intent = new Intent(getActivity(), Home.class);
                    intent.putExtra("fragment", "emp_add");
                    startActivity(intent);
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText(" لا توجد صلاحيه للدخول");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }

            }
        });

        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissions.get(4).isUpdate()) {
                    intent = new Intent(getActivity(), Mabi3atNavigator.class);
                    intent.putExtra("fragment", "emp_control");
                    startActivity(intent);
                }else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText(" لا توجد صلاحيه للدخول");

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
