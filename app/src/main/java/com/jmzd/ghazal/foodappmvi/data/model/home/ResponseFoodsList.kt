package com.jmzd.ghazal.foodappmvi.data.model.home


import com.google.gson.annotations.SerializedName

data class ResponseFoodsList(
    @SerializedName("meals")
    val meals: List<Meal>?
) {
    data class Meal(
        @SerializedName("dateModified")
        val dateModified: Any?, // null
        @SerializedName("idMeal")
        val idMeal: String?, // 52988
        @SerializedName("strArea")
        val strArea: String?, // British
        @SerializedName("strCategory")
        val strCategory: String?, // Dessert
        @SerializedName("strCreativeCommonsConfirmed")
        val strCreativeCommonsConfirmed: Any?, // null
        @SerializedName("strDrinkAlternate")
        val strDrinkAlternate: Any?, // null
        @SerializedName("strImageSource")
        val strImageSource: Any?, // null
        @SerializedName("strIngredient1")
        val strIngredient1: String?, // Almonds
        @SerializedName("strIngredient10")
        val strIngredient10: String?, // Brandy
        @SerializedName("strIngredient11")
        val strIngredient11: String?, // Butter
        @SerializedName("strIngredient12")
        val strIngredient12: String?,
        @SerializedName("strIngredient13")
        val strIngredient13: String?,
        @SerializedName("strIngredient14")
        val strIngredient14: String?,
        @SerializedName("strIngredient15")
        val strIngredient15: String?,
        @SerializedName("strIngredient16")
        val strIngredient16: String?,
        @SerializedName("strIngredient17")
        val strIngredient17: String?,
        @SerializedName("strIngredient18")
        val strIngredient18: String?,
        @SerializedName("strIngredient19")
        val strIngredient19: String?,
        @SerializedName("strIngredient2")
        val strIngredient2: String?, // Bramley Apples
        @SerializedName("strIngredient20")
        val strIngredient20: String?,
        @SerializedName("strIngredient3")
        val strIngredient3: String?, // Candied Peel
        @SerializedName("strIngredient4")
        val strIngredient4: String?, // Nutmeg
        @SerializedName("strIngredient5")
        val strIngredient5: String?, // Raisins
        @SerializedName("strIngredient6")
        val strIngredient6: String?, // Plain Flour
        @SerializedName("strIngredient7")
        val strIngredient7: String?, // Breadcrumbs
        @SerializedName("strIngredient8")
        val strIngredient8: String?, // Muscovado Sugar
        @SerializedName("strIngredient9")
        val strIngredient9: String?, // Eggs
        @SerializedName("strInstructions")
        val strInstructions: String?, // Get everything for the pudding prepared. Chop the almonds coarsely. Peel, core and chop the cooking apples. Sharpen your knife and chop the candied peel. (You can chop the almonds and apples in a food processor, but the peel must be done by hand.) Grate three quarters of the nutmeg (sounds a lot but it's correct).Mix the almonds, apples, candied peel, nutmeg, raisins, flour, breadcrumbs, light muscovado sugar, eggs and 2 tbsp brandy or cognac in a large bowl. Holding the butter in its wrapper, grate a quarter of it into the bowl, then stir everything together. Repeat until all the butter is grated, then stir for 3-4 mins – the mixture is ready when it subsides slightly after each stir. Ask the family to stir too, and get everyone to make a wish. Generously butter two 1.2 litre bowls and put a circle of baking parchment in the bottom of each. Pack in the pudding mixture. Cover with a double layer of baking parchment, pleating it to allow for expansion, then tie with string (keep the paper in place with a rubber band while tying). Trim off any excess paper. Now stand each bowl on a large sheet of foil and bring the edges up over the top, then put another sheet of foil over the top and bring it down underneath to make a double package (this makes the puddings watertight). Tie with more string, and make a handle for easy lifting in and out of the pan. Watch our video to see how to tie up a pudding correctly.Boil or oven steam the puddings for 8 hrs, topping up with water as necessary. Remove from the pans and leave to cool overnight. When cold, discard the messy wrappings and re-wrap in new baking parchment, foil and string. Store in a cool, dry place until Christmas. To make the brandy butter, cream the butter with the orange zest and icing sugar. Gradually beat in the brandy or cognac and chopped stem ginger. Put in a small bowl, fork the top attractively and put in the fridge to set. The butter will keep for a week in the fridge, or it can be frozen for up to six weeks. On Christmas Day, boil or oven steam for 1 hr. Unwrap and turn out. To flame, warm 3-4 tbsp brandy in a small pan, pour it over the pudding and set light to it.
        @SerializedName("strMeal")
        val strMeal: String?, // Classic Christmas pudding
        @SerializedName("strMealThumb")
        val strMealThumb: String?, // https://www.themealdb.com/images/media/meals/1d85821576790598.jpg
        @SerializedName("strMeasure1")
        val strMeasure1: String?, // 50g
        @SerializedName("strMeasure10")
        val strMeasure10: String?, // 2 tbs
        @SerializedName("strMeasure11")
        val strMeasure11: String?, // 250g
        @SerializedName("strMeasure12")
        val strMeasure12: String?,
        @SerializedName("strMeasure13")
        val strMeasure13: String?,
        @SerializedName("strMeasure14")
        val strMeasure14: String?,
        @SerializedName("strMeasure15")
        val strMeasure15: String?,
        @SerializedName("strMeasure16")
        val strMeasure16: String?,
        @SerializedName("strMeasure17")
        val strMeasure17: String?,
        @SerializedName("strMeasure18")
        val strMeasure18: String?,
        @SerializedName("strMeasure19")
        val strMeasure19: String?,
        @SerializedName("strMeasure2")
        val strMeasure2: String?, // 2 large
        @SerializedName("strMeasure20")
        val strMeasure20: String?,
        @SerializedName("strMeasure3")
        val strMeasure3: String?, // 200g
        @SerializedName("strMeasure4")
        val strMeasure4: String?, // 1 whole
        @SerializedName("strMeasure5")
        val strMeasure5: String?, // 1kg
        @SerializedName("strMeasure6")
        val strMeasure6: String?, // 140g
        @SerializedName("strMeasure7")
        val strMeasure7: String?, // 100g 
        @SerializedName("strMeasure8")
        val strMeasure8: String?, // 100g 
        @SerializedName("strMeasure9")
        val strMeasure9: String?, // 3 Large
        @SerializedName("strSource")
        val strSource: String?, // https://www.bbcgoodfood.com/recipes/classic-christmas-pudding
        @SerializedName("strTags")
        val strTags: String?, // Christmas
        @SerializedName("strYoutube")
        val strYoutube: String? // https://www.youtube.com/watch?v=Pb_lJxL1vtk
    )
}