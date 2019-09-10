package com.example.bete.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bete.Main2Activity;
import com.example.bete.R;
import com.example.bete.app.RequestHandler;
import com.example.bete.network.InitRetrofit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class EditProfile extends AppCompatActivity {
    private Button editnow, selectimage;
    private EditText et_nama, et_username;
    SessionManager sessionManager;
    private String idku;
    private String JSON_STRING;
    Bitmap bitmap;
    boolean check = true;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        et_nama = findViewById(R.id.et_namalengkap);
        et_username = findViewById(R.id.et_username);
        imageView  = findViewById(R.id.show_foto);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        idku = user.get(sessionManager.ID);
        getJSON();

        editnow = findViewById(R.id.btn_editnow);
        editnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee();
            }
        });

        selectimage = findViewById(R.id.btn_selectimage);
        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
            }
        });
    }

    private void showEmployee(){

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

                et_nama.setText(name);
                et_username.setText(username);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(InitRetrofit.TAG_ID,id);
                employees.put(InitRetrofit.TAG_NAMA,name);
                employees.put(InitRetrofit.TAG_USERNAME, username);

                list.add(employees);
            }
        } catch (JSONException e) {
            new AlertDialog.Builder(EditProfile.this)
                    .setMessage(e.getMessage())
                    .show();
        }catch (NullPointerException e){
            new AlertDialog.Builder(EditProfile.this)
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
                        EditProfile.this,
                        "Mengambil Data",
                        "Mohon tunggu...",
                        false,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
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

    public void updateEmployee(){
        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        final String nama = et_nama.getText().toString().trim();
        final String username = et_username.getText().toString().trim();


        class UpdateEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        EditProfile.this,"Mengubah",
                        "Tunggu Sebentar...",false,false
                );

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(EditProfile.this, s, Toast.LENGTH_SHORT).show();
                imageView.setImageResource(android.R.color.transparent);
                Intent main = new Intent(EditProfile.this, Main2Activity.class);
                startActivity(main);
                finish();
            }

            @Override
            protected String doInBackground(Void... voids) {
                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> map = new HashMap<>();
                map.put(InitRetrofit.KEY_EMP_ID,idku);
                map.put(InitRetrofit.KEY_EMP_NAMA,nama);
                map.put(InitRetrofit.KEY_EMP_USERNAME,username);
                map.put(InitRetrofit.KEY_EMP_FOTOPROFILE, ConvertImage);
                String FinalData = imageProcessClass.ImageHttpRequest(InitRetrofit.URL_UPDATE_EMP, map);
                return FinalData;

            }
        }

        new UpdateEmployee().execute();
    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                imageView.setImageBitmap(bitmap);

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
