package com.example.chimychoo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View.inflate
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide.init
import com.example.chimychoo.UserInfo.userInfoEmail
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

        binding.AddFinishBtn.setOnClickListener{
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
                    storageRef.child("${imageData?.lastPathSegment}")
                val profilePathName = imageData?.lastPathSegment.toString()
                val db = Firebase.firestore
                val data = hashMapOf("image" to profilePathName)
                db.collection("Cards").document("hobby1")
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