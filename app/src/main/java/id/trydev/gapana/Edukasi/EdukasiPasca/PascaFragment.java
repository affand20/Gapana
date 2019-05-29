package id.trydev.gapana.Edukasi.EdukasiPasca;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.trydev.gapana.Edukasi.EdukasiPasca.Adapter.EdukasiPascaAdapter;
import id.trydev.gapana.Edukasi.EdukasiPasca.Model.EdukasiPasca;
import id.trydev.gapana.R;
import id.trydev.gapana.Utils.ItemClickSupport;

public class PascaFragment extends Fragment implements EdukasiPascaView{

    private RecyclerView rvPasca;
    private EdukasiPascaAdapter adapter;
    private List<EdukasiPasca> listEdukasiPasca = new ArrayList<>();
    private EdukasiPascaPresenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edukasi_pasca, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPasca = view.findViewById(R.id.rv_pasca);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

//        adapter = new EdukasiPascaAdapter(listEdukasiPasca);
        presenter = new EdukasiPascaPresenter(this);

        rvPasca.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        presenter.getListVideo();

        ItemClickSupport.addTo(rvPasca).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Uri uri = Uri.parse(listEdukasiPasca.get(position).getVideo());
                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = intentBuilder.build();
                customTabsIntent.launchUrl(getActivity().getApplicationContext(), uri);
                intentBuilder.setCloseButtonIcon(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_close_black_24dp));
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getListVideo();
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
    public void sendToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void populateData(List<EdukasiPasca> listEdukasiPasca) {
        this.listEdukasiPasca.clear();
        this.listEdukasiPasca = listEdukasiPasca;
        adapter = new EdukasiPascaAdapter(this.listEdukasiPasca);
        rvPasca.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
    }
}
