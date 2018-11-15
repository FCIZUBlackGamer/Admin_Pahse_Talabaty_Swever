package com.talabaty.swever.admin.Mabi3at;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.talabaty.swever.admin.AgentReports.Fragment_agent_report;
import com.talabaty.swever.admin.Home;
import com.talabaty.swever.admin.Login;
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.Mabi3at.Delevry.DelevryHome;
import com.talabaty.swever.admin.Mabi3at.DoneTalabat.DoneTalabat;
import com.talabaty.swever.admin.Mabi3at.NewTalabat.NewTalabatFragment;
import com.talabaty.swever.admin.Mabi3at.NotificationToFriendTalabat.NotificationToFriendTalabat;
import com.talabaty.swever.admin.Mabi3at.PendedTalabat.PendedTalabatFragment;
import com.talabaty.swever.admin.Mabi3at.ReadyTalabat.ReadyTalabatFragment;
import com.talabaty.swever.admin.Mabi3at.RejectedReports.RejectedReports;
import com.talabaty.swever.admin.Mabi3at.ReturnedTalabat.ReturnedTalabatFragment;
import com.talabaty.swever.admin.Mabi3at.SailedReports.SailedReports;
import com.talabaty.swever.admin.Mabi3at.Tasks.MyTasksFragment;
import com.talabaty.swever.admin.Mabi3atDatabase;
import com.talabaty.swever.admin.Mabi3atTrend.Mabi3atTrendReports;
import com.talabaty.swever.admin.Managment.Employees.ControlEmployee.FragmentControlEmployee;
import com.talabaty.swever.admin.Managment.Privilages.AddPrivilege.FragmentAddPrivilege;
import com.talabaty.swever.admin.Managment.Privilages.ControlPrivilege.FragmentControlPrivilege;
import com.talabaty.swever.admin.Montagat.ControlBaseFood_Additions.FragmentControlBaseFood_Additions;
import com.talabaty.swever.admin.Montagat.ControlMontag.ControlMontag;
import com.talabaty.swever.admin.Offers.Fragment_offers;
import com.talabaty.swever.admin.Offers.Market.Fragment_offers_Market;
import com.talabaty.swever.admin.Offers.Restaurant.Fragment_offers_Restaurant;
import com.talabaty.swever.admin.Offers.TotalOffer;
import com.talabaty.swever.admin.R;
import com.talabaty.swever.admin.SystemDatabase;
import com.talabaty.swever.admin.SystemPermission;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Mabi3atNavigator extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    //    Fragment fragment;
    FragmentManager fragmentManager;
    Intent intent;

    CircleImageView imageView;
    TextView user_name;
    LoginDatabae loginDatabae;
    Cursor cursor;
    SystemDatabase systemDatabase;
    Cursor sysCursor;
    List<SystemPermission> permissions;

    Mabi3atDatabase systemDatabaseddd;
    Cursor cursordd;
    boolean trend_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_talabat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginDatabae = new LoginDatabae(this);
        cursor = loginDatabae.ShowData();

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

        systemDatabaseddd = new Mabi3atDatabase(this);
        cursordd = systemDatabaseddd.ShowData();
        while (cursordd.moveToNext()){
            if (cursordd.getString(12).equals("9")){
                trend_view = Boolean.valueOf(cursordd.getString(1));
            }
        }

        //Get Fragment Name
        intent = getIntent();

        fragmentManager = getSupportFragmentManager();
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        if (intent.getStringExtra("fragment").equals("new")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new NewTalabatFragment()).commit();
        } else if (intent.getStringExtra("fragment").equals("ready")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new ReadyTalabatFragment()).commit();
        } else if (intent.getStringExtra("fragment").equals("pend")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new PendedTalabatFragment()).commit();
        } else if (intent.getStringExtra("fragment").equals("returned")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new ReturnedTalabatFragment()).commit();
        } else if (intent.getStringExtra("fragment").equals("notification")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new NotificationToFriendTalabat()).commit();
        } else if (intent.getStringExtra("fragment").equals("done")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new DoneTalabat()).commit();
        } else if (intent.getStringExtra("fragment").equals("rejected")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new RejectedReports()).commit();
        } else if (intent.getStringExtra("fragment").equals("sailed")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new SailedReports()).commit();
        } else if (intent.getStringExtra("fragment").equals("control")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new ControlMontag().setType("0")).commit();
        } else if (intent.getStringExtra("fragment").equals("control1")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new ControlMontag().setType("1")).commit();
        } else if (intent.getStringExtra("fragment").equals("control2")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new ControlMontag().setType("2")).commit();
        } else if (intent.getStringExtra("fragment").equals("control3")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new FragmentControlBaseFood_Additions().setType("3")).commit();
        } else if (intent.getStringExtra("fragment").equals("control4")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new FragmentControlBaseFood_Additions().setType("4")).commit();
        } else if (intent.getStringExtra("fragment").equals("trend")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new Mabi3atTrendReports()).commit();
        } else if (intent.getStringExtra("fragment").equals("report")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new Fragment_agent_report()).commit();
        } else if (intent.getStringExtra("fragment").equals("priv_add")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new FragmentAddPrivilege()).commit();
        } else if (intent.getStringExtra("fragment").equals("priv_control")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new FragmentControlPrivilege()).commit();
        } else if (intent.getStringExtra("fragment").equals("edit_priv")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new FragmentAddPrivilege()).commit();
        } else if (intent.getStringExtra("fragment").equals("emp_control")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new FragmentControlEmployee()).commit();
        } else if (intent.getStringExtra("fragment").equals("my_tasks")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new MyTasksFragment()).commit();
        } else if (intent.getStringExtra("fragment").equals("delivery")) {
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new DelevryHome()).commit();
        } else if (intent.getStringExtra("fragment").equals("control_offer_other")) {
                                                /** Add For other */
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new Fragment_offers()).commit();
        } else if (intent.getStringExtra("fragment").equals("control_offer_rest")) {
                                                /** Add For restaurant */
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new Fragment_offers_Restaurant()).commit();
        } else if (intent.getStringExtra("fragment").equals("control_offer_market")) {
                                                /** Add For market */
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new Fragment_offers_Market()).commit();
        } else if (intent.getStringExtra("fragment").equals("edit_other")) {
            TotalOffer offer = (TotalOffer) intent.getSerializableExtra("Model");
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new Fragment_offers().setData(offer)).commit();
        } else if (intent.getStringExtra("fragment").equals("edit_restaurant")) {//Todo: Change Class Name
            TotalOffer offer = (TotalOffer) intent.getSerializableExtra("Model");
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new Fragment_offers_Restaurant().setData(offer)).commit();
        } else if (intent.getStringExtra("fragment").equals("edit_market")) {//Todo: Change Class Name
            TotalOffer offer = (TotalOffer) intent.getSerializableExtra("Model");
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new Fragment_offers_Market().setData(offer)).commit();
        } else if (intent.getStringExtra("fragment").equals("control_offer_rest")) {//Todo: Change Class Name
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new Fragment_offers()).commit();
        } else if (intent.getStringExtra("fragment").equals("control_offer_market")) {//Todo: Change Class Name
            fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new Fragment_offers()).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu nav_Menu = navigationView.getMenu();

        nav_Menu.findItem(R.id.nav_contact).setVisible(false);

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            Log.e("Here","Close");
        } else {
            Log.e("Here","Back");
            super.onBackPressed();
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
                Intent intent = new Intent(Mabi3atNavigator.this, Home.class);
                intent.putExtra("fragment", "offer");
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
//            startActivity(new Intent(Home.this, Mabi3atNavigator.class));
        }else if (id == R.id.nav_mabe3at) {
//            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            nav_Menu.findItem(R.id.nav_mabe3at).setIcon(R.drawable.ic_shopping_basket_on_24dp);
            nav_Menu.findItem(R.id.nav_montagat).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_trendmontag).setIcon(R.drawable.ic_trending_up_off_24dp);
            nav_Menu.findItem(R.id.nav_customer).setIcon(R.drawable.ic_people_off_24dp);
            nav_Menu.findItem(R.id.nav_contact).setIcon(R.drawable.ic_message_off_24dp);
            nav_Menu.findItem(R.id.nav_management).setIcon(R.drawable.ic_assistant_photo_off_24dp);

