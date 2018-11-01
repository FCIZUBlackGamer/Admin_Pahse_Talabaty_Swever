package com.talabaty.swever.admin.Montagat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.talabaty.swever.admin.Home;
import com.talabaty.swever.admin.Montagat.ControlUnitAndDepartment.FragmentControlUnitAndDepartment;
import com.talabaty.swever.admin.Montagat.UnitAndDepartment.Department.FragmentDepartment;
import com.talabaty.swever.admin.Montagat.UnitAndDepartment.Unit.FragmentUnit;
import com.talabaty.swever.admin.R;

public class FragmentMontag extends Fragment {

    Button add_montage, control_montage;
    Button add_unit, control_unit;
    Button add_dep, control_dep;
    FragmentManager fragmentManager;

    int type;

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
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ((Home) getActivity())
                .setActionBarTitle("المنتجات");
        fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.home_montag_frame,new FragmentHomeMontag()).commit();
        add_montage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                type = 1;

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frame_mabi3at, new FragmentChooseAdd().setType(1));
                transaction.addToBackStack("FragmentChooseAdd");
                transaction.commit();


//                fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new FragmentChooseAdd().setType(1)).addToBackStack("FragmentChooseAdd").commit();

//                // Prepare the View for the animation
//                restaurant.setVisibility(View.VISIBLE);
//                restaurant.setAlpha(0.0f);
//
//                // Start the animation
//                restaurant.animate()
////                        .translationY(restaurant.getHeight())
//                        .translationX(restaurant.getWidth())
//                        .alpha(1.0f)
//                        .setListener(null);
//
//                s = restaurant.getWidth();
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        // Start the animation
//                        restaurant.animate()
////                        .translationY(restaurant.getHeight())
////                                .translationX(-s)
//                                .translationXBy(-s)
//                                .alpha(1.0f)
//                                .setListener(null);
//
//                    }
//                }, 1000);
//
//
//                // Prepare the View for the animation
//                market.setVisibility(View.VISIBLE);
//                market.setAlpha(0.0f);
//
//                // Start the animation
//                market.animate()
////                        .translationY(restaurant.getHeight())
//                        .translationX(-market.getWidth())
//                        .alpha(1.0f)
//                        .setListener(null);
//
//                s = market.getWidth();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        // Start the animation
//                        market.animate()
////                        .translationY(restaurant.getHeight())
////                                .translationX(-s)
//                                .translationXBy(s)
//                                .alpha(1.0f)
//                                .setListener(null);
//
//                    }
//                }, 1000);
//
//
//                // Prepare the View for the animation
//                other.setVisibility(View.VISIBLE);
//                other.setAlpha(0.0f);
//
//                // Start the animation
//                other.animate()
////                        .translationY(restaurant.getHeight())
//                        .translationX(other.getWidth())
//                        .alpha(1.0f)
//                        .setListener(null);
//
//                s = other.getWidth();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        // Start the animation
//                        other.animate()
////                        .translationY(restaurant.getHeight())
////                                .translationX(-s)
//                                .translationXBy(-s)
//                                .alpha(1.0f)
//                                .setListener(null);
//
//
//                        // Prepare the View for the animation
//                        base_food.setVisibility(View.VISIBLE);
//                        base_food.setAlpha(0.0f);
//
//                        // Start the animation
//                        base_food.animate()
//                                .rotationX(360)
//                                .alpha(1.0f)
//                                .setListener(null);
//
//
//                    }
//                }, 1000);
//
//
//                // Prepare the View for the animation
//                additions.setVisibility(View.VISIBLE);
//                additions.setAlpha(0.0f);
//
//                // Start the animation
//                additions.animate()
////                        .translationY(restaurant.getHeight())
//                        .translationX(-additions.getWidth())
//                        .alpha(1.0f)
//                        .setListener(null);
//
//                s = additions.getWidth();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        // Start the animation
//                        additions.animate()
////                        .translationY(restaurant.getHeight())
////                                .translationX(-s)
//                                .translationXBy(s)
//                                .alpha(1.0f)
//                                .setListener(null);
//
//                    }
//                }, 1000);

