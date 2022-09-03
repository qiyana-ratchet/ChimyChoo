package com.example.chimychoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.example.chimychoo.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
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

        val cardStackView : CardStackView = binding.cardStackView
        val menuIcon : ImageView = binding.menuIcon

        /**
        menuIcon.setOnClickListener {
            // 마이페이지로 이동.
            val intent = Intent( this, MenuActivity::class.java )
            startActivity( intent )
        }
        */
        userDataList.add(UserInfoModel("jogging","가나다","m","Seoul","1","23409"))
        userDataList.add(UserInfoModel("cafe","가나다2","m","Seoul","2","23409"))
        userDataList.add(UserInfoModel("swimming","가나다2","m","Seoul","3","23409"))
        userDataList.add(UserInfoModel("tennis","가나다2","m","Seoul","4","23409"))
        userDataList.add(UserInfoModel("cooking","가나다2","m","Seoul","5","23409"))
        userDataList.add(UserInfoModel("biking","가나다2","m","Seoul","6","23409"))
        userDataList.add(UserInfoModel("reading","가나다2","m","Seoul","7","23409"))
        userDataList.add(UserInfoModel("music","가나다2","m","Seoul","8","23409"))
        Log.d("값", userDataList[0].nickname!!)

        manager = CardStackLayoutManager( baseContext, object : CardStackListener {

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


















