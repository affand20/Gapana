package id.trydev.gapana.Notifikasi;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import id.trydev.gapana.Base.MainActivity;
import id.trydev.gapana.R;

public class NotifikasiActivity extends AppCompatActivity {
    
    MaterialButton btnCariPosko;
    TextView infoBencana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);
        
        btnCariPosko = findViewById(R.id.btn_cari_posko);
        infoBencana = findViewById(R.id.info_bencana);

        String info = getIntent().getStringExtra("info");
        String magnitude = getIntent().getStringExtra("magnitude");
        String type = getIntent().getStringExtra("type");

        infoBencana.setText(String.format(getResources().getString(R.string.info_gempa),magnitude,info));

        btnCariPosko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotifikasiActivity.this, MainActivity.class);
                intent.putExtra("redirect", "posko");
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });
    }
}
