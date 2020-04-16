package com.hfad.lzr;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.lzr.adapter.TeamAdapter;
import com.hfad.lzr.model.Team;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class StandingsActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;

    RecyclerView standingsRV;
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
        adapterList = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, leagues);
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

        byte[] bytesA = createImage(standingsRV);
        imageToPDF(bytesA);
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

    public void imageToPDF(byte[] bytesA) {
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
            document.add(imgA);
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

}
