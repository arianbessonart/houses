package smt.ort.houses.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "houses")
public class House {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Long id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    public House(@NonNull Long id, @NonNull String title) {
        this.id = id;
        this.title = title;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }
}
