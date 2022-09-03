package com.example.chimychoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.example.chimychoo.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var cardStackAdapter: CardStackAdapter
    private lateinit var manager: CardStackLayoutManager

    private lateinit var auth: FirebaseAuth

    private val userDataList = ArrayList<UserInfoModel>()

    private var userCount = 0
    private lateinit var myGender: String

    private val TAG = MainActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val cardStackView: CardStackView = binding.cardStackView
        val menuIcon: ImageView = binding.menuIcon

        /**
        menuIcon.setOnClickListener {
        // 마이페이지로 이동.
        val intent = Intent( this, MenuActivity::class.java )
        startActivity( intent )
        }
         */
//        userDataList.add(UserInfoModel("jogging","조깅","m","조깅하는 사람은 신체나이가 10살 젊습니다","1","23409"))
//        userDataList.add(UserInfoModel("cafe","카페가기","m","집앞 새로운 카페 탐색하기","2","23409"))
//        userDataList.add(UserInfoModel("swimming","수영","m","수영은 심폐지구력 발달에 도움이 됩니다","3","23409"))
//        userDataList.add(UserInfoModel("tennis","테니스","m","테니스를 배워보시는 건 어떨까요?","4","23409"))
//        userDataList.add(UserInfoModel("cooking","요리하기","m","평소에 먹어보고 싶었던 걸 만들어보세요","5","23409"))
//        userDataList.add(UserInfoModel("biking","자전거타기","m","자전거를 타고 안가본 곳을 가보세요","6","23409"))
//        userDataList.add(UserInfoModel("reading","책읽기","m","소설책을 읽어보세요","7","23409"))
//        userDataList.add(UserInfoModel("music","노래듣기","m","노래를 들어보세요","8","23409"))
//        userDataList.add(UserInfoModel("playwithcat","애완동물과 놀기","m","애완묘가 있으신가요?","9","23409"))
//        userDataList.add(UserInfoModel("boardgame","보드게임","m","친구들과 보드게임을 해보세요","10","23409"))
//        Log.d("값", userDataList[0].nickname!!)
        ///
//        val docData = hashMapOf(
//            "difficulty" to "easy",
//            "image" to "swimming",
//            "name" to "수영",
//            "explanation" to "수영은 심폐지구력 발달에 도움이 됩니다",
//        )
//        val db = Firebase.firestore
//        db.collection("Cards").document("hobby3")
//            .set(docData)
//            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
//            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        ///
        loadCardData()
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
                    Log.d(TAG, "${document.id} => ${document.data}")
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
                cardStackAdapter.notifyDataSetChanged()

                Log.d(TAG, userDataList[0].nickname.toString())

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }


}


















