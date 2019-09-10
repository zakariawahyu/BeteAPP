package com.example.bete.tabfragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.bete.bookmark.AdapterBookmark;
import com.example.bete.forum.AdapterForum;
import com.example.bete.network.ApiServices;
import com.example.bete.network.InitRetrofit;
import com.example.bete.profile.Login;
import com.example.bete.R;
import com.example.bete.response.BookmarkItem;
import com.example.bete.response.ForumItem;
import com.example.bete.response.ResponseBookmark;
import com.example.bete.response.ResponseForum;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForumFragment extends Fragment {
    private Button loginforum;
    private RelativeLayout relativeLayout, forum;

    private static final String LOGIN = "LOGIN";

    Boolean status= false;
    SharedPreferences sharedpreferences;

    public Context context;
    int PRIVATE_MODE = 0;

    private RecyclerView recyclerView;

    public ForumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_forum, container, false);

        recyclerView = v.findViewById(R.id.lstforum);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Eksekusi method
        tampilBerita();

        relativeLayout = v.findViewById(R.id.emptyforum);
        forum = v.findViewById(R.id.forum);

        sharedpreferences = getContext().getSharedPreferences("LOGIN", PRIVATE_MODE);
        status = sharedpreferences.getBoolean(LOGIN, false);


        if (status) {
            relativeLayout.setVisibility(View.INVISIBLE);
            forum.setVisibility(View.VISIBLE);
        } else {
            relativeLayout.setVisibility(View.VISIBLE);
            forum.setVisibility(View.INVISIBLE);
        }

        loginforum = v.findViewById(R.id.loginforum);
        loginforum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lgnforum = new Intent(getActivity(), Login.class);
                startActivity(lgnforum);
                getActivity().finish();
            }
        });
        return v;
    }

    private void tampilBerita() {
        ApiServices api = InitRetrofit.getInstance();
        // Siapkan request
        Call<ResponseForum> forumCall = api.request_show_all_forum();
        // Kirim request
        forumCall.enqueue(new Callback<ResponseForum>() {
            @Override
            public void onResponse(Call<ResponseForum> call, Response<ResponseForum> response) {
                // Pasikan response Sukses
                if (response.isSuccessful()){
                    Log.d("response api", response.body().toString());
                    // tampung data response body ke variable
                    List<ForumItem> data_forum = response.body().getForum();
                    boolean status = response.body().isStatus();
                    // Kalau response status nya = true
                    if (status){
                        // Buat Adapter untuk recycler view
                        AdapterForum adapter = new AdapterForum(getActivity(), data_forum);
                        recyclerView.setAdapter(adapter);
                    } else {
                        // kalau tidak true
                        Toast.makeText(getActivity(), "Tidak ada bookmark untuk saat ini", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseForum> call, Throwable t) {
                // print ke log jika Error
                t.printStackTrace();

            }
        });
    }

}
