package com.klcovid19project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.klcovid19project.Adapter.PersonAdapter;
import com.klcovid19project.Models.Persons;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OnlineDoctorsActivity extends AppCompatActivity implements PersonAdapter.SearchAdapterListener {

    private FloatingActionButton Add;
    private RecyclerView mPersonList;
    private PersonAdapter personAdapter;
    private List<Persons> personsList;
    private RelativeLayout mNoPersons;

    private DatabaseReference mPersonsDatabase;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_doctors);

        Add = findViewById(R.id.add);
        mNoPersons = findViewById(R.id.no_persons);
        mPersonList = findViewById(R.id.volunteer_list);

        mPersonsDatabase = FirebaseDatabase.getInstance().getReference().child("Persons");
        mPersonsDatabase.keepSynced(true);

        mPersonList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mPersonList.setLayoutManager(mLayoutManager);
        personsList = new ArrayList<>();
        personAdapter = new PersonAdapter(this, personsList, this);
        mPersonList.setAdapter(personAdapter);

        readPersons("Doctor Online Appointment");
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkConnected()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OnlineDoctorsActivity.this);
                    builder.setMessage("Do you want to become Online Doctor ?").setTitle("Doctor Online Appointment");
                    builder.setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(OnlineDoctorsActivity.this, ConfirmActivity.class);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Doctor Online Appointment");
                    alert.show();
                } else {
                    Toast.makeText(OnlineDoctorsActivity.this, "Connect to Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void readPersons(final String title) {
        mPersonsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                        personsList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Persons myStatus = snapshot.getValue(Persons.class);
                            if (myStatus.getType().contains(title))
                                personsList.add(myStatus);
                        }
                    if (!personsList.isEmpty()) {
                        mPersonList.setVisibility(View.VISIBLE);
                        mNoPersons.setVisibility(View.GONE);
                        personAdapter.notifyDataSetChanged();
                    }else{
                        mNoPersons.setVisibility(View.VISIBLE);
                        mPersonList.setVisibility(View.GONE);
                    }

                } else {
                    mNoPersons.setVisibility(View.VISIBLE);
                    mPersonList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onSearchSelected(Persons persons) {

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
                personAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                personAdapter.getFilter().filter(query);
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
}