//            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at,new MainHome()).addToBackStack("MainHome").commit();
//            getSupportActionBar().setTitle("المبيعات");
//            getSupportActionBar().setTitle("المبيعات");
            if (permissions.get(0).isView()) {
                Intent intent = new Intent(Mabi3atNavigator.this, Home.class);
                intent.putExtra("fragment", "mabi3at");
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
        } else if (id == R.id.nav_montagat) {

            nav_Menu.findItem(R.id.nav_mabe3at).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_montagat).setIcon(R.drawable.ic_shopping_basket_on_24dp);
            nav_Menu.findItem(R.id.nav_trendmontag).setIcon(R.drawable.ic_trending_up_off_24dp);
            nav_Menu.findItem(R.id.nav_customer).setIcon(R.drawable.ic_people_off_24dp);
            nav_Menu.findItem(R.id.nav_contact).setIcon(R.drawable.ic_message_off_24dp);
            nav_Menu.findItem(R.id.nav_management).setIcon(R.drawable.ic_assistant_photo_off_24dp);
//            getSupportActionBar().setTitle("المنتجات");
//            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at,new FragmentMontag()).addToBackStack("FragmentMontag").commit();
//            getSupportActionBar().setTitle("المنتجات");
            if (permissions.get(1).isView()) {
                Intent intent = new Intent(Mabi3atNavigator.this, Home.class);
                intent.putExtra("fragment", "montag");
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
        } else if (id == R.id.nav_trendmontag) {
// read mabi3at permission
            nav_Menu.findItem(R.id.nav_mabe3at).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_montagat).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_trendmontag).setIcon(R.drawable.ic_trending_up_on_24dp);
            nav_Menu.findItem(R.id.nav_customer).setIcon(R.drawable.ic_people_off_24dp);
            nav_Menu.findItem(R.id.nav_contact).setIcon(R.drawable.ic_message_off_24dp);
            nav_Menu.findItem(R.id.nav_management).setIcon(R.drawable.ic_assistant_photo_off_24dp);
