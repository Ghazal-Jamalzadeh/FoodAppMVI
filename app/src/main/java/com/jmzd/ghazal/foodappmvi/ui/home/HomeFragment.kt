package com.jmzd.ghazal.foodappmvi.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.jmzd.ghazal.foodappmvi.R
import com.jmzd.ghazal.foodappmvi.databinding.FragmentHomeBinding
import com.jmzd.ghazal.foodappmvi.utils.setupListWithAdapter
import com.jmzd.ghazal.foodappmvi.viewmodel.home.HomeIntent
import com.jmzd.ghazal.foodappmvi.viewmodel.home.HomeState
import com.jmzd.ghazal.foodappmvi.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    //Binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    //Other
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            //Lifecycle
            lifecycleScope.launchWhenCreated {

                //call intents
                viewModel.intentChannel.send(HomeIntent.LoadRandomBanner)
                viewModel.intentChannel.send(HomeIntent.LoadFilterLetters)

                //Get data
                viewModel.state.collect { state : HomeState ->
                    when (state) {
                        is HomeState.Idle -> {}
                        is HomeState.FilterLettersLoaded -> {
                            filterSpinner.setupListWithAdapter(state.letters) {
                                lifecycleScope.launchWhenCreated {
                                    //viewModel.intentChannel.send(ListIntent.LoadFoods(it))
                                }
                            }
                        }
                        is HomeState.RandomBannerLoaded -> {
                            if (state.banner != null) {
                                headerImg.load(state.banner.strMealThumb) {
                                    crossfade(true)
                                    crossfade(500)
                                }
                            }
                        }
//                        is ListState.LoadingCategory -> {
//                            homeCategoryLoading.isVisible(true, categoryList)
//                        }
//                        is ListState.CategoriesList -> {
//                            homeCategoryLoading.isVisible(false, categoryList)
//                            categoriesAdapter.setData(state.categories)
//                            categoryList.setupRecyclerView(
//                                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
//                                categoriesAdapter
//                            )
//
//                            categoriesAdapter.setOnItemClickListener {
//                                lifecycleScope.launchWhenCreated {
//                                    viewModel.intentChannel.send(ListIntent.LoadFoodByCategory(it.strCategory!!))
//                                }
//                            }
//                        }
//                        is ListState.LoadingFoods -> {
//                            homeFoodsLoading.isVisible(true, foodsList)
//                        }
//                        is ListState.FoodsList -> {
//                            checkConnectionOrEmpty(false, PageState.NONE)
//                            homeFoodsLoading.isVisible(false, foodsList)
//                            foodsAdapter.setData(state.foods)
//                            foodsList.setupRecyclerView(
//                                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
//                                foodsAdapter
//                            )
//
//                            foodsAdapter.setOnItemClickListener {
//                                val direction = FoodsListFragmentDirections.actionListToDetail(it.idMeal?.toInt()!!)
//                                findNavController().navigate(direction)
//                            }
//                        }
//                        is ListState.Empty -> {
//                            checkConnectionOrEmpty(true, PageState.EMPTY)
//                        }
                        is HomeState.Error -> {
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        _binding = null
    }

}