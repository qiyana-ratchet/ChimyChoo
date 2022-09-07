package com.example.chimychoo

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View.inflate
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide.init
import com.example.chimychoo.UserInfo.userInfoEmail
import com.example.chimychoo.UserInfo.userNewDocumentName
import com.example.chimychoo.databinding.ActivityCreatePlanBinding
import com.example.chimychoo.databinding.ActivityCreatePlanBinding.inflate
import com.example.chimychoo.databinding.ActivityLoginBinding
import com.example.chimychoo.databinding.ActivityLoginBinding.inflate
import com.example.chimychoo.databinding.ActivityMainBinding.inflate
import com.example.chimychoo.databinding.ItemCardBinding.inflate
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class AddHobbyActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreatePlanBinding
    var storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAddHobby()
    }

    private val GALLERY = 1
    private fun initAddHobby() {
        val addPicButton = binding.addPicButton
        addPicButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY)
        }
        binding.createCancelBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.AddFinishBtn.setOnClickListener {
            if (!binding.hobbyTitle.equals("") && !binding.hobbyInfo.equals("")) {
                ///
                val userData = hashMapOf(
                    "explanation" to binding.hobbyInfo.text.toString(),
                    "difficulty" to "easy",
                    "name" to binding.hobbyTitle.text.toString(),
                )

                val db = Firebase.firestore
                db.collection("Cards").document(userNewDocumentName).set(userData, SetOptions.merge())
                    .addOnSuccessListener { Log.d("테스트", "취미 추가 완료") }
                    .addOnFailureListener { exception ->
                        Log.w(
                            ContentValues.TAG,
                            "Error writing document",
                            exception
                        )
                    }
                ///
            } else {
                Toast.makeText(applicationContext, "내용을 다시 확인해주세요", Toast.LENGTH_SHORT).show()
            }


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY) {
                var imageData: Uri? = data?.data
                val storageRef = storage.reference
                val profileRef =
                    storageRef.child("${imageData?.lastPathSegment}.png")
                val profilePathName = imageData?.lastPathSegment.toString()
                userNewDocumentName = profilePathName
                val db = Firebase.firestore
                val data = hashMapOf("image" to profilePathName)
                db.collection("Cards").document(profilePathName)
                    .set(data, SetOptions.merge())

                val uploadTask = profileRef.putFile(imageData!!)
                uploadTask.addOnFailureListener {
                    // Handle unsuccessful uploads
                }.addOnSuccessListener { taskSnapshot ->
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                }
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageData)
                    binding.userImg.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}