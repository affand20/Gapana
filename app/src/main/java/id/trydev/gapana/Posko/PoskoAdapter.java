package id.trydev.gapana.Posko;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.trydev.gapana.Posko.Model.NomorPenting;
import id.trydev.gapana.R;

public class PoskoAdapter extends RecyclerView.Adapter<PoskoAdapter.ViewHolder> {

    private List<NomorPenting> listNomorPenting = new ArrayList<>();

    public PoskoAdapter(List<NomorPenting> listNomorPenting){
        this.listNomorPenting = listNomorPenting;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_nomor_penting, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindItem(listNomorPenting.get(i));
    }

    @Override
    public int getItemCount() {
        return listNomorPenting.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama, nomorTelepon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.nama);
            nomorTelepon = itemView.findViewById(R.id.nomor);
        }

        public void bindItem(NomorPenting item){
            nama.setText(item.getNama());
            nomorTelepon.setText(item.getNomor());
        }
    }
}
