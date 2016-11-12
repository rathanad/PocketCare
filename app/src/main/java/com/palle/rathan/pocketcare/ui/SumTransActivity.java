package com.palle.rathan.pocketcare.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.palle.rathan.pocketcare.R;
import com.palle.rathan.pocketcare.data.MyDatabase;
import com.palle.rathan.pocketcare.ui.login.LoginActivity;

public class SumTransActivity extends AppCompatActivity implements AddCategoryFragment.NewCategoryListener{

private ClickedClearData listener;
    MyDatabase database;
    SectionPagerAdapter mAdapter;
    ViewPager mPager;

    @Override

    public boolean categoryAddedToDatabase(String categoryName) {
        if(database.checkIfCategoryExist(categoryName)){
            return false;
        } else {
            long result = database.insertCategory(categoryName);
            if(result != -1){

                Fragment f = mAdapter.getItem(mPager.getCurrentItem());

                try {
                    TransactionFragment transactionFragment = (TransactionFragment) f;
                    transactionFragment.updateCategorySpinner(categoryName, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public interface ClickedClearData {
        public void clearData();
    }    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sumtrans);
        initializeScreen();

        database = new MyDatabase(this);



       /* FragmentManager fragmentManager= getSupportFragmentManager();
        SummaryFragment summaryFragment=new SummaryFragment();

        Intent intent=getIntent();
        String summaryDeatils;
        Bundle bundle;
        if(intent != null){
            summaryDeatils= intent.getExtras().getString("summary");
            bundle= new Bundle();
            bundle.putString("summary",summaryDeatils);
            summaryFragment.setArguments(bundle);
        }

        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container,summaryFragment).commit();*/
    }

    private void initializeScreen() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mPager = (ViewPager) findViewById(R.id.pager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pocket Care");

        mAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mPager.setOffscreenPageLimit(3);
        mPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mPager);
    }

    private class SectionPagerAdapter extends FragmentStatePagerAdapter {
        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new SummaryFragment();
                case 1:
                    return new TransactionFragment();
                default:
                    return new SummaryFragment();
            }
        }



        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Summary";
                case 1:
                    return "Transactions";
                default:
                    return "Summary";
            }
        }

        @Override
        public int getCount() {
            return 2;
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sumtrans, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_clear_data:
                /*TransactionFragment fragment = (TransactionFragment) getFragmentManager().findFragmentByTag(R.);
                fragment.get();*/
                TransactionFragment fragment = new TransactionFragment();
                               /* TransactionFragment transactionFragment = new TransactionFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                //listener.clearData();
                transactionFragment.getClearData();*/

                /*transactionFragment.getFragmentManager()
                transaction.replace(R.id.login_container, registerFragment, "RegisterFragment");
                transaction.addToBackStack("");
                transaction.commit()*/

                break;
            case R.id.action_Logout:
                Intent intent1 = new Intent(SumTransActivity.this, LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.action_quit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


