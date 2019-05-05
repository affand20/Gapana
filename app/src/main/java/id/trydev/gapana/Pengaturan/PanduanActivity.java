package id.trydev.gapana.Pengaturan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.trydev.gapana.R;

public class PanduanActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
     HashMap<String, List<String>> listChild;
     List<String> listHeader;
     CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panduan);
    }
}
