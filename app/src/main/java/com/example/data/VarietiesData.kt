package com.example.data

data class VarietyItem(
    val id: Int,
    val name: String,
    val category: String,
    val priceTier: String,
    val description: String,
    val defaultImageResName: String = "img_crockery_items"
)

object VarietiesData {
    val categories = listOf(
        "All Items",
        "Jewellery & Accessories",
        "Kitchen & Crockery",
        "Bags & Pouches",
        "Toys & Kids",
        "Home Decor & Clocks",
        "Plastics & Household",
        "Cosmetics & Perfumes",
        "Textiles & Linens"
    )

    val allVarieties: List<VarietyItem> = listOf(
        // Jewellery & Accessories (زیورات اور فینسی جیولری)
        VarietyItem(101, "Bridal Jewellery Sets", "Jewellery & Accessories", "Rs. 600 / Rs. 1,200", "Gold-plated artificial bridal set with necklace, jhumkas, matha patti, and bangles", "img_jewellery_collection"),
        VarietyItem(102, "Artificial Kundan Necklaces", "Jewellery & Accessories", "Rs. 300 / Rs. 600", "Handcrafted traditional Kundan chokers, pendant malas, and matching earrings", "img_jewellery_collection"),
        VarietyItem(103, "Glass Bangles & Churi", "Jewellery & Accessories", "Rs. 120 / Rs. 300", "Vibrant colors traditional glass, velvet, and metallic bangles (چوڑیاں)", "img_jewellery_collection"),
        VarietyItem(104, "Fancy Jhumkay & Earrings", "Jewellery & Accessories", "Rs. 120 / Rs. 300", "Gold-toned pearl and crystal dangling jhumkas, balian, and stone studs", "img_jewellery_collection"),
        VarietyItem(105, "Designer Rings & Tops", "Jewellery & Accessories", "Rs. 120 / Rs. 300", "Adjustable zircon rings, party tops, and delicate stone studs", "img_jewellery_collection"),
        VarietyItem(106, "Payal & Anklets", "Jewellery & Accessories", "Rs. 120 / Rs. 300", "Silver-plated and golden tinkling ghungroo payal (پائل)", "img_jewellery_collection"),
        VarietyItem(107, "Hair Accessories & Pins", "Jewellery & Accessories", "Rs. 120 / Rs. 300", "Fancy bridal pearl pins, tiaras, claw clutures, and hair bands", "img_jewellery_collection"),
        VarietyItem(108, "Bracelets & Chains", "Jewellery & Accessories", "Rs. 120 / Rs. 300", "Men and women casual golden link chains and cuff bracelets", "img_jewellery_collection"),

        // Kitchen & Crockery
        VarietyItem(1, "Plastic Crockery", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Durable food-grade plates, bowls, cups, and trays", "img_plastic_items"),
        VarietyItem(2, "Bowl Sets", "Kitchen & Crockery", "Rs. 300 / Rs. 600", "Serving bowls, mixing bowls, and dessert bowl sets", "img_crockery_items"),
        VarietyItem(3, "Melamine Bowl Sets", "Kitchen & Crockery", "Rs. 300 / Rs. 600", "Unbreakable elegant melamine printed bowl sets", "img_crockery_items"),
        VarietyItem(4, "Steel Items", "Kitchen & Crockery", "Rs. 300 / Rs. 600", "Stainless steel utensils, dabbas, spoons, and plates", "img_crockery_items"),
        VarietyItem(5, "Dinner Items", "Kitchen & Crockery", "Rs. 600 / Rs. 1,200", "Complete dinner plates, platters, and dining sets", "img_crockery_items"),
        VarietyItem(6, "Melamine Items", "Kitchen & Crockery", "Rs. 300 / Rs. 600", "Premium printed melamine dinnerware and trays", "img_crockery_items"),
        VarietyItem(7, "Plastic Jugs", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Heavy-duty plastic water jugs with airtight lids", "img_plastic_items"),
        VarietyItem(8, "Crystal Jugs", "Kitchen & Crockery", "Rs. 600 / Rs. 1,200", "Sparkling embossed crystal glass jugs for guests", "img_crockery_items"),
        VarietyItem(9, "Printed Jugs", "Kitchen & Crockery", "Rs. 300 / Rs. 600", "Floral and geometric printed stylish water jugs", "img_crockery_items"),
        VarietyItem(10, "Glass", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Crystal clear drinking glasses and tumblers", "img_crockery_items"),
        VarietyItem(11, "Jugs", "Kitchen & Crockery", "Rs. 300 / Rs. 600", "Assorted beverage and water serving jugs", "img_crockery_items"),
        VarietyItem(12, "Cups", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Ceramic, melamine, and glass tea and coffee cups", "img_crockery_items"),
        VarietyItem(13, "Mugs", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Large ceramic, travel, and insulated coffee mugs", "img_crockery_items"),
        VarietyItem(14, "China Items", "Kitchen & Crockery", "Rs. 600 / Rs. 1,200", "Fine bone china teapots, saucers, and decorative plates", "img_crockery_items"),
        VarietyItem(15, "Cutlery", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Stainless steel spoon, fork, and butter knife sets", "img_crockery_items"),
        VarietyItem(16, "Kitchen Items", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Peelers, graters, tongs, sieves, and kitchen tools", "img_crockery_items"),
        VarietyItem(17, "Glass Cake Sets", "Kitchen & Crockery", "Rs. 600 / Rs. 1,200", "Domed glass cake stands and serving platter sets", "img_crockery_items"),
        VarietyItem(18, "Glass Water Sets", "Kitchen & Crockery", "Rs. 600 / Rs. 1,200", "7-piece complete glass water jug and 6 glasses sets", "img_crockery_items"),
        VarietyItem(19, "Glass Custard Sets", "Kitchen & Crockery", "Rs. 600 / Rs. 1,200", "Dessert and custard serving bowl with small cups", "img_crockery_items"),
        VarietyItem(20, "Water Bottles", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Gym, fridge, and school sports water bottles", "img_plastic_items"),
        VarietyItem(21, "School Tiffin", "Kitchen & Crockery", "Rs. 300 / Rs. 600", "Leak-proof compartmentalized kids lunch boxes", "img_plastic_items"),
        VarietyItem(22, "Jars", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Airtight spice, pickle, and dry fruit glass jars", "img_crockery_items"),
        VarietyItem(23, "Plastic Jars", "Kitchen & Crockery", "Rs. 120 / Rs. 300", "Transparent plastic kitchen storage container sets", "img_plastic_items"),

        // Bags & Pouches
        VarietyItem(24, "Handbags", "Bags & Pouches", "Rs. 600 / Rs. 1,200", "Trendy shoulder bags, totes, and party handbags", "img_bags_items"),
        VarietyItem(25, "Pouches", "Bags & Pouches", "Rs. 120 / Rs. 300", "Makeup pouches, coin wallets, and stationery kits", "img_bags_items"),
        VarietyItem(26, "School Bags", "Bags & Pouches", "Rs. 600 / Rs. 1,200", "Heavy-duty ergonomic school backpacks for children", "img_bags_items"),
        VarietyItem(27, "Baby Bags", "Bags & Pouches", "Rs. 600 / Rs. 1,200", "Multi-pocket mother diaper and baby essential bags", "img_bags_items"),
        VarietyItem(28, "Teddy Bear Bags", "Bags & Pouches", "Rs. 300 / Rs. 600", "Adorable plush teddy bear toddler backpacks", "img_bags_items"),

        // Toys & Kids
        VarietyItem(29, "Toys", "Toys & Kids", "Rs. 120 / Rs. 300 / Rs. 600", "Friction cars, dolls, musical toys, and action figures", "img_toys_items"),
        VarietyItem(30, "Teddy Bears", "Toys & Kids", "Rs. 300 / Rs. 600 / Rs. 1,200", "Soft fluffy plush stuffed teddy bears in all sizes", "img_toys_items"),
        VarietyItem(31, "Chunki", "Toys & Kids", "Rs. 120", "Fun traditional kids trinkets, whistles, and play toys", "img_toys_items"),

        // Home Decor & Clocks
        VarietyItem(32, "Decoration Pieces", "Home Decor & Clocks", "Rs. 300 / Rs. 600 / Rs. 1,200", "Vases, figurines, Islamic calligraphy, and showpieces", "img_clocks_decor"),
        VarietyItem(33, "Artificial Flowers", "Home Decor & Clocks", "Rs. 120 / Rs. 300", "Vibrant silk roses, orchids, and bouquet bunches", "img_clocks_decor"),
        VarietyItem(34, "Garlands", "Home Decor & Clocks", "Rs. 120 / Rs. 300", "Decorative floral and festive door/wall hanging garlands", "img_clocks_decor"),
        VarietyItem(35, "Glass Mirrors", "Home Decor & Clocks", "Rs. 300 / Rs. 600", "Framed vanity, wall, and dressing table mirrors", "img_clocks_decor"),
        VarietyItem(36, "Wall Clocks", "Home Decor & Clocks", "Rs. 600 / Rs. 1,200", "Silent sweep quartz modern and antique wall clocks", "img_clocks_decor"),
        VarietyItem(37, "Fancy Items", "Home Decor & Clocks", "Rs. 300 / Rs. 600", "Glitter showpieces, candle holders, and gifts", "img_clocks_decor"),
        VarietyItem(38, "Wood Items", "Home Decor & Clocks", "Rs. 300 / Rs. 600 / Rs. 1,200", "Carved wooden handicrafts, tissue boxes, and shelves", "img_clocks_decor"),

        // Plastics & Household (پلاسٹک کے برتن اور سامان)
        VarietyItem(39, "Smart Tables", "Plastics & Household", "Rs. 600 / Rs. 1,200", "Foldable laptop, study, and bed dining smart tables", "img_plastic_items"),
        VarietyItem(40, "Smart Chairs", "Plastics & Household", "Rs. 600 / Rs. 1,200", "Sturdy portable plastic stools and smart folding chairs", "img_plastic_items"),
        VarietyItem(41, "Buckets", "Plastics & Household", "Rs. 300 / Rs. 600", "Heavy unbreakable bathroom and cleaning buckets", "img_plastic_items"),
        VarietyItem(42, "Baskets", "Plastics & Household", "Rs. 120 / Rs. 300", "Fruit baskets, laundry bins, and organizer baskets", "img_plastic_items"),
        VarietyItem(43, "Dustbins", "Plastics & Household", "Rs. 120 / Rs. 300 / Rs. 600", "Pedal bins, swing-top bins, and room wastebaskets", "img_plastic_items"),
        VarietyItem(44, "Hangers", "Plastics & Household", "Rs. 120 / Rs. 300", "Velvet and heavy plastic wardrobe clothing hangers", "img_plastic_items"),
        VarietyItem(45, "Shoe Racks", "Plastics & Household", "Rs. 600 / Rs. 1,200", "Multi-tier lightweight space-saving shoe organizers", "img_plastic_items"),
        VarietyItem(46, "Churi Stands", "Plastics & Household", "Rs. 300 / Rs. 600", "Rotating acrylic and wooden bangle display stands", "img_plastic_items"),
        VarietyItem(47, "Wipers", "Plastics & Household", "Rs. 120 / Rs. 300", "Floor wipers with durable silicone and rubber blades", "img_plastic_items"),
        VarietyItem(48, "Brushes", "Plastics & Household", "Rs. 120", "Toilet brushes, dusting brushes, and scrubbers", "img_plastic_items"),
        VarietyItem(49, "Laundry Items", "Plastics & Household", "Rs. 120 / Rs. 300", "Clothespins, laundry mesh bags, and hamper baskets", "img_plastic_items"),

        // Cosmetics & Fragrances (کاسمیٹکس اور پرفیومز)
        VarietyItem(50, "Cosmetics", "Cosmetics & Perfumes", "Rs. 120 / Rs. 300 / Rs. 600", "Lipsticks, compacts, nail polishes, and eye makeup", "img_cosmetics_items"),
        VarietyItem(51, "Body Sprays", "Cosmetics & Perfumes", "Rs. 300 / Rs. 600", "Long-lasting deodorants and fragrance body mists", "img_cosmetics_items"),
        VarietyItem(52, "Room Sprays", "Cosmetics & Perfumes", "Rs. 300 / Rs. 600", "Refreshing room air fresheners and floral sprays", "img_cosmetics_items"),
        VarietyItem(53, "Perfumes", "Cosmetics & Perfumes", "Rs. 600 / Rs. 1,200", "Imported and local non-alcoholic concentrated perfumes", "img_cosmetics_items"),

        // Personal Accessories & Watches
        VarietyItem(54, "Watches", "Cosmetics & Perfumes", "Rs. 300 / Rs. 600 / Rs. 1,200", "Stylish analog wristwatches for men and women", "img_cosmetics_items"),
        VarietyItem(55, "Key Chains", "Cosmetics & Perfumes", "Rs. 120", "Metallic, leather, and cartoon fancy keychains", "img_cosmetics_items"),
        VarietyItem(56, "Tops & Earrings", "Jewellery & Accessories", "Rs. 120 / Rs. 300", "Fancy artificial earrings, tops, and jewelry", "img_jewellery_collection"),

        // Textiles & Linens - Hosiery (ہوزری اور کپڑے)
        VarietyItem(57, "Hosiery", "Textiles & Linens", "Rs. 120 / Rs. 300", "Cotton socks, undergarments, and tights", "img_hosiery_items"),
        VarietyItem(58, "Towels", "Textiles & Linens", "Rs. 120 / Rs. 300 / Rs. 600", "Soft absorbent face towels, hand towels, and bath towels", "img_hosiery_items"),
        VarietyItem(59, "Tissues", "Textiles & Linens", "Rs. 120 / Rs. 300", "Facial tissue boxes and wet wipe packs", "img_hosiery_items"),
        VarietyItem(60, "Mats", "Textiles & Linens", "Rs. 120 / Rs. 300 / Rs. 600", "Anti-slip bathroom door mats and bedroom rugs", "img_hosiery_items"),
        VarietyItem(61, "Table Sheets", "Textiles & Linens", "Rs. 300 / Rs. 600", "Waterproof printed dining table cloth covers", "img_hosiery_items"),
        VarietyItem(62, "Fancy Sheets", "Textiles & Linens", "Rs. 600 / Rs. 1,200", "Lace and embroidered decorative sheets", "img_hosiery_items"),
        VarietyItem(63, "Aprons", "Textiles & Linens", "Rs. 120 / Rs. 300", "Waterproof chef and kitchen cooking aprons", "img_hosiery_items"),
        VarietyItem(64, "And More Items", "All Items", "Rs. 120 to Rs. 1,200", "Hundreds of seasonal and everyday household items in store!", "img_shop_bazaar_view")
    )
}
