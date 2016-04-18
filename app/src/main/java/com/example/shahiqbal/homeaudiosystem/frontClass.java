package com.example.shahiqbal.homeaudiosystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by shahiqbal on 2016-04-08.
 */
public class frontClass extends AppCompatActivity {

    Button bAddAlbum, bAddSongs, bMakePlaylist, bSelectPlaylist, bAddRoom, bVolumeAdjust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_stuff);

    }

    public void openAddAlbum(View v){
        bAddAlbum = (Button) findViewById(R.id.bAddAlbum);
        startActivity(new Intent(frontClass.this, MainActivity.class));
    }

    public void openAddSongs(View v){
        bAddSongs = (Button) findViewById(R.id.bAddSongs);
        startActivity(new Intent(frontClass.this, addSongInterface.class));
    }

    public void openAddRoom(View v){
        bAddRoom = (Button) findViewById(R.id.bAddRoom);
        startActivity(new Intent(frontClass.this, addRoom.class));
    }

    public void openMakePlaylist(View v){
        startActivity(new Intent(frontClass.this, createPlaylist.class));
    }

    public void openSongToPlaylist(View v){
        startActivity(new Intent(frontClass.this, songsToPlaylist.class));
    }

    public void openPlayPlaylist(View v){
        startActivity(new Intent(frontClass.this, playPlaylist.class));
    }


}
