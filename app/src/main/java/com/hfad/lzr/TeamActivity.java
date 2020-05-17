package com.hfad.lzr;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hfad.lzr.adapter.PlayerViewHolder;
import com.hfad.lzr.model.Player;
import com.hfad.lzr.model.Team;

public class TeamActivity extends AppCompatActivity {

    DatabaseReference databaseReference, databaseReferencePlayers;
    TextView teamNameTV, teamLeagueTV;
    LinearLayout teamNameLL1, teamNameLL2, leagueLL1, leagueLL2, addPlayerLL;
    EditText teamNameET, playerNumberET, playerNameET;
    ImageView editTeamName, addPlayer, editPlayer, deletePlayer, showAdd, backAdd;
    String teamName, idTeam;
    Spinner leagueSpinner;
    RecyclerView playersRV;
    FirebaseRecyclerOptions<Player> options;
    FirebaseRecyclerAdapter adapter;
    Toolbar toolbar;
    View divider;
    Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.team);

        divider = findViewById(R.id.divider);
        teamNameTV = findViewById(R.id.teamNameTV);
        teamLeagueTV = findViewById(R.id.teamLeagueTV);
        teamNameLL1 = findViewById(R.id.teamNameLL1);
        editTeamName = findViewById(R.id.image_editTeamName);
        teamName = getIntent().getStringExtra("team_name");

        leagueSpinner = findViewById(R.id.choose_league);

        idTeam = getIntent().getStringExtra("idTeam");
        playerNumberET = findViewById(R.id.player_number_edt);
        playerNameET = findViewById(R.id.player_name_edt);
        addPlayer = findViewById(R.id.add_image);
        playersRV = findViewById(R.id.recyclerView);
        editPlayer = findViewById(R.id.image_edit);
        deletePlayer = findViewById(R.id.image_delete);
        showAdd = findViewById(R.id.show_add_image);
        addPlayerLL = findViewById(R.id.add_player_ll);
        backAdd = findViewById(R.id.back_image);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider));
        playersRV.addItemDecoration(itemDecorator);
        playersRV.setLayoutManager(new LinearLayoutManager(this));
        playersRV.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("teams");

        databaseReferencePlayers = FirebaseDatabase.getInstance().getReference("players");

        databaseReference.orderByChild("name").equalTo(teamName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsChild : dataSnapshot.getChildren()) {
                    team = dsChild.getValue(Team.class);
                    idTeam = dsChild.getKey();
                    teamNameTV.setText(team.getName());
                    teamLeagueTV.setText(team.getLeague());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        showAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdd.setVisibility(View.GONE);
                addPlayer.setVisibility(View.VISIBLE);
                addPlayerLL.setVisibility(View.VISIBLE);
                divider.setVisibility(View.VISIBLE);
            }
        });

        backAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdd.setVisibility(View.VISIBLE);
                addPlayer.setVisibility(View.GONE);
                addPlayerLL.setVisibility(View.GONE);
                divider.setVisibility(View.GONE);
            }
        });

        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlayer();
                playersRV = findViewById(R.id.recyclerView);
                playersRV.setHasFixedSize(true);
                playersRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                fetch(idTeam);
                showAdd.setVisibility(View.VISIBLE);
                addPlayer.setVisibility(View.GONE);
                addPlayerLL.setVisibility(View.GONE);

            }
        });

        playersRV.setLayoutManager(new LinearLayoutManager(this));
        playersRV.setHasFixedSize(true);
        fetch(idTeam);

       /* editTeamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTeamName();
            }
        });*/

   /*     acceptTeamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTeamName();
            }
        });*/

  /*      editLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLeague();
            }
        });*/

        /*acceptLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptChanges();
            }
        });*/
    }


    private void addPlayer() {
        String number = playerNumberET.getText().toString();
        String name = playerNameET.getText().toString();
        if (!TextUtils.isEmpty(number) || !TextUtils.isEmpty(name)) {
            String id = databaseReferencePlayers.push().getKey();
            Player player = new Player(id, name, number, idTeam, team.getLeague());
            player.setId(id);
            player.setTeamId(idTeam);
            databaseReferencePlayers.child(id).setValue(player);
            playerNumberET.setText("");
            playerNameET.setText("");

            Toast.makeText(this, R.string.player_added, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.player_not_added, Toast.LENGTH_LONG).show();
        }
    }


    private void fetch(final String idTeam) {

        final Query query = FirebaseDatabase.getInstance().getReference("players").orderByChild("teamId").equalTo(idTeam);

        options = new FirebaseRecyclerOptions.Builder<Player>().setQuery(query, Player.class).build();

        adapter = new FirebaseRecyclerAdapter<Player, PlayerViewHolder>(options) {

            @NonNull
            @Override
            public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
                return new PlayerViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final PlayerViewHolder holder, final int position, @NonNull final Player model) {
                holder.playerNumberTV.setText(model.getNumber());
                holder.playerNameTV.setText(model.getNameAndLastname());

                holder.playerDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(TeamActivity.this);

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
                holder.playerEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.playerNumberET.setText(holder.playerNumberTV.getText().toString());
                        holder.playerNameET.setText(holder.playerNameTV.getText().toString());
                        holder.playerLL1.setVisibility(View.GONE);
                        holder.playerLL2.setVisibility(View.VISIBLE);
                    }
                });

                holder.playerAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        holder.playerNumberTV.setText(holder.playerNumberET.getText().toString());
                        holder.playerNameTV.setText(holder.playerNameET.getText().toString());
                        holder.playerLL1.setVisibility(View.VISIBLE);
                        holder.playerLL2.setVisibility(View.GONE);

                        databaseReferencePlayers.child(model.getId()).child("number").setValue(holder.playerNumberTV.getText().toString());
                        databaseReferencePlayers.child(model.getId()).child("nameAndLastname").setValue(holder.playerNameTV.getText().toString());

                    }
                });

                holder.cancelEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.playerLL1.setVisibility(View.VISIBLE);
                        holder.playerLL2.setVisibility(View.GONE);
                    }
                });
            }
        };
        adapter.startListening();
        playersRV.setAdapter(adapter);
    }

    public void editLeague() {
        leagueLL1.setVisibility(View.INVISIBLE);
        leagueLL2.setVisibility(View.VISIBLE);
    }

    public void acceptChanges() {
        teamLeagueTV.setText(leagueSpinner.getSelectedItem().toString());
        leagueLL1.setVisibility(View.VISIBLE);
        leagueLL2.setVisibility(View.INVISIBLE);

        databaseReference.child(idTeam).child("league").setValue(teamLeagueTV.getText().toString());
    }

    public void editTeamName() {
        teamNameET.setText(teamNameTV.getText().toString());
        teamNameLL1.setVisibility(View.INVISIBLE);
        teamNameLL2.setVisibility(View.VISIBLE);
    }

    public void saveTeamName() {

        teamNameTV.setText(teamNameET.getText().toString());
        teamNameLL1.setVisibility(View.VISIBLE);
        teamNameLL2.setVisibility(View.GONE);

        databaseReference.child(idTeam).child("name").setValue(teamNameTV.getText().toString());

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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