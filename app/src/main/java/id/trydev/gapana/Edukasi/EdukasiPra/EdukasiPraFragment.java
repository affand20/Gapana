package id.trydev.gapana.Edukasi.EdukasiPra;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.trydev.gapana.Edukasi.EdukasiPra.Adapter.EdukasiPraAdapter;
import id.trydev.gapana.Edukasi.EdukasiPra.Detail.DetailEdukasiPraActivity;
import id.trydev.gapana.Edukasi.EdukasiPra.Model.EdukasiPra;
import id.trydev.gapana.R;
import id.trydev.gapana.Utils.ItemClickSupport;

public class EdukasiPraFragment extends Fragment implements EdukasiPraView{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvEdukasiPra;
    private TextView emptyText;

    private List<EdukasiPra> listEdukasiPra = new ArrayList<>();

    private EdukasiPraAdapter adapter;
    private EdukasiPraPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edukasi_pra, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toast.makeText(getActivity(), "TOAST!!!!!!!", Toast.LENGTH_SHORT).show();

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        rvEdukasiPra = view.findViewById(R.id.rv_edukasi_pra);
        emptyText = view.findViewById(R.id.empty_text);

        rvEdukasiPra.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        presenter = new EdukasiPraPresenter(this);
        adapter = new EdukasiPraAdapter(listEdukasiPra);
        rvEdukasiPra.setAdapter(adapter);

        presenter.getEdukasiPra();
        // if data has been expired, fetch data from server
//        presenter.getDataFromServer();
        // else fetch data from local
//        presenter.getDataFromLocal();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            presenter.getEdukasiPra();
            }
        });

        ItemClickSupport.addTo(rvEdukasiPra).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), DetailEdukasiPraActivity.class);
                intent.putExtra("edukasi_pra", listEdukasiPra.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showEmptyKonten() {
        rvEdukasiPra.setVisibility(View.GONE);
        emptyText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData(List<EdukasiPra> listKontenEdukasiPra) {
        this.listEdukasiPra.clear();
        this.listEdukasiPra.addAll(listKontenEdukasiPra);
        Log.d("EDUKASI PRA", "listKontenEdukasiPra : "+listKontenEdukasiPra.size()+", listEdukasiPra : "+listEdukasiPra.size());
        adapter.notifyDataSetChanged();
        rvEdukasiPra.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.GONE);
    }
}
