package com.example.bete.forum;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bete.R;
import com.example.bete.bookmark.DetailBookmark;
import com.example.bete.profile.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class DetailForum extends AppCompatActivity {

    private static final String TAG = DetailBookmark.class.getSimpleName();
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";




    SessionManager sessionManager;
    private String idku;
    private String JSON_STRING;
    Bitmap bitmap;
    boolean check = true;

    ImageView ivGambarBerita;
    TextView tvTglTerbit, tvPenulis;
    WebView wvKontenBerita;

    Boolean status= false;
    SharedPreferences sharedpreferences;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String LOGIN = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_forum);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        sharedpreferences = getSharedPreferences("LOGIN", PRIVATE_MODE);
        status = sharedpreferences.getBoolean(LOGIN, false);

//        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
//        FloatingActionButton.Behavior behavior = (FloatingActionButton.Behavior) lp.getBehavior();
//
//        if (status){
//            if (behavior != null) {
//                behavior.setAutoHideEnabled(true);
//            }
//        }else {
//            if (behavior != null) {
//                behavior.setAutoHideEnabled(false);
//            }
//
//            fab.hide();
//        }
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Snackbar.make(view, "Berita ID : "+ getIntent().getStringExtra("ID_FORUM")+"berhasil ditambahkan ke bookmark", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // Inisialisasi
        ivGambarBerita = (ImageView) findViewById(R.id.ivGambarBerita);
        tvTglTerbit = (TextView) findViewById(R.id.tvTglTerbit);
        tvPenulis = (TextView) findViewById(R.id.tvPenulis);
        wvKontenBerita = (WebView) findViewById(R.id.wvKontenBerita);

        // Jalankan method tampil detail berita
        showDetailBerita();

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        idku = user.get(sessionManager.ID);
    }

    private void showDetailBerita() {
        // Tangkap data dari intent
        String judul_berita = getIntent().getStringExtra("JDL_BERITA");
        String tanggal_berita = getIntent().getStringExtra("TGL_BERITA");
        String penulis_berita = getIntent().getStringExtra("PNS_BERITA");
        String isi_berita = getIntent().getStringExtra("ISI_BERITA");
        String foto_berita = getIntent().getStringExtra("FTO_BERITA");

        // Set judul actionbar / toolbar
        getSupportActionBar().setTitle(judul_berita);

        // Set ke widget
        tvPenulis.setText("Oleh : " + penulis_berita);
        tvTglTerbit.setText(tanggal_berita);
        // Untuk gambar berita
        Picasso.with(this).load(foto_berita).into(ivGambarBerita);
        // Set isi berita sebagai html ke WebView
        wvKontenBerita.getSettings().setJavaScriptEnabled(true);
        wvKontenBerita.loadData(isi_berita, "text/html; charset=utf-8", "UTF-8");
    }
}
