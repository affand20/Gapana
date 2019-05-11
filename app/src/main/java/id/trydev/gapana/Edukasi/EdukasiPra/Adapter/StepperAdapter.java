package id.trydev.gapana.Edukasi.EdukasiPra.Adapter;


import android.content.Context;
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
import id.trydev.gapana.R;

public  class StepperAdapter extends AbstractFragmentStepAdapter {

    @NonNull
    private final FragmentManager mFragmentManager;

    @NonNull
    protected final Context context;

    public StepperAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm,context);
        this.mFragmentManager = fm;
        this.context = context;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        return new StepViewModel.Builder(context).create();
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Step createStep(int position) {
        StepFragment stepfragment=new StepFragment();
        return stepfragment;
    }
}
