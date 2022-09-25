package com.uc.week2_0706012110031

import Database.GlobalVar
import Model.Animals
import Model.Ayam
import Model.Kambing
import Model.Sapi
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.uc.week2_0706012110031.databinding.ActivityAddAnimalsBinding
import kotlinx.android.synthetic.main.activity_add_animals.*

class AddAnimalsActivity : AppCompatActivity() {
    private lateinit var viewBind:ActivityAddAnimalsBinding
    private lateinit var animals: Animals
    var position = -1
    var image: String = ""

    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){   // APLIKASI GALLERY SUKSES MENDAPATKAN IMAGE
            val uri = it.data?.data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if(uri != null){
                    baseContext.getContentResolver().takePersistableUriPermission(uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }}// GET PATH TO IMAGE FROM GALLEY
            viewBind.imageview.setImageURI(uri)  // MENAMPILKAN DI IMAGE VIEW
            image = uri.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityAddAnimalsBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.hide()
        getintent()
        listener()
    }
    private fun getintent(){
        position = intent.getIntExtra("position", -1)
        if(position != -1){
            val animals = GlobalVar.listDataAnimals[position]
            viewBind.toolbarAnimals.title = "Edit Animals"
            viewBind.Simpanhewan.text = "Edit"
            viewBind.imageview.setImageURI(Uri.parse(animals.imageUri))
            viewBind.Nama.editText?.setText(animals.nama)
            viewBind.Usia.editText?.setText(animals.usia.toString())
            when(animals?.jenis) {
                "Ayam" -> viewBind.radio1Ayam.setChecked(true)
                "Sapi" -> viewBind.radio2Sapi.setChecked(true)
                "Kambing" -> viewBind.radio3Kambing.setChecked(true)
            }
//            if(animals.jenis == "Ayam"){
//                viewBind.radioGroup.check(-1);
//            }else if(animals.jenis == "Sapi") {
//                viewBind.radioGroup.check(-1);
//            }else if(animals.jenis == "Kambing"){
//                viewBind.radioGroup.check(-1);
//            }

        }
    }

    private fun listener(){
        viewBind.imageview.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }

        viewBind.Simpanhewan.setOnClickListener{
//            var nama= viewBind.Nama.editText?.text.toString().trim()
//            var usia = 0
//            var jenis = findViewById<RadioButton>(viewBind.radioGroup.checkedRadioButtonId).text.toString().trim();
//            if(position==-1){
//                if(jenis == "Ayam") {
//                    animals = Ayam(GlobalVar.ID_INCREMENT++,nama, jenis, usia)
//                    checker()
//                }else if(jenis == "Sapi"){
//                    animals = Sapi(GlobalVar.ID_INCREMENT++,nama, jenis, usia)
//                    checker()
//                }else{
//                    animals = Kambing(GlobalVar.ID_INCREMENT++,nama, jenis, usia)
//                    checker()
//                }
//            }else{
//                if(jenis == "Ayam") {
//                    animals = Ayam(GlobalVar.listDataAnimals[position].id,nama, jenis, usia)
//                    checker()
//                }else if(jenis == "Sapi"){
//                    animals = Sapi(GlobalVar.listDataAnimals[position].id,nama, jenis, usia)
//                    checker()
//                }else{
//                    animals = Kambing(GlobalVar.listDataAnimals[position].id,nama, jenis, usia)
//                    checker()
//                }
//
//            }
            if (viewBind.radioGroup.checkedRadioButtonId == -1) {
                var nama = viewBind.Nama.editText?.text.toString().trim()
                var usia = 0
                var jenis = ""
                animals = Ayam(GlobalVar.ID_INCREMENT++,nama, jenis, usia)
                checker()
            } else {
                var nama = viewBind.Nama.editText?.text.toString().trim()
                var usia = 0
                var jenis = findViewById<RadioButton>(viewBind.radioGroup.checkedRadioButtonId).text.toString()
                if (jenis == "Ayam") {
                    animals = Ayam(GlobalVar.ID_INCREMENT++,nama, jenis, usia)
                    checker()
                } else if (jenis == "Sapi") {
                    animals = Sapi(GlobalVar.ID_INCREMENT++,nama, jenis, usia)
                    checker()
                } else {
                    animals = Kambing(GlobalVar.ID_INCREMENT++,nama, jenis, usia)
                    checker()
                }

            }


        }

        viewBind.toolbarAnimals.getChildAt(1).setOnClickListener {
            finish()
        }
    }

    private fun checker() {
        var isCompleted:Boolean = true

        if(animals.nama!!.isEmpty()){
            viewBind.Nama.error = "Title cannot be empty"
            isCompleted = false
        }else{
            viewBind.Nama.error = ""
        }

        if(animals.jenis!!.isEmpty()){
            viewBind.radio1Ayam.error = "?"
            viewBind.radio2Sapi.error = "?"
            viewBind.radio3Kambing.error = "?"
            isCompleted = false
        }

//        if(viewBind.radioGroup.checkedRadioButtonId < 0){
//            viewBind.radio3Kambing.error = "Jenis tidak bisa kosong"
//            isCompleted = false
//        }
//        else{
//            isCompleted = true
//        }

//        animals.imageUri = image.toString()
//        if(animals.imageUri!!.isEmpty()){
//            //toast
//            Toast.makeText(this, "Picture cannot be empty", Toast.LENGTH_LONG).show()
//            isCompleted = false
//        }

        if(viewBind.Usia.editText?.text.toString().isEmpty() || viewBind.Usia.editText?.text.toString().toInt() < 0)
        {
            viewBind.Usia.error = "usia cannot be empty or 0"
            isCompleted = false
        }

        if(isCompleted == true)
        {
            if(position == -1)
            {
                animals.usia = viewBind.Usia.editText?.text.toString().toInt()
                GlobalVar.listDataAnimals.add(animals)

            }else
            {
                animals.usia = viewBind.Usia.editText?.text.toString().toInt()
                GlobalVar.listDataAnimals[position] = animals
            }
            finish()
        }
    }
}