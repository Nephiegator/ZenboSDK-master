package com.asus.robot.onzenbo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.results.RoomInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.robot.asus.robotactivity.RobotActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class dbAlarmManager extends RobotActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "dbAlarmManager";
    private FirebaseFirestore db;
    private TaskAdapter adapter;
    private List<dbReminder> remtasklist;
    private RecyclerView recyclerView;
    private String dbtime;
    private dbReminder tt;
    private TextView mTextView;

    private String sRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainplan);

        remtasklist = new ArrayList<>();
        adapter = new TaskAdapter(this, remtasklist);

        recyclerView = findViewById(R.id.recyclerViewTask);


        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        remtasklist = new ArrayList<>();
        adapter = new TaskAdapter(this, remtasklist);

        recyclerView.setAdapter(adapter);


        tt = new dbReminder();
        db = FirebaseFirestore.getInstance();
        tt = (dbReminder) getIntent().getSerializableExtra("Reminder");

        db.collection("Reminder").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {
                                dbReminder p = d.toObject(dbReminder.class);
                                p.setId(d.getId());
                                remtasklist.add(p);


                            }
                            adapter.notifyDataSetChanged();


                        }
                    }
                });


        mTextView = findViewById(R.id.textView);

    /*    Button buttonTimePicker = findViewById(R.id.setalarmbutton);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });*/

        Button buttonCancelAlarm = findViewById(R.id.cancelbutton);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
        startAlarm(c);
    }

    private void updateTimeText(Calendar c) {
        String timeText = "Alarm set for: " + DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        mTextView.setText(timeText);
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

        ArrayList<RoomInfo> roomInfoArrayList = robotAPI.contacts.room.getAllRoomInfo();
        sRoom = roomInfoArrayList.get(0).keyword;
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        mTextView.setText("Alarm canceled");
    }

    public dbAlarmManager(RobotCallback robotCallback, RobotCallback.Listen robotListenCallback){
        super(robotCallback, robotListenCallback);
    }

}
