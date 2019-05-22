package id.trydev.gapana.Edukasi.EdukasiPra.Detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import id.trydev.gapana.Edukasi.EdukasiPra.Adapter.StepperAdapter;
import id.trydev.gapana.Edukasi.EdukasiPra.Model.EdukasiPra;
import id.trydev.gapana.R;

public class DetailEdukasiPraActivity extends AppCompatActivity  {

    EdukasiPra edukasiPra;
    StepperLayout stepperlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_edukasi_pra);

        if (getIntent().getSerializableExtra("edukasi_pra")!=null){
            edukasiPra = (EdukasiPra) getIntent().getSerializableExtra("edukasi_pra");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(edukasiPra.getJudul());

        stepperlayout= findViewById(R.id.stepperLayout);
        stepperlayout.setAdapter(new StepperAdapter(getSupportFragmentManager(),this, edukasiPra));
        stepperlayout.setListener(new StepperLayout.StepperListener() {
            @Override
            public void onCompleted(View completeButton) {
                onReturn();
            }

            @Override
            public void onError(VerificationError verificationError) {
                Toast.makeText(DetailEdukasiPraActivity.this, verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStepSelected(int newStepPosition) {

            }

            @Override
            public void onReturn() {
                finish();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
