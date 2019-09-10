package com.example.bete.forum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bete.R;
import com.example.bete.berita.DetailActivity;
import com.example.bete.response.BeritaItem;
import com.example.bete.response.BookmarkItem;
import com.example.bete.response.ForumItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterForum extends RecyclerView.Adapter<AdapterForum.MyViewHolder> {
    // Buat Global variable untuk manampung context
    Context context;
    List<ForumItem> forum;
    public AdapterForum(Context context, List<ForumItem> data_forum) {
        // Inisialisasi
        this.context = context;
        this.forum = data_forum;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Layout inflater
        View view = LayoutInflater.from(context).inflate(R.layout.forum_layout, parent, false);

        // Hubungkan dengan MyViewHolder
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // Set widget
        holder.tvJudul.setText(forum.get(position).getJudulBerita());
        holder.tvTglTerbit.setText(forum.get(position).getTanggalPosting());
        holder.tvPenulis.setText(forum.get(position).getPenulis());

        // Dapatkan url gambar
        final String urlGambarBerita = "https://bete-app.000webhostapp.com/images/" + forum.get(position).getFotoBerita();
        // Set image ke widget dengna menggunakan Library Piccasso
        // krena imagenya dari internet
        Picasso.with(context).load(urlGambarBerita).into(holder.ivGambarBookmark);

        // Event klik ketika item list nya di klik
        holder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                // Mulai activity Detail
                Intent varIntent = new Intent(context, DetailForum.class);
                // sisipkan data ke intent
                varIntent.putExtra("JDL_BERITA", forum.get(position).getJudulBerita());
                varIntent.putExtra("TGL_BERITA", forum.get(position).getTanggalPosting());
                varIntent.putExtra("PNS_BERITA", forum.get(position).getPenulis());
                varIntent.putExtra("FTO_BERITA", urlGambarBerita);
                varIntent.putExtra("ISI_BERITA", forum.get(position).getIsiBerita());
                varIntent.putExtra("ID_FORUM", forum.get(position).getIdForum());

                // method startActivity cma bisa di pake di activity/fragment
                // jadi harus masuk ke context dulu
                context.startActivity(varIntent);
            }
        });
    }
    // Menentukan Jumlah item yang tampil
    @Override
    public int getItemCount() {
        return forum.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Deklarasi widget
        private ImageView ivGambarBookmark;
        private TextView tvJudul, tvTglTerbit, tvPenulis;
        public MyViewHolder(View itemView) {
            super(itemView);
            // inisialisasi widget
            ivGambarBookmark = (ImageView) itemView.findViewById(R.id.imgvw);
            tvJudul = (TextView) itemView.findViewById(R.id.tvjudulberitaku);
            tvTglTerbit = (TextView) itemView.findViewById(R.id.tvtglku);
            tvPenulis = (TextView) itemView.findViewById(R.id.tvpenulisku);
        }
    }
}
