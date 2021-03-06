package com.example.evergreensongs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    private ListView listView;
    private ImageView play_btn, nxt_btn, bck_btn;
    ArrayList<String> musicName;
    ArrayList<Integer> music;
    MediaPlayer mediaPlayer;
    int currentPosition;
//    private SeekBar seekBar;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences("startMusic", MODE_PRIVATE);
        currentPosition = sharedPreferences.getInt("position", 0);

        createNotification(0);

        music = new ArrayList<>();
        musicName = new ArrayList<>();

        listView = (ListView) findViewById(R.id.list_view);
        play_btn = (ImageView) findViewById(R.id.play_button);
        nxt_btn = (ImageView) findViewById(R.id.nxt_button);
        bck_btn = (ImageView) findViewById(R.id.bck_button);
//      seekBar=(SeekBar) findViewById(R.id.seekbar);

        AudioManager manager= (AudioManager) getSystemService(AUDIO_SERVICE);

        if (manager != null) {
            manager.setStreamVolume(AudioManager.STREAM_MUSIC,manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);
        }

        musicName.add("Ambadi_Payyukal");
        musicName.add("Maranittum_Enthino");
        musicName.add("Pranaya_Saugandikam");
        musicName.add("viral_thottal");
        musicName.add("ennum_ninne_poojikam");
        musicName.add("dooreyoru_tharam");
        musicName.add("vennila_chandhana_kinnam");
        musicName.add("ethrayoo_janmamayi");
        musicName.add("vennila_kombile_rapadi");
        musicName.add("nadodi_poonthinkal");
        musicName.add("onnu_thodanullil");
        musicName.add("kanumpol_parayamo");
        musicName.add("mele_vinninmutatharoo");
        musicName.add("kannil_kannil_minnum");
        musicName.add("aroral_pularmazhayil");
        musicName.add("swayam_vara_chandrikee");
        musicName.add("padii_thodiyilethoo");

        music.add(R.raw.ambadi_payyukal);
        music.add(R.raw.maranittum_enthino);
        music.add(R.raw.pranaya_saugandikam);
        music.add(R.raw.viral_thottal);
        music.add(R.raw.ennum_ninne_poojikam);
        music.add(R.raw.dooreyoru_tharam);
        music.add(R.raw.vennila_chandhana_kinnam);
        music.add(R.raw.ethrayoo_janmamayi);
        music.add(R.raw.vennila_kombile_rapadi);
        music.add(R.raw.nadodi_poonthinkal);
        music.add(R.raw.onnu_thodanullil);
        music.add(R.raw.kanumpol_parayamo);
        music.add(R.raw.mele_vinninmutatharoo);
        music.add(R.raw.kannil_kannil_minnum);
        music.add(R.raw.aroral_pularmazhayil);
        music.add(R.raw.swayam_vara_chandrikee);
        music.add(R.raw.padii_thodiyilethoo);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, musicName);
        listView.setAdapter(arrayAdapter);

