package id.trydev.gapana.Berita;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

import id.trydev.gapana.Base.MainActivity;
import id.trydev.gapana.Berita.Adapter.BeritaAdapter;
import id.trydev.gapana.Berita.Adapter.CarouselAdapter;
import id.trydev.gapana.Berita.Model.Berita;
import id.trydev.gapana.R;

public class BeritaFragment extends Fragment implements BeritaView {

    private CarouselView carouselView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvBerita;

    private BeritaPresenter presenter;

    private List<Berita> listBerita = new ArrayList<>();
    private List<Berita> listBeritaCarousel = new ArrayList<>();
    private CarouselAdapter carouselAdapter;
    private BeritaAdapter beritaAdapter;

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Berita");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_berita, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        carouselView = view.findViewById(R.id.carousel_view);
        rvBerita = view.findViewById(R.id.rv_berita);

        rvBerita.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 0b10));

        presenter = new BeritaPresenter(this);
        beritaAdapter = new BeritaAdapter(listBerita);
        rvBerita.setAdapter(beritaAdapter);

        presenter.get5NewestBerita();
        presenter.getBerita();

        listBerita = new ArrayList<>();
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(R.drawable.dummy_image);
            }
        });
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity().getApplicationContext(), listBeritaCarousel.get(position).getJudul()+" Clicked !", Toast.LENGTH_SHORT).show();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.get5NewestBerita();
                presenter.getBerita();
            }
        });
    }

    private ImageListener listener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(R.drawable.dummy_image);
        }
    };

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void show5NewestBerita(List<Berita> listCarousel) {
        this.listBeritaCarousel.clear();
        this.listBeritaCarousel.addAll(listCarousel);

        carouselAdapter = new CarouselAdapter(getActivity().getApplicationContext(), this.listBeritaCarousel);
        carouselView.setViewListener(carouselAdapter);
        carouselView.setPageCount(listBeritaCarousel.size());
    }

    @Override
    public void showListBerita(List<Berita> listBeritaGrid) {
        this.listBerita.clear();
        this.listBerita.addAll(listBeritaGrid);
        beritaAdapter.notifyDataSetChanged();
    }
}
