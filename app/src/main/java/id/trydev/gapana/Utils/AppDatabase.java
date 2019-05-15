package id.trydev.gapana.Utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import id.trydev.gapana.Edukasi.EdukasiPra.Model.Dao.EdukasiPraDao;
import id.trydev.gapana.Edukasi.EdukasiPra.Model.EdukasiPra;
import id.trydev.gapana.Posko.Model.NomorPenting;
import id.trydev.gapana.Posko.Model.NomorPentingDao;

@Database(entities = {EdukasiPra.class, NomorPenting.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    public abstract EdukasiPraDao edukasiPraDao();
    public abstract NomorPentingDao nomorPentingDao();
}
