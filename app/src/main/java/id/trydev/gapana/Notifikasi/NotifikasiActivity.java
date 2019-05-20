package id.trydev.gapana.Notifikasi;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import id.trydev.gapana.Base.MainActivity;
import id.trydev.gapana.R;

public class NotifikasiActivity extends AppCompatActivity {
    
    MaterialButton btnCariPosko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);
        
        btnCariPosko = findViewById(R.id.btn_cari_posko);
        btnCariPosko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(NotifikasiActivity.this, MainActivity.class));
                Toast.makeText(NotifikasiActivity.this, "This button should bring you to posko fragment !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
