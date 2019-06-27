package id.trydev.gapana.Berita.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.trydev.gapana.Berita.Model.Berita;
import id.trydev.gapana.R;
import id.trydev.gapana.Utils.GlideApp;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ViewHolder> {

    private List<Berita> listBerita;

    public BeritaAdapter(List<Berita> listBerita) {
        this.listBerita = listBerita;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_berita_grid_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindItem(listBerita.get(i));
    }

    @Override
    public int getItemCount() {
        return listBerita.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBerita;
        TextView judulBerita, sumberBerita;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBerita = itemView.findViewById(R.id.img_berita);
            judulBerita = itemView.findViewById(R.id.judul_berita);
            sumberBerita = itemView.findViewById(R.id.sumber_berita);
        }

        public void bindItem(Berita item){
            GlideApp.with(itemView)
                    .asBitmap()
                    .centerCrop()
                    .load(item.getPhoto_url())
                    .thumbnail(0.25f)
                    .placeholder(R.color.colorAccent)
                    .into(imgBerita);
            imgBerita.setImageResource(R.drawable.dummy_image);
            judulBerita.setText(item.getTitle());
            sumberBerita.setText(item.getSumber());
        }
    }
}
