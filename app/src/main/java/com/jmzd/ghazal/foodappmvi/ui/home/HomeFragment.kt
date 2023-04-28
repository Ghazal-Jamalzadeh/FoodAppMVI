package com.jmzd.ghazal.foodappmvi.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.jmzd.ghazal.foodappmvi.R
import com.jmzd.ghazal.foodappmvi.data.model.home.ResponseCategoriesList
import com.jmzd.ghazal.foodappmvi.data.model.home.ResponseCategoriesList.Category
import com.jmzd.ghazal.foodappmvi.databinding.FragmentHomeBinding
import com.jmzd.ghazal.foodappmvi.ui.home.adapters.CategoriesAdapter
import com.jmzd.ghazal.foodappmvi.ui.home.adapters.FoodsAdapter
import com.jmzd.ghazal.foodappmvi.utils.*
import com.jmzd.ghazal.foodappmvi.viewmodel.home.HomeIntent
import com.jmzd.ghazal.foodappmvi.viewmodel.home.HomeState
import com.jmzd.ghazal.foodappmvi.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    //Binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var categoriesAdapter: CategoriesAdapter

    @Inject
    lateinit var foodsAdapter: FoodsAdapter

    //Other
    private val viewModel: HomeViewModel by viewModels()

    enum class PageState { EMPTY, NETWORK, NONE }

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
                viewModel.intentChannel.send(HomeIntent.LoadCategoriesList)
                val rand: String = ('A'..'Z').random().toString()
                viewModel.intentChannel.send(HomeIntent.LoadFoodsByLetter(letter = rand))

                //Get data
                viewModel.state.collect { state: HomeState ->
                    when (state) {
                        is HomeState.Idle -> {
                        }
                        is HomeState.FilterLettersLoaded -> {
                            var spinnerFirstLoad = true
                            filterSpinner.setupListWithAdapter(state.letters) { selectedLetter: String ->
                                lifecycleScope.launchWhenCreated {
                                    if (spinnerFirstLoad) {
                                        spinnerFirstLoad = false
                                    } else {
                                        viewModel.intentChannel.send(HomeIntent.LoadFoodsByLetter(
                                            selectedLetter))
                                    }
                                }
                            }
                            filterSpinner.isSelected = false
                        }
                        is HomeState.RandomBannerLoaded -> {
                            if (state.banner != null) {
                                headerImg.load(state.banner.strMealThumb) {
                                    crossfade(true)
                                    crossfade(500)
                                }
                            }
                        }
                        is HomeState.LoadingCategories -> {
                            homeCategoryLoading.isVisibleGone(true, categoryList)
                        }
                        is HomeState.CategoriesLoaded -> {
                            homeCategoryLoading.isVisibleGone(false, categoryList)
                            categoriesAdapter.setData(state.categories)
                            categoryList.setupRecyclerView(
                                LinearLayoutManager(requireContext(),
                                    LinearLayoutManager.HORIZONTAL,
                                    false),
                                categoriesAdapter
                            )

                            categoriesAdapter.setOnItemClickListener { category: Category ->
                                lifecycleScope.launchWhenCreated {
                                    viewModel.intentChannel.send(HomeIntent.LoadFoodsByCategory(
                                        category.strCategory!!))
                                }
                            }
                        }
                        is HomeState.LoadingFoods -> {
                            homeFoodsLoading.isVisibleInvisible(true, foodsList)
                        }
                        is HomeState.FoodsListLoaded -> {
                            checkConnectionOrEmpty(false, PageState.NONE)
                            homeFoodsLoading.isVisibleInvisible(false, foodsList)
                            foodsAdapter.setData(state.foods)
                            foodsList.setupRecyclerView(
                                LinearLayoutManager(requireContext(),
                                    LinearLayoutManager.HORIZONTAL,
                                    false),
                                foodsAdapter
                            )

                            foodsAdapter.setOnItemClickListener {
                                val direction =
                                    HomeFragmentDirections.actionToDetailFragment(it.idMeal?.toInt()!!)
                                findNavController().navigate(direction)
                            }
                        }
                        is HomeState.Empty -> {
                            checkConnectionOrEmpty(true, PageState.EMPTY)
                        }
                        is HomeState.Error -> {
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
            //Search
            searchEdt.addTextChangedListener {
                if (it.toString().length > 2) {
                    lifecycleScope.launchWhenCreated {
                        viewModel.intentChannel.send(HomeIntent.LoadFoodsBySearch(it.toString()))
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        _binding = null
    }

    private fun checkConnectionOrEmpty(isShownError: Boolean, state: PageState) {
        binding?.apply {
            if (isShownError) {
                homeDisLay.isVisibleInvisible(true, homeContent)
                when (state) {
                    PageState.EMPTY -> {
                        disconnectLay.disImg.setImageResource(R.drawable.box)
                        disconnectLay.disTxt.text = getString(R.string.emptyList)
                    }
                    PageState.NETWORK -> {
                        disconnectLay.disImg.setImageResource(R.drawable.disconnect)
                        disconnectLay.disTxt.text = getString(R.string.checkInternet)
                    }
                    else -> {}
                }
            } else {
                homeDisLay.isVisibleInvisible(false, homeContent)
            }
        }
    }

}