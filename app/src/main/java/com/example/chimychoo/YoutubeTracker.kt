package com.example.chimychoo

import android.app.AppOpsManager
import android.app.AppOpsManager.MODE_ALLOWED
import android.app.AppOpsManager.OPSTR_GET_USAGE_STATS
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Process.myUid
import android.provider.Settings
import android.util.Log
import android.widget.Toast
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
            Log.i("테스트", "The user may not allow the access to apps usage. ")
            Toast.makeText(this, "앱 사용시간 권한을 허용해주세요", Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        } else {
            // We have the permission. Query app usage stats.
            Log.d("테스트", "리스트 사이즈 : ${getAppUsageStats().size}")
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
        }
    }

    private fun getYoutube(){
        //TODO : for문 문자열 파싱해서 com.google.android.youtube 데이터 추출하기
        //Log.d("형민", "이거뜨면 잘되는거임 ㅋㅋ")
    }

    private fun checkLastTimeChanged(){
        //TODO : 날짜가 바뀌었는지 비교해서 true, false 값 return 해주는 함수 작성하기
    }
}