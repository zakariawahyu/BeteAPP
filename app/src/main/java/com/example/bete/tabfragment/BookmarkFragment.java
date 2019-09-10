package com.example.bete.tabfragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.bete.berita.AdapterBerita;
import com.example.bete.berita.DetailActivity;
import com.example.bete.bookmark.AdapterBookmark;
import com.example.bete.network.ApiServices;
import com.example.bete.network.InitRetrofit;
import com.example.bete.profile.Login;
import com.example.bete.R;
import com.example.bete.response.BeritaItem;
import com.example.bete.response.BookmarkItem;
import com.example.bete.response.ResponseBerita;
import com.example.bete.response.ResponseBookmark;
import com.example.bete.sliderintro.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends Fragment {
    private Button loginbookmark;
    private RelativeLayout relativeLayout, bookmark;

    private static final String LOGIN = "LOGIN";

    Boolean status= false;
    SharedPreferences sharedpreferences;

    int PRIVATE_MODE = 0;

    private RecyclerView recyclerView;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bookmark, container, false);



        recyclerView = v.findViewById(R.id.lst);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Eksekusi method
        tampilBerita();

        relativeLayout = v.findViewById(R.id.emptybookmark);
        bookmark = v.findViewById(R.id.bookmark);

        sharedpreferences = getContext().getSharedPreferences("LOGIN", PRIVATE_MODE);
        status = sharedpreferences.getBoolean(LOGIN, false);

        if (status) {
            relativeLayout.setVisibility(View.INVISIBLE);
            bookmark.setVisibility(View.VISIBLE);
        } else {
            relativeLayout.setVisibility(View.VISIBLE);
            bookmark.setVisibility(View.INVISIBLE);

        }

        loginbookmark = v.findViewById(R.id.loginbookmark);
        loginbookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lgnbookmark = new Intent(getActivity(), Login.class);
                startActivity(lgnbookmark);
                getActivity().finish();
            }
        });
        return v;
    }

    private void tampilBerita() {
        ApiServices api = InitRetrofit.getInstance();
        // Siapkan request
        Call<ResponseBookmark> bookmarkCall = api.request_show_all_bookmark();
        // Kirim request
        bookmarkCall.enqueue(new Callback<ResponseBookmark>() {
            @Override
            public void onResponse(Call<ResponseBookmark> call, Response<ResponseBookmark> response) {
                // Pasikan response Sukses
                if (response.isSuccessful()){
                    Log.d("response api", response.body().toString());
                    // tampung data response body ke variable
                    List<BookmarkItem> data_bookmark = response.body().getBookmark();
                    boolean status = response.body().isStatus();
                    // Kalau response status nya = true
                    if (status){
                        // Buat Adapter untuk recycler view
                        AdapterBookmark adapter = new AdapterBookmark(getActivity(), data_bookmark);
                        recyclerView.setAdapter(adapter);
                    } else {
                        // kalau tidak true
                        Toast.makeText(getActivity(), "Tidak ada bookmark untuk saat ini", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBookmark> call, Throwable t) {
                // print ke log jika Error
                t.printStackTrace();

            }
        });
    }
}
