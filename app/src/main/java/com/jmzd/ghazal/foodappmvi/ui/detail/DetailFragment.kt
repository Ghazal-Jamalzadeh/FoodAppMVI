package com.jmzd.ghazal.foodappmvi.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.gson.Gson
import com.jmzd.ghazal.foodappmvi.R
import com.jmzd.ghazal.foodappmvi.data.database.FoodEntity
import com.jmzd.ghazal.foodappmvi.data.model.home.ResponseFoodsList
import com.jmzd.ghazal.foodappmvi.data.model.home.ResponseFoodsList.*
import com.jmzd.ghazal.foodappmvi.databinding.FragmentDetailBinding
import com.jmzd.ghazal.foodappmvi.databinding.FragmentHomeBinding
import com.jmzd.ghazal.foodappmvi.ui.detail.player.PlayerActivity
import com.jmzd.ghazal.foodappmvi.ui.home.HomeFragment
import com.jmzd.ghazal.foodappmvi.ui.home.HomeFragment.PageState
import com.jmzd.ghazal.foodappmvi.ui.home.HomeFragment.PageState.*
import com.jmzd.ghazal.foodappmvi.utils.VIDEO_ID
import com.jmzd.ghazal.foodappmvi.utils.isVisibleGone
import com.jmzd.ghazal.foodappmvi.utils.network.ConnectivityStatus
import com.jmzd.ghazal.foodappmvi.utils.network.NetworkConnectivity
import com.jmzd.ghazal.foodappmvi.viewmodel.detail.DetailIntent
import com.jmzd.ghazal.foodappmvi.viewmodel.detail.DetailState
import com.jmzd.ghazal.foodappmvi.viewmodel.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class DetailFragment : Fragment() {

    //Binding
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var entity: FoodEntity

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    //Other
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private var foodId = 0
    private var isFavorite = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get id
        foodId = args.foodId

        //Check internet
        lifecycleScope.launchWhenCreated {
            networkConnectivity.observe().collect {
                when (it) {
                    ConnectivityStatus.Status.Available -> {
                        checkConnectionOrEmpty(false, NONE)
                        //Send on channel
                        viewModel.detailIntent.send(DetailIntent.LoadDetail(foodId))
                        viewModel.detailIntent.send(DetailIntent.CheckFavourite(foodId))
                    }
                    ConnectivityStatus.Status.Unavailable -> {}
                    ConnectivityStatus.Status.Losing -> {}
                    ConnectivityStatus.Status.Lost -> {
                        checkConnectionOrEmpty(true, NETWORK)
                    }
                }
            }
        }



        binding?.apply {
            //Back
            detailBack.setOnClickListener { lifecycleScope.launch { viewModel.detailIntent.send(DetailIntent.FinishPage) } }

            //Save / Delete
            detailFav.setOnClickListener {
                lifecycleScope.launch {
                    if (!isFavorite) {
                        viewModel.detailIntent.send(DetailIntent.SaveToFavourite(entity =entity))
                    } else {
                        viewModel.detailIntent.send(DetailIntent.RemoveFromFavourite( entity =entity))
                    }
                }
            }

            //Intents
            lifecycleScope.launch {
                //Load data
                viewModel.state.collect { state ->
                    when (state) {
                        is DetailState.FinishPage -> findNavController().navigateUp()
                        is DetailState.Loading -> detailLoading.isVisibleGone(true, detailContentLay)

                        is DetailState.DetailLoaded -> {
                            detailLoading.isVisibleGone(false, detailContentLay)
                            //Set data
                            state.detail.meals?.get(0)?.let { itMeal : Meal->
                                //Entity
                                entity.id = itMeal.idMeal!!.toInt()
                                entity.title = itMeal.strMeal.toString()
                                entity.img = itMeal.strMealThumb.toString()
                                //Update UI
                                foodCoverImg.load(itMeal.strMealThumb) {
                                    crossfade(true)
                                    crossfade(500)
                                }
                                foodCategoryTxt.text = itMeal.strCategory
                                foodAreaTxt.text = itMeal.strArea
                                foodTitleTxt.text = itMeal.strMeal
                                foodDescTxt.text = itMeal.strInstructions
                                //Play
                                if (itMeal.strYoutube != null) {
                                    foodPlayImg.visibility = View.VISIBLE
                                    foodPlayImg.setOnClickListener {
                                        val videoId = itMeal.strYoutube.split("=")[1]
                                        Intent(requireContext(), PlayerActivity::class.java).also {
                                            it.putExtra(VIDEO_ID, videoId)
                                            startActivity(it)
                                        }
                                    }
                                } else {
                                    foodPlayImg.visibility = View.GONE
                                }
                                //Source
                                if (itMeal.strSource != null) {
                                    foodSourceImg.visibility = View.VISIBLE
                                    foodSourceImg.setOnClickListener {
                                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(itMeal.strSource)))
                                    }
                                } else {
                                    foodSourceImg.visibility = View.GONE
                                }
                                //Json Array
                                val jsonData = JSONObject(Gson().toJson(state.detail))
                                val meals = jsonData.getJSONArray("meals")
                                val meal = meals.getJSONObject(0)
                                //Ingredient
                                for (i in 1..15) {
                                    val ingredient = meal.getString("strIngredient$i")
                                    if (ingredient.isNullOrEmpty().not()) {
                                        ingredientsTxt.append("$ingredient\n")
                                    }
                                }
                                //Measure
                                for (i in 1..15) {
                                    val measure = meal.getString("strMeasure$i")
                                    if (measure.isNullOrEmpty().not()) {
                                        measureTxt.append("$measure\n")
                                    }
                                }
                            }
                        }
                        is DetailState.AddedToFavourite -> {

                        }
                        is DetailState.RemovedFromFavourite -> {

                        }
                        is DetailState.FavouriteStatusLoaded -> {
                            isFavorite = state.isFavourite
                            if (state.isFavourite) {
                                detailFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.tartOrange))
                            } else {
                                detailFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                            }
                        }
                        is DetailState.Error -> {
                            detailLoading.isVisibleGone(false, detailContentLay)
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }
    }

    private fun checkConnectionOrEmpty(isShownError: Boolean, state: PageState) {
        binding?.apply {
            if (isShownError) {
                homeDisLay.isVisibleGone(true, detailContentLay)
                when (state) {
                    EMPTY -> {
                        disconnectLay.disImg.setImageResource(R.drawable.box)
                        disconnectLay.disTxt.text = getString(R.string.emptyList)
                    }
                    NETWORK -> {
                        disconnectLay.disImg.setImageResource(R.drawable.disconnect)
                        disconnectLay.disTxt.text = getString(R.string.checkInternet)
                    }
                    else -> {}
                }
            } else {
                homeDisLay.isVisibleGone(false, detailContentLay)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}