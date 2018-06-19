package com.hro.ictlab.ict_lab.services

import android.app.*
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.IBinder
import com.hro.ictlab.ict_lab.R
import com.hro.ictlab.ict_lab.events.EventsActivity
import com.hro.ictlab.ict_lab.retrofit.UserNotification
import java.util.*

class NotificationService : Service() {

    val userNotification = UserNotification("Les verplaatst", "Let op!", Date(), false)

    companion object {
        val ALERT = "ALERT"
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            Handler().postDelayed({
                getUserNotifications()
            }, 120000)
        return Service.START_STICKY
    }

    private fun getUserNotifications() {
        //This is where the API call should be made to check if a user has notifications ready
        //For now there is a dummy notification as member variable that acts as the server response
        notifyUser(userNotification)
    }

    private fun markUserNotified() {
        //This is where a call to there server should be made that the user has received the notification to prevents users from receiving the same alert multiple times
        userNotification.read = true
    }

    private fun notifyUser(userNotification: UserNotification) {

        if (!userNotification.read) {
            val intent = Intent(this@NotificationService, EventsActivity::class.java)
            intent.putExtra(ALERT, userNotification.time)

            val resultPendingIntent = PendingIntent.getActivity(this@NotificationService, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId = "channel_01"

            val notification = NotificationCompat.Builder(this@NotificationService)
                    .setSmallIcon(R.drawable.ic_priority_high)
                    .setAutoCancel(true)
                    .setContentTitle(userNotification.type)
                    .setContentText(userNotification.message)
                    .setVibrate(longArrayOf(1000, 2000, 3000, 4000, 5000))
                    .setLights(Color.RED, 500, 500)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setChannelId(channelId)

            notification.setContentIntent(resultPendingIntent)

            if (Build.VERSION.SDK_INT >= 26) {
                val channel: NotificationChannel
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    channel = NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT)
                    notificationManager.createNotificationChannel(channel)
                }
            }

            val buildedNotification = notification.build()
            buildedNotification.flags = Notification.FLAG_INSISTENT
            buildedNotification.flags += Notification.FLAG_AUTO_CANCEL
            notificationManager.notify(userNotification.time.time.toInt(), buildedNotification)

            markUserNotified()
        }
    }
}