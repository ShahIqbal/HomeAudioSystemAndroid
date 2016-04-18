package com.example.shahiqbal.homeaudiosystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.mcgill.ecse321.homeaudiosystem1.controller.HomeAudioSystem1Controller;
import ca.mcgill.ecse321.homeaudiosystem1.controller.InvalidInputException;
import ca.mcgill.ecse321.homeaudiosystem1.model.HAS;
import ca.mcgill.ecse321.homeaudiosystem1.model.Library;
import ca.mcgill.ecse321.homeaudiosystem1.persistence.PersistenceHomeAudioSystem1;
import ca.mcgill.ecse321.homeaudiosystem1.roomcontroller.HomeAudioSystem1RoomController;
import ca.mcgill.ecse321.homeaudiosystem1.roompersistence.RoomPersistenceHomeAudioSystem1;

/**
 * Created by shahiqbal on 2016-04-14.
 */
public class createPlaylist extends AppCompatActivity {

    Library lib;
    HAS has;
    EditText etAddPlaylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_playlist);
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



        RoomPersistenceHomeAudioSystem1.setFileName(getFilesDir().getName() + "roomData.xml");
        RoomPersistenceHomeAudioSystem1.loadHomeAudioSystem1Model();
        has = HAS.getInstance();
        //lib = Library.getInstance();


        //refreshData();
    }

    public void playListAdd(View v){
        etAddPlaylist = (EditText) findViewById(R.id.etAddPlaylist);
        String playlistName = etAddPlaylist.getText().toString();
        TextView tvErrorPlaylist = (TextView) findViewById(R.id.tvErrorPlaylist);
        HomeAudioSystem1RoomController ar = new HomeAudioSystem1RoomController();

        try {
            ar.createPlaylist(playlistName);
            tvErrorPlaylist.setText("");
            Toast.makeText(getBaseContext(), "Playlist Saved", Toast.LENGTH_LONG).show();
            //discussion board has tips for why you are getting error here
        } catch (InvalidInputException e) {
            tvErrorPlaylist.setText(e.getMessage());
        }


    }

    public void goToMainMenu(View v){
        //bMainMenu = (Button) findViewById(R.id.bMainMenu);
        startActivity(new Intent(this, frontClass.class));
    }






}
