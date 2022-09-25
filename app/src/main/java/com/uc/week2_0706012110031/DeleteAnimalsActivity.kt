package com.uc.week2_0706012110031

import Database.GlobalVar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_animals_item.cancelbtn
import kotlinx.android.synthetic.main.activity_delete_animals.delete_button

class DeleteAnimalsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_animals)
        listener()

    }

    private fun listener(){
        cancelbtn.setOnClickListener {
            finish()
        }

        delete_button.setOnClickListener {
            GlobalVar.listDataAnimals.removeAt(intent.getIntExtra("position", -1))
            val intent = Intent()
            intent.putExtra("delete", "Hapus berhasil")
            setResult(RESULT_OK, intent)
            finish()
        }
    }


}