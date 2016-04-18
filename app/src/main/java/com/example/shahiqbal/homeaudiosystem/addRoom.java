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
import ca.mcgill.ecse321.homeaudiosystem1.model.Artist;
import ca.mcgill.ecse321.homeaudiosystem1.model.HAS;
import ca.mcgill.ecse321.homeaudiosystem1.persistence.PersistenceHomeAudioSystem1;
import ca.mcgill.ecse321.homeaudiosystem1.roomcontroller.HomeAudioSystem1RoomController;
import ca.mcgill.ecse321.homeaudiosystem1.roommodel.Room;
import ca.mcgill.ecse321.homeaudiosystem1.roompersistence.RoomPersistenceHomeAudioSystem1;

/**
 * Created by shahiqbal on 2016-04-08.
 */

public class addRoom extends AppCompatActivity {

    EditText etAddRoom;
    HAS has;
    TextView tvErrorRoom;
    Spinner roomspinner;
    private HashMap<Integer, Room> rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.room);

        RoomPersistenceHomeAudioSystem1.setFileName(getFilesDir().getName() + "roomData.xml");
        RoomPersistenceHomeAudioSystem1.loadHomeAudioSystem1Model();
        has = HAS.getInstance();

        dataRefresh();

    }

    public void dataRefresh(){
        roomspinner = (Spinner) findViewById(R.id.roomspinner);
        ArrayAdapter<CharSequence> roomAdapter = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        roomAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        int j = 0;
        this.rooms = new HashMap<Integer, Room>();
        //rooms.clear();
        //roomAdapter.clear();
        //roomspinner.setAdapter(null);

        for (Iterator<Room> rooms = has.getRooms().iterator();
             rooms.hasNext(); j++) {
            Room b = rooms.next();
            roomAdapter.add(b.getTypeOfRoom());
            this.rooms.put(j, b);
        }


        roomspinner.setAdapter(roomAdapter);
    }

    public void roomAdd(View v){
        etAddRoom = (EditText) findViewById(R.id.etAddRoom);
        String room = etAddRoom.getText().toString();
        tvErrorRoom = (TextView) findViewById(R.id.tvErrorRoom);



        HomeAudioSystem1RoomController rc = new HomeAudioSystem1RoomController();

        try {
            rc.createRoom(room);
            tvErrorRoom.setText("");
            Toast.makeText(getBaseContext(), "Room Saved", Toast.LENGTH_LONG).show();
            //discussion board has tips for why you are getting error here
        } catch (InvalidInputException e) {
            tvErrorRoom.setText(e.getMessage());
        }

        dataRefresh();

    }

    public void goToMainMenu(View v){
        //bMainMenu = (Button) findViewById(R.id.bMainMenu);
        startActivity(new Intent(this, frontClass.class));
    }
}
