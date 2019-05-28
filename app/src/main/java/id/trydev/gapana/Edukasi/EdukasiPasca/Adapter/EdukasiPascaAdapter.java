package id.trydev.gapana.Edukasi.EdukasiPasca.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.trydev.gapana.Edukasi.EdukasiPasca.Model.EdukasiPasca;
import id.trydev.gapana.R;
import id.trydev.gapana.Utils.GlideApp;

public class EdukasiPascaAdapter extends RecyclerView.Adapter<EdukasiPascaAdapter.ViewHolder> {

    private List<EdukasiPasca> listvideo;

    public EdukasiPascaAdapter (List<EdukasiPasca> listvideo){
        this.listvideo = listvideo;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_edukasi_pasca, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindItem(listvideo.get(i));
    }

    @Override
    public int getItemCount() {
        return listvideo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnail;
        private TextView judul_video;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            judul_video = itemView.findViewById(R.id.judul_video);

        }

        public void bindItem (EdukasiPasca item){
            judul_video.setText(item.getJudul());
            GlideApp.with(itemView)
                    .asBitmap()
                    .centerCrop()
                    .thumbnail(0.02f)
                    .load(item.getThumbnail())
                    .fallback(R.color.ms_material_grey_400)
                    .into(thumbnail);
        }
    }
}
