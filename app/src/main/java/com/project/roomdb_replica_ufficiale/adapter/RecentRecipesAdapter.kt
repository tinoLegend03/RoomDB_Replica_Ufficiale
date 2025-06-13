package com.project.roomdb_replica_ufficiale.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.roomdb_replica_ufficiale.data.ricetta.Ricetta
import com.project.roomdb_replica_ufficiale.databinding.CustomRowBinding
import com.project.roomdb_replica_ufficiale.fragments.home.HomeFragmentDirections
import com.project.roomdb_replica_ufficiale.fragments.list.ListFragmentDirections
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class RecentRecipesAdapter: RecyclerView.Adapter<RecentRecipesAdapter.MyViewHolder>() {

    private var recipeList = emptyList<Ricetta>()

    class MyViewHolder(val binding: CustomRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = recipeList[position]
        holder.binding.nomeTxt.text = currentItem.nomeRicetta
        holder.binding.durataTxt.text = currentItem.durata.toString()
        holder.binding.livelloTxt.text = currentItem.livello
        holder.binding.categoriaTxt.text = currentItem.categoria
        holder.binding.descrizioneTxt.text = currentItem.descrizione
        holder.binding.ultimaModificaTxt.text = formattaData(currentItem.ultimaModifica)
        holder.binding.ultimaEsecuzioneTxt.text = formattaData(currentItem.ultimaEsecuzione)
        holder.binding.countTxt.text = currentItem.count.toString()

        holder.binding.rowLayout.setOnClickListener{
            //val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    fun setData(ricetta: List<Ricetta>){
        this.recipeList = ricetta
        notifyDataSetChanged()
    }

    fun formattaData(timestamp: Long): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'alle' HH:mm")
        val zona = ZoneId.systemDefault() // oppure ZoneId.of("Europe/Rome")
        val data = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zona)
        return data.format(formatter)
    }

}