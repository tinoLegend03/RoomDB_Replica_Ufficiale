package com.project.roomdb_replica_ufficiale.fragments.update

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.roomdb_replica_ufficiale.R
import com.project.roomdb_replica_ufficiale.data.ricetta.Ricetta
import com.project.roomdb_replica_ufficiale.data.ricetta.RicettaViewModel
import com.project.roomdb_replica_ufficiale.databinding.FragmentUpdateBinding


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mRecipeViewModel: RicettaViewModel

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        mRecipeViewModel = ViewModelProvider(this).get(RicettaViewModel::class.java)

        binding.updateRecipeNameEt.setText(args.currentRecipe.nomeRicetta)
        binding.updateDurationEt.setText(args.currentRecipe.durata.toString())
        binding.updateRecipeDescriptionEt.setText(args.currentRecipe.descrizione)
        //binding.updateRecipeNameEt.setText(args.currentRecipe.nomeRicetta)
        //binding.updateRecipeNameEt.setText(args.currentRecipe.nomeRicetta)
        //binding.updateRecipeNameEt.setText(args.currentRecipe.nomeRicetta)

        ArrayAdapter.createFromResource(requireContext(), R.array.level_array,
            android.R.layout.simple_spinner_item).also { spinnerAdapter ->
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.updateRecipeLevelEt.adapter = spinnerAdapter
        }
        ArrayAdapter.createFromResource(requireContext(), R.array.category_array,
            android.R.layout.simple_spinner_item).also { spinnerAdapter ->
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.updateRecipeCategoryEt.adapter = spinnerAdapter
        }

        for(i in 0 until binding.updateRecipeLevelEt.count){
            val toSelect = binding.updateRecipeLevelEt.getItemAtPosition(i)
            if (toSelect == args.currentRecipe.livello){
                binding.updateRecipeLevelEt.setSelection(i)
            }
        }
        for(i in 0 until binding.updateRecipeCategoryEt.count){
            val toSelect = binding.updateRecipeCategoryEt.getItemAtPosition(i)

            if (toSelect == args.currentRecipe.categoria){
                binding.updateRecipeCategoryEt.setSelection(i)
            }
        }

        binding.updateBtn.setOnClickListener{
            updateItem()
        }

        binding.deleteBtn.setOnClickListener{
            deleteRecipe()
        }

        /*binding.deleteBtn.setOnClickListener{
            deleteUser()
        }*/

        return view


    }

    private fun updateItem(){
        val nomeRicetta = binding.updateRecipeNameEt.text.toString()
        val durata = Integer.parseInt(binding.updateDurationEt.text.toString())
        val descrizione = binding.updateRecipeDescriptionEt.text.toString()

        val posLivello = binding.updateRecipeLevelEt.lastVisiblePosition
        val posCategoria = binding.updateRecipeCategoryEt.lastVisiblePosition

        if(posLivello == 0 || posCategoria == 0) {
            Toast.makeText(requireContext(), "Please fill put all fields.", Toast.LENGTH_LONG).show()
            return
        }

        val livello = binding.updateRecipeLevelEt.selectedItem.toString()
        val categoria = binding.updateRecipeCategoryEt.selectedItem.toString()

        if(inputCheck(nomeRicetta, binding.updateDurationEt.text, livello, categoria, descrizione)){
            //Create recipe Object
            val testList = listOf("test1", "test2")
            val updatedRecipe = Ricetta(args.currentRecipe.nomeRicetta, durata, livello, categoria, descrizione, System.currentTimeMillis(), args.currentRecipe.ultimaEsecuzione, args.currentRecipe.count, testList)
            //Update Current Recipe
            mRecipeViewModel.aggiornaRicetta(updatedRecipe)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            //Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(nomeRicetta: String, durata: Editable, livello: String, categoria: String, descrizione: String): Boolean{
        return !(TextUtils.isEmpty(nomeRicetta) && durata.isEmpty() && TextUtils.isEmpty(livello) && TextUtils.isEmpty(categoria) && TextUtils.isEmpty(descrizione))
    }


    private fun deleteRecipe() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _->
            mRecipeViewModel.eliminaRicetta(args.currentRecipe)
            Toast.makeText(requireContext(), "Successfully removed: ${args.currentRecipe.nomeRicetta}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_, _-> }
        builder.setTitle("Delete ${args.currentRecipe.nomeRicetta}?")
        builder.setMessage("Are you sure you want to delete ${args.currentRecipe.nomeRicetta}")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}