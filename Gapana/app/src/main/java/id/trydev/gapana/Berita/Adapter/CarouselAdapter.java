package id.trydev.gapana.Berita.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.synnapps.carouselview.ViewListener;

import java.util.List;

import id.trydev.gapana.Berita.Model.Berita;
import id.trydev.gapana.R;
import id.trydev.gapana.Utils.GlideApp;

public class CarouselAdapter implements ViewListener {

    private List<Berita> listBerita;
    private Context context;

    public CarouselAdapter(Context context, List<Berita> listBerita){
        this.context = context;
        this.listBerita = listBerita;
    }

    @Override
    public View setViewForPosition(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_carousel_berita, null);

        System.out.println("JUDUL BERITA -> "+listBerita.get(position).getTitle());

        ImageView ivBerita = view.findViewById(R.id.iv_carousel_berita);
        TextView judulBerita = view.findViewById(R.id.judul_berita);

//        ivBerita.setImageResource(R.drawable.dummy_image);
        GlideApp.with(context)
                .asBitmap()
                .centerCrop()
                .load(listBerita.get(position).getPhoto_url())
                .thumbnail(0.25f)
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Log.d("LOAD FAILED", "onLoadFailed: "+e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("LOAD READY", "onResourceReady: SUCCESS");
                        return false;
                    }
                })
                .placeholder(R.color.colorAccent)
                .into(ivBerita);
        judulBerita.setText(listBerita.get(position).getTitle());

        return view;
    }
}
