package com.talabaty.swever.admin.Offers;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.talabaty.swever.admin.Home;
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.Montagat.Additions.FragmentAdditions;
import com.talabaty.swever.admin.Montagat.NewFood.FragmentAddNewFood;
import com.talabaty.swever.admin.R;
import com.talabaty.swever.admin.SystemDatabase;
import com.talabaty.swever.admin.SystemPermission;

import java.util.ArrayList;
import java.util.List;

public class FragmentChooseOffers extends Fragment {

    FragmentManager fragmentManager;

    Button restaurant, market, other, base_food, additions;

    static int type;

    LoginDatabae loginDatabae;
    Cursor cursor;
    String permession = "";

    SystemDatabase systemDatabase;
    Cursor sysCursor;
    List<SystemPermission> permissions;

    public static FragmentChooseOffers setType(int type1){
        FragmentChooseOffers chooseAdd = new FragmentChooseOffers();
        type = type1;
        return chooseAdd;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_add,container,false);
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

        while (cursor.moveToNext()) {
            permession = cursor.getString(6);
        }

        systemDatabase = new SystemDatabase(getActivity());
        sysCursor = systemDatabase.ShowData();
        permissions = new ArrayList<>();
        while (sysCursor.moveToNext()) {
            SystemPermission systemPermission = new SystemPermission();
            systemPermission.setCreate(Boolean.valueOf(sysCursor.getString(1)));
            systemPermission.setDelete(Boolean.valueOf(sysCursor.getString(2)));
            systemPermission.setUpdate(Boolean.valueOf(sysCursor.getString(4)));
            systemPermission.setScreensId(Integer.parseInt(sysCursor.getString(5)));
            permissions.add(systemPermission);
        }

        fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.home_montag_frame,new FragmentHomeMontag()).commit();

        if (permession.equals("1")) {
            //super market
            other.setVisibility(View.GONE);
            restaurant.setVisibility(View.GONE);
            additions.setVisibility(View.GONE);
            base_food.setVisibility(View.GONE);

            if (type == 2) {
                /** Edit */
                if (permissions.get(1).isUpdate()) {
                    fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new Fragment_List_Offers().setData("1")).commit();
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
            } else {
                /** Add */
                if (permissions.get(1).isCreate()) {
                    Intent intent = new Intent(getActivity(), Mabi3atNavigator.class);
                    intent.putExtra("fragment", "control_offer_market");
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

            if (type == 2) {
                /** Edit */
                if (permissions.get(1).isUpdate()) {
                    fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new Fragment_List_Offers().setData("0")).commit();
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

            } else {
                /** Add */
                if (permissions.get(1).isCreate()) {
                    Intent intent = new Intent(getActivity(), Mabi3atNavigator.class);
                    intent.putExtra("fragment", "control_offer_other");
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

        }

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 2) {
                    /** Edit */
                    if (permissions.get(1).isUpdate()) {
                        fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new Fragment_List_Offers().setData("0")).commit();
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
                } else {
                    /** Add */
                    if (permissions.get(1).isCreate()) {
                        Intent intent = new Intent(getActivity(), Mabi3atNavigator.class);
                        intent.putExtra("fragment", "control_offer_other");
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
            }
        });


        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 2) {
                    /** Edit */
                    if (permissions.get(1).isUpdate()) {
                        fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new Fragment_List_Offers().setData("2")).commit();
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
                } else {
                    /** Add */
                    if (permissions.get(1).isCreate()) {
                        Intent intent = new Intent(getActivity(), Mabi3atNavigator.class);
                        intent.putExtra("fragment", "control_offer_rest");
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
            }
        });

        additions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    if (permissions.get(1).isCreate()) {
                        fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new FragmentAdditions()).addToBackStack("FragmentAdditions").commit();
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
                } else {
                    if (permissions.get(1).isUpdate()) {
                        Intent intent = new Intent(getActivity(), Mabi3atNavigator.class);
                        intent.putExtra("fragment", "control4");
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
            }
        });


        base_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    if (permissions.get(1).isCreate()) {
                        fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new FragmentAddNewFood()).addToBackStack("FragmentAddNewFood").commit();
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
                } else {
                    if (permissions.get(1).isUpdate()) {
                        Intent intent = new Intent(getActivity(), Mabi3atNavigator.class);
                        intent.putExtra("fragment", "control3");
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
            }
        });

        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 2) {
                    /** Edit */
                    if (permissions.get(1).isUpdate()) {
                        fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new Fragment_List_Offers().setData("1")).commit();
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
                } else {
                    /** Add */
                    if (permissions.get(1).isCreate()) {
                        Intent intent = new Intent(getActivity(), Mabi3atNavigator.class);
                        intent.putExtra("fragment", "control_offer_market");
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
            }
        });

    }
}
