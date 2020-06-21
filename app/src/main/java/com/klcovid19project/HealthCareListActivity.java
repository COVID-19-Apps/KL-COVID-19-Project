package com.klcovid19project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.klcovid19project.Adapter.HealthCareListAdapter;
import com.klcovid19project.Models.HealthCareList;
import com.klcovid19project.Models.Persons;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HealthCareListActivity extends AppCompatActivity implements HealthCareListAdapter.SearchAdapterListener {

    private RecyclerView mHealthCareList;

    private HealthCareListAdapter healthCareListAdapter;
    private List<HealthCareList> healthCareLists;

    private DatabaseReference mHealthCareDatabase;

    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_care_list);

        mHealthCareList =findViewById(R.id.health_care_list);

        mHealthCareDatabase = FirebaseDatabase.getInstance().getReference().child("health_care").child("data");
        mHealthCareDatabase.keepSynced(true);

        mHealthCareList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mHealthCareList.setLayoutManager(mLayoutManager);
        healthCareLists = new ArrayList<>();
        healthCareListAdapter = new HealthCareListAdapter(this, healthCareLists, this);
        mHealthCareList.setAdapter(healthCareListAdapter);

        read();
    }

    private void read() {
        mHealthCareDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                healthCareLists.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HealthCareList myStatus = snapshot.getValue(HealthCareList.class);
                    healthCareLists.add(myStatus);
                    healthCareListAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                healthCareListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                healthCareListAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchSelected(HealthCareList healthCareList) {

    }
}
