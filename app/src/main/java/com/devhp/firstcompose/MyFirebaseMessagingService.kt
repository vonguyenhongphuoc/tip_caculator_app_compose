package com.devhp.firstcompose

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.remoteMessage


const val channelID = "notification_channel"
const val channelName = "com.devhp.firstcompose"

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if(message.notification != null){
            Log.d("MyTagNotification", "This is a notification")
            generateNotification(message.notification!!.title!!, message.notification!!.body!!)
        }

        Log.d("MyTagNotification", "This is a message data")
        val customerID = message.data["customerid"]
        Log.d("MyTagNotification", "CustomerID: $customerID")

        generateNotification(title = "Test", customerID.toString())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    fun generateNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true).setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
        builder = builder.setContent(getRemoteView(title, message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())
    }

    private fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView = RemoteViews(channelName, R.layout.notification)
        remoteView.setTextViewText(R.id.tv_title, title)
        remoteView.setTextViewText(R.id.tv_description, message)
        remoteView.setImageViewResource(R.id.iv_logo, R.drawable.ic_launcher_foreground)
        return remoteView
    }

}



//// Declare the launcher at the top of your Activity/Fragment:
//private val requestPermissionLauncher = registerForActivityResult(
//    ActivityResultContracts.RequestPermission(),
//) { isGranted: Boolean ->
//    if (isGranted) {
//        // FCM SDK (and your app) can post notifications.
//    } else {
//        // TODO: Inform user that that your app will not show notifications.
//    }
//}
//
//private fun askNotificationPermission() {
//    // This is only necessary for API level >= 33 (TIRAMISU)
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
//            PackageManager.PERMISSION_GRANTED
//        ) {
//            // FCM SDK (and your app) can post notifications.
//            Log.d("MyTag", "Notification Permission is Granted")
//        } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//            // TODO: display an educational UI explaining to the user the features that will be enabled
//            //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
//            //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
//            //       If the user selects "No thanks," allow the user to continue without notifications.
//        } else {
//            // Directly ask for the permission
//            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//        }
//    }
//}


//askNotificationPermission()
////frxzafRNSTOAUg9cTAdtBn:APA91bGp3_H6RRR6YLhH7A1UO5BW1T8IlwRDjiCjGkFcv33G5RvRW2LE08Nw-3BMbjI3qiEH4rJa4V6Kv7w3JtVCplNkEpHAlI9LVKFC8PzeZzMdWZqe5Ui2gtF9DjlAO8uhkqLK6lgU
//FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//    if (!task.isSuccessful) {
//        Log.w("MyTag", "Fetching FCM registration token failed", task.exception)
//        return@OnCompleteListener
//    }
//
//    // Get new FCM registration token
//    val token = task.result
//
//    // Log and toast
//    val msg = "FCM Token: $token"
//    Log.d("MyTag", msg)
//    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//})
