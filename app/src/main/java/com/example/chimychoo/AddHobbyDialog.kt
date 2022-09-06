package com.example.chimychoo

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.chimychoo.databinding.ActivityCreatePlanBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AddHobbyDialog(context: MainActivity) {
    lateinit var binding: ActivityCreatePlanBinding
    var context = this
    private val dialog = Dialog(context)
    private lateinit var onDismissedClickListener: OnPlanCreateClickListener
    private val GALLERY = 1

    fun onDismissedClickListener(listener: OnPlanCreateClickListener) {
        onDismissedClickListener = listener
    }

    interface OnPlanCreateClickListener {
        fun onPlanCreateClick(name: String)
    }

    @SuppressLint("CutPasteId", "SetTextI18n")
    fun showDialog() {
        dialog.setContentView(R.layout.activity_create_plan)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        dialog.findViewById<Button>(R.id.AddFinishBtn).setOnClickListener {
            onDismissedClickListener.onPlanCreateClick("확인")
            var hobbyTitle = dialog.findViewById<TextView>(R.id.hobbyTitle).text.toString()
            var hobbyInfo = dialog.findViewById<TextView>(R.id.hobbyInfo).text.toString()
            ///
            val addPicButton = binding.addPicButton
            ///
            if (true) { //TODO: isValidFormat() 추가하기
                //db 추가
                val db = Firebase.firestore
                val doc = db.collection("User").document(UserInfo.userInfoEmail)
//                doc.update("plans", FieldValue.arrayUnion("$planTitle!$planStartTime!$planFinishTime"))
                dialog.dismiss()
            }else{
                Toast.makeText(dialog.context, "잘못 입력하셨습니다. 다시 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.findViewById<ImageView>(R.id.createCancelBtn).setOnClickListener {
            dialog.dismiss()
        }

//        dialog.findViewById<CalendarView>(R.id.calendar).setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
//            var toast = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
////            Toast.makeText(dialog.context, toast, Toast.LENGTH_SHORT).show()
//            dialog.findViewById<TextView>(R.id.planStartTime).text =
//                year.toString()+"-"+(month+1).toString()+"-"+dayOfMonth.toString()+"/12:00"
//            dialog.findViewById<TextView>(R.id.planFinishTime).text =
//                year.toString()+"-"+(month+1).toString()+"-"+(dayOfMonth+1).toString()+"/12:00"
//        }
    }



//    private fun isValidPlan(planTitle: String, planStartTime: String, planFinishTime: String): Boolean {
//        return !(planTitle==""||planStartTime==""||planFinishTime=="")
//    }



}