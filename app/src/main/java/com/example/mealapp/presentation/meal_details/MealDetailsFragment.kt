package com.example.mealapp.presentation.meal_details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import com.example.mealapp.R
import com.example.mealapp.databinding.FragmentMealDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealDetailsFragment : Fragment() {

    private var _binding : FragmentMealDetailsBinding? = null
    private val binding get() = _binding!!
    private val mealDetailsViewModel : MealDetailsViewModel by viewModels()
    private val args : MealDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMealDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setUpObserver()
    }

    private fun initView() {
        args.mealID?.let {id ->
            Log.d("mealID", "initView: $id")
            mealDetailsViewModel.getMealDetails(id)
        }
    }

    private fun setUpObserver() {
        lifecycle.coroutineScope.launch {
            mealDetailsViewModel.mealDetails.collect{
                if(it.loading){}
                if(it.error.isNotBlank()) Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()

                it.mealData?.let {
                    binding.mealDetails = it
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}