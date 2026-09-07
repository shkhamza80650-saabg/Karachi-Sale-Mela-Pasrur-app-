package com.example.data

data class VarietyItem(
    val id: Int,
    val name: String,
    val category: String,
    val priceTier: String,
    val description: String
)

object VarietiesData {
    val categories = listOf(
        "All Items",
        "Kitchen & Crockery",
        "Bags & Pouches",
        "Toys & Kids",
        "Home Decor & Clocks",
        "Plastics & Household",
        "Cosmetics & Perfumes",
        "Textiles & Linens"
    )

    val allVarieties: List<VarietyItem> = listOf(
        // Kitchen & Crockery
        VarietyItem(1, "Plastic Crockery", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Durable food-grade plates, bowls, cups, and trays"),
        VarietyItem(2, "Bowl Sets", "Kitchen & Crockery", "Rs. 300 / Rs. 600", "Serving bowls, mixing bowls, and dessert bowl sets"),
        VarietyItem(3, "Melamine Bowl Sets", "Kitchen & Crockery", "Rs. 300 / Rs. 600", "Unbreakable elegant melamine printed bowl sets"),
        VarietyItem(4, "Steel Items", "Kitchen & Crockery", "Rs. 300 / Rs. 600", "Stainless steel utensils, dabbas, spoons, and plates"),
        VarietyItem(5, "Dinner Items", "Kitchen & Crockery", "Rs. 600 / Rs. 1,200", "Complete dinner plates, platters, and dining sets"),
        VarietyItem(6, "Melamine Items", "Kitchen & Crockery", "Rs. 300 / Rs. 600", "Premium printed melamine dinnerware and trays"),
        VarietyItem(7, "Plastic Jugs", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Heavy-duty plastic water jugs with airtight lids"),
        VarietyItem(8, "Crystal Jugs", "Kitchen & Crockery", "Rs. 600 / Rs. 1,200", "Sparkling embossed crystal glass jugs for guests"),
        VarietyItem(9, "Printed Jugs", "Kitchen & Crockery", "Rs. 300 / Rs. 600", "Floral and geometric printed stylish water jugs"),
        VarietyItem(10, "Glass", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Crystal clear drinking glasses and tumblers"),
        VarietyItem(11, "Jugs", "Kitchen & Crockery", "Rs. 300 / Rs. 600", "Assorted beverage and water serving jugs"),
        VarietyItem(12, "Cups", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Ceramic, melamine, and glass tea and coffee cups"),
        VarietyItem(13, "Mugs", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Large ceramic, travel, and insulated coffee mugs"),
        VarietyItem(14, "China Items", "Kitchen & Crockery", "Rs. 600 / Rs. 1,200", "Fine bone china teapots, saucers, and decorative plates"),
        VarietyItem(15, "Cutlery", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Stainless steel spoon, fork, and butter knife sets"),
        VarietyItem(16, "Kitchen Items", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Peelers, graters, tongs, sieves, and kitchen tools"),
        VarietyItem(17, "Glass Cake Sets", "Kitchen & Crockery", "Rs. 600 / Rs. 1,200", "Domed glass cake stands and serving platter sets"),
        VarietyItem(18, "Glass Water Sets", "Kitchen & Crockery", "Rs. 600 / Rs. 1,200", "7-piece complete glass water jug and 6 glasses sets"),
        VarietyItem(19, "Glass Custard Sets", "Kitchen & Crockery", "Rs. 600 / Rs. 1,200", "Dessert and custard serving bowl with small cups"),
        VarietyItem(20, "Water Bottles", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Gym, fridge, and school sports water bottles"),
        VarietyItem(21, "School Tiffin", "Kitchen & Crockery", "Rs. 300 / Rs. 600", "Leak-proof compartmentalized kids lunch boxes"),
        VarietyItem(22, "Jars", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Airtight spice, pickle, and dry fruit glass jars"),
        VarietyItem(23, "Plastic Jars", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Transparent plastic kitchen storage container sets"),

        // Bags & Pouches
        VarietyItem(24, "Handbags", "Bags & Pouches", "Rs. 600 / Rs. 1,200", "Trendy shoulder bags, totes, and party handbags"),
        VarietyItem(25, "Pouches", "Bags & Pouches", "Rs. 120 / Rs. 300", "Makeup pouches, coin wallets, and stationery kits"),
        VarietyItem(26, "School Bags", "Bags & Pouches", "Rs. 600 / Rs. 1,200", "Heavy-duty ergonomic school backpacks for children"),
        VarietyItem(27, "Baby Bags", "Bags & Pouches", "Rs. 600 / Rs. 1,200", "Multi-pocket mother diaper and baby essential bags"),
        VarietyItem(28, "Teddy Bear Bags", "Bags & Pouches", "Rs. 300 / Rs. 600", "Adorable plush teddy bear toddler backpacks"),

        // Toys & Kids
        VarietyItem(29, "Toys", "Toys & Kids", "Rs. 120 / Rs. 300 / Rs. 600", "Friction cars, dolls, musical toys, and action figures"),
        VarietyItem(30, "Teddy Bears", "Toys & Kids", "Rs. 300 / Rs. 600 / Rs. 1,200", "Soft fluffy plush stuffed teddy bears in all sizes"),
        VarietyItem(31, "Chunki", "Toys & Kids", "Rs. 120", "Fun traditional kids trinkets, whistles, and play toys"),

        // Home Decor & Clocks
        VarietyItem(32, "Decoration Pieces", "Home Decor & Clocks", "Rs. 300 / Rs. 600 / Rs. 1,200", "Vases, figurines, Islamic calligraphy, and showpieces"),
        VarietyItem(33, "Artificial Flowers", "Home Decor & Clocks", "Rs. 120 / Rs. 300", "Vibrant silk roses, orchids, and bouquet bunches"),
        VarietyItem(34, "Garlands", "Home Decor & Clocks", "Rs. 120 / Rs. 300", "Decorative floral and festive door/wall hanging garlands"),
        VarietyItem(35, "Glass Mirrors", "Home Decor & Clocks", "Rs. 300 / Rs. 600", "Framed vanity, wall, and dressing table mirrors"),
        VarietyItem(36, "Wall Clocks", "Home Decor & Clocks", "Rs. 600 / Rs. 1,200", "Silent sweep quartz modern and antique wall clocks"),
        VarietyItem(37, "Fancy Items", "Home Decor & Clocks", "Rs. 300 / Rs. 600", "Glitter showpieces, candle holders, and gifts"),
        VarietyItem(38, "Wood Items", "Home Decor & Clocks", "Rs. 300 / Rs. 600 / Rs. 1,200", "Carved wooden handicrafts, tissue boxes, and shelves"),

        // Plastics & Household Furniture
        VarietyItem(39, "Smart Tables", "Plastics & Household", "Rs. 600 / Rs. 1,200", "Foldable laptop, study, and bed dining smart tables"),
        VarietyItem(40, "Smart Chairs", "Plastics & Household", "Rs. 600 / Rs. 1,200", "Sturdy portable plastic stools and smart folding chairs"),
        VarietyItem(41, "Buckets", "Plastics & Household", "Rs. 300 / Rs. 600", "Heavy unbreakable bathroom and cleaning buckets"),
        VarietyItem(42, "Baskets", "Plastics & Household", "Rs. 120 / Rs. 300", "Fruit baskets, laundry bins, and organizer baskets"),
        VarietyItem(43, "Dustbins", "Plastics & Household", "Rs. 120 / Rs. 300 / Rs. 600", "Pedal bins, swing-top bins, and room wastebaskets"),
        VarietyItem(44, "Hangers", "Plastics & Household", "Rs. 120 / Rs. 300", "Velvet and heavy plastic wardrobe clothing hangers"),
        VarietyItem(45, "Shoe Racks", "Plastics & Household", "Rs. 600 / Rs. 1,200", "Multi-tier lightweight space-saving shoe organizers"),
        VarietyItem(46, "Churi Stands", "Plastics & Household", "Rs. 300 / Rs. 600", "Rotating acrylic and wooden bangle display stands"),
        VarietyItem(47, "Wipers", "Plastics & Household", "Rs. 120 / Rs. 300", "Floor wipers with durable silicone and rubber blades"),
        VarietyItem(48, "Brushes", "Plastics & Household", "Rs. 120", "Toilet brushes, dusting brushes, and scrubbers"),
        VarietyItem(49, "Laundry Items", "Plastics & Household", "Rs. 120 / Rs. 300", "Clothespins, laundry mesh bags, and hamper baskets"),

        // Cosmetics & Fragrances
        VarietyItem(50, "Cosmetics", "Cosmetics & Perfumes", "Rs. 120 / Rs. 300 / Rs. 600", "Lipsticks, compacts, nail polishes, and eye makeup"),
        VarietyItem(51, "Body Sprays", "Cosmetics & Perfumes", "Rs. 300 / Rs. 600", "Long-lasting deodorants and fragrance body mists"),
        VarietyItem(52, "Room Sprays", "Cosmetics & Perfumes", "Rs. 300 / Rs. 600", "Refreshing room air fresheners and floral sprays"),
        VarietyItem(53, "Perfumes", "Cosmetics & Perfumes", "Rs. 600 / Rs. 1,200", "Imported and local non-alcoholic concentrated perfumes"),

        // Personal Accessories & Watches
        VarietyItem(54, "Watches", "Cosmetics & Perfumes", "Rs. 300 / Rs. 600 / Rs. 1,200", "Stylish analog wristwatches for men and women"),
        VarietyItem(55, "Key Chains", "Cosmetics & Perfumes", "Rs. 120", "Metallic, leather, and cartoon fancy keychains"),
        VarietyItem(56, "Tops", "Cosmetics & Perfumes", "Rs. 120 / Rs. 300", "Fancy artificial earrings, tops, and jewelry"),

        // Textiles & Linens
        VarietyItem(57, "Hosiery", "Textiles & Linens", "Rs. 120 / Rs. 300", "Cotton socks, undergarments, and tights"),
        VarietyItem(58, "Towels", "Textiles & Linens", "Rs. 120 / Rs. 300 / Rs. 600", "Soft absorbent face towels, hand towels, and bath towels"),
        VarietyItem(59, "Tissues", "Textiles & Linens", "Rs. 120 / Rs. 300", "Facial tissue boxes and wet wipe packs"),
        VarietyItem(60, "Mats", "Textiles & Linens", "Rs. 120 / Rs. 300 / Rs. 600", "Anti-slip bathroom door mats and bedroom rugs"),
        VarietyItem(61, "Table Sheets", "Textiles & Linens", "Rs. 300 / Rs. 600", "Waterproof printed dining table cloth covers"),
        VarietyItem(62, "Fancy Sheets", "Textiles & Linens", "Rs. 600 / Rs. 1,200", "Lace and embroidered decorative sheets"),
        VarietyItem(63, "Aprons", "Textiles & Linens", "Rs. 120 / Rs. 300", "Waterproof chef and kitchen cooking aprons"),
        VarietyItem(64, "And More Items", "All Items", "Rs. 120 to Rs. 1,200", "Hundreds of seasonal and everyday household items in store!")
    )
}
