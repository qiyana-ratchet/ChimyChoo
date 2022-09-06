package com.example.chimychoo

import android.app.AppOpsManager
import android.app.AppOpsManager.MODE_ALLOWED
import android.app.AppOpsManager.OPSTR_GET_USAGE_STATS
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
import java.util.*


class YoutubeTracker : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)); // 권한 주는 코드
        usageStatsInit()
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
            /*Log.d("테스트", "packageName: ${it.packageName}, lastTimeUsed: ${Date(it.lastTimeUsed)}, " +
                    "totalTimeInForeground: ${it.totalTimeInForeground}")

             */
            if(it.packageName=="com.google.android.youtube"){
                Log.d("성공성공성공성공","유튜브-lastTimestamp: ${Date(it.lastTimeStamp)}")
                Log.d("성공성공성공","유튜브-firstTimestamp: ${Date(it.firstTimeStamp)}")
                Log.d("성공성공","유튜브 최근 실행한 시간 ---:${Date(it.lastTimeUsed)}")
                Log.d("성공","유튜브 실행한 총 시간 4: ${it.totalTimeInForeground}")

            }

        }
    }//showAppUsageStats



}