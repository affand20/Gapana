package id.trydev.gapana.Edukasi.EdukasiPra.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.trydev.gapana.Edukasi.EdukasiPra.Model.EdukasiPra;
import id.trydev.gapana.R;
import id.trydev.gapana.Utils.GlideApp;

public class EdukasiPraAdapter extends RecyclerView.Adapter<EdukasiPraAdapter.ViewHolder> {

    private List<EdukasiPra> listKontenEdukasiPra;

    public EdukasiPraAdapter(List<EdukasiPra> listKontenEdukasiPra){
        this.listKontenEdukasiPra = listKontenEdukasiPra;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_edukasi_pra,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindItem(listKontenEdukasiPra.get(i));
    }

    @Override
    public int getItemCount() {
        return listKontenEdukasiPra.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView judul, kategori;
        ImageView berita;
        CardView card;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            judul = itemView.findViewById(R.id.judul);
            kategori = itemView.findViewById(R.id.kategori);
            berita = itemView.findViewById(R.id.berita);
            card = itemView.findViewById(R.id.card);
        }

        public void bindItem(EdukasiPra item){
            judul.setText(item.getJudul());
            kategori.setText(item.getKategori());
            String url = item.getListGambar().split(",")[0];
            Log.d("URL IMAGE", "bindItem: "+url);
//            GlideApp.with(itemView)
            berita.setImageResource(itemView.getContext().getResources().getIdentifier(url, "drawable", itemView.getContext().getPackageName()));
//                    .asBitmap()
//                    .load(url)
//                    .into(berita);
//            isi.setText(item.getIsi());

        }
    }
}
