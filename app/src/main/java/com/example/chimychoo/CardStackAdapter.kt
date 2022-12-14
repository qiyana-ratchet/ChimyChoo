package com.example.chimychoo

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class CardStackAdapter( val context : Context, val items : List<UserInfoModel> ) : RecyclerView.Adapter<CardStackAdapter.ViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate( R.layout.item_card, parent, false )
        return ViewHolder( view )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding( items[position] )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder( itemView : View) : RecyclerView.ViewHolder(itemView) {

        val profileImageArea : ImageView = itemView.findViewById( R.id.profileImageArea )
        val nicknameArea : TextView = itemView.findViewById( R.id.itemNickname )
        val ageArea : TextView = itemView.findViewById( R.id.itemAge )
        val cityArea : TextView = itemView.findViewById( R.id.itemCity )

        val left_overlay = itemView.findViewById<FrameLayout>(R.id.left_overlay)
        val right_overlay = itemView.findViewById<FrameLayout>(R.id.right_overlay)

        fun binding( user : UserInfoModel) {

            // 오버레이 아이콘 최상단으로 가져오기.
            left_overlay.bringToFront()
            right_overlay.bringToFront()
            /////////////////////////////
            Log.d("유저",user.uid.toString())
//            profileImageArea.setImageResource(R.drawable.img)
            /////////////////////////////
            // 카드 스택 뷰에 프로필 사진 가져와서 Glide로 적용시키기.
            val profileImageRef = Firebase.storage.reference.child("${user.uid}.png")
            profileImageRef.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
                if( task.isSuccessful ) {
                    Glide.with(context)
                        .load(task.result)
                        .into(profileImageArea)
                }
            })


            // 텍스트 바인딩.
            nicknameArea.text = user.nickname
            ageArea.text = "${user.age}"
            cityArea.text = "${user.city}"

        }
    }
}