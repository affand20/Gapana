package id.trydev.gapana.Berita;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
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

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
import id.trydev.gapana.Utils.GlideApp;
import id.trydev.gapana.Utils.ItemClickSupport;

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
        ((MainActivity) getActivity()).setNavigationItemSelected(R.id.berita);
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

        beritaAdapter = new BeritaAdapter(this.listBerita);
        rvBerita.setAdapter(beritaAdapter);

        presenter = new BeritaPresenter(this);

//        presenter.get5NewestBerita();
        presenter.getBerita();

        carouselView.setPageCount(listBeritaCarousel.size());
        carouselView.setViewListener(viewListener);

//        carouselView.setImageListener(new ImageListener() {
//            @Override
//            public void setImageForPosition(int position, ImageView imageView) {
//                imageView.setImageResource(R.drawable.dummy_image);
//            }
//        });
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Uri uri = Uri.parse(listBeritaCarousel.get(position).getUrl());
                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = intentBuilder.build();
                customTabsIntent.launchUrl(getActivity().getApplicationContext(), uri);
                intentBuilder.setCloseButtonIcon(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_close_black_24dp));
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getBerita();
            }
        });

        ItemClickSupport.addTo(rvBerita).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Log.d("LIST BERITA", "showListBerita: "+listBerita.size());
                Uri uri = Uri.parse(listBerita.get(position).getUrl());
                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = intentBuilder.build();
                customTabsIntent.launchUrl(getActivity().getApplicationContext(), uri);
                intentBuilder.setCloseButtonIcon(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_close_black_24dp));
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
        carouselView.setViewListener(null);
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
//        Log.d("LIST CAROUSEL", "show5NewestBerita: "+listCarousel.get(0).getTitle());

        carouselAdapter = new CarouselAdapter(getActivity().getApplicationContext(), this.listBeritaCarousel);
        carouselView.setPageCount(listBeritaCarousel.size());

        carouselView.setViewListener(carouselAdapter);
    }

    private ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_carousel_berita, null);

            System.out.println("JUDUL BERITA -> "+listBerita.get(position).getTitle());

            ImageView ivBerita = view.findViewById(R.id.iv_carousel_berita);
            TextView judulBerita = view.findViewById(R.id.judul_berita);

//        ivBerita.setImageResource(R.drawable.dummy_image);
            GlideApp.with(getActivity())
                    .asBitmap()
                    .centerCrop()
                    .load(listBeritaCarousel.get(position).getPhoto_url())
                    .thumbnail(0.025f)
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
            judulBerita.setText(listBeritaCarousel.get(position).getTitle());

            return view;
        }
    };

    @Override
    public void showListBerita(List<Berita> listBeritaGrid) {
        this.listBerita.clear();
        this.listBerita.addAll(listBeritaGrid);
//        Log.d("LIST BERITA", "showListBerita: "+listBerita.get(0).getUrl());
        beritaAdapter.notifyDataSetChanged();
    }

    @Override
    public void sendToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
