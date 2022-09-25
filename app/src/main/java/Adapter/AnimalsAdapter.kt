package Adapter

import Interface.CardListener
import Model.Animals
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.uc.week2_0706012110031.R
import kotlinx.android.synthetic.main.activity_animals_item.view.*
import kotlinx.android.synthetic.main.activity_delete_animals.view.*
import com.uc.week2_0706012110031.databinding.ActivityAnimalsItemBinding

class AnimalsAdapter (val listDataAnimals : ArrayList<Animals>, val cardListener: CardListener) :
    RecyclerView.Adapter<AnimalsAdapter.viewHolder>() {


    class viewHolder(val itemView: View, val cardListener: CardListener) :
        RecyclerView.ViewHolder(itemView) {

        val binding = ActivityAnimalsItemBinding.bind(itemView)

        fun setData(data: Animals) {
            binding.namahewan.text = data.nama
            binding.jenishewan.text = data.jenis
            binding.usia.text = data.usia.toString()

            if (data.imageUri.isNotEmpty()) {
                binding.imageAnimal.setImageURI(Uri.parse(data.imageUri))
            }
            binding.cancelbtn.setOnClickListener {
                cardListener.onEditClick(data.id)
            }

            binding.soundbutton.setOnClickListener {
                Toast.makeText(itemView.context, data.animalsound, Toast.LENGTH_SHORT).show();
            }
            binding.foodbutton.setOnClickListener {
                Toast.makeText(itemView.context, data.animalsfood, Toast.LENGTH_SHORT).show();
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.activity_animals_item, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(listDataAnimals[position])
    }

    override fun getItemCount(): Int {
        return listDataAnimals.size
    }
}
