package com.hfad.lzr;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.lzr.adapter.TeamAdapter;
import com.hfad.lzr.model.League;
import com.hfad.lzr.model.Team;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.Line;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class StandingsActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;

    RecyclerView standingsRV;
    ValueEventListener listener;
    Spinner leagueSpinner;
    ArrayAdapter<String> adapterList;
    ArrayList<String> leaguesSpinnerList;
    ArrayList<League> leagues;
    DatabaseReference databaseReference, databaseReferenceLeagues;
    ArrayList<Team> teams;
    TeamAdapter teamAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    String myFilePath;
    FloatingActionButton fab;

    LinearLayout standingsLL;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.standings);

        standingsRV = findViewById(R.id.standingsRV);
        leagueSpinner = findViewById(R.id.choose_league);
        standingsLL = findViewById(R.id.standings_ll);
        fab = findViewById(R.id.sharePdfFab);
       /* share = findViewById(R.id.sharePdf);
        create = findViewById(R.id.createPdf);*/

        myFilePath = "";

        databaseReferenceLeagues = FirebaseDatabase.getInstance().getReference("leagues");

        leagues = new ArrayList<>();

        leaguesSpinnerList = new ArrayList<>();
        adapterList = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, leaguesSpinnerList);
//        adapterList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        leagueSpinner.setAdapter(adapterList);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider));
        standingsRV.addItemDecoration(itemDecorator);
        standingsRV.setLayoutManager(new LinearLayoutManager(this));
        standingsRV.setHasFixedSize(true);

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

        fetchLeagues();

        fetch("Liga A");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPdf();
            }
        });
    }

    private void fetchLeagues() {
        listener = databaseReferenceLeagues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot league : dataSnapshot.getChildren()) {
                    leaguesSpinnerList.add(league.child("name").getValue().toString());
                    leagues.add(league.getValue(League.class));
                }

                adapterList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void createPdf() {

        if (ContextCompat.checkSelfPermission(StandingsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestStoragePermission();
        }
        fab.setVisibility(View.INVISIBLE);
        byte[] bytesA = createImage(standingsLL);
        imageToPDF(bytesA);
        sharePdf();
        fab.setVisibility(View.VISIBLE);
    }

    public byte[] createImage(LinearLayout standingsLL) {
        standingsLL.setDrawingCacheEnabled(true);
        standingsLL.buildDrawingCache();
        Bitmap bm = standingsLL.getDrawingCache();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        return bytes.toByteArray();
    }

    public void imageToPDF(byte[] bytesA) {
        try {
            Document document = new Document();
            String dirpath = getApplicationContext().getCacheDir().toString();

            Date c = Calendar.getInstance().getTime();

            SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy");
            String formattedDate = df.format(c);

            myFilePath = dirpath + "/standings_" + formattedDate + "_" + leagueSpinner.getSelectedItem().toString() + ".pdf";
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
            document.close();
            Toast.makeText(this, "PDF Generated successfully!..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sharePdf() {
        File fileWithinMyDir = new File(myFilePath);
        Uri pdfUri = Uri.fromFile(fileWithinMyDir);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
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

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this).setTitle("Permission needed").setMessage("This permission is needed because of that and that").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(StandingsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                }
            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission NOT GRANTED", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void fetch(String league) {
        databaseReference.orderByChild("league").equalTo(league).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teams.clear();
                for (DataSnapshot dsChild : dataSnapshot.getChildren()) {
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
