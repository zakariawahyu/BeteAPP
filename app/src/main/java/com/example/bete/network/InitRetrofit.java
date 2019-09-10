package com.example.bete.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitRetrofit {
    // URL Server API
    public static final String API_URL = "https://bete-app.000webhostapp.com/";

    public static final String URL_GET_ALL = "https://bete-app.000webhostapp.com/select_pengguna.php?idku=";
    public static final String URL_UPDATE_EMP = "https://bete-app.000webhostapp.com/update_pengguna.php";
    public static final String URL_ADD = "https://bete-app.000webhostapp.com/tambah_berita.php";
    public static final String URL_ADD_BOOKMARK = "https://bete-app.000webhostapp.com/tambah_bookmark.php";
    public static final String URL_DELETE_BOOKMARK = "https://bete-app.000webhostapp.com/delete_bookmark.php";
    public static final String URL_ADD_FORUM = "https://bete-app.000webhostapp.com/tambah_forum.php";

    public static final String KEY_EMP_IDBERITABOOKMARK = "idBerita";
    public static final String KEY_EMP_IDPENGGUNABOOKMARK = "idPengguna";

    public static final String KEY_EMP_IDBERITAFORUM = "idBeritaForum";


    public static final String TAG_IDBERITABOOKMARK = "idBerita";
    public static final String TAG_IDPENGGUNABOOKMARK = "idPengguna";


    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_NAMA = "nama";
    public static final String KEY_EMP_USERNAME = "username";
    public static final String KEY_EMP_FOTOPROFILE = "fotoprofile";

    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_FOTOPROFILE = "fotoprofile";

    public static final String KEY_EMP_IDBERITA = "idberita";
    public static final String KEY_EMP_FOTO = "foto";
    public static final String KEY_EMP_JUDUL = "judul";
    public static final String KEY_EMP_TGLPOSTING = "tglposting";
    public static final String KEY_EMP_ISI = "isi";
    public static final String KEY_EMP_PENULIS = "penulis";
    public static final String KEY_EMP_KATEGORI = "kategori";

    public static final String TAG_IDBERITA = "idberita";
    public static final String TAG_FOTO = "foto";
    public static final String TAG_JUDUL = "judul";
    public static final String TAG_TGLPOSTING = "tglposting";
    public static final String TAG_ISI = "isi";
    public static final String TAG_PENULIS = "penulis";
    public static final String TAG_KATEGORI = "kategori";

    public static Retrofit setInit() {
        return new Retrofit.Builder().baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiServices getInstance() {
        return setInit().create(ApiServices.class);
    }
}