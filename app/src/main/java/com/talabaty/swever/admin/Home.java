package com.talabaty.swever.admin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.talabaty.swever.admin.Communication.CommunicationHome;
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.Mabi3at.MainHome;
import com.talabaty.swever.admin.Managment.Employees.AddEmployee.PersonalInfo;
import com.talabaty.swever.admin.Managment.Employees.Employee;
import com.talabaty.swever.admin.Managment.ManagmentHome;
import com.talabaty.swever.admin.Managment.Privilages.PrivilagesHome;
import com.talabaty.swever.admin.Montagat.AddMontag.AddMontag;
import com.talabaty.swever.admin.Montagat.AddReturanteMontage.AddReturanteMontage;
import com.talabaty.swever.admin.Montagat.Additions.FragmentAdditions;
import com.talabaty.swever.admin.Montagat.ControlMontag.ControlMontagModel;
import com.talabaty.swever.admin.Montagat.FragmentMontag;
import com.talabaty.swever.admin.Montagat.Market.AddMarketMontage;
import com.talabaty.swever.admin.Montagat.NewFood.FragmentAddNewFood;
import com.talabaty.swever.admin.Offers.Fragment_Offer_home;
import com.talabaty.swever.admin.Offers.Fragment_offers;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    NavigationView navigationView;
    //    Fragment fragment;
    FragmentManager fragmentManager;
    Intent intent;
    CircleImageView imageView;
    TextView user_name;
    LoginDatabae loginDatabae;
    SystemDatabase systemDatabase;
    Cursor cursor, sysCursor;
    List<SystemPermission> permissions;

    Mabi3atDatabase systemDatabaseddd;
    Cursor cursordd;
    boolean trend_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent = getIntent();

        loginDatabae = new LoginDatabae(this);
        cursor = loginDatabae.ShowData();


        systemDatabaseddd = new Mabi3atDatabase(this);
        cursordd = systemDatabaseddd.ShowData();
        while (cursordd.moveToNext()) {
            if (cursordd.getString(12).equals("9")) {
                trend_view = Boolean.valueOf(cursordd.getString(1));
            }
        }

        systemDatabase = new SystemDatabase(this);
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

        while (sysCursor.moveToNext()) {
            Log.e("0", sysCursor.getString(0));
            Log.e("1", sysCursor.getString(1));
            Log.e("2", sysCursor.getString(2));
            Log.e("3", sysCursor.getString(3));
            Log.e("4", sysCursor.getString(4));
        }

        if (permissions.size()==0){
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_warning,
                    (ViewGroup) findViewById(R.id.lay));

            TextView text = (TextView) layout.findViewById(R.id.txt);

            text.setText(" لا يتم تحديث الصلاحيات");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
            Intent intent = new Intent(Home.this, Temp.class);
            startActivity(intent);
        }

//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate(R.layout.toast_info,
//                (ViewGroup) findViewById(R.id.lay));
//
//        TextView text = (TextView) layout.findViewById(R.id.txt);
//
//        text.setText(" تم تحديث الصلاحيات");
//
//        Toast toast = new Toast(getApplicationContext());
//        toast.setGravity(Gravity.BOTTOM, 0, 0);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(layout);
//        toast.show();
        fragmentManager = getSupportFragmentManager();
//        fragment = new MainHome();
//        fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new MainHome()).commit();
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        if (intent.getStringExtra("fragment").equals("montag")) {
            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new FragmentMontag()).commit();
        } else if (intent.getStringExtra("fragment").equals("mabi3at")) {
            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new MainHome()).commit();
        } else if (intent.getStringExtra("fragment").equals("offer")) {
            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new Fragment_offers()).commit();
        } else if (intent.getStringExtra("fragment").equals("edit_control0")){
            ControlMontagModel model = new ControlMontagModel();
            model = (ControlMontagModel) intent.getSerializableExtra("Class");
            Gson gson = new Gson();
            Log.e("Model",gson.toJson(model));
            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new AddMontag().setData(model)).commit();
        } else if (intent.getStringExtra("fragment").equals("edit_control1")){
            ControlMontagModel model = new ControlMontagModel();
            model = (ControlMontagModel) intent.getSerializableExtra("Class");
            Gson gson = new Gson();
            Log.e("Model",gson.toJson(model));
            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new AddMarketMontage().setData(model)).commit();
        } else if (intent.getStringExtra("fragment").equals("edit_control2")){
            ControlMontagModel model = new ControlMontagModel();
            model = (ControlMontagModel) intent.getSerializableExtra("Class");
            Gson gson = new Gson();
            Log.e("Model",gson.toJson(model));
            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new AddReturanteMontage().setData(model)).commit();
        } else if (intent.getStringExtra("fragment").equals("edit_control3")){
            ControlMontagModel model = new ControlMontagModel();
            model = (ControlMontagModel) intent.getSerializableExtra("Class");
            Gson gson = new Gson();
            Log.e("Model",gson.toJson(model));
            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new FragmentAddNewFood().setData(model)).commit();
        } else if (intent.getStringExtra("fragment").equals("edit_control4")){
            ControlMontagModel model = new ControlMontagModel();
            model = (ControlMontagModel) intent.getSerializableExtra("Class");
            Gson gson = new Gson();
            Log.e("Model",gson.toJson(model));
            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new FragmentAdditions().setData(model)).commit();
        } else if (intent.getStringExtra("fragment").equals("contact")){
            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new CommunicationHome()).commit();
        } else if (intent.getStringExtra("fragment").equals("management")){
            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new ManagmentHome()).commit();
        } else if (intent.getStringExtra("fragment").equals("save")){
            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new PrivilagesHome()).addToBackStack(null).commit();
        } else if (intent.getStringExtra("fragment").equals("emp_add")){
            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new PersonalInfo()).commit();
        } else if (intent.getStringExtra("fragment").equals("edit_emp")){
            Employee employee = new Employee();
            employee = (Employee) intent.getSerializableExtra("Class");
            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new PersonalInfo().setData(employee)).commit();
        }
