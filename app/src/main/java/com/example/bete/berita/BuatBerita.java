package com.example.bete.berita;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bete.Main2Activity;
import com.example.bete.R;
import com.example.bete.profile.SessionManager;
import com.example.bete.network.InitRetrofit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class BuatBerita extends AppCompatActivity {
    private EditText et_judul, et_isi;
    private Spinner kategori;
    private Button buatberita, pilihgambar;
    String penulis;

    SessionManager sessionManager;
    public Context context;
    int PRIVATE_MODE = 0;
    private static final String LOGIN = "LOGIN";
    Bitmap bitmap;
    boolean check = true;
    private ImageView imageViewberita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_berita);

        et_judul = findViewById(R.id.et_judulberita);
        et_isi = findViewById(R.id.et_isiberita);
        kategori = findViewById(R.id.spinner_buatberita);
        buatberita = findViewById(R.id.btn_buatberita);
        imageViewberita = findViewById(R.id.show_fotoberita);
        pilihgambar = findViewById(R.id.btn_selectimageberita);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        penulis = user.get(sessionManager.NAME);

        buatberita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBerita();
            }
        });

        pilihgambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
            }
        });

    }

    private void reset(){
        et_isi.setText("");
        et_judul.setText("");
    }

    public void AddBerita(){
        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        final String judul = et_judul.getText().toString().trim();
        final String isi = et_isi.getText().toString().trim();
        final String kategoriku  = kategori.getSelectedItem().toString().trim();
        Date d = new Date();
        CharSequence s  = DateFormat.format("yyyy-MM-dd", d.getTime());
        final String tglposting = s.toString();
        class AddBerita extends AsyncTask<Void,Void,String > {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        BuatBerita.this,
                        "Menambahkan...",
                        "Tunggu Sebentar...",
                        false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                reset();
                Toast.makeText(BuatBerita.this, s, Toast.LENGTH_LONG).show();
                imageViewberita.setImageResource(android.R.color.transparent);
                Intent main = new Intent(BuatBerita.this, Main2Activity.class);
                startActivity(main);
                finish();
            }

            @Override
            protected String doInBackground(Void... voids) {
                ImageProcessClass imageProcessClass = new ImageProcessClass();
                HashMap<String,String> params = new HashMap<>();
                params.put(InitRetrofit.KEY_EMP_FOTO, ConvertImage);
                params.put(InitRetrofit.KEY_EMP_JUDUL,judul);
                params.put(InitRetrofit.KEY_EMP_TGLPOSTING, tglposting);
                params.put(InitRetrofit.KEY_EMP_ISI,isi);
                params.put(InitRetrofit.KEY_EMP_KATEGORI,kategoriku);
                params.put(InitRetrofit.KEY_EMP_PENULIS, penulis);
                String FinalData = imageProcessClass.ImageHttpRequest(InitRetrofit.URL_ADD, params);
                return FinalData;
            }
        }

        new AddBerita().execute();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                imageViewberita.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
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
}
