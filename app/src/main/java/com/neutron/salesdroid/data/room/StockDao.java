package com.neutron.salesdroid.data.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.neutron.salesdroid.data.model.Stock;
import com.neutron.salesdroid.data.model.UnitAndStockname;

import java.util.List;

@Dao
public interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStock(Stock stock);

    @Query("SELECT * FROM stocks")
    LiveData<List<Stock>> getStock();

    @Query("SELECT unit, name AS stockName FROM stocks")
    LiveData<List<UnitAndStockname>> getUnitAndStockname();

    @Delete
    void deleteStock(Stock stock);

    @Query("UPDATE stocks "
            +"SET availableQuantity=availableQuantity - :qtySold "
            +"WHERE name = :stockName AND unit = :unit")
    void reduceStockQty(int qtySold, String stockName, String unit);
}
