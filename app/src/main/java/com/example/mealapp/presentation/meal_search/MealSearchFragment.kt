package com.example.mealapp.presentation.meal_search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.mealapp.R
import com.example.mealapp.databinding.FragmentMealSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealSearchFragment : Fragment() {

    private var _binding : FragmentMealSearchBinding? = null
    private val mealSearchViewModel : MealSearchViewModel by viewModels()
    private val mealSearchAdapter = MealAdapter()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMealSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initAdapter()
        setUpObserver()
    }

    private fun initAdapter() {
        binding.searchRV.apply {
            adapter = mealSearchAdapter
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }

        mealSearchAdapter.mealCallBack = {meal ->
            Log.d("mealCallBack", "initAdapter: $meal")
            findNavController().navigate(
                MealSearchFragmentDirections.actionMealSearchFragmentToMealDetailsFragment(meal.id ?: "")
            )
        }
    }

    private fun setUpObserver() {
        lifecycle.coroutineScope.launch {
            mealSearchViewModel.mealSearchList.collect{it ->
                if(it.loading){
                    binding.progressMealSearch.visibility = View.VISIBLE
                    binding.noDataFound.visibility = View.GONE
                    binding.searchRV.visibility = View.GONE
                }
                if(it.error.isNotBlank()){
                    binding.progressMealSearch.visibility = View.GONE
                    binding.noDataFound.visibility = View.VISIBLE
                    binding.searchRV.visibility = View.GONE
                }

                it.list?.let {
                    if(it.isEmpty()){
                        binding.progressMealSearch.visibility = View.GONE
                        binding.noDataFound.visibility = View.VISIBLE
                        binding.searchRV.visibility = View.GONE
                    }else{
                        binding.progressMealSearch.visibility = View.GONE
                        binding.noDataFound.visibility = View.GONE
                        binding.searchRV.visibility = View.VISIBLE
                        mealSearchAdapter.setContentList(it.toMutableList())
                    }

                }

            }
        }

    }

    private fun initView() {
        binding.searchMeal.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    mealSearchViewModel.getMealList(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}