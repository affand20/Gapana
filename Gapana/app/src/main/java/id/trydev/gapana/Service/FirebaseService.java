package id.trydev.gapana.Service;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import id.trydev.gapana.Base.MainActivity;
import id.trydev.gapana.Notifikasi.NotifikasiActivity;
import id.trydev.gapana.R;

import static id.trydev.gapana.Base.MainActivity.preferences;

public class FirebaseService extends FirebaseMessagingService {

    public String TAG = "FIREBASE MESSAGING";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("lat"));

            float distance[] = new float[2];
            Location.distanceBetween(
                    preferences.getLastLatitude(),
                    preferences.getLastLongitude(),
                    Double.parseDouble(remoteMessage.getData().get("lat")),
                    Double.parseDouble(remoteMessage.getData().get("long")),
                    distance);
            Log.d(TAG, "DISTANCE : "+distance[0]);

            if (distance[0]<=1500){
                Log.d(TAG, "onMessageReceived: "+preferences.getTogleNotif());
                if (preferences.getTogleNotif()==1){
                    sendNotification("Waspada Bencana", "latitude : "+preferences.getLastLatitude()+" longitude : "+preferences.getLastLongitude());

                    startActivity(
                            new Intent(this, NotifikasiActivity.class)
                                    .putExtra("magnitude", remoteMessage.getData().get("magnitude"))
                                    .putExtra("type", remoteMessage.getData().get("type"))
                                    .putExtra("info", remoteMessage.getData().get("loc")));
                }
            } else{
                Log.d(TAG, "onMessageReceived: "+preferences.getTogleNotif2());
                if (preferences.getTogleNotif2() == 1){
                    sendNotification("Informasi Bencana", "Telah terjadi gempa "+remoteMessage.getData().get("magnitude")+" SR "+remoteMessage.getData().get("loc"));
                }
            }

        }

    }



    private void sendNotification(RemoteMessage remoteMessage) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.ic_marker_24dp)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendNotification(String title, String body){

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("redirect", "posko");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,
                getResources().getString(R.string.channel_id))
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(3, notificationBuilder.build());

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }
}
