package com.idev.idevchat.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.idev.idevchat.Fragment.InputSoal;
import com.idev.idevchat.Fragment.LihatSoal;
import com.idev.idevchat.R;

import java.util.HashMap;

public class HalamanAdmin extends AppCompatActivity {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2;
    private HashMap<Integer, Fragment> refFragmentMap = new HashMap<>();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_admin);

        mAuth = FirebaseAuth.getInstance();

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        tabLayout.post(() -> {
            tabLayout.setupWithViewPager(viewPager);
            if (viewPager.getAdapter().getCount() == 1) {
                viewPager.getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            logoutfromChat();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, HalamanLogin.class));
            finish();
        }
    }

    private void logoutfromChat() {
        mAuth.signOut();
        startActivity(new Intent(HalamanAdmin.this, HalamanLogin.class));
        finish();
    }

    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Fragment baru = new InputSoal();
                    refFragmentMap.put(position, baru);
                    return baru;
                case 1:
                    Fragment lama = new LihatSoal();
                    refFragmentMap.put(position, lama);
                    return lama;

            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Input Soal";
                case 1:
                    return "Lihat Soal";
            }
            return null;
        }

        public void destroyItem(View container, int position, Object object) {
            super.destroyItem(container, position, object);
            refFragmentMap.remove(position);
        }

        public Fragment getFragment(int key) {
            return refFragmentMap.get(key);
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
