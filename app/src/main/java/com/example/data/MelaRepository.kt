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
                    title = "Gold-Plated Bridal Artificial Jewellery Set",
                    category = "Jewellery & Accessories",
                    priceTier = "Rs. 600",
                    arrivalDate = "Today's Arrival",
                    description = "Dazzling Pakistani artificial bridal jewellery set with gold-plated Kundan necklace, matching jhumkas, and bangles.",
                    imageResName = "img_jewellery_collection",
                    timestamp = System.currentTimeMillis()
                ),
                DailyArrivalEntity(
                    title = "Imported 7-Pcs Glass Water & Jug Set",
                    category = "Kitchen & Crockery",
                    priceTier = "Rs. 1,200",
                    arrivalDate = "Today's Arrival",
                    description = "Fresh stock of crystal embossed water set including 1 large pitcher and 6 drinking glasses. Available on Ground Floor.",
                    imageResName = "img_crockery_items",
                    timestamp = System.currentTimeMillis() - 1800000L
                ),
                DailyArrivalEntity(
                    title = "Multi-Compartment Ergonomic School Bags",
                    category = "Bags & Pouches",
                    priceTier = "Rs. 600",
                    arrivalDate = "Today's Arrival",
                    description = "Brand new vibrant cartoon and waterproof school backpacks with water bottle side pockets.",
                    imageResName = "img_bags_items",
                    timestamp = System.currentTimeMillis() - 3600000L
                ),
                DailyArrivalEntity(
                    title = "Melamine 6-Piece Dessert Bowl Sets",
                    category = "Kitchen & Crockery",
                    priceTier = "Rs. 300",
                    arrivalDate = "Yesterday's Stock",
                    description = "Unbreakable floral printed dessert bowls with spoons. Premium Pakistani bazaar quality.",
                    imageResName = "img_crockery_items",
                    timestamp = System.currentTimeMillis() - 86400000L
                ),
                DailyArrivalEntity(
                    title = "Durable Plastic Crockery & Basin Set (پلاسٹک برتن)",
                    category = "Plastics & Household",
                    priceTier = "Rs. 300",
                    arrivalDate = "Yesterday's Stock",
                    description = "Colorful unbreakable plastic washing basins, buckets, bowls, and strainer baskets for kitchen & laundry.",
                    imageResName = "img_plastic_items",
                    timestamp = System.currentTimeMillis() - 90000000L
                ),
                DailyArrivalEntity(
                    title = "Cotton Hosiery & Winter Socks Pack (ہوزری اور جرابیں)",
                    category = "Textiles & Linens",
                    priceTier = "Rs. 300",
                    arrivalDate = "Yesterday's Stock",
                    description = "Comfortable soft cotton hosiery socks, vests, and daily wear textiles for the whole family.",
                    imageResName = "img_hosiery_items",
                    timestamp = System.currentTimeMillis() - 95000000L
                ),
                DailyArrivalEntity(
                    title = "French Fragrance Body Sprays & Perfumes (کاسمیٹکس)",
                    category = "Cosmetics & Perfumes",
                    priceTier = "Rs. 300",
                    arrivalDate = "Recent Arrival",
                    description = "Assorted French-inspired scents, perfumes, and body sprays for daily freshness.",
                    imageResName = "img_cosmetics_items",
                    timestamp = System.currentTimeMillis() - 172800000L
                ),
                DailyArrivalEntity(
                    title = "Multi-Tier Plastic Storage Baskets & Racks (پلاسٹک آئٹمز)",
                    category = "Plastics & Household",
                    priceTier = "Rs. 600",
                    arrivalDate = "Recent Arrival",
                    description = "Easy snap-on plastic organizers and multipurpose utility crates. High quality durable plastic.",
                    imageResName = "img_plastic_items",
                    timestamp = System.currentTimeMillis() - 250000000L
                ),
                DailyArrivalEntity(
                    title = "Airtight Kitchen Container Jar Sets",
                    category = "Kitchen & Crockery",
                    priceTier = "Rs. 120",
                    arrivalDate = "Recent Arrival",
                    description = "Transparent spice and food storage containers with colorful twist caps.",
                    imageResName = "img_crockery_items",
                    timestamp = System.currentTimeMillis() - 300000000L
                )
            )
            dailyArrivalDao.insertAll(initialArrivals)
        }
    }

    suspend fun addArrival(arrival: DailyArrivalEntity) = withContext(Dispatchers.IO) {
        dailyArrivalDao.insertArrival(arrival)
    }

    suspend fun updateArrivalImage(id: Int, newImageUri: String) = withContext(Dispatchers.IO) {
        dailyArrivalDao.updateArrivalImage(id, newImageUri)
    }

    suspend fun removeArrival(id: Int) = withContext(Dispatchers.IO) {
        dailyArrivalDao.deleteArrivalById(id)
    }
}
