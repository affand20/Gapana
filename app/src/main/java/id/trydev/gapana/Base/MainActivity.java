package id.trydev.gapana.Base;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.location.LocationListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.mapbox.mapboxsdk.Mapbox;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import id.trydev.gapana.Berita.BeritaFragment;
import id.trydev.gapana.BuildConfig;
import id.trydev.gapana.Cuaca.CuacaFragment;
import id.trydev.gapana.Edukasi.EdukasiFragment;
import id.trydev.gapana.Edukasi.EdukasiPra.Model.EdukasiPra;
import id.trydev.gapana.Pengaturan.PengaturanActivity;
import id.trydev.gapana.Posko.Model.NomorPenting;
import id.trydev.gapana.Posko.PoskoFragment;
import id.trydev.gapana.R;
import id.trydev.gapana.Utils.AppDatabase;
import id.trydev.gapana.Utils.AppPreferences;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;

    public static AppDatabase db;
    public static AppPreferences preferences;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//    public static LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "gapana")
                .allowMainThreadQueries()
                .build();

        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        createNotificationChannel();

        preferences = new AppPreferences(this);
//        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (preferences.getFirstRun()==0){
            importDb();
            importDbNomorTelepon();

            preferences.setFirstRun(1);
        }

        Mapbox.getInstance(this, BuildConfig.TOKEN);

        FirebaseMessaging.getInstance().subscribeToTopic("bencana");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (getIntent().getStringExtra("redirect")!=null){
            getSupportActionBar().setTitle("Posko Evakuasi");
            Bundle bundle = new Bundle();
            bundle.putString("type", getIntent().getStringExtra("type"));
            PoskoFragment fragment = new PoskoFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
        } else{
            getSupportActionBar().setTitle("Beranda");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new CuacaFragment()).commit();
        }
        navigationView.setCheckedItem(R.id.beranda);

    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.ch_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(getResources().getString(R.string.channel_id), name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void importDb() {
        try {
            InputStreamReader is = new InputStreamReader(getApplicationContext()
                    .getAssets()
                    .open("db_edukasi_pra.csv"));
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line = "";
            while ((line = reader.readLine()) != null){
//                Log.d("IMPORT DB ROOM", "importDb: "+line);
                String lineSplitter[] = line.split("/");
                EdukasiPra edukasiPra = new EdukasiPra();
                edukasiPra.setId(Integer.parseInt(lineSplitter[0]));
                edukasiPra.setJudul(lineSplitter[1]);
                edukasiPra.setKategori(lineSplitter[2]);
                edukasiPra.setKonten(lineSplitter[3]);
                edukasiPra.setListGambar(lineSplitter[4]);
                edukasiPra.setWarnaBg(lineSplitter[5]);
                db.edukasiPraDao().insert(edukasiPra);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void importDbNomorTelepon(){
        try {
            InputStreamReader is = new InputStreamReader(getApplicationContext()
                    .getAssets()
                    .open("nomortelepon.csv"));
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line = "";
            while ((line = reader.readLine()) != null){
                String lineSplitter[] = line.split(",");
                Log.d("ROOM DATABASE", lineSplitter[0]+" "+lineSplitter[1]+" "+lineSplitter[2]);
                NomorPenting nomorPenting = new NomorPenting();
                nomorPenting.setId(Integer.parseInt(lineSplitter[0]));
                nomorPenting.setNama(lineSplitter[1]);
                nomorPenting.setNomor(lineSplitter[2]);
                db.nomorPentingDao().inserAll(nomorPenting);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.beranda) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new CuacaFragment()).commit();
        } else if (id == R.id.edukasi_bencana) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new EdukasiFragment()).commit();
        } else if (id == R.id.berita) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new BeritaFragment()).commit();
        } else if (id == R.id.posko_evakuasi) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new PoskoFragment()).commit();
        } else if (id == R.id.pengaturan) {
            startActivity(new Intent(MainActivity.this, PengaturanActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setActionBarTitle(String msg){
        getSupportActionBar().setTitle(msg);
    }

    public void setNavigationItemSelected(int id){
        navigationView.setCheckedItem(id);
    }
}
