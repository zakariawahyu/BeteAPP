package com.example.bete.bookmark;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bete.Main2Activity;
import com.example.bete.R;
import com.example.bete.app.AppController;
import com.example.bete.app.RequestHandler;
import com.example.bete.berita.DetailActivity;
import com.example.bete.network.InitRetrofit;
import com.example.bete.profile.EditProfile;
import com.example.bete.profile.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class DetailBookmark extends AppCompatActivity {

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
        setContentView(R.layout.activity_detail_bookmark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        sharedpreferences = getSharedPreferences("LOGIN", PRIVATE_MODE);
        status = sharedpreferences.getBoolean(LOGIN, false);

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        FloatingActionButton.Behavior behavior = (FloatingActionButton.Behavior) lp.getBehavior();

        if (status){
            if (behavior != null) {
                behavior.setAutoHideEnabled(true);
            }
        }else {
            if (behavior != null) {
                behavior.setAutoHideEnabled(false);
            }

            fab.hide();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idx = getIntent().getStringExtra("IdBookmark");
                delete(idx);

//                Snackbar.make(view, "Berita ID : "+ getIntent().getStringExtra("IdBookmark")+"berhasil ditambahkan ke bookmark", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });


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


    public void updateEmployee(){
        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();


        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        final String id_berita = getIntent().getStringExtra("IdBerita");


        class UpdateEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        DetailBookmark.this,"Mengubah",
                        "Tunggu Sebentar...",false,false
                );

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(DetailBookmark.this, s, Toast.LENGTH_SHORT).show();
                Intent main = new Intent(DetailBookmark.this, Main2Activity.class);
                startActivity(main);
                finish();
            }

            @Override
            protected String doInBackground(Void... voids) {
                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> map = new HashMap<>();
                map.put(InitRetrofit.KEY_EMP_IDBERITABOOKMARK,id_berita);
                map.put(InitRetrofit.KEY_EMP_IDPENGGUNABOOKMARK,idku);
                String FinalData = imageProcessClass.ImageHttpRequest(InitRetrofit.URL_ADD_BOOKMARK, map);
                return FinalData;

            }
        }

        new UpdateEmployee().execute();
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }

    // fungsi untuk menghapus

    private void delete(final String idx){

        StringRequest strReq = new StringRequest(Request.Method.POST, InitRetrofit.URL_DELETE_BOOKMARK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("delete", jObj.toString());


                        Toast.makeText(DetailBookmark.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        Intent main = new Intent(DetailBookmark.this, Main2Activity.class);
                        startActivity(main);
                        finish();


                    } else {
                        Toast.makeText(DetailBookmark.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(DetailBookmark.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }
}
