package id.trydev.gapana.Edukasi.EdukasiPra.Adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import id.trydev.gapana.Edukasi.EdukasiPra.Detail.StepFragment;
import id.trydev.gapana.Edukasi.EdukasiPra.Model.EdukasiPra;
import id.trydev.gapana.R;

public  class StepperAdapter extends AbstractFragmentStepAdapter {

    @NonNull
    private final FragmentManager mFragmentManager;

    @NonNull
    protected final Context context;

    private String listGambar[];
    EdukasiPra data;

    public StepperAdapter(@NonNull FragmentManager fm, @NonNull Context context, EdukasiPra data) {
        super(fm,context);
        this.mFragmentManager = fm;
        this.context = context;
        this.listGambar = data.getListGambar().split(",");
        this.data = data;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        return new StepViewModel.Builder(context).create();
    }

    @Override
    public int getCount() {
        return listGambar.length;
    }

    @Override
    public Step createStep(int position) {
        StepFragment stepfragment=new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gambar", listGambar[position]);
        bundle.putString("warna", data.getWarnaBg());
        stepfragment.setArguments(bundle);
        return stepfragment;
    }
}
