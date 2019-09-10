package com.example.bete.network;

import android.renderscript.Sampler;

import com.example.bete.response.ResponseBerita;
import com.example.bete.response.ResponseBookmark;
import com.example.bete.response.ResponseForum;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServices {

    @GET("tampil_apps.php")
    Call<ResponseBerita> request_show_apps();

    @GET("tampil_gadget.php")
    Call<ResponseBerita> request_show_gadget();

    @GET("tampil_game.php")
    Call<ResponseBerita> request_show_game();

    @GET("tampil_internet.php")
    Call<ResponseBerita> request_show_internet();

    @GET("tampil_robotik.php")
    Call<ResponseBerita> request_show_robotik();

    @GET("tampil_technews.php")
    Call<ResponseBerita> request_show_technews();

    @GET("tampil_tipstrik.php")
    Call<ResponseBerita> request_show_tipstrik();

    @GET("tampil_berita.php")
    Call<ResponseBerita> request_show_all_berita();

    @GET("tampil_bookmark.php")
    Call<ResponseBookmark> request_show_all_bookmark();

    @GET("tampil_forum.php")
    Call<ResponseForum> request_show_all_forum();






}
