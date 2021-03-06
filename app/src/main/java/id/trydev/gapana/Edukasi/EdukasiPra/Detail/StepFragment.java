package id.trydev.gapana.Edukasi.EdukasiPra.Detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import id.trydev.gapana.R;

public class StepFragment extends Fragment implements BlockingStep {

    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edukasi_detail, container, false);

        RelativeLayout background = v.findViewById(R.id.background);
        ImageView gambarPanduan = v.findViewById(R.id.gambar_panduan);
        TextView panduanText = v.findViewById(R.id.panduan_text);

        bundle = this.getArguments();

        gambarPanduan.setImageResource(v.getContext().getResources().getIdentifier(bundle.getString("gambar"), "drawable", v.getContext().getPackageName()));
        background.setBackgroundColor(Color.parseColor(bundle.getString("warna")));

        //initialize your UI

        return v;
    }

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null;
    }

    @Override
    public void onSelected() {
        //update UI when selected
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        callback.complete();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }
}
