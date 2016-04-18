package com.example.shahiqbal.homeaudiosystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;

import ca.mcgill.ecse321.homeaudiosystem1.controller.HomeAudioSystem1Controller;
import ca.mcgill.ecse321.homeaudiosystem1.controller.InvalidInputException;
import ca.mcgill.ecse321.homeaudiosystem1.model.Album;
import ca.mcgill.ecse321.homeaudiosystem1.model.Artist;
import ca.mcgill.ecse321.homeaudiosystem1.model.Library;
import ca.mcgill.ecse321.homeaudiosystem1.persistence.PersistenceHomeAudioSystem1;

public class MainActivity extends AppCompatActivity{
    Button bAddAlbum, bAddSongs, bMainMenu;
    EditText etAlbumName, etAddYear, etAddGenre, etAddArtist, etAlbumSongs, etAlbumPosition;
    TextView tvErrorMessage;
    public final static String EXTRA_MESSAGE = "com.shahiqbal";
    Library lib;
    Spinner spinner, spinner1;
//    public final static String EXTRA_MESSAGE_2 = "com.shahiqbal";

    String albumName, albumGenre, albumYear, artistName, tvError, albumPosition, numberOfSongs, error;
    private HashMap<Integer, Artist> artists;
    //private HashMap<Integer, Album> albums;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */



        PersistenceHomeAudioSystem1.setFileName(getFilesDir().getAbsolutePath() + "homeaudiosystem.xml");
        PersistenceHomeAudioSystem1.loadHomeAudioSystem1Model();
        lib = Library.getInstance();


        refreshData();





    }

    public void refreshData(){

        spinner1 = (Spinner) findViewById(R.id.artistspinner);
        ArrayAdapter<CharSequence> artistAdapter = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        artistAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        int j = 0;
        this.artists = new HashMap<Integer, Artist>();
        //artists.clear();
        //artistAdapter.clear();
        //spinner1.setAdapter(null);

        for (Iterator<Artist> artists = lib.getArtists().iterator();
             artists.hasNext(); j++) {
            Artist b = artists.next();
            artistAdapter.add(b.getArtistName());
            this.artists.put(j, b);
        }


        spinner1.setAdapter(artistAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);


    }



    public void addArtistStuff(View v){
        EditText etAddArtist = (EditText) findViewById(R.id.etAddArtist);
        String artistName = etAddArtist.getText().toString();
        TextView tvErrorMessage = (TextView) findViewById(R.id.tv);
        HomeAudioSystem1Controller ar = new HomeAudioSystem1Controller();

        try {
            tvErrorMessage.setText("");
            ar.createArtist(artistName);
            Toast.makeText(getBaseContext(), "Artist Saved", Toast.LENGTH_LONG).show();
            //discussion board has tips for why you are getting error here
        } catch (InvalidInputException e) {
            tvErrorMessage.setText(e.getMessage());
        }

        refreshData();


    }


    public void saveData(View v) {
        //initialize all the edit-texts, textviews and buttons by id
        bAddAlbum = (Button) findViewById(R.id.bAddAlbum);
        etAlbumName = (EditText) findViewById(R.id.etAlbumName);
        //etAddYear = (EditText) findViewById(R.id.etAddYear);
        etAddGenre = (EditText) findViewById(R.id.etAddGenre);
        etAddArtist = (EditText) findViewById(R.id.etAddArtist);
        tvErrorMessage = (TextView) findViewById(R.id.tv);
        TextView tvAlbumDate = (TextView) findViewById(R.id.tvAlbumDate);
        etAlbumSongs = (EditText) findViewById(R.id.etAlbumSongs);
        etAlbumPosition = (EditText) findViewById(R.id.etAlbumPosition);

        //convert user input to string
        albumName = etAlbumName.getText().toString();
        albumGenre = etAddGenre.getText().toString();
        //albumYear = etAddYear.getText().toString();
        albumPosition = etAlbumPosition.getText().toString();
        int albumPositionLibrary = Integer.parseInt(albumPosition);
        numberOfSongs = etAlbumSongs.getText().toString();
        int songNumbers = Integer.parseInt(numberOfSongs);
        artistName = etAddArtist.getText().toString();
        tvError = tvErrorMessage.getText().toString();
        tvErrorMessage.setTextSize(20);


        error = ""; //error message initializer


        Bundle dateBundle = getDateFromLabel(tvAlbumDate.getText());
        Date eventDate = dateBundle(dateBundle);
        //Artist a = new Artist(artistName);


        //et1 = (EditText) findViewById(R.id.et1);
        HomeAudioSystem1Controller ar = new HomeAudioSystem1Controller();
        //TextView errorMessage = (TextView) findViewById(R.id.tvErrorMessage);

        try {
                ar.createAlbum(eventDate, albumGenre, songNumbers, albumName, albumPositionLibrary, artists.get(spinner1.getSelectedItemPosition()));
                Toast.makeText(getBaseContext(), "Album Saved", Toast.LENGTH_LONG).show();
                //discussion board has tips for why you are getting error here
        } catch (InvalidInputException e) {
                tvErrorMessage.setText(e.getMessage());
        }


            refreshData();
        }


    public void goToAddSongs(View v){
        bAddSongs = (Button) findViewById(R.id.bSong);
        startActivity(new Intent(MainActivity.this, addSongInterface.class));
    }
    public void goToMainMenu(View v){
        //bMainMenu = (Button) findViewById(R.id.bMainMenu);
        startActivity(new Intent(MainActivity.this, frontClass.class));
    }

    public Date dateBundle (Bundle inputDate) {
        int unbundleDay = inputDate.getInt("day");
        int unbundleMonth = inputDate.getInt("month");
        int unbundleYear = inputDate.getInt("year");

        Date date = new Date(unbundleDay, unbundleMonth, unbundleYear);
        return date;
    }

    private Bundle getDateFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split("-");
        int day = 1;
        int month = 1;
        int year = 1;
        if (comps.length == 3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }
        rtn.putInt("day", day);
        rtn.putInt("month", month-1);
        rtn.putInt("year", year);
        return rtn;
    }

    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", d, m + 1, y));
    }

    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText());
        args.putInt("id", v.getId());
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