//            getSupportActionBar().setTitle("المنتجات الأكثر بيعا");
//            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            if (trend_view) {
                fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new Mabi3atTrendReports()).addToBackStack("Mabi3atTrendReports").commit();
                getSupportActionBar().setTitle("المنتجات الأكثر بيعا");
            }else {
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
//            getSupportActionBar().setTitle("العملاء");
//            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            if (permissions.get(2).isView()) {
                fragmentManager.beginTransaction().replace(R.id.new_talabat_frame, new Fragment_agent_report()).commit();
                getSupportActionBar().setTitle("العملاء");
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
//            getSupportActionBar().setTitle("التواصل");
            if (permissions.get(3).isView()) {
                Intent intent = new Intent(Mabi3atNavigator.this, Home.class);
                intent.putExtra("fragment", "contact");
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
        } else if (id == R.id.nav_management) {

            nav_Menu.findItem(R.id.nav_mabe3at).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_montagat).setIcon(R.drawable.ic_shopping_basket_off_24dp);
            nav_Menu.findItem(R.id.nav_trendmontag).setIcon(R.drawable.ic_trending_up_off_24dp);
            nav_Menu.findItem(R.id.nav_customer).setIcon(R.drawable.ic_people_off_24dp);
            nav_Menu.findItem(R.id.nav_contact).setIcon(R.drawable.ic_message_off_24dp);
            nav_Menu.findItem(R.id.nav_management).setIcon(R.drawable.ic_assistant_photo_on_24dp);
//            getSupportActionBar().setTitle("إداره الموظفين");
            if (permissions.get(4).isView()) {
                Intent intent = new Intent(Mabi3atNavigator.this, Home.class);
                intent.putExtra("fragment", "management");
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
        } else if (id == R.id.nav_out) {
            loginDatabae.UpdateData("1", "c", "c", "c", "0", "", "", "");
            this.finish();
            startActivity(new Intent(Mabi3atNavigator.this,Login.class));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
