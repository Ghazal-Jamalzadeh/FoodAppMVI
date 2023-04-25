package com.jmzd.ghazal.foodappmvi.data.repository

import android.util.Log
import com.jmzd.ghazal.foodappmvi.data.model.home.ResponseCategoriesList
import com.jmzd.ghazal.foodappmvi.data.model.home.ResponseFoodsList
import com.jmzd.ghazal.foodappmvi.data.server.ApiServices
import com.jmzd.ghazal.foodappmvi.utils.MyResponse
import com.jmzd.ghazal.foodappmvi.utils.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject


/*فقط api رندوم را ا حالت عادی میزنیم بقیه api ها را با حالت myResponse میزنیم که اصولی تر است */
class HomeRepository @Inject constructor(private val api : ApiServices) {

}