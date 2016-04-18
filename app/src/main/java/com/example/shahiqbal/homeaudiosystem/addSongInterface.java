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
import java.util.HashMap;
import java.util.Iterator;

import ca.mcgill.ecse321.homeaudiosystem1.controller.HomeAudioSystem1Controller;
import ca.mcgill.ecse321.homeaudiosystem1.controller.InvalidInputException;
import ca.mcgill.ecse321.homeaudiosystem1.model.Album;
import ca.mcgill.ecse321.homeaudiosystem1.model.Library;
import ca.mcgill.ecse321.homeaudiosystem1.persistence.PersistenceHomeAudioSystem1;

/**
 * Created by shahiqbal on 2016-04-07.
 */
public class addSongInterface extends AppCompatActivity {

    Spinner spinner;
    private HashMap<Integer, Album> albums;
    Library lib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.song);

        PersistenceHomeAudioSystem1.setFileName(getFilesDir().getAbsolutePath() + "homeaudiosystem.xml");
        PersistenceHomeAudioSystem1.loadHomeAudioSystem1Model();
        lib = Library.getInstance();

        refreshingData();


    }
    public void refreshingData(){
        spinner = (Spinner) findViewById(R.id.albumSpinner);
        ArrayAdapter<CharSequence> albumAdapter = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        albumAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        int i = 0;
        this.albums = new HashMap<Integer, Album>();
        for (Iterator<Album> albums = lib.getAlbums().iterator();
             albums.hasNext(); i++) {
            Album a = albums.next();
            albumAdapter.add(a.getAlbumName());
            this.albums.put(i, a);
        }
        spinner.setAdapter(albumAdapter);
    }

    public void addSong(View v){
        EditText etSong1 = (EditText) findViewById(R.id.etSong1);
        EditText etSongDuration = (EditText) findViewById(R.id.etSongDuration);
        String songDuration = etSongDuration.getText().toString();
        int duration = Integer.parseInt(songDuration);
        EditText etAlbumPosition = (EditText) findViewById(R.id.etAlbumPosition);
        String albumPosition = etAlbumPosition.getText().toString();
        int position = Integer.parseInt(albumPosition);
        String song = etSong1.getText().toString();
        HomeAudioSystem1Controller ar = new HomeAudioSystem1Controller();
        TextView tvError = (TextView) findViewById(R.id.tvError);
        String error = "";

        if(song.equals("") || song.trim().length() == 0 || songDuration.equals("") || songDuration.trim().length() == 0 ||
                albumPosition.equals("") || albumPosition.trim().length() == 0){
            //error message for empty album name
            if(song.equals("") || song.trim().length() == 0){
               error = error + "Add song name please!";
                tvError.setText(error);

            }

            if(songDuration.equals("") || songDuration.trim().length() == 0){
                error = error + "Add song duration please";
                tvError.setText(error);
            }

            if(albumPosition.equals("") || albumPosition.trim().length() == 0){
                error = error + "Add position in album please";
                tvError.setText(error);
            }

            } else {
                tvError.setText("");
                try {
                    ar.createSong(song, duration, position, albums.get(spinner.getSelectedItemPosition()));
                    Toast.makeText(getBaseContext(), "Song Saved", Toast.LENGTH_LONG).show();

                //discussion board has tips for why you are getting error here
                 } catch (InvalidInputException e) {
                    tvError.setText(e.getMessage());
                 }

            refreshingData();
            }
    }

    public void goToMainMenu(View v){
        //bMainMenu = (Button) findViewById(R.id.bMainMenu);
        startActivity(new Intent(this, frontClass.class));
    }
}


