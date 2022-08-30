package com.example.chimychoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.example.chimychoo.databinding.ActivityMainBinding
import com.example.mytinder.auth.UserInfoModel
import com.example.mytinder.slider.CardStackAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var cardStackAdapter: CardStackAdapter
    private lateinit var manager : CardStackLayoutManager

    private lateinit var auth: FirebaseAuth

    private val userDataList = ArrayList<UserInfoModel>()

    private var userCount = 0
    private lateinit var myGender : String

    private val TAG = MainActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main )

//        auth = Firebase.auth

        val cardStackView : CardStackView = binding.cardStackView
        val menuIcon : ImageView = binding.menuIcon

        /**
        menuIcon.setOnClickListener {
            // 마이페이지로 이동.
            val intent = Intent( this, MenuActivity::class.java )
            startActivity( intent )
        }
        */
        userDataList.add(UserInfoModel("123","가나다","m","Seoul","20","23409"))
        Log.d("값", userDataList[0].nickname!!)

        manager = CardStackLayoutManager( baseContext, object : CardStackListener {

            override fun onCardDragging(direction: Direction?, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction?) {
                userDataList.add(UserInfoModel("123","가나다","m","Seoul","20","23409"))

                Log.d("유저", "이거지"+userDataList[1].toString())
                cardStackAdapter.notifyDataSetChanged()

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
                }
                userCount += 1

                if( userCount == userDataList.count() ) {

                    getUserDataList( myGender )

                    userCount = 0

                }*/

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
        cardStackAdapter = CardStackAdapter( baseContext, userDataList )

        cardStackView.adapter = cardStackAdapter
        cardStackView.layoutManager = manager


    }


}


















