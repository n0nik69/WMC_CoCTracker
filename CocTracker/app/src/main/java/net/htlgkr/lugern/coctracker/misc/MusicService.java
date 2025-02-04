package net.htlgkr.lugern.coctracker.misc;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import net.htlgkr.lugern.coctracker.R;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        // MediaPlayer erstellen und Musik laden
        mediaPlayer = MediaPlayer.create(this, R.raw.cocmaintheme);
        mediaPlayer.setLooping(true);  // Musik in Schleife abspielen
        mediaPlayer.start();  // Musik starten
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Der Service läuft auch dann weiter, wenn die Aktivität gestoppt wird
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // MediaPlayer freigeben, wenn der Service beendet wird
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // Bindung des Services (optional, nicht benötigt für reinen Hintergrundbetrieb)
        return null;
    }
}
