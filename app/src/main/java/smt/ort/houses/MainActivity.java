package smt.ort.houses;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.List;

import smt.ort.houses.ui.adapter.HouseListAdapter;
import smt.ort.houses.model.House;
import smt.ort.houses.ui.HouseViewModel;

public class MainActivity extends AppCompatActivity {

    private HouseViewModel houseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final HouseListAdapter adapter = new HouseListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        houseViewModel = ViewModelProviders.of(this).get(HouseViewModel.class);
        houseViewModel.getHouses().observe(this, new Observer<List<House>>() {
            @Override
            public void onChanged(@Nullable List<House> houses) {
                adapter.setHouses(houses);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
