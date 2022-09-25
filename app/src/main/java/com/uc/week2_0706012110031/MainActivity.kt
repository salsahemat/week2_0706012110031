package com.uc.week2_0706012110031

import Adapter.AnimalsAdapter
import Database.GlobalVar
import Interface.CardListener
import Model.Animals
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.uc.week2_0706012110031.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CardListener {
    private lateinit var viewBind: ActivityMainBinding
    private var adapter = AnimalsAdapter(GlobalVar.listDataAnimals,this)
    private var jumlah : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.hide()
        permission()
        setUpRecyclerView()
        listener()
    }

    override fun onResume() {
        super.onResume()
        jumlah = GlobalVar.listDataAnimals.size
        if (jumlah == 0){
            viewBind.textView2.alpha = 1f
        }else{
            viewBind.textView2.alpha = 0f
        }
        if(viewBind.spFilter.selectedItem.toString() == "-"){
            adapter.notifyDataSetChanged()
        }else{
            val temp = ArrayList<Animals>()
            for(animal in GlobalVar.listDataAnimals){
                if(animal.jenis.equals(viewBind.spFilter.selectedItem.toString())){
                    temp.add(animal)
                }
            }
            adapter=AnimalsAdapter(temp,this@MainActivity)
            setUpRecyclerView()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this,data?.getStringExtra("delete"),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun permission(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), GlobalVar.STORAGE_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "Storage Permission already granted", Toast.LENGTH_SHORT).show()
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), GlobalVar.STORAGE_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "Storage Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GlobalVar.STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun listener(){
        viewBind.addbutton.setOnClickListener{
            val myIntent = Intent(this, AddAnimalsActivity::class.java)
            startActivity(myIntent)
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.jenis,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            viewBind.spFilter.adapter = adapter
        }
        viewBind.spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                if(viewBind.spFilter.selectedItem.toString() == "-"){
                    adapter=AnimalsAdapter(GlobalVar.listDataAnimals,this@MainActivity)
                }else{
                    val temp = ArrayList<Animals>()
                    for(animal in GlobalVar.listDataAnimals){
                        if(animal.jenis.equals(viewBind.spFilter.selectedItem.toString())){
                            temp.add(animal)
                        }
                    }
                    adapter=AnimalsAdapter(temp,this@MainActivity)
                }
                setUpRecyclerView()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }


    private fun setUpRecyclerView(){
        val layoutManager = GridLayoutManager(this,1)
        viewBind.listdata.layoutManager = layoutManager
        viewBind.listdata.adapter = adapter
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                for(animal in GlobalVar.listDataAnimals){
                    if(adapter.listDataAnimals[viewHolder.adapterPosition].id==animal.id){
                        val myIntent = Intent(this@MainActivity, DeleteAnimalsActivity::class.java).apply {
                            putExtra("position", GlobalVar.listDataAnimals.indexOf(animal))
                        }
                        startActivityForResult(myIntent,1)
                        break
                    }
                }
            }
        }).attachToRecyclerView(listdata)
    }

    override fun onCardClick(position: Int) {
//
    }

    override fun onEditClick(id: Int) {
        for(animal in GlobalVar.listDataAnimals){
            if(id==animal.id){
                val myIntent = Intent(this, AddAnimalsActivity::class.java).apply {
                    putExtra("position", GlobalVar.listDataAnimals.indexOf(animal))
                }
                startActivity(myIntent)
                break
            }
        }

    }



}