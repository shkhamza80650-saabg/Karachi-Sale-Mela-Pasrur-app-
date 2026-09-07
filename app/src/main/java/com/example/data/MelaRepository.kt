package com.example.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MelaRepository(private val dailyArrivalDao: DailyArrivalDao) {

    val allArrivals: Flow<List<DailyArrivalEntity>> = dailyArrivalDao.getAllArrivals()

    suspend fun seedInitialArrivalsIfEmpty() = withContext(Dispatchers.IO) {
        if (dailyArrivalDao.getCount() == 0) {
            val initialArrivals = listOf(
                DailyArrivalEntity(
                    title = "Imported 7-Pcs Glass Water & Jug Set",
                    category = "Kitchen & Crockery",
                    priceTier = "Rs. 1,200",
                    arrivalDate = "Today's Arrival",
                    description = "Fresh stock of crystal embossed water set including 1 large pitcher and 6 drinking glasses. Available on Ground Floor.",
                    imageResName = "img_shop_interior",
                    timestamp = System.currentTimeMillis()
                ),
                DailyArrivalEntity(
                    title = "Multi-Compartment Ergonomic School Bags",
                    category = "Bags & Pouches",
                    priceTier = "Rs. 600",
                    arrivalDate = "Today's Arrival",
                    description = "Brand new vibrant cartoon and waterproof school backpacks with water bottle side pockets.",
                    imageResName = "img_sale_banner",
                    timestamp = System.currentTimeMillis() - 3600000L
                ),
                DailyArrivalEntity(
                    title = "Melamine 6-Piece Dessert Bowl Sets",
                    category = "Kitchen & Crockery",
                    priceTier = "Rs. 300",
                    arrivalDate = "Yesterday's Stock",
                    description = "Unbreakable floral printed dessert bowls with spoons. Premium Pakistani bazaar quality.",
                    imageResName = "img_shop_interior",
                    timestamp = System.currentTimeMillis() - 86400000L
                ),
                DailyArrivalEntity(
                    title = "Foldable Smart Study & Bed Tables",
                    category = "Plastics & Household",
                    priceTier = "Rs. 600",
                    arrivalDate = "Yesterday's Stock",
                    description = "Lightweight steel leg folding tables with tablet holder and cup holder for students.",
                    imageResName = "img_shop_front",
                    timestamp = System.currentTimeMillis() - 90000000L
                ),
                DailyArrivalEntity(
                    title = "Long-Lasting Fragrance Body Sprays",
                    category = "Cosmetics & Perfumes",
                    priceTier = "Rs. 300",
                    arrivalDate = "Recent Arrival",
                    description = "Assorted French-inspired scents and body sprays for daily freshness.",
                    imageResName = "img_sale_banner",
                    timestamp = System.currentTimeMillis() - 172800000L
                ),
                DailyArrivalEntity(
                    title = "Multi-Tier Plastic Shoe Racks",
                    category = "Plastics & Household",
                    priceTier = "Rs. 1,200",
                    arrivalDate = "Recent Arrival",
                    description = "Easy snap-on 4-tier shoe organizers. Space-saving for entrance and rooms.",
                    imageResName = "img_shop_interior",
                    timestamp = System.currentTimeMillis() - 250000000L
                ),
                DailyArrivalEntity(
                    title = "Airtight Kitchen Container Jar Sets",
                    category = "Kitchen & Crockery",
                    priceTier = "Rs. 120",
                    arrivalDate = "Recent Arrival",
                    description = "Transparent spice and food storage containers with colorful twist caps.",
                    imageResName = "img_shop_interior",
                    timestamp = System.currentTimeMillis() - 300000000L
                )
            )
            dailyArrivalDao.insertAll(initialArrivals)
        }
    }

    suspend fun addArrival(arrival: DailyArrivalEntity) = withContext(Dispatchers.IO) {
        dailyArrivalDao.insertArrival(arrival)
    }

    suspend fun removeArrival(id: Int) = withContext(Dispatchers.IO) {
        dailyArrivalDao.deleteArrivalById(id)
    }
}