//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                if(mediaPlayer!=null && fromUser)
//                {
//                    mediaPlayer.seekTo(progress*1000);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

        if(savedInstanceState==null) {
            mediaPlayer = MediaPlayer.create(this, music.get(currentPosition));
            mediaPlayer.start();
            play_btn.setImageResource(R.drawable.pause_icon);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                currentPosition = position;
                changePrefValue(currentPosition);


                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(HomeActivity.this, music.get(position));
//                    seekBar.setVisibility(View.VISIBLE);
//                    seekBar.setProgress(0);
                    mediaPlayer.start();
                    play_btn.setImageResource(R.drawable.pause_icon);
//                    gotoSeek();
                    musicCompleted();
                } else {

                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), music.get(position));
                    mediaPlayer.setVolume(100, 100);
                    mediaPlayer.start();
                    musicCompleted();
                }
            }
        });


        bck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentPosition == 0) {

                    changePrefValue(currentPosition);// music is at the beginning
                    currentPosition = music.size() - 1;
                } else {

                    currentPosition -= 1;
                    changePrefValue(currentPosition);
                }

                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(HomeActivity.this, music.get(currentPosition));
                    mediaPlayer.start();
                    play_btn.setImageResource(R.drawable.pause_icon);
                    musicCompleted();
                } else {

                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), music.get(currentPosition));
                    mediaPlayer.setVolume(100, 100);
                    play_btn.setImageResource(R.drawable.pause_icon);
                    mediaPlayer.start();
                    musicCompleted();
                }


            }
        });


        nxt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentPosition == music.size() - 1) {
                    currentPosition = 0;
                    changePrefValue(currentPosition);
                } else {
                    currentPosition += 1;
                    changePrefValue(currentPosition);
                }
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(HomeActivity.this, music.get(currentPosition));
                    mediaPlayer.setVolume(100, 100);
                    mediaPlayer.start();
                    play_btn.setImageResource(R.drawable.pause_icon);
                    musicCompleted();
                } else {

                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), music.get(currentPosition));
                    mediaPlayer.setVolume(100, 100);
                    play_btn.setImageResource(R.drawable.pause_icon);
                    mediaPlayer.start();
                    musicCompleted();

                }

            }
        });


        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        play_btn.setImageResource(R.drawable.play_icon);
                    } else {
                        mediaPlayer.start();
                        play_btn.setImageResource(R.drawable.pause_icon);
                        musicCompleted();
                    }
                } else {
                    mediaPlayer = MediaPlayer.create(HomeActivity.this, music.get(currentPosition));
                    mediaPlayer.setVolume(100, 100);
                    mediaPlayer.start();
                    play_btn.setImageResource(R.drawable.pause_icon);
                    musicCompleted();

                }
            }
        });


        PhoneStateListener phoneStateListener=new PhoneStateListener()
        {
            @Override
            public void onCallStateChanged(int state, String phoneNumber) {

                if(state== TelephonyManager.CALL_STATE_RINGING)
                {
                    if(mediaPlayer!=null)
                    {
                        if(mediaPlayer.isPlaying())
                        {
                            mediaPlayer.pause();
                            play_btn.setImageResource(R.drawable.play_icon);

                        }
                    }
                }
                else if(state==TelephonyManager.CALL_STATE_IDLE)
                {
                    if(mediaPlayer!=null)
                    {
                        if(!mediaPlayer.isPlaying())
                        {
                            mediaPlayer.start();
                            play_btn.setImageResource(R.drawable.pause_icon);
                            musicCompleted();
                        }
                    }
                }
                else if(state==TelephonyManager.CALL_STATE_OFFHOOK)
                {
                    if(mediaPlayer!=null)
                    {
                        if(mediaPlayer.isPlaying())
                        {
                            mediaPlayer.pause();
                            play_btn.setImageResource(R.drawable.play_icon);
                        }
                    }
                }
                super.onCallStateChanged(state, phoneNumber);
            }
        };

        TelephonyManager mgr=(TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        if(mgr!=null)
        {
            mgr.listen(phoneStateListener,PhoneStateListener.LISTEN_NONE);
        }

    }

    public void changePrefValue(int value) {

        editor = sharedPreferences.edit();
        editor.putInt("position", value);
        editor.apply();
    }

    public void musicCompleted() {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                if (currentPosition == music.size() - 1) {
                    currentPosition = 0;
                    changePrefValue(currentPosition);
                } else {
                    currentPosition += 1;
                    changePrefValue(currentPosition);
                }
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
//                seekBar.setProgress(0);
                mediaPlayer = MediaPlayer.create(HomeActivity.this, music.get(currentPosition));
                mediaPlayer.start();
                play_btn.setImageResource(R.drawable.pause_icon);



            }
        });
    }

//    public void gotoSeek()
//    {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                int currentPos=mediaPlayer.getCurrentPosition()/1000;
//                int duration=mediaPlayer.getDuration()/1000;
//                seekBar.setMax(duration);
//               seekBar.setProgress(currentPos);
//            }
//        },1000);
//    }



//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(mediaPlayer!=null)
//        {
//            if(mediaPlayer.isPlaying())
//            {
//                mediaPlayer=null;
//            }
//        }
//        else
//        {
//            mediaPlayer.reset();
//            mediaPlayer.release();
//            mediaPlayer = null;
//            mediaPlayer = MediaPlayer.create(HomeActivity.this, music.get(currentPosition));
//            mediaPlayer.start();
//            play_btn.setImageResource(R.drawable.pause_icon);
//        }
//    }



    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit!")
                .setIcon(R.drawable.icon)
                .setMessage("Do you really want to exit")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                        mediaPlayer.stop();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        Button positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button negative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        positive.setTextColor(getResources().getColor(R.color.btn_color));
        negative.setTextColor(getResources().getColor(R.color.btn_color));

    }

    public void createNotification(int tag) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "com.example.evergreensongs.channel";

//        Intent intent = new Intent(this, HomeActivity.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addNextIntentWithParentStack(intent);

//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.setSound(null,null);
            channel.setDescription("Channel");

            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID);
        builder.setWhen(System.currentTimeMillis())
                .setContentTitle("Evergreen Song Playing..")
                .setSmallIcon(R.mipmap.launcher_icon)
                .setAutoCancel(false)
                .setSound(null)
                .setDefaults(Notification.DEFAULT_ALL);
        manager.notify(new Random().nextInt(100), builder.build());


        if (tag == 1) {
            manager.cancelAll();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        createNotification(1);


    }
}