//                Toast.makeText(getActivity(),"Done",Toast.LENGTH_SHORT).show();
            }
        });

        control_montage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                type = 0;

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frame_mabi3at, new FragmentChooseAdd().setType(0));
                transaction.addToBackStack("FragmentChooseAdd");
                transaction.commit();

//                fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new FragmentChooseAdd().setType(0)).addToBackStack("FragmentChooseAdd").commit();
//                // Prepare the View for the animation
//                restaurant.setVisibility(View.VISIBLE);
//                restaurant.setAlpha(0.0f);
//
//                // Start the animation
//                restaurant.animate()
////                        .translationY(restaurant.getHeight())
//                        .translationX(restaurant.getWidth())
//                        .alpha(1.0f)
//                        .setListener(null);
//
//                s = restaurant.getWidth();
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        // Start the animation
//                        restaurant.animate()
////                        .translationY(restaurant.getHeight())
////                                .translationX(-s)
//                                .translationXBy(-s)
//                                .alpha(1.0f)
//                                .setListener(null);
//
//                    }
//                }, 1000);
//
//
//                // Prepare the View for the animation
//                market.setVisibility(View.VISIBLE);
//                market.setAlpha(0.0f);
//
//                // Start the animation
//                market.animate()
////                        .translationY(restaurant.getHeight())
//                        .translationX(-market.getWidth())
//                        .alpha(1.0f)
//                        .setListener(null);
//
//                s = market.getWidth();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        // Start the animation
//                        market.animate()
////                        .translationY(restaurant.getHeight())
////                                .translationX(-s)
//                                .translationXBy(s)
//                                .alpha(1.0f)
//                                .setListener(null);
//
//                    }
//                }, 1000);
//
//
//                // Prepare the View for the animation
//                other.setVisibility(View.VISIBLE);
//                other.setAlpha(0.0f);
//
//                // Start the animation
//                other.animate()
////                        .translationY(restaurant.getHeight())
//                        .translationX(other.getWidth())
//                        .alpha(1.0f)
//                        .setListener(null);
//
//                s = other.getWidth();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        // Start the animation
//                        other.animate()
////                        .translationY(restaurant.getHeight())
////                                .translationX(-s)
//                                .translationXBy(-s)
//                                .alpha(1.0f)
//                                .setListener(null);
//
//
//                        // Prepare the View for the animation
//                        base_food.setVisibility(View.VISIBLE);
//                        base_food.setAlpha(0.0f);
//
//                        // Start the animation
//                        base_food.animate()
//                                .rotationX(360)
//                                .alpha(1.0f)
//                                .setListener(null);
//
//
//                    }
//                }, 1000);
//
//
//                // Prepare the View for the animation
//                additions.setVisibility(View.VISIBLE);
//                additions.setAlpha(0.0f);
//
//                // Start the animation
//                additions.animate()
////                        .translationY(restaurant.getHeight())
//                        .translationX(-additions.getWidth())
//                        .alpha(1.0f)
//                        .setListener(null);
//
//                s = additions.getWidth();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        // Start the animation
//                        additions.animate()
////                        .translationY(restaurant.getHeight())
////                                .translationX(-s)
//                                .translationXBy(s)
//                                .alpha(1.0f)
//                                .setListener(null);
//
//                    }
//                }, 1000);

//                Toast.makeText(getActivity(),"Done",Toast.LENGTH_SHORT).show();
            }
        });

        add_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frame_mabi3at, new FragmentUnit());
                transaction.addToBackStack("FragmentUnit");
                transaction.commit();
            }
        });

        add_dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frame_mabi3at, new FragmentDepartment());
                transaction.addToBackStack("FragmentDepartment");
                transaction.commit();
            }
        });

        control_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frame_mabi3at, new FragmentControlUnitAndDepartment().setType("5"));
                transaction.addToBackStack("FragmentControlUnit");
                transaction.commit();
            }
        });

        control_dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frame_mabi3at, new FragmentControlUnitAndDepartment().setType("6"));
                transaction.addToBackStack("FragmentControlDepartment");
                transaction.commit();
            }
        });


    }
}
