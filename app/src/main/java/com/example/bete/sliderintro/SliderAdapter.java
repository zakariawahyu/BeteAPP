package com.example.bete.sliderintro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bete.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    //Array
    public int[] slide_images = {
            R.drawable.logobete,
            R.drawable.onlinenews,
            R.drawable.list
    };

    public String[] slide_headings = {
            "BERITA TEKNOLOGI TERKINI",
            "KEMBANGKAN INFORMASI",
            "SIMPAN BERITA"
    };

    public String[] slide_descs = {
            "adalah aplikasi mobile yang bertujuan untuk meningkatkan pengetahuan para pembaca atau pencari informasi tentang dunia teknologi yang sedang digandrungi oleh masyarakat luas.",
            "Jika anda mempunya jiwa jurnalistik, terdapat fitur buat berita. Anda dapat membuat berita terkini berdasarkan kategori yang ada kemudian berita akan di post sesuai nama pemilik berita",
            "Anda dapat memanfaatkan fitur Bookmark, dapan menyimpan dan menghapus boookmark sesuai keinginan. Jika ada berita yang akan anda baca lagi, klik tombol bookmark !"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view ==(ConstraintLayout)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View view = inflater.inflate(R.layout.slide_layout, container, false);

        CircleImageView slideImageView = (CircleImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescs = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescs.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
