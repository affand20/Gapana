package id.trydev.gapana.Pengaturan;

import android.content.Intent;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import id.trydev.gapana.R;

import static id.trydev.gapana.Base.MainActivity.preferences;

public class PengaturanActivity extends AppCompatActivity {

//    TextView faqBtn, tentangBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Pengaturan");

        Switch switchNotif = findViewById(R.id.switchNotif);

        switchNotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                 if (b){
                     preferences.setTogleNotif(1);
                 } else{
                     preferences.setTogleNotif(0);
                 }
            }
        });

        TextView faqBtn = (TextView) findViewById(R.id.faqBtn);
        faqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getBaseContext(), PanduanActivity.class);
                startActivity(intent);
            }
        });

        TextView tentangBtn = (TextView) findViewById(R.id.tentangBtn);
        tentangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getBaseContext(), TentangActivity.class);
                startActivity(intent);
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
