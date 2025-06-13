package com.project.roomdb_replica_ufficiale.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.roomdb_replica_ufficiale.R
import com.project.roomdb_replica_ufficiale.data.ricetta.Ricetta

class RecipeAdapter(
    private val recipes: List<Ricetta>,
    private val onItemClickListener: (Ricetta) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recent_recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)

        // Imposta il click listener sull'intero elemento
        holder.itemView.setOnClickListener {
            onItemClickListener(recipe)
        }
    }

    override fun getItemCount(): Int = recipes.size

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeName: TextView = itemView.findViewById(R.id.recipe_name)
        private val recipeDetails: TextView = itemView.findViewById(R.id.recipe_details)
        private val recipeDate: TextView = itemView.findViewById(R.id.recipe_date)

        fun bind(recipe: Ricetta) {
            recipeName.text = recipe.nomeRicetta
            recipeDetails.text = "${recipe.durata} min • ${recipe.livello}"

            // Formatta la data come "Ultima volta: 1 giorno fa"
            val diff = System.currentTimeMillis() - recipe.ultimaEsecuzione
            val daysAgo = java.util.concurrent.TimeUnit.MILLISECONDS.toDays(diff)
            recipeDate.text = "Ultima volta: $daysAgo giorni fa"
            }
        }
}





/*class RecipeAdapter(
    private val recipes: List<Ricetta>,
    private val onItemClickListener: (Ricetta) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recent_recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)

        // Imposta il click listener sull'intero elemento
        holder.itemView.setOnClickListener {
            onItemClickListener(recipe)
        }
    }

    override fun getItemCount(): Int = recipes.size

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeName: TextView = itemView.findViewById(R.id.recipe_name)
        private val recipeDetails: TextView = itemView.findViewById(R.id.recipe_details)
        private val recipeDate: TextView = itemView.findViewById(R.id.recipe_date)

        fun bind(recipe: Ricetta) {
            recipeName.text = recipe.nomeRicetta
            recipeDetails.text = "${recipe.durata} min • ${recipe.livello}"

            // Formatta la data come "Ultima volta: 1 giorno fa"
            val diff = System.currentTimeMillis() - recipe.ultimaEsecuzione
            val daysAgo = java.util.concurrent.TimeUnit.MILLISECONDS.toDays(diff)
            recipeDate.text = "Ultima volta: $daysAgo giorni fa"
            }
        }
}*/