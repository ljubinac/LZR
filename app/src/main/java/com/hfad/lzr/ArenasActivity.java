package com.hfad.lzr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.hfad.lzr.adapter.ArenaViewHolder;
import com.hfad.lzr.model.Arena;

public class ArenasActivity extends AppCompatActivity {

    DatabaseReference databaseReferenceArenas;
    EditText arenaNameET;
    ImageView saveArena;
    Toolbar toolbar;
    RecyclerView arenasRV;
    FirebaseRecyclerAdapter adapter;
    FirebaseRecyclerOptions<Arena> options;
    ImageView showAdd;
    ImageView cancelAdd;
    LinearLayout addArenaLL;

    String arenaName;
    String arenaId;
    Arena arena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arenas);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.arenas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        arenaNameET = findViewById(R.id.arena_name_et);
        saveArena = findViewById(R.id.save_arena_image);
        showAdd = findViewById(R.id.show_add_image);
        cancelAdd = findViewById(R.id.back_image);
        addArenaLL = findViewById(R.id.add_arena_ll);

        databaseReferenceArenas = FirebaseDatabase.getInstance().getReference("arenas");

        showAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdd.setVisibility(View.GONE);
                saveArena.setVisibility(View.VISIBLE);
                addArenaLL.setVisibility(View.VISIBLE);
            }
        });

        cancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdd.setVisibility(View.VISIBLE);
                saveArena.setVisibility(View.GONE);
                addArenaLL.setVisibility(View.GONE);
            }
        });

        saveArena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArena();
            }
        });

        arenasRV = findViewById(R.id.arena_recycler_view);
        arenasRV.setHasFixedSize(true);
        arenasRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider));
        arenasRV.addItemDecoration(itemDecorator);
        arenasRV.setLayoutManager(new LinearLayoutManager(this));
        arenasRV.setHasFixedSize(true);

        Query query = databaseReferenceArenas;
        options = new FirebaseRecyclerOptions.Builder<Arena>().setQuery(query, Arena.class).build();
        adapter = new FirebaseRecyclerAdapter<Arena, ArenaViewHolder>(options) {

            @NonNull
            @Override
            public ArenaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.arena_item, parent, false);
                return new ArenaViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ArenaViewHolder holder, int position, @NonNull Arena model) {
                holder.arenaNameTV.setText(model.getName());

                holder.deleteArenaImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ArenasActivity.this);

                        builder.setTitle("Confirm");
                        builder.setMessage("Are you sure?");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog
                                adapter.getRef(position).removeValue();
                                dialog.dismiss();
                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();

                    }
                });

            }
        };

        adapter.startListening();;
        arenasRV.setAdapter(adapter);
    }

    public void addArena(){
        arenaName = arenaNameET.getText().toString();
        if (!TextUtils.isEmpty(arenaName)){
            arenaId = databaseReferenceArenas.push().getKey();
            arena = new Arena(arenaId, arenaName);
            databaseReferenceArenas.child(arenaId).setValue(arena);
            Toast.makeText(this, R.string.arena_added, Toast.LENGTH_LONG).show();
            arenaNameET.setText("");
        } else if (TextUtils.isEmpty(arenaName)){
            Toast.makeText(this, R.string.arena_name_fail, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.arena_not_added, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
