package id.trydev.gapana.Edukasi.EdukasiPra.Detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import id.trydev.gapana.Edukasi.EdukasiPra.Model.EdukasiPra;
import id.trydev.gapana.R;

public class DetailEdukasiPraActivity extends AppCompatActivity {

    EdukasiPra edukasiPra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_edukasi_pra);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getSerializableExtra("edukasi_pra")!=null){
            edukasiPra = (EdukasiPra) getIntent().getSerializableExtra("edukasi_pra");
        }

        //then show data
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
