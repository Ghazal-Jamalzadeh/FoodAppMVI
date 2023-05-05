package com.jmzd.ghazal.foodappmvi.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmzd.ghazal.foodappmvi.R
import com.jmzd.ghazal.foodappmvi.data.database.FoodEntity
import com.jmzd.ghazal.foodappmvi.databinding.FragmentFavoritesBinding
import com.jmzd.ghazal.foodappmvi.databinding.FragmentHomeBinding
import com.jmzd.ghazal.foodappmvi.utils.isVisibleGone
import com.jmzd.ghazal.foodappmvi.utils.setupRecyclerView
import com.jmzd.ghazal.foodappmvi.viewmodel.favourites.FavouritesIntent
import com.jmzd.ghazal.foodappmvi.viewmodel.favourites.FavouritesState
import com.jmzd.ghazal.foodappmvi.viewmodel.favourites.FavouritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    //Binding
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    //Other
    private val viewModel: FavouritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            //Scope
            lifecycleScope.launchWhenCreated {
                //Send
                viewModel.favoriteIntent.send(FavouritesIntent.LoadFavourites)
                //Load data
                viewModel.state.collect { state ->
                    when (state) {
                        is FavouritesState.Empty -> {

                            emptyLay.isVisibleGone(true, favoriteList)

                      /*      statusLay.disImg.setImageResource(R.drawable.box)
                            statusLay.disTxt.text = getString(R.string.emptyList)*/
                        }
                        is FavouritesState.FavouritesLoaded -> {
                            emptyLay.isVisibleGone(false, favoriteList)
                            favoriteAdapter.setData(state.favourites)
                            favoriteList.setupRecyclerView(LinearLayoutManager(requireContext()),
                                favoriteAdapter)

                            favoriteAdapter.setOnItemClickListener { food : FoodEntity ->
                                val direction =
                                    FavoritesFragmentDirections.actionToDetailFragment(food.id)
                                findNavController().navigate(direction)
                            }
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