//        getSupportActionBar().setTitle("المبيعات");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        Menu m = navigationView.getMenu();
//        for (int i=0;i<m.size();i++) {
//            MenuItem mi = m.getItem(i);
//
//            //for aapplying a font to subMenu ...
//            SubMenu subMenu = mi.getSubMenu();
//            if (subMenu!=null && subMenu.size() >0 ) {
//                for (int j=0; j <subMenu.size();j++) {
//                    MenuItem subMenuItem = subMenu.getItem(j);
//                    applyFontToMenuItem(subMenuItem);
//                }
//            }
//
//            //the method we have create in activity
//            applyFontToMenuItem(mi);
//        }
        Menu nav_Menu = navigationView.getMenu();

        nav_Menu.findItem(R.id.nav_contact).setVisible(false);

        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);
        imageView = view.findViewById(R.id.imageView);
        user_name = view.findViewById(R.id.user_name);
        while (cursor.moveToNext()) {
            user_name.setText(cursor.getString(1));
            if (!cursor.getString(5).isEmpty()) {
                Picasso.with(this)
                        .load(cursor.getString(5))
                        .into(imageView);
            }
        }
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "font/changamedium.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int count = getFragmentManager().getBackStackEntryCount();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (count == 0) {
                super.onBackPressed();
                //additional code
            } else {
                getFragmentManager().popBackStack();
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Menu nav_Menu = navigationView.getMenu();


        if (id == R.id.nav_offer) {
            nav_Menu.findItem(R.id.nav_mabe3at).setIcon(R.drawable.ic_shopping_basket_on_24dp);
            nav_Menu.findItem(R.id.nav_montagat).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_trendmontag).setIcon(R.drawable.ic_trending_up_off_24dp);
            nav_Menu.findItem(R.id.nav_customer).setIcon(R.drawable.ic_people_off_24dp);
            nav_Menu.findItem(R.id.nav_contact).setIcon(R.drawable.ic_message_off_24dp);
            nav_Menu.findItem(R.id.nav_management).setIcon(R.drawable.ic_assistant_photo_off_24dp);

//            fragment = new MainHome();
            if (permissions.get(1).isView()) {
                fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new Fragment_Offer_home()).addToBackStack("Fragment_Offer_home").commit();
                getSupportActionBar().setTitle("العروض");
            } else {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_warning,
                        (ViewGroup) findViewById(R.id.lay));

                TextView text = (TextView) layout.findViewById(R.id.txt);

                text.setText(" لا توجد صلاحيه للدخول");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }

//            startActivity(new Intent(Home.this, Mabi3atNavigator.class));
        }else if (id == R.id.nav_mabe3at) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            nav_Menu.findItem(R.id.nav_mabe3at).setIcon(R.drawable.ic_shopping_basket_on_24dp);
            nav_Menu.findItem(R.id.nav_montagat).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_trendmontag).setIcon(R.drawable.ic_trending_up_off_24dp);
            nav_Menu.findItem(R.id.nav_customer).setIcon(R.drawable.ic_people_off_24dp);
            nav_Menu.findItem(R.id.nav_contact).setIcon(R.drawable.ic_message_off_24dp);
            nav_Menu.findItem(R.id.nav_management).setIcon(R.drawable.ic_assistant_photo_off_24dp);

//            fragment = new MainHome();
            if (permissions.get(0).isView()) {
                fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new MainHome()).addToBackStack("MainHome").commit();
                getSupportActionBar().setTitle("المبيعات");
            } else {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_warning,
                        (ViewGroup) findViewById(R.id.lay));

                TextView text = (TextView) layout.findViewById(R.id.txt);

                text.setText(" لا توجد صلاحيه للدخول");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
//            startActivity(new Intent(Home.this, Mabi3atNavigator.class));
        } else if (id == R.id.nav_montagat) {
            nav_Menu.findItem(R.id.nav_mabe3at).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_montagat).setIcon(R.drawable.ic_shopping_basket_on_24dp);
            nav_Menu.findItem(R.id.nav_trendmontag).setIcon(R.drawable.ic_trending_up_off_24dp);
            nav_Menu.findItem(R.id.nav_customer).setIcon(R.drawable.ic_people_off_24dp);
            nav_Menu.findItem(R.id.nav_contact).setIcon(R.drawable.ic_message_off_24dp);
            nav_Menu.findItem(R.id.nav_management).setIcon(R.drawable.ic_assistant_photo_off_24dp);
//            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            if (permissions.get(1).isView()) {
                fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new FragmentMontag()).addToBackStack("FragmentMontag").commit();
                getSupportActionBar().setTitle("المنتجات");
            } else {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_warning,
                        (ViewGroup) findViewById(R.id.lay));

                TextView text = (TextView) layout.findViewById(R.id.txt);

                text.setText(" لا توجد صلاحيه للدخول");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        } else if (id == R.id.nav_trendmontag) {

            nav_Menu.findItem(R.id.nav_mabe3at).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_montagat).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_trendmontag).setIcon(R.drawable.ic_trending_up_on_24dp);
            nav_Menu.findItem(R.id.nav_customer).setIcon(R.drawable.ic_people_off_24dp);
            nav_Menu.findItem(R.id.nav_contact).setIcon(R.drawable.ic_message_off_24dp);
            nav_Menu.findItem(R.id.nav_management).setIcon(R.drawable.ic_assistant_photo_off_24dp);
//            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new Fragment_offers_Restaurant()).addToBackStack("Fragment_offers_Restaurant").commit();
//            getSupportActionBar().setTitle("المنتجات الأكثر بيعا");
            if (trend_view) {
                Intent intent = new Intent(this, Mabi3atNavigator.class);
                intent.putExtra("fragment", "trend");
                startActivity(intent);
            } else {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_warning,
                        (ViewGroup) findViewById(R.id.lay));

                TextView text = (TextView) layout.findViewById(R.id.txt);

                text.setText(" لا توجد صلاحيه للدخول");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        } else if (id == R.id.nav_customer) {

            nav_Menu.findItem(R.id.nav_mabe3at).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_montagat).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_trendmontag).setIcon(R.drawable.ic_trending_up_off_24dp);
            nav_Menu.findItem(R.id.nav_customer).setIcon(R.drawable.ic_people_on_24dp);
            nav_Menu.findItem(R.id.nav_contact).setIcon(R.drawable.ic_message_off_24dp);
            nav_Menu.findItem(R.id.nav_management).setIcon(R.drawable.ic_assistant_photo_off_24dp);
//            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new Fragment_offers_Restaurant()).addToBackStack("Fragment_offers_Restaurant").commit();
//            getSupportActionBar().setTitle("العملاء");
            if (permissions.get(2).isView()) {
                Intent intent = new Intent(this, Mabi3atNavigator.class);
                intent.putExtra("fragment", "report");
                startActivity(intent);
            } else {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_warning,
                        (ViewGroup) findViewById(R.id.lay));

                TextView text = (TextView) layout.findViewById(R.id.txt);

                text.setText(" لا توجد صلاحيه للدخول");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        } else if (id == R.id.nav_contact) {

            nav_Menu.findItem(R.id.nav_mabe3at).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_montagat).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_trendmontag).setIcon(R.drawable.ic_trending_up_off_24dp);
            nav_Menu.findItem(R.id.nav_customer).setIcon(R.drawable.ic_people_off_24dp);
            nav_Menu.findItem(R.id.nav_contact).setIcon(R.drawable.ic_message_on_24dp);
            nav_Menu.findItem(R.id.nav_management).setIcon(R.drawable.ic_assistant_photo_off_24dp);
            if (permissions.get(3).isView()) {
                fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new CommunicationHome()).addToBackStack("CommunicationHome").commit();
                getSupportActionBar().setTitle("التواصل");
            } else {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_warning,
                        (ViewGroup) findViewById(R.id.lay));

                TextView text = (TextView) layout.findViewById(R.id.txt);

                text.setText(" لا توجد صلاحيه للدخول");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        } else if (id == R.id.nav_management) {

            nav_Menu.findItem(R.id.nav_mabe3at).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_montagat).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_trendmontag).setIcon(R.drawable.ic_trending_up_off_24dp);
            nav_Menu.findItem(R.id.nav_customer).setIcon(R.drawable.ic_people_off_24dp);
            nav_Menu.findItem(R.id.nav_contact).setIcon(R.drawable.ic_message_off_24dp);
            nav_Menu.findItem(R.id.nav_management).setIcon(R.drawable.ic_assistant_photo_on_24dp);
            if (permissions.get(4).isView()) {
                getSupportActionBar().setTitle("إداره الموظفين");
                fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new ManagmentHome()).addToBackStack("ManagmentHome").commit();
            } else {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_warning,
                        (ViewGroup) findViewById(R.id.lay));

                TextView text = (TextView) layout.findViewById(R.id.txt);

                text.setText(" لا توجد صلاحيه للدخول");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        } else if (id == R.id.nav_out) {
            loginDatabae.UpdateData("1", "c", "c", "c", "0", "", "", "");
            this.finish();
            startActivity(new Intent(Home.this,Login.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
