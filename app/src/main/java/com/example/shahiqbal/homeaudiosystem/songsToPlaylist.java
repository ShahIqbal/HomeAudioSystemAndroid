package com.example.shahiqbal.homeaudiosystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;

import ca.mcgill.ecse321.homeaudiosystem1.controller.InvalidInputException;
import ca.mcgill.ecse321.homeaudiosystem1.model.Album;
import ca.mcgill.ecse321.homeaudiosystem1.model.Artist;
import ca.mcgill.ecse321.homeaudiosystem1.model.HAS;
import ca.mcgill.ecse321.homeaudiosystem1.model.Library;
import ca.mcgill.ecse321.homeaudiosystem1.model.Playlist;
import ca.mcgill.ecse321.homeaudiosystem1.model.Song;
import ca.mcgill.ecse321.homeaudiosystem1.persistence.PersistenceHomeAudioSystem1;
import ca.mcgill.ecse321.homeaudiosystem1.roomcontroller.HomeAudioSystem1RoomController;
import ca.mcgill.ecse321.homeaudiosystem1.roompersistence.RoomPersistenceHomeAudioSystem1;

/**
 * Created by shahiqbal on 2016-04-14.
 */
public class songsToPlaylist extends AppCompatActivity {


    HAS has;
    Library lib;
    Spinner sSongSpinner, sPlaylistSpinner;
    private HashMap<Integer, Song> songs;
    private HashMap<Integer, Playlist> playlists;
    private HashMap<Integer, Artist> artists;
    TextView tvErrorSTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.songs_to_playlist);

//        RoomPersistenceHomeAudioSystem1.setFileName(getFilesDir().getName() + "roomData.xml");
//        RoomPersistenceHomeAudioSystem1.loadHomeAudioSystem1Model();
//        has = HAS.getInstance();
/*
        PersistenceHomeAudioSystem1.setFileName(getFilesDir().getAbsolutePath() + "homeaudiosystem.xml");
        PersistenceHomeAudioSystem1.loadHomeAudioSystem1Model();
        lib = Library.getInstance();
*/

        refreshPlaylist();
        refreshSong();
    }

    public void refreshPlaylist(){

        RoomPersistenceHomeAudioSystem1.setFileName(getFilesDir().getName() + "roomData.xml");
        RoomPersistenceHomeAudioSystem1.loadHomeAudioSystem1Model();
        has = HAS.getInstance();

        sPlaylistSpinner = (Spinner) findViewById(R.id.sPlaylistSpinner);
        ArrayAdapter<CharSequence> playlistAdapter = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        playlistAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        int i = 0;
        this.playlists = new HashMap<Integer, Playlist>();
        playlists.clear();
        playlistAdapter.clear();
        sPlaylistSpinner.setAdapter(null);

        for (Iterator<Playlist> playlists = has.getPlaylists().iterator();
             playlists.hasNext(); i++) {
            Playlist a = playlists.next();
            playlistAdapter.add(a.getPlaylistName());
            this.playlists.put(i, a);
        }


        sPlaylistSpinner.setAdapter(playlistAdapter);


/*

        sSongSpinner = (Spinner) findViewById(R.id.sSongSpinner);
        ArrayAdapter<CharSequence> songAdapter = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        songAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        int j = 0;
        this.songs = new HashMap<Integer, Song>();
        //songs.clear();
        //songAdapter.clear();
        //sSongSpinner.setAdapter(null);

        for (Iterator<Song> songs = lib.getSongs().iterator();
             songs.hasNext(); j++) {
            Song b = songs.next();
            songAdapter.add(b.getSongName());
            this.songs.put(j, b);
        }


        sSongSpinner.setAdapter(songAdapter);

        */
    }

    public void refreshSong() {
        PersistenceHomeAudioSystem1.setFileName(getFilesDir().getAbsolutePath() + "homeaudiosystem.xml");
        PersistenceHomeAudioSystem1.loadHomeAudioSystem1Model();
        lib = Library.getInstance();

        sSongSpinner = (Spinner) findViewById(R.id.sSongSpinner);
        ArrayAdapter<CharSequence> songAdapter = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        songAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        int j = 0;
        //int k = 0;
        this.songs = new HashMap<Integer, Song>();
        //this.artists = new HashMap<Integer, Artist>();
        //songs.clear();
        //songAdapter.clear();
        //sSongSpinner.setAdapter(null);

            for (Iterator<Song> songs = lib.getSongs().iterator();
                 songs.hasNext(); j++) {

                Song b = songs.next();
                songAdapter.add(b.getSongName());
                this.songs.put(j, b);
            }



            sSongSpinner.setAdapter(songAdapter);

    }

    public void songToPlay(View v){
        //etAddRoom = (EditText) findViewById(R.id.etAddRoom);
        //String room = etAddRoom.getText().toString();
        tvErrorSTP = (TextView) findViewById(R.id.tvErrorSTP);



        HomeAudioSystem1RoomController rc = new HomeAudioSystem1RoomController();

        try {
            rc.addSongToPlaylist(playlists.get(sPlaylistSpinner.getSelectedItemPosition()),
                    songs.get(sSongSpinner.getSelectedItemPosition()));
            tvErrorSTP.setText("");
            Toast.makeText(getBaseContext(), "Song Added To Playlist", Toast.LENGTH_LONG).show();
            //discussion board has tips for why you are getting error here
        } catch (InvalidInputException e) {
            tvErrorSTP.setText(e.getMessage());
        }


    }

    public void goToMainMenu(View v){
        //bMainMenu = (Button) findViewById(R.id.bMainMenu);
        startActivity(new Intent(this, frontClass.class));
    }
}



