package com.hfad.lzr.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.hfad.lzr.LineupActivity;
import com.hfad.lzr.R;
import com.hfad.lzr.adapter.GameViewHolder;
import com.hfad.lzr.model.Game;

/**
 * A simple {@link Fragment} subclass.
 */
public class GamesFragment extends Fragment {

    DatabaseReference databaseReference;
    RecyclerView gamesRV;
    FirebaseRecyclerOptions<Game> options;
    FirebaseRecyclerAdapter adapter;

    Game game;

    boolean isFinished;

    public GamesFragment() {
        // Required empty public constructor
    }

    public static GamesFragment newInstance(boolean recent){
        GamesFragment gamesFragment = new GamesFragment();

        Bundle args = new Bundle();
        args.putBoolean("recent", recent);
        gamesFragment.setArguments(args);
        return gamesFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isFinished = getArguments().getBoolean("recent");
        game = ( Game ) getArguments().getSerializable("game");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_games, container, false);

        gamesRV = root.findViewById(R.id.games_RV);

        gamesRV.setLayoutManager(new LinearLayoutManager(getContext()));
        gamesRV.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("games");

        Query query = databaseReference.orderByChild("finished").equalTo(isFinished);

        options = new FirebaseRecyclerOptions.Builder<Game>().setQuery(query, Game.class).build();
        adapter = new FirebaseRecyclerAdapter<Game, GameViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull GameViewHolder holder, int position, @NonNull final Game model) {
                holder.teamAnameTV.setText(model.getTeamAnaziv());
                holder.teamBnameTV.setText(model.getTeamBnaziv());
                holder.gameDateTV.setText(model.getGameDate());
                holder.gameTimeTV.setText(model.getGameTime());
                holder.gameVenueTV.setText(model.getGameArenaName());
                if(model.isFinished()) {
                    holder.resAtv.setText(String.valueOf(model.getResA()));
                    holder.resBtv.setText(String.valueOf(model.getResB()));
                    holder.dateTimeHallLL.setBackground(getResources().getDrawable(R.drawable.game_item_recent_lower));
                }

                if(!model.isFinished()) {
                    holder.upcomingGameLL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), LineupActivity.class);
                            intent.putExtra("game", model);
                            getActivity().startActivity(intent);
                        }
                    });
                }
            }

            @NonNull
            @Override
            public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
                return  new GameViewHolder(view);
            }
        };
        adapter.startListening();
        gamesRV.setAdapter(adapter);

        return root;
    }
}
