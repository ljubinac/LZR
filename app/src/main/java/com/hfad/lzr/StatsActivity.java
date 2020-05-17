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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.hfad.lzr.model.Game;
import com.hfad.lzr.model.PlayerGame;
import com.hfad.lzr.ui.main.StatsFragment;
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
import java.util.List;

public class StatsActivity extends AppCompatActivity {


    private int STORAGE_PERMISSION_CODE = 1;

    ArrayList<PlayerGame> playersGameA, playersGameB;

    TableLayout tableTeamA, tableTeamB;

    LinearLayout ll;

    String myFilePath;

    Game game;

    Toolbar toolbar;

    ViewPager viewPager;
    TabLayout tabLayout;

    StatsFragment statsFragmentA, statsFragmentB;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        playersGameA = (ArrayList<PlayerGame>) getIntent().getSerializableExtra("playersGameA");
        playersGameB = (ArrayList<PlayerGame>) getIntent().getSerializableExtra("playersGameB");
        game = (Game) getIntent().getSerializableExtra("game");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.game_stats);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);

        fab = findViewById(R.id.sharePdfFab);

        tabLayout.setupWithViewPager(viewPager, true);
        tabLayout.setSelected(true);

        tabLayout.setTabTextColors(getResources().getColor(R.color.tab_not_selected),
                getResources().getColor(R.color.white));

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.orange_start));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        statsFragmentA = StatsFragment.newInstance(playersGameA);
        statsFragmentB = StatsFragment.newInstance(playersGameB);
        viewPagerAdapter.addFragment(statsFragmentA, game.getTeamAnaziv());
        viewPagerAdapter.addFragment(statsFragmentB, game.getTeamBnaziv());
        viewPager.setAdapter(viewPagerAdapter);

        ll = findViewById(R.id.ll);

        myFilePath = "";

        /*share = findViewById(R.id.sharePdf);
        create = findViewById(R.id.createPdf);

        create.setEnabled(true);
        share.setEnabled(false);*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPdf();
            }
        });

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private  List<String> fragmentTitle = new ArrayList<>();
        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
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

    public void createPdf() {

        if (ContextCompat.checkSelfPermission(StatsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestStoragePermission();
        }
        // get view group using reference
        // convert view group to bitmap
        fab.setVisibility(View.INVISIBLE);
        tableTeamA = statsFragmentA.getTable();
        tableTeamB = statsFragmentB.getTable();
        byte[] bytesA = createImage(tableTeamA);
        byte[] bytesB = createImage(tableTeamB);
        try {
            imageToPDF(bytesA, bytesB);
            sharePdf();
            fab.setVisibility(View.VISIBLE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public byte[] createImage(TableLayout table) {
        table.setDrawingCacheEnabled(true);
        table.buildDrawingCache();
        Bitmap bm = table.getDrawingCache();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        return bytes.toByteArray();
    }

    public void imageToPDF(byte[] bytesA, byte[] bytesB) throws FileNotFoundException {
        try {
            Document document = new Document();
            String dirpath = android.os.Environment.getExternalStorageDirectory().toString();
            myFilePath = dirpath + "/" + game.getGameDate() + "_" + game.getTeamAnaziv() + "_" + game.getTeamBnaziv() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(myFilePath)); //  Change pdf's name.
            document.open();
            Image imgA = Image.getInstance(bytesA);
            float scalerA = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / imgA.getWidth()) * 100;
            imgA.scalePercent(scalerA);

            //imgA.setSpacingAfter(50f);
            //imgA.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(new Paragraph(game.getGameDate() + " " + game.getTeamAnaziv() + " vs. " + game.getTeamBnaziv()));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph(game.getTeamAnaziv()));
            document.add(imgA);
            document.add(Chunk.NEWLINE);

            Image imgB = Image.getInstance(bytesB);
            float scalerB = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / imgB.getWidth()) * 100;
            imgB.scalePercent(scalerB);
            //imgB.setPaddingTop(20f);
            //imgB.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_CENTER);
            //imgB.setSpacingAfter();
            document.add(new Paragraph(game.getTeamBnaziv()));
            document.add(imgB);

            document.close();
            Toast.makeText(this, "PDF Generated successfully!..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this).setTitle("Permission needed").setMessage("This permission is needed because of that and that").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(StatsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}