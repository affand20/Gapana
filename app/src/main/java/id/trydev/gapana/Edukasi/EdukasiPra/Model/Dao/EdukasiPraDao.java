package id.trydev.gapana.Edukasi.EdukasiPra.Model.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import id.trydev.gapana.Edukasi.EdukasiPra.Model.EdukasiPra;

@Dao
public interface EdukasiPraDao {
    @Query("SELECT * FROM edukasipra")
    List<EdukasiPra> getAll();

    @Insert
    void insert(EdukasiPra... edukasiPra);

//    @Query("SELECT * FROM edukasipra WHERE id = :id")
//    EdukasiPra getById(int id);
//
//    //Example Custum Query
//    @Query("SELECT * FROM edukasipra WHERE kategori LIKE :nama ")
//    EdukasiPra findByName(String nama);
//
//    @Insert
//    void insertAll(EdukasiPra... mahasiswa);
//
//    @Delete
//    public void deleteUsers(EdukasiPra... users);

}
