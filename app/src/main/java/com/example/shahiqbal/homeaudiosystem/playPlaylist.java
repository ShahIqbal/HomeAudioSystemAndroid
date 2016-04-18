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
import ca.mcgill.ecse321.homeaudiosystem1.model.HAS;
import ca.mcgill.ecse321.homeaudiosystem1.model.Playlist;
import ca.mcgill.ecse321.homeaudiosystem1.roomcontroller.HomeAudioSystem1RoomController;
import ca.mcgill.ecse321.homeaudiosystem1.roommodel.Room;
import ca.mcgill.ecse321.homeaudiosystem1.roompersistence.RoomPersistenceHomeAudioSystem1;

/**
 * Created by shahiqbal on 2016-04-14.
 */
public class playPlaylist extends AppCompatActivity {

    HAS has;
    Spinner sRoomSpinner, sPlaySpinner, sRoomSpinner2;
    private HashMap<Integer, Playlist> playlists;
    private HashMap<Integer, Room> rooms;
    EditText etSetVolume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_playlist);

        RoomPersistenceHomeAudioSystem1.setFileName(getFilesDir().getName() + "roomData.xml");
        RoomPersistenceHomeAudioSystem1.loadHomeAudioSystem1Model();
        has = HAS.getInstance();

        roomRefresh();

    }

    public void roomRefresh(){
        sPlaySpinner = (Spinner) findViewById(R.id.sPlaySpinner);
        ArrayAdapter<CharSequence> playlistAdapter = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        playlistAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        int i = 0;
        this.playlists = new HashMap<Integer, Playlist>();
        playlists.clear();
        playlistAdapter.clear();
        sPlaySpinner.setAdapter(null);

        for (Iterator<Playlist> playlists = has.getPlaylists().iterator();
             playlists.hasNext(); i++) {
            Playlist a = playlists.next();
            playlistAdapter.add(a.getPlaylistName());
            this.playlists.put(i, a);
        }


        sPlaySpinner.setAdapter(playlistAdapter);

        sRoomSpinner = (Spinner) findViewById(R.id.sRoomSpinner);
        ArrayAdapter<CharSequence> roomAdapter = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        playlistAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        int j = 0;
        this.rooms = new HashMap<Integer, Room>();
        rooms.clear();
        roomAdapter.clear();
        sRoomSpinner.setAdapter(null);

        for (Iterator<Room> rooms = has.getRooms().iterator();
             rooms.hasNext(); j++) {
            Room b = rooms.next();
            roomAdapter.add(b.getTypeOfRoom());
            this.rooms.put(j, b);
        }


        sRoomSpinner.setAdapter(roomAdapter);

        sRoomSpinner2 = (Spinner) findViewById(R.id.sRoomSpinner2);
        ArrayAdapter<CharSequence> roomAdapter2 = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        playlistAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        int k = 0;
        this.rooms = new HashMap<Integer, Room>();
        //rooms.clear();
        //roomAdapter.clear();
        //sRoomSpinner.setAdapter(null);

        for (Iterator<Room> rooms = has.getRooms().iterator();
             rooms.hasNext(); k++) {
            Room b = rooms.next();
            roomAdapter2.add(b.getTypeOfRoom());
            this.rooms.put(j, b);
        }


        sRoomSpinner2.setAdapter(roomAdapter2);


    }

    public void playPlaylist(View v){
        if(sRoomSpinner.getSelectedItem() == null || sPlaySpinner.getSelectedItem() == null){
            Toast.makeText(getBaseContext(), "You need to add Room AND playlists", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getBaseContext(), sRoomSpinner.getSelectedItem().toString() + " is now playing the " +
                    sPlaySpinner.getSelectedItem().toString() + " playlist. ", Toast.LENGTH_LONG).show();
        }
    }

    public void volumeSet(View v){
        sRoomSpinner2 = (Spinner) findViewById(R.id.sRoomSpinner2);
        etSetVolume = (EditText) findViewById(R.id.etSetVolume);
        String volume = etSetVolume.getText().toString();
        TextView tvErrorVol = (TextView) findViewById(R.id.tvErrorVol);
        HomeAudioSystem1RoomController ar = new HomeAudioSystem1RoomController();

        int vol = Integer.parseInt(volume);
        /*
        if(vol < 0 || vol > 100){
            tvErrorVol.setText("Volume needs to be between 0 - 100");
        } else {

            Toast.makeText(getBaseContext(), sRoomSpinner2.getSelectedItem().toString() + " volume has been changed to " +
                    volume, Toast.LENGTH_LONG).show();

        }
        */

        if(vol == 0){

            try {
                ar.adjustRoomVolume(vol, true, rooms.get(sRoomSpinner2.getSelectedItemPosition()));
                Toast.makeText(getBaseContext(), sRoomSpinner2.getSelectedItem().toString() + " volume has been changed to " +
                    volume, Toast.LENGTH_LONG).show();

            //discussion board has tips for why you are getting error here
            } catch (InvalidInputException e) {
                tvErrorVol.setText(e.getMessage());
            }
        } else {
            try {
                ar.adjustRoomVolume(vol, false, rooms.get(sRoomSpinner2.getSelectedItemPosition()));
                Toast.makeText(getBaseContext(), sRoomSpinner2.getSelectedItem().toString() + " volume has been changed to " +
                        volume, Toast.LENGTH_LONG).show();

                //discussion board has tips for why you are getting error here
            } catch (InvalidInputException e) {
                tvErrorVol.setText(e.getMessage());
            }
        }
    }

    public void goToMainMenu(View v){
        //bMainMenu = (Button) findViewById(R.id.bMainMenu);
        startActivity(new Intent(this, frontClass.class));
    }



}
