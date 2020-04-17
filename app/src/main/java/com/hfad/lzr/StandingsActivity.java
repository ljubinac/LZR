package com.hfad.lzr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hfad.lzr.adapter.PlayersGameAdapter;
import com.hfad.lzr.adapter.StandingsViewHolder;
import com.hfad.lzr.adapter.TeamAdapter;
import com.hfad.lzr.adapter.TeamViewHolder;
import com.hfad.lzr.model.Team;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class StandingsActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;

    RecyclerView standingsRV;
    FirebaseRecyclerOptions<Team> options;
    FirebaseRecyclerAdapter adapter;
    Spinner leagueSpinner;
    ArrayAdapter<String> adapterList;
    ArrayList<String> leagues;
    DatabaseReference databaseReference;
    ArrayList<Team> teams;
    TeamAdapter teamAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    Button share, create;
    String myFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);

        standingsRV = findViewById(R.id.standingsRV);
        leagueSpinner = findViewById(R.id.choose_league);

        share = findViewById(R.id.sharePdf);
        create = findViewById(R.id.createPdf);

        myFilePath = "";

        leagues = new ArrayList<>();
        leagues.add("Liga A");
        leagues.add("Liga B");
        adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, leagues);
        adapterList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        databaseReference = FirebaseDatabase.getInstance().getReference("teams");

        teams = new ArrayList<>();

        leagueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                fetch(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        fetch("Liga A");

        create.setEnabled(true);
        share.setEnabled(false);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePdf();
            }
        });

    }

    public void sharePdf(){
        File fileWithinMyDir = new File(myFilePath);

        Uri pdfUri = Uri.fromFile(fileWithinMyDir);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            pdfUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", fileWithinMyDir);
        }

        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        intentShareFile.setType("application/pdf");
        intentShareFile.putExtra(Intent.EXTRA_STREAM, pdfUri);

        intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                "Sharing File...");
        intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");

        startActivity(Intent.createChooser(intentShareFile, "Share File"));
    }

    public void createPdf(View view){

        if(ContextCompat.checkSelfPermission(StandingsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestStoragePermission();
        }
        // get view group using reference
        // convert view group to bitmap
        byte[] bytesA = createImage(standingsRV);
        try {
            imageToPDF(bytesA);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        share.setEnabled(true);
    }

    public byte[] createImage(RecyclerView recyclerView){
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.buildDrawingCache();
        Bitmap bm = recyclerView.getDrawingCache();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        return bytes.toByteArray();
    }

    public void imageToPDF(byte[] bytesA) throws FileNotFoundException {
        try {
            Document document = new Document();
            String dirpath = android.os.Environment.getExternalStorageDirectory().toString();
            myFilePath = dirpath + "/newPdf.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(myFilePath)); //  Change pdf's name.
            document.open();
            Image imgA = Image.getInstance(bytesA);
            float scalerA = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / imgA.getWidth()) * 100;
            imgA.scalePercent(scalerA);

            //imgA.setSpacingAfter(50f);
            //imgA.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
           // document.add(new Paragraph(game.getGameDate() + " " + game.getTeamAnaziv() + " vs. " + game.getTeamBnaziv()));
           // document.add(Chunk.NEWLINE);
           // document.add(new Paragraph(game.getTeamAnaziv()));
            document.add(imgA);
           // document.add(Chunk.NEWLINE);

           /* Image imgB = Image.getInstance(bytesB);
            float scalerB = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / imgB.getWidth()) * 100;
            imgB.scalePercent(scalerB);
            //imgB.setPaddingTop(20f);
            //imgB.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_CENTER);
            //imgB.setSpacingAfter();
            document.add(new Paragraph(game.getTeamBnaziv()));
            document.add(imgB);*/

            document.close();
            Toast.makeText(this, "PDF Generated successfully!..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this).setTitle("Permission needed").setMessage("This permission is needed because of that and that").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(StandingsActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                }
            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        } else{
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission NOT GRANTED", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void fetch(String league){
        databaseReference.orderByChild("league").equalTo(league).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teams.clear();
                for (DataSnapshot dsChild : dataSnapshot.getChildren()){
                    Team team = dsChild.getValue(Team.class);
                    teams.add(team);
                }

                Collections.sort(teams);

                standingsRV.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                teamAdapter = new TeamAdapter(teams);
                standingsRV.setLayoutManager(mLayoutManager);
                standingsRV.setAdapter(teamAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /*private void fetch(String league){

        Query query = FirebaseDatabase.getInstance().getReference().child("teams").orderByChild("league").equalTo(league);

        options = new FirebaseRecyclerOptions.Builder<Team>().setQuery(query, Team.class).build();

        adapter = new FirebaseRecyclerAdapter<Team, StandingsViewHolder>(options) {
            @NonNull
            @Override
            public StandingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.standings_item, parent, false);
                return  new StandingsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull StandingsViewHolder holder, final int position, @NonNull final Team model) {
                holder.teamNameTV.setText(model.getName());
                holder.ptsPlusTV.setText(String.valueOf(model.getPointsScored()));
                holder.ptsMinusTV.setText(String.valueOf(model.getPointsReceived()));

                holder.teamNameTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(StandingsActivity.this, TeamActivity.class);
                        intent.putExtra("team_name", model.getName());
                        intent.putExtra("idTeam", model.getId());
                        startActivity(intent);
                    }
                });
            }
        };
        adapter.startListening();
        standingsRV.setAdapter(adapter);
    }*/

}
