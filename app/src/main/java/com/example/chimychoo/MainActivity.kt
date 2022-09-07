package com.example.chimychoo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.chimychoo.UserInfo.userInfoEmail
import com.example.chimychoo.UserInfo.youtubeFlag
import com.example.chimychoo.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var cardStackAdapter: CardStackAdapter
    private lateinit var manager: CardStackLayoutManager

    private lateinit var auth: FirebaseAuth

    private val userDataList = ArrayList<UserInfoModel>()

    private var userCount = 0
    private lateinit var myGender: String

    private val TAG = MainActivity::class.java.simpleName
    var storage = FirebaseStorage.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val cardStackView: CardStackView = binding.cardStackView


//        menuIcon.setOnClickListener {
//        // 마이페이지로 이동.
//        val intent = Intent( this, MenuActivity::class.java )
//        startActivity( intent )
//        }
        if (youtubeFlag == 0) {
            youtubeFlag = 1
            Log.d("테스트", "---------------유튜브트래커 시작")
            val intent = Intent(this, YoutubeTracker::class.java)
            startActivity(intent)
            Log.d("테스트", "---------------유튜브트래커 종료")

        }
        userDataList.clear() //data 초기화
        initDialog() // 다이얼로그 초기화
        loadCardData() // 카드 데이터 로딩
        manager = CardStackLayoutManager(baseContext, object : CardStackListener {

            override fun onCardDragging(direction: Direction?, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction?) {

                /**                when(direction) {
                Direction.Left -> {
                // 왼쪽 스와이프
                Log.d( TAG, userDataList[userCount].uid.toString() )

                val myUid = FirebaseAuthUtils.getMyUid()
                val otherUid = userDataList[userCount].uid.toString()

                disLike(myUid , otherUid )
                }

                Direction.Right -> {
                // 오른쪽 스와이프

                Log.d( TAG, userDataList[userCount].uid.toString() )

                val myUid = FirebaseAuthUtils.getMyUid()
                val otherUid = userDataList[userCount].uid.toString()

                Like( myUid , otherUid)

                }

                else -> {}
                }*/
                userCount += 1

                if (userCount == userDataList.count()) {
                    loadCardData()
                    userCount = 0 //초기화
                    userDataList.clear() //초기화
                }

            }

            override fun onCardRewound() {

            }

            override fun onCardCanceled() {

            }

            override fun onCardAppeared(view: View?, position: Int) {

            }

            override fun onCardDisappeared(view: View?, position: Int) {

            }
        })

        // 카드 스택 뷰에 연결하기.
        cardStackAdapter = CardStackAdapter(baseContext, userDataList)
        cardStackView.adapter = cardStackAdapter
        cardStackView.layoutManager = manager


    }

    fun loadCardData() {
        val db = Firebase.firestore
        db.collection("Cards")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("테스트", "${document.id} => ${document.data}")
//                    Log.d(TAG, "${document.data["difficulty"]}")
                    userDataList.add(
                        UserInfoModel(
                            document.data["image"] as String?,
                            document.data["name"] as String?,
                            "m",
                            document.data["explanation"] as String?,
                            "",
                            "23409"
                        )
                    )
                }
                userDataList.shuffle() //카드 랜덤 섞기
                cardStackAdapter.notifyDataSetChanged()
                Log.d(TAG, userDataList[0].nickname.toString())
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    private val GALLERY = 1
    private fun initDialog() {
        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddHobbyActivity::class.java)
            startActivity(intent)

//            val dialog = AddHobbyDialog(this)
//            dialog.showDialog()
//            dialog.onDismissedClickListener(object :
//                AddHobbyDialog.OnPlanCreateClickListener {
//                override fun onPlanCreateClick(name: String) {
////                    initRecyclerView()
//                }
//            })

        }

    }


}


















