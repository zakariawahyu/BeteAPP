package com.example.bete.kategori;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bete.About;
import com.example.bete.profile.SessionManager;
import com.example.bete.app.RequestHandler;
import com.example.bete.berita.AdapterBerita;
import com.example.bete.Main2Activity;
import com.example.bete.R;
import com.example.bete.network.ApiServices;
import com.example.bete.network.InitRetrofit;
import com.example.bete.response.BeritaItem;
import com.example.bete.response.ResponseBerita;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppsActivity extends AppCompatActivity {
    private String JSON_STRING,idku;
    private CircleImageView imageku;
    ProgressDialog mProgressDialog;
    private TextView tv_header;
    public Context context;
    SessionManager sessionManager;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private RecyclerView recyclerView;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        mDrawerLayout = findViewById(R.id.drawerapps);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.mainapps);
        setupDrawerContent(navigationView);

        recyclerView = findViewById(R.id.RcApps);
        // RecyclerView harus pakai Layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Eksekusi method
        tampilBerita();

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
                //URL Foto dan langsung di eksekusi
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
            new AlertDialog.Builder(AppsActivity.this)
                    .setMessage(e.getMessage())
                    .show();
        }catch (NullPointerException e){
            new AlertDialog.Builder(AppsActivity.this)
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
                        AppsActivity.this,
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
//            // Create a progressdialog
//            mProgressDialog = new ProgressDialog(AppsActivity.this);
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
                Intent home = new Intent(AppsActivity.this, Main2Activity.class);
                startActivity(home);
                finish();
                break;

            case R.id.tecs_news:
                Intent tech = new Intent(AppsActivity.this, TechNewsActivity.class);
                startActivity(tech);
                finish();
                break;

            case R.id.tipstrik:
                Intent tipstrik = new Intent(AppsActivity.this, TipsTrikActivity.class);
                startActivity(tipstrik);
                finish();
                break;

            case R.id.apps:
                Toast.makeText(this,"Anda sudah berada di Apps",Toast.LENGTH_SHORT).show();
                break;

            case R.id.game:
                Intent game = new Intent(AppsActivity.this, GameActivity.class);
                startActivity(game);
                finish();
                break;

            case R.id.gadget:
                Intent gadget = new Intent(AppsActivity.this, GadgetActivity.class);
                startActivity(gadget);
                finish();
                break;

            case R.id.robotik:
                Intent robotik = new Intent(AppsActivity.this, RobotikActivity.class);
                startActivity(robotik);
                finish();
                break;

            case R.id.internet:
                Intent internet = new Intent(AppsActivity.this, InternetActivity.class);
                startActivity(internet);
                finish();
                break;

            case R.id.about:
                Intent about = new Intent(AppsActivity.this, About.class);
                startActivity(about);
                break;

        }
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();
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

    private void tampilBerita() {
        ApiServices api = InitRetrofit.getInstance();
        // Siapkan request
        Call<ResponseBerita> beritaCall = api.request_show_apps();
        // Kirim request
        beritaCall.enqueue(new Callback<ResponseBerita>() {
            @Override
            public void onResponse(Call<ResponseBerita> call, Response<ResponseBerita> response) {
                // Pasikan response Sukses
                if (response.isSuccessful()){
                    Log.d("response api", response.body().toString());
                    // tampung data response body ke variable
                    List<BeritaItem> data_berita = response.body().getBerita();
                    boolean status = response.body().isStatus();
                    // Kalau response status nya = true
                    if (status){
                        // Buat Adapter untuk recycler view
                        AdapterBerita adapter = new AdapterBerita(AppsActivity.this, data_berita);
                        recyclerView.setAdapter(adapter);
                    } else {
                        // kalau tidak true
                        Toast.makeText(AppsActivity.this, "Tidak ada berita untuk saat ini", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBerita> call, Throwable t) {
                // print ke log jika Error
                t.printStackTrace();

            }
        });
    }
}
