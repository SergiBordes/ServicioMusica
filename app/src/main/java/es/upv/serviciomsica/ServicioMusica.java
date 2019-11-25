package es.upv.serviciomsica;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class ServicioMusica extends Service {

    private NotificationManager notificationManager;
    private NotificationManager notificationManager2;
    static final String CANAL_ID = "mi_canal";
    static final int NOTIFICACION_ID = 1;
    static final int NOTIFICACION_ID2 = 2;

    MediaPlayer reproductor;
    @Override public void onCreate() {
        Toast.makeText(this,"Servicio creado",
                Toast.LENGTH_SHORT).show();
        reproductor = MediaPlayer.create(this, R.raw.audio);
    }
    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {
        //para crear una nueva notificación
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CANAL_ID, "Mis Notificaciones",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Descripcion del canal");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificacion =
                new NotificationCompat.Builder(this, CANAL_ID)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                android.R.drawable.ic_media_play))
                        .setWhen(System.currentTimeMillis() + 1000 * 60 * 60)
                        .setContentInfo("más info")
                        .setTicker("Texto en barra de estado")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Título")
                        .setContentText("Texto informativo");
        PendingIntent intencionPendiente = PendingIntent.getActivity(
                this, 0, new Intent(this, MainActivity.class), 0);
        notificacion.setContentIntent(intencionPendiente);
        //notificationManager.notify(NOTIFICACION_ID, notificacion.build());
        notificationManager.notify(NOTIFICACION_ID, notificacion.build());

        //---

        //Otra notificacion
        notificationManager2 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CANAL_ID, "Mis Notificaciones",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Descripcion del canal");
            notificationManager2.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificacion2 =
                new NotificationCompat.Builder(this, CANAL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Servicio de Música")
                        .setContentText("Servicio de música");
        PendingIntent intencionPendiente2 = PendingIntent.getActivity(
                this, 0, new Intent(this, MainActivity.class), 0);
        notificacion2.setContentIntent(intencionPendiente2);
        notificationManager2.notify(NOTIFICACION_ID2, notificacion2.build());
        //startForeground(NOTIFICACION_ID, notificacion.build());

        //---

        Toast.makeText(this,"Servicio arrancado "+ idArranque,
                Toast.LENGTH_SHORT).show();
        reproductor.start();
        return START_STICKY;
    }
    @Override public void onDestroy() {
        Toast.makeText(this,"Servicio detenido",
                Toast.LENGTH_SHORT).show();
        reproductor.stop();
        reproductor.release();
        notificationManager.cancel(NOTIFICACION_ID);
        notificationManager.cancel(NOTIFICACION_ID2);
    }
    @Override public IBinder onBind(Intent intencion) {
        return null;
    }
}
