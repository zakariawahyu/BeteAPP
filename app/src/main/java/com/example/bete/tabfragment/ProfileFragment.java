package com.example.bete.tabfragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bete.profile.EditProfile;
import com.example.bete.profile.Login;
import com.example.bete.Main2Activity;
import com.example.bete.R;
import com.example.bete.profile.SessionManager;
import com.example.bete.app.RequestHandler;
import com.example.bete.berita.BuatBerita;
import com.example.bete.network.InitRetrofit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private String JSON_STRING,idku;

    CircleImageView image;
    ProgressDialog mProgressDialog;
    private Button loginprofile, log_outprofile, editprofile, buat_berita;
    private TextView txt_id, txt_username;
    private RelativeLayout emptyprofile, profile;

    Boolean status= false;
    SharedPreferences sharedpreferences;
    SessionManager sessionManager;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String LOGIN = "LOGIN";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        sessionManager = new SessionManager(getActivity());

//        txt_id = v.findViewById(R.id.txt_id);
        txt_username = v.findViewById(R.id.txt_username);
        image = v.findViewById(R.id.image_profile);
        getJSON();

        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getUserDetail();
        String nama = user.get(sessionManager.NAME);
        idku = user.get(sessionManager.ID);

        txt_username.setText(nama);

        emptyprofile = v.findViewById(R.id.emptyprofile);
        profile = v.findViewById(R.id.profile);

        sharedpreferences = getContext().getSharedPreferences("LOGIN", PRIVATE_MODE);
        status = sharedpreferences.getBoolean(LOGIN, false);

        if (status) {
            emptyprofile.setVisibility(View.INVISIBLE);
            profile.setVisibility(View.VISIBLE);
        } else {
            emptyprofile.setVisibility(View.VISIBLE);
            profile.setVisibility(View.INVISIBLE);
        }

        loginprofile = v.findViewById(R.id.loginprofile);
        loginprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lgnprofile = new Intent(getActivity(), Login.class);
                startActivity(lgnprofile);
                getActivity().finish();
            }
        });

        log_outprofile = v.findViewById(R.id.btn_logout);
        log_outprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
                Intent logout = new Intent(getActivity(), Main2Activity.class);
                startActivity(logout);
                getActivity().finish();
            }
        });

        editprofile = v.findViewById(R.id.btn_edit_profile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(getActivity(), EditProfile.class);
                startActivity(edit);
                getActivity().finish();

            }
        });

        buat_berita = v.findViewById(R.id.buat_berita);
        buat_berita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buatberita = new Intent(getActivity(), BuatBerita.class);
                startActivity(buatberita);

            }
        });


        return v;
    }

    public void showEmployee(){

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
                HashMap<String,String> employees = new HashMap<>();
                employees.put(InitRetrofit.TAG_ID,id);
                employees.put(InitRetrofit.TAG_NAMA,name);
                employees.put(InitRetrofit.TAG_USERNAME, username);

                list.add(employees);
            }
        } catch (JSONException e) {
            new AlertDialog.Builder(getActivity())
                    .setMessage(e.getMessage())
                    .show();
        }catch (NullPointerException e){
            new AlertDialog.Builder(getActivity())
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
                        getActivity(),
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

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
//            mProgressDialog = new ProgressDialog(getActivity());
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
            image.setImageBitmap(result);
            // Close progressdialog
//            mProgressDialog.dismiss();
        }
    }

}
