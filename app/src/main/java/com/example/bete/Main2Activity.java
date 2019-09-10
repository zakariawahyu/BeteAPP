package com.example.bete;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bete.app.RequestHandler;
import com.example.bete.kategori.AppsActivity;
import com.example.bete.kategori.GadgetActivity;
import com.example.bete.kategori.GameActivity;
import com.example.bete.kategori.InternetActivity;
import com.example.bete.kategori.RobotikActivity;
import com.example.bete.kategori.TechNewsActivity;
import com.example.bete.kategori.TipsTrikActivity;
import com.example.bete.network.InitRetrofit;
import com.example.bete.profile.SessionManager;
import com.example.bete.tabfragment.BookmarkFragment;
import com.example.bete.tabfragment.ForumFragment;
import com.example.bete.tabfragment.HomeFragment;
import com.example.bete.tabfragment.ProfileFragment;
import com.example.bete.tabfragment.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class Main2Activity extends AppCompatActivity {
    private String JSON_STRING,idku;
    private CircleImageView imageku;
    ProgressDialog mProgressDialog;
    private TextView tv_header;
    public Context context;
    SessionManager sessionManager;

    private NavigationView navigationView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        navigationView = findViewById(R.id.main);
        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Add Fragment
        adapter.AddFragment(new HomeFragment(),"Home");
        adapter.AddFragment(new ForumFragment(),"Forum");
        adapter.AddFragment(new BookmarkFragment(),"Bookmark");
        adapter.AddFragment(new ProfileFragment(),"Profile");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_people_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_bookmark_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_account_circle_black_24dp);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

        setupDrawerContent(navigationView);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        String nama = user.get(sessionManager.NAME);
        idku = user.get(sessionManager.ID);

        View headerView = navigationView.getHeaderView(0);
        tv_header = headerView.findViewById(R.id.tv_headernama);
        imageku = headerView.findViewById(R.id.show_foto);

        getJSON();

    }

    public void showFoto(){

        JSONObject jsonObject;
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(InitRetrofit.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length();i++){

                JSONObject object = result.getJSONObject(i);
                String id = object.getString(InitRetrofit.TAG_ID);
                String name = object.getString(InitRetrofit.TAG_NAMA);
                String username = object.getString(InitRetrofit.TAG_USERNAME);
                String fotoprofile = object.getString(InitRetrofit.TAG_FOTOPROFILE);
                String URL = "https://bete-app.000webhostapp.com/images/"+fotoprofile;
                new DownloadImage().execute(URL);

                tv_header.setText(name);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(InitRetrofit.TAG_ID,id);
                employees.put(InitRetrofit.TAG_NAMA,name);
                employees.put(InitRetrofit.TAG_USERNAME, username);

                list.add(employees);
            }
        } catch (JSONException e) {
            new AlertDialog.Builder(Main2Activity.this)
                    .setMessage(e.getMessage())
                    .show();
        }catch (NullPointerException e){
            new AlertDialog.Builder(Main2Activity.this)
                    .setMessage(e.getMessage())
                    .show();
        }

    }

    private void getJSON(){

        class GetJSON extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        Main2Activity.this,
                        "Mengambil Data",
                        "Mohon tunggu...",
                        false,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showFoto();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequestParam(InitRetrofit.URL_GET_ALL,idku);
            }
        }

        GetJSON js = new GetJSON();
        js.execute();
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
//            mProgressDialog = new ProgressDialog(Main2Activity.this);
//            // Set progressdialog title
//            mProgressDialog.setTitle("Download Image Tutorial");
//            // Set progressdialog message
//            mProgressDialog.setMessage("Loading...");
//            mProgressDialog.setIndeterminate(false);
//            // Show progressdialog
//            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {


            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            imageku.setImageBitmap(result);
            // Close progressdialog
//            mProgressDialog.dismiss();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectItemDrawer(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.home:
                Toast.makeText(this,"Anda sudah berada di Home",Toast.LENGTH_SHORT).show();
                break;

            case R.id.tecs_news:
                Intent tech = new Intent(Main2Activity.this, TechNewsActivity.class);
                startActivity(tech);
                break;

            case R.id.tipstrik:
                Intent tipstrik = new Intent(Main2Activity.this, TipsTrikActivity.class);
                startActivity(tipstrik);
                break;

            case R.id.apps:
                Intent apps = new Intent(Main2Activity.this, AppsActivity.class);
                startActivity(apps);
                break;

            case R.id.game:
                Intent game = new Intent(Main2Activity.this, GameActivity.class);
                startActivity(game);
                break;

            case R.id.gadget:
                Intent gadget = new Intent(Main2Activity.this, GadgetActivity.class);
                startActivity(gadget);
                break;

            case R.id.robotik:
                Intent robotik = new Intent(Main2Activity.this, RobotikActivity.class);
                startActivity(robotik);
                break;

            case R.id.internet:
                Intent internet = new Intent(Main2Activity.this, InternetActivity.class);
                startActivity(internet);
                break;

            case R.id.about:
                Intent about = new Intent(Main2Activity.this, About.class);
                startActivity(about);
                break;

        }
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem itemku) {
                selectItemDrawer(itemku);
                return true;
            }
        });
    }
}
