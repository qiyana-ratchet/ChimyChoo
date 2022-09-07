package com.example.chimychoo

import android.app.AppOpsManager
import android.app.AppOpsManager.MODE_ALLOWED
import android.app.AppOpsManager.OPSTR_GET_USAGE_STATS
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Process.myUid
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.Dispatchers.Main
import java.util.*
//밑으로는 현재 시간 불러오기
import java.util.Locale
import java.text.SimpleDateFormat
import java.util.Date
//timer
import kotlin.concurrent.timer


class YoutubeTracker : AppCompatActivity() {

    private val CHANNEL_ID = "ChimyChoo Alarm"
    // Channel for notification
    private var notificationManager: NotificationManager? = null


    public var time_used = 0

    public var tenminute_control = 0

    public var isit_oneminute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel(CHANNEL_ID, "testChannel", "this is a test Channel")
//        startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)); // 권한 주는 코드
        timer(period = 5000,)//주기 5초
        {

            usageStatsInit()
        }
        val intent = Intent( this, MainActivity::class.java )
        startActivity( intent )
    }

    private fun usageStatsInit() {

        if (!checkForPermission()) {
            //Log.i("테스트", "The user may not allow the access to apps usage. ")
            //Toast.makeText(this, "앱 사용시간 권한을 허용해주세요", Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        } else {
            // We have the permission. Query app usage stats.
            //Log.d("테스트", "리스트 사이즈 : ${getAppUsageStats().size}")
//            Log.d("테스트", "리스트 목록 : ${getAppUsageStats()}")
            showAppUsageStats(getAppUsageStats())
        }

    }

    private fun checkForPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS, myUid(), packageName)
        return mode == MODE_ALLOWED
    }

    private fun getAppUsageStats(): MutableList<UsageStats> {
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, -1)    // 1

        val usageStatsManager =
            getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager // 2
        val queryUsageStats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY, cal.timeInMillis, System.currentTimeMillis() // 3
        )
        return queryUsageStats
    }


    private fun showAppUsageStats(usageStats: MutableList<UsageStats>) {
        usageStats.sortWith(Comparator { right, left ->
            compareValues(left.lastTimeUsed, right.lastTimeUsed)
        })

        usageStats.forEach { it ->
            Log.d("테스트", "packageName: ${it.packageName}, lastTimeUsed: ${Date(it.lastTimeUsed)}, " +
                    "totalTimeInForeground: ${it.totalTimeInForeground}")


            if(it.packageName=="com.google.android.youtube"){
                Log.d("성공1","유튜브-lastTimestamp: ${Date(it.lastTimeStamp)}")
                Log.d("성공2","유튜브-firstTimestamp: ${Date(it.firstTimeStamp)}")
                Log.d("성공3","유튜브 최근 실행한 시간 ---:${Date(it.lastTimeUsed)}")
                Log.d("성공4","유튜브 실행한 총 시간 4: ${it.totalTimeInForeground}")
                Log.d("시간확인1","유튜브를 실행했는지 1분내로 확인.time_used : ${time_used}")
                if (it.totalTimeInForeground.toInt() > time_used)
                {
                    if(tenminute_control%2 ==1)
                    {
                        Log.d("유튜브 켜짐","유튜브 켜짐")
                        //tenminute_control이 홀수면 유튜브가 켜졌다고 뜸뜸
                        displayNotification()
                    }
                    else
                    {
                        Log.d("유튜브 꺼짐","유튜브 꺼짐")
                        notificationManager?.cancelAll()
                    }
                    Log.d("시간확인2","유튜브를 실행했는지 1분내로 확인.time_used : ${time_used}")

                    tenminute_control++
                }

                time_used = it.totalTimeInForeground.toInt()
                Log.d("시간확인3","유튜브를 실행했는지 1분내로 확인.time_used : ${time_used}")
                return
                /*
                // 현재시간을 가져오기
                val long_now = System.currentTimeMillis()

                Log.d("현재시간 가져오기 ","현재 시간: ${Date(long_now)}")

                if(Date(it.lastTimeUsed)==Date(long_now))
                {
                    Log.d("현재시간 가져오기 성공","현재 시간: ${Date(long_now)}")
                }
                */

            }

        }
    }//showAppUsageStats


    private fun createNotificationChannel(channelId: String, name: String, channelDescription: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT // set importance
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = channelDescription
            }
            // Register the channel with the system
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }

    private fun displayNotification() {
        val notificationId = 66

        val notification = Notification.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("유튜브를 실행하셨습니다.")
            .setContentText("도파민 중독에 유의하세요!")
            .build()

        notificationManager?.notify(notificationId, notification)
    }
}