package id.trydev.gapana.Posko.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NomorPentingDao {

    @Query("SELECT * FROM nomorpenting")
    List<NomorPenting> getAll();

    @Insert
    void inserAll(NomorPenting... nomorPentings);
}
