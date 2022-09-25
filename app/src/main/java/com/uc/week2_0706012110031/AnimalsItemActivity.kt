package com.uc.week2_0706012110031

import Database.GlobalVar
import android.app.Activity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_animals.*

public class AnimalsItemActivity : AppCompatActivity() {
    private var position: Int = -1

    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            val uri = it.data?.data
            imageview.setImageURI(uri)
            GlobalVar.listDataAnimals[position].imageUri = uri.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animals_item)

        listener()
    }

    private fun listener(){
        toolbarAnimals.getChildAt(1).setOnClickListener {
            finish()
        }

    }


}