package smt.ort.houses.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import smt.ort.houses.model.Favorite;
import smt.ort.houses.model.House;


@Database(entities = {
        House.class, Favorite.class
}, version = 7)
public abstract class HouseRoomDatabase extends RoomDatabase {

    private static volatile HouseRoomDatabase instance;

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
//            new PopulateDbAsync(instance).execute();
        }
    };

    public static HouseRoomDatabase getDatabase(final Context ctx) {
        if (instance == null) {
            synchronized (HouseRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(ctx.getApplicationContext(), HouseRoomDatabase.class, "houses_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract HouseDao houseDao();

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final HouseDao mDao;

        PopulateDbAsync(HouseRoomDatabase db) {
            mDao = db.houseDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
//            House house = new House(1L, "Casa 1");
//            mDao.insert(house);
//            house = new House(2L, "Casa 2");
//            mDao.insert(house);
            return null;
        }
    }
}
