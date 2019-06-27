package id.trydev.gapana.Edukasi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.trydev.gapana.Base.MainActivity;
import id.trydev.gapana.Edukasi.EdukasiPasca.PascaFragment;
import id.trydev.gapana.Edukasi.EdukasiPra.EdukasiPraFragment;
import id.trydev.gapana.R;

public class EdukasiFragment extends Fragment {



    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int totalTabs = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edukasi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewpager);



        viewPager.setAdapter(new TabAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    class TabAdapter extends FragmentPagerAdapter{

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0:
                    return new EdukasiPraFragment();
                case 1:
                    return new PascaFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return totalTabs;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Pra";
                case 1:
                    return "Pasca";
            }
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Edukasi Bencana");
        ((MainActivity) getActivity()).setNavigationItemSelected(R.id.edukasi_bencana);
    }
}
