package id.trydev.gapana.Edukasi.EdukasiPra.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import id.trydev.gapana.Edukasi.EdukasiPra.Model.EdukasiPra;

public class EdukasiPraAdapter extends RecyclerView.Adapter<EdukasiPraAdapter.ViewHolder> {

    private List<EdukasiPra> listKontenEdukasiPra;

    public EdukasiPraAdapter(List<EdukasiPra> listKontenEdukasiPra){
        this.listKontenEdukasiPra = listKontenEdukasiPra;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindItem(listKontenEdukasiPra.get(i));
    }

    @Override
    public int getItemCount() {
        return listKontenEdukasiPra.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bindItem(EdukasiPra item){

        }
    }
}
