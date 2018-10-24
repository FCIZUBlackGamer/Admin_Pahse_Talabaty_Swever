package com.talabaty.swever.admin.Managment.Employees.AddEmployee;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fourhcode.forhutils.FUtilsValidation;
import com.talabaty.swever.admin.Home;
import com.talabaty.swever.admin.Managment.Employees.Employee;
import com.talabaty.swever.admin.R;

public class MoneyStaff extends Fragment {
    Button first, third;
    FragmentManager fragmentManager;
    static Employee employee;
    static Bitmap phot, car;
    EditText add_employee_fragment_employeenameTxt, add_employee_fragment_mailTxt, add_employee_fragment_employmentName,
            add_employee_fragment_employeeManagement, add_employee_fragment_employeeResponsibilities, add_employee_fragment_employeeWorkplaceName,
            add_employee_fragment_employeeBranchName, add_employee_fragment_employeePhone, add_employee_fragment_emloyeePhone;

    static int Type = 0;

    static MoneyStaff setData(Employee data, Bitmap photo, Bitmap card, int type) {
        MoneyStaff staff = new MoneyStaff();
        employee = new Employee();
        employee = data;
        phot = photo;
        car = card;
        Type = type;
        return staff;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addemployee2,container,false);
        first = view.findViewById(R.id.first);
        third = view.findViewById(R.id.third);
        add_employee_fragment_employeenameTxt = view.findViewById(R.id.add_employee_fragment_employeenameTxt);
        add_employee_fragment_mailTxt = view.findViewById(R.id.add_employee_fragment_mailTxt);
        add_employee_fragment_employmentName = view.findViewById(R.id.add_employee_fragment_employmentName);
        add_employee_fragment_employeeManagement = view.findViewById(R.id.add_employee_fragment_employeeManagement);
        add_employee_fragment_employeeResponsibilities = view.findViewById(R.id.add_employee_fragment_employeeResponsibilities);
        add_employee_fragment_employeeWorkplaceName = view.findViewById(R.id.add_employee_fragment_employeeWorkplaceName);
        add_employee_fragment_employeeBranchName = view.findViewById(R.id.add_employee_fragment_employeeBranchName);
        add_employee_fragment_employeePhone = view.findViewById(R.id.add_employee_fragment_employeePhone);
        add_employee_fragment_emloyeePhone = view.findViewById(R.id.add_employee_fragment_emloyeePhone);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((Home) getActivity())
                .setActionBarTitle("إضافه موظف");

        fragmentManager = getFragmentManager();
        if (employee != null && Type == 1) {
            add_employee_fragment_employeenameTxt.setText(employee.getSalary() + "");
            add_employee_fragment_mailTxt.setText(employee.getSalaryBonus() + "");
            add_employee_fragment_employmentName.setText(employee.getTransportationAllowance() + "");
            add_employee_fragment_employeeManagement.setText(employee.getHousingAllowance() + "");
            add_employee_fragment_employeeResponsibilities.setText(employee.getInsuranceAllowance() + "");
            add_employee_fragment_employeeWorkplaceName.setText(employee.getInsuranceNum() + "");
            add_employee_fragment_employeeBranchName.setText(employee.getInfectionAllowance() + "");
            add_employee_fragment_employeePhone.setText(employee.getWorkingHours() + "");
            add_employee_fragment_emloyeePhone.setText(employee.getWorkingHoursBonus() + "");
        }
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Type == 1)
                    fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new PersonalInfo().setData(employee)).addToBackStack("PersonalInfo").commit();
                else
                    fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new PersonalInfo().setData(employee)).addToBackStack("PersonalInfo").commit();

            }
        });

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!FUtilsValidation.isEmpty(add_employee_fragment_employeenameTxt, "ادخل المرتب")&&
                        !FUtilsValidation.isEmpty(add_employee_fragment_mailTxt, "ادخل اضافى المرتب")&&
                        !FUtilsValidation.isEmpty(add_employee_fragment_employmentName, "ادخل بدل التنقل")&&
                        !FUtilsValidation.isEmpty(add_employee_fragment_employeeManagement, "ادخل قيمه")&&
                        !FUtilsValidation.isEmpty(add_employee_fragment_employeeResponsibilities, "ادخل قيمه")&&
                        !FUtilsValidation.isEmpty(add_employee_fragment_employeeWorkplaceName, "ادخل قيمه")&&
                        !FUtilsValidation.isEmpty(add_employee_fragment_employeeBranchName, "ادخل قيمه")&&
                        !FUtilsValidation.isEmpty(add_employee_fragment_employeePhone, "ادخل قيمه")&&
                        !FUtilsValidation.isEmpty(add_employee_fragment_emloyeePhone, "ادخل قيمه")
                        ){
                }else {
                    employee.setSalary(Integer.parseInt(add_employee_fragment_employeenameTxt.getText().toString()));
                    employee.setSalaryBonus(add_employee_fragment_mailTxt.getText().toString());
                    employee.setTransportationAllowance(Integer.parseInt(add_employee_fragment_employmentName.getText().toString()));
                    employee.setHousingAllowance(Integer.parseInt(add_employee_fragment_employeeManagement.getText().toString()));
                    employee.setInsuranceAllowance(Integer.parseInt(add_employee_fragment_employeeResponsibilities.getText().toString()));
                    employee.setInsuranceNum(Integer.parseInt(add_employee_fragment_employeeWorkplaceName.getText().toString()));
                    employee.setInfectionAllowance(Integer.parseInt(add_employee_fragment_employeeBranchName.getText().toString()));
                    employee.setWorkingHours(Integer.parseInt(add_employee_fragment_employeePhone.getText().toString()));
                    employee.setWorkingHoursBonus(Integer.parseInt(add_employee_fragment_emloyeePhone.getText().toString()));
                    if (Type == 1)
                        fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new AddtionalInfo().setData(employee, phot, car, 1)).addToBackStack("AddtionalInfo").commit();
                    else
                        fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new AddtionalInfo().setData(employee, phot, car, 0)).addToBackStack("AddtionalInfo").commit();
                }


            }
        });
    }
}
