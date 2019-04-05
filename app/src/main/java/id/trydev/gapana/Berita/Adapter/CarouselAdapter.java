package id.trydev.gapana.Berita.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.synnapps.carouselview.ViewListener;

import java.util.List;

import id.trydev.gapana.Berita.Model.Berita;
import id.trydev.gapana.R;

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

        System.out.println("JUDUL BERITA -> "+listBerita.get(position).getJudul());

        ImageView ivBerita = view.findViewById(R.id.iv_carousel_berita);
        TextView judulBerita = view.findViewById(R.id.judul_berita);

        ivBerita.setImageResource(R.drawable.dummy_image);
        judulBerita.setText(listBerita.get(position).getJudul());

        return view;
    }
}
