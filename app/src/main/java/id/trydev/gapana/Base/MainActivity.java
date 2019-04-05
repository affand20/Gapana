package id.trydev.gapana.Base;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mapbox.mapboxsdk.Mapbox;

import id.trydev.gapana.Berita.BeritaFragment;
import id.trydev.gapana.BuildConfig;
import id.trydev.gapana.Cuaca.CuacaFragment;
import id.trydev.gapana.Edukasi.EdukasiFragment;
import id.trydev.gapana.Pengaturan.PengaturanActivity;
import id.trydev.gapana.Posko.PoskoFragment;
import id.trydev.gapana.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Mapbox.getInstance(this, BuildConfig.TOKEN);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("Beranda");
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new CuacaFragment()).commit();
        navigationView.setCheckedItem(R.id.beranda);
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
}
