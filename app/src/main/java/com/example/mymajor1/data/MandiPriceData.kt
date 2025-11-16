package com.example.mymajor1.data

// ==================== INDIAN CROPS ====================
object IndianCrops {
    val crops = listOf(
        "Rice (Paddy)",
        "Wheat",
        "Maize (Corn)",
        "Bajra (Pearl Millet)",
        "Jowar (Sorghum)",
        "Ragi (Finger Millet)",
        "Barley",
        "Cotton",
        "Sugarcane",
        "Jute",
        "Groundnut (Peanut)",
        "Soybean",
        "Sunflower",
        "Mustard",
        "Sesame",
        "Safflower",
        "Linseed",
        "Castor",
        "Chickpea (Chana)",
        "Pigeon Pea (Arhar/Tur)",
        "Black Gram (Urad)",
        "Green Gram (Moong)",
        "Lentil (Masoor)",
        "Kidney Bean (Rajma)",
        "Peas",
        "Potato",
        "Onion",
        "Tomato",
        "Brinjal (Eggplant)",
        "Cauliflower",
        "Cabbage",
        "Okra (Bhindi)",
        "Chilli",
        "Garlic",
        "Ginger",
        "Turmeric",
        "Banana",
        "Mango",
        "Guava",
        "Papaya",
        "Pomegranate",
        "Orange",
        "Apple",
        "Grapes",
        "Tea",
        "Coffee",
        "Rubber",
        "Coconut",
        "Areca Nut"
    ).sorted()
}

// ==================== INDIAN SOIL TYPES ====================
object IndianSoilTypes {
    val soilTypes = listOf(
        "Alluvial Soil",
        "Black Soil (Regur)",
        "Red Soil",
        "Laterite Soil",
        "Desert Soil (Arid)",
        "Mountain Soil (Forest)",
        "Peaty Soil",
        "Saline and Alkaline Soil",
        "Sandy Soil",
        "Clay Soil",
        "Loamy Soil"
    ).sorted()
}

// ==================== MANDI COMMODITIES ====================
object MandiCommodities {
    val commodities = listOf(
        "Apple",
        "Arhar (Tur/Red Gram)",
        "Bajra (Pearl Millet)",
        "Banana",
        "Barley",
        "Bengal Gram (Gram/Chana)",
        "Bitter Gourd",
        "Black Gram (Urad)",
        "Bottle Gourd",
        "Brinjal",
        "Cabbage",
        "Capsicum",
        "Carrot",
        "Castor Seed",
        "Cauliflower",
        "Chilli Red",
        "Coconut",
        "Coriander",
        "Cotton",
        "Cucumber",
        "Cumin Seed",
        "Drumstick",
        "Garlic",
        "Ginger",
        "Grapes",
        "Green Gram (Moong)",
        "Green Peas",
        "Groundnut",
        "Guava",
        "Jowar (Sorghum)",
        "Jute",
        "Lady Finger (Okra/Bhindi)",
        "Lemon",
        "Lentil (Masoor)",
        "Maize",
        "Mango",
        "Mustard",
        "Onion",
        "Orange",
        "Paddy (Rice)",
        "Papaya",
        "Peas",
        "Pomegranate",
        "Potato",
        "Pumpkin",
        "Radish",
        "Ragi (Finger Millet)",
        "Ridge Gourd",
        "Safflower",
        "Sesamum (Sesame/Gingelly)",
        "Soybean",
        "Sugarcane",
        "Sunflower",
        "Sweet Potato",
        "Tomato",
        "Turmeric",
        "Watermelon",
        "Wheat"
    ).sorted()
}

// ==================== STATES AND DISTRICTS ====================
object IndiaStatesDistricts {
    val statesWithDistricts = mapOf(
        "Andhra Pradesh" to listOf(
            "Anantapur", "Chittoor", "East Godavari", "Guntur", "Krishna",
            "Kurnool", "Prakasam", "Srikakulam", "Visakhapatnam", "Vizianagaram",
            "West Godavari", "YSR Kadapa"
        ),
        "Arunachal Pradesh" to listOf(
            "Tawang", "West Kameng", "East Kameng", "Papum Pare", "Kurung Kumey",
            "Kra Daadi", "Lower Subansiri", "Upper Subansiri", "West Siang", "East Siang",
            "Upper Siang", "Dibang Valley", "Lower Dibang Valley", "Lohit", "Anjaw",
            "Namsai", "Changlang", "Tirap", "Longding"
        ),
        "Assam" to listOf(
            "Baksa", "Barpeta", "Biswanath", "Bongaigaon", "Cachar", "Charaideo",
            "Chirang", "Darrang", "Dhemaji", "Dhubri", "Dibrugarh", "Dima Hasao",
            "Goalpara", "Golaghat", "Hailakandi", "Hojai", "Jorhat", "Kamrup",
            "Kamrup Metropolitan", "Karbi Anglong", "Karimganj", "Kokrajhar", "Lakhimpur",
            "Majuli", "Morigaon", "Nagaon", "Nalbari", "Sivasagar", "Sonitpur",
            "South Salmara-Mankachar", "Tinsukia", "Udalguri", "West Karbi Anglong"
        ),
        "Bihar" to listOf(
            "Araria", "Arwal", "Aurangabad", "Banka", "Begusarai", "Bhagalpur",
            "Bhojpur", "Buxar", "Darbhanga", "East Champaran", "Gaya", "Gopalganj",
            "Jamui", "Jehanabad", "Kaimur", "Katihar", "Khagaria", "Kishanganj",
            "Lakhisarai", "Madhepura", "Madhubani", "Munger", "Muzaffarpur", "Nalanda",
            "Nawada", "Patna", "Purnia", "Rohtas", "Saharsa", "Samastipur",
            "Saran", "Sheikhpura", "Sheohar", "Sitamarhi", "Siwan", "Supaul",
            "Vaishali", "West Champaran"
        ),
        "Chhattisgarh" to listOf(
            "Balod", "Baloda Bazar", "Balrampur", "Bastar", "Bemetara", "Bijapur",
            "Bilaspur", "Dantewada", "Dhamtari", "Durg", "Gariaband", "Janjgir-Champa",
            "Jashpur", "Kabirdham", "Kanker", "Kondagaon", "Korba", "Koriya",
            "Mahasamund", "Mungeli", "Narayanpur", "Raigarh", "Raipur", "Rajnandgaon",
            "Sukma", "Surajpur", "Surguja"
        ),
        "Goa" to listOf(
            "North Goa", "South Goa"
        ),
        "Gujarat" to listOf(
            "Ahmedabad", "Amreli", "Anand", "Aravalli", "Banaskantha", "Bharuch",
            "Bhavnagar", "Botad", "Chhota Udaipur", "Dahod", "Dang", "Devbhoomi Dwarka",
            "Gandhinagar", "Gir Somnath", "Jamnagar", "Junagadh", "Kheda", "Kutch",
            "Mahisagar", "Mehsana", "Morbi", "Narmada", "Navsari", "Panchmahal",
            "Patan", "Porbandar", "Rajkot", "Sabarkantha", "Surat", "Surendranagar",
            "Tapi", "Vadodara", "Valsad"
        ),
        "Haryana" to listOf(
            "Ambala", "Bhiwani", "Charkhi Dadri", "Faridabad", "Fatehabad", "Gurugram",
            "Hisar", "Jhajjar", "Jind", "Kaithal", "Karnal", "Kurukshetra",
            "Mahendragarh", "Nuh", "Palwal", "Panchkula", "Panipat", "Rewari",
            "Rohtak", "Sirsa", "Sonipat", "Yamunanagar"
        ),
        "Himachal Pradesh" to listOf(
            "Bilaspur", "Chamba", "Hamirpur", "Kangra", "Kinnaur", "Kullu",
            "Lahaul and Spiti", "Mandi", "Shimla", "Sirmaur", "Solan", "Una"
        ),
        "Jharkhand" to listOf(
            "Bokaro", "Chatra", "Deoghar", "Dhanbad", "Dumka", "East Singhbhum",
            "Garhwa", "Giridih", "Godda", "Gumla", "Hazaribagh", "Jamtara",
            "Khunti", "Koderma", "Latehar", "Lohardaga", "Pakur", "Palamu",
            "Ramgarh", "Ranchi", "Sahebganj", "Seraikela Kharsawan", "Simdega",
            "West Singhbhum"
        ),
        "Karnataka" to listOf(
            "Bagalkot", "Ballari", "Belagavi", "Bengaluru Rural", "Bengaluru Urban",
            "Bidar", "Chamarajanagar", "Chikkaballapur", "Chikkamagaluru", "Chitradurga",
            "Dakshina Kannada", "Davanagere", "Dharwad", "Gadag", "Hassan",
            "Haveri", "Kalaburagi", "Kodagu", "Kolar", "Koppal", "Mandya",
            "Mysuru", "Raichur", "Ramanagara", "Shivamogga", "Tumakuru", "Udupi",
            "Uttara Kannada", "Vijayapura", "Yadgir"
        ),
        "Kerala" to listOf(
            "Alappuzha", "Ernakulam", "Idukki", "Kannur", "Kasaragod", "Kollam",
            "Kottayam", "Kozhikode", "Malappuram", "Palakkad", "Pathanamthitta",
            "Thiruvananthapuram", "Thrissur", "Wayanad"
        ),
        "Madhya Pradesh" to listOf(
            "Agar Malwa", "Alirajpur", "Anuppur", "Ashoknagar", "Balaghat", "Barwani",
            "Betul", "Bhind", "Bhopal", "Burhanpur", "Chhatarpur", "Chhindwara",
            "Damoh", "Datia", "Dewas", "Dhar", "Dindori", "Guna", "Gwalior",
            "Harda", "Hoshangabad", "Indore", "Jabalpur", "Jhabua", "Katni",
            "Khandwa", "Khargone", "Mandla", "Mandsaur", "Morena", "Narsinghpur",
            "Neemuch", "Panna", "Raisen", "Rajgarh", "Ratlam", "Rewa", "Sagar",
            "Satna", "Sehore", "Seoni", "Shahdol", "Shajapur", "Sheopur", "Shivpuri",
            "Sidhi", "Singrauli", "Tikamgarh", "Ujjain", "Umaria", "Vidisha"
        ),
        "Maharashtra" to listOf(
            "Ahmednagar", "Akola", "Amravati", "Aurangabad", "Beed", "Bhandara",
            "Buldhana", "Chandrapur", "Dhule", "Gadchiroli", "Gondia", "Hingoli",
            "Jalgaon", "Jalna", "Kolhapur", "Latur", "Mumbai City", "Mumbai Suburban",
            "Nagpur", "Nanded", "Nandurbar", "Nashik", "Osmanabad", "Palghar",
            "Parbhani", "Pune", "Raigad", "Ratnagiri", "Sangli", "Satara",
            "Sindhudurg", "Solapur", "Thane", "Wardha", "Washim", "Yavatmal"
        ),
        "Manipur" to listOf(
            "Bishnupur", "Chandel", "Churachandpur", "Imphal East", "Imphal West",
            "Jiribam", "Kakching", "Kamjong", "Kangpokpi", "Noney", "Pherzawl",
            "Senapati", "Tamenglong", "Tengnoupal", "Thoubal", "Ukhrul"
        ),
        "Meghalaya" to listOf(
            "East Garo Hills", "East Jaintia Hills", "East Khasi Hills", "North Garo Hills",
            "Ri Bhoi", "South Garo Hills", "South West Garo Hills", "South West Khasi Hills",
            "West Garo Hills", "West Jaintia Hills", "West Khasi Hills"
        ),
        "Mizoram" to listOf(
            "Aizawl", "Champhai", "Kolasib", "Lawngtlai", "Lunglei", "Mamit",
            "Saiha", "Serchhip"
        ),
        "Nagaland" to listOf(
            "Dimapur", "Kiphire", "Kohima", "Longleng", "Mokokchung", "Mon",
            "Peren", "Phek", "Tuensang", "Wokha", "Zunheboto"
        ),
        "Odisha" to listOf(
            "Angul", "Balangir", "Balasore", "Bargarh", "Bhadrak", "Boudh",
            "Cuttack", "Deogarh", "Dhenkanal", "Gajapati", "Ganjam", "Jagatsinghpur",
            "Jajpur", "Jharsuguda", "Kalahandi", "Kandhamal", "Kendrapara", "Kendujhar",
            "Khordha", "Koraput", "Malkangiri", "Mayurbhanj", "Nabarangpur", "Nayagarh",
            "Nuapada", "Puri", "Rayagada", "Sambalpur", "Subarnapur", "Sundargarh"
        ),
        "Punjab" to listOf(
            "Amritsar", "Barnala", "Bathinda", "Faridkot", "Fatehgarh Sahib", "Fazilka",
            "Ferozepur", "Gurdaspur", "Hoshiarpur", "Jalandhar", "Kapurthala", "Ludhiana",
            "Mansa", "Moga", "Mohali", "Muktsar", "Pathankot", "Patiala",
            "Rupnagar", "Sangrur", "Shaheed Bhagat Singh Nagar", "Tarn Taran"
        ),
        "Rajasthan" to listOf(
            "Ajmer", "Alwar", "Banswara", "Baran", "Barmer", "Bharatpur",
            "Bhilwara", "Bikaner", "Bundi", "Chittorgarh", "Churu", "Dausa",
            "Dholpur", "Dungarpur", "Hanumangarh", "Jaipur", "Jaisalmer", "Jalore",
            "Jhalawar", "Jhunjhunu", "Jodhpur", "Karauli", "Kota", "Nagaur",
            "Pali", "Pratapgarh", "Rajsamand", "Sawai Madhopur", "Sikar", "Sirohi",
            "Sri Ganganagar", "Tonk", "Udaipur"
        ),
        "Sikkim" to listOf(
            "East Sikkim", "North Sikkim", "South Sikkim", "West Sikkim"
        ),
        "Tamil Nadu" to listOf(
            "Ariyalur", "Chengalpattu", "Chennai", "Coimbatore", "Cuddalore", "Dharmapuri",
            "Dindigul", "Erode", "Kallakurichi", "Kanchipuram", "Kanyakumari", "Karur",
            "Krishnagiri", "Madurai", "Nagapattinam", "Namakkal", "Nilgiris", "Perambalur",
            "Pudukkottai", "Ramanathapuram", "Ranipet", "Salem", "Sivaganga", "Tenkasi",
            "Thanjavur", "Theni", "Thoothukudi", "Tiruchirappalli", "Tirunelveli", "Tirupathur",
            "Tiruppur", "Tiruvallur", "Tiruvannamalai", "Tiruvarur", "Vellore", "Viluppuram",
            "Virudhunagar"
        ),
        "Telangana" to listOf(
            "Adilabad", "Bhadradri Kothagudem", "Hyderabad", "Jagtial", "Jangaon", "Jayashankar Bhupalpally",
            "Jogulamba Gadwal", "Kamareddy", "Karimnagar", "Khammam", "Komaram Bheem", "Mahabubabad",
            "Mahbubnagar", "Mancherial", "Medak", "Medchal-Malkajgiri", "Nagarkurnool", "Nalgonda",
            "Nirmal", "Nizamabad", "Peddapalli", "Rajanna Sircilla", "Ranga Reddy", "Sangareddy",
            "Siddipet", "Suryapet", "Vikarabad", "Wanaparthy", "Warangal Rural", "Warangal Urban",
            "Yadadri Bhuvanagiri"
        ),
        "Tripura" to listOf(
            "Dhalai", "Gomati", "Khowai", "North Tripura", "Sepahijala", "South Tripura",
            "Unakoti", "West Tripura"
        ),
        "Uttar Pradesh" to listOf(
            "Agra", "Aligarh", "Ambedkar Nagar", "Amethi", "Amroha", "Auraiya",
            "Azamgarh", "Baghpat", "Bahraich", "Ballia", "Balrampur", "Banda",
            "Barabanki", "Bareilly", "Basti", "Bhadohi", "Bijnor", "Budaun",
            "Bulandshahr", "Chandauli", "Chitrakoot", "Deoria", "Etah", "Etawah",
            "Faizabad", "Farrukhabad", "Fatehpur", "Firozabad", "Gautam Buddha Nagar",
            "Ghaziabad", "Ghazipur", "Gonda", "Gorakhpur", "Hamirpur", "Hapur",
            "Hardoi", "Hathras", "Jalaun", "Jaunpur", "Jhansi", "Kannauj",
            "Kanpur Dehat", "Kanpur Nagar", "Kasganj", "Kaushambi", "Kushinagar",
            "Lakhimpur Kheri", "Lalitpur", "Lucknow", "Maharajganj", "Mahoba",
            "Mainpuri", "Mathura", "Mau", "Meerut", "Mirzapur", "Moradabad",
            "Muzaffarnagar", "Pilibhit", "Pratapgarh", "Prayagraj", "Raebareli",
            "Rampur", "Saharanpur", "Sambhal", "Sant Kabir Nagar", "Shahjahanpur",
            "Shamli", "Shravasti", "Siddharthnagar", "Sitapur", "Sonbhadra",
            "Sultanpur", "Unnao", "Varanasi"
        ),
        "Uttarakhand" to listOf(
            "Almora", "Bageshwar", "Chamoli", "Champawat", "Dehradun", "Haridwar",
            "Nainital", "Pauri Garhwal", "Pithoragarh", "Rudraprayag", "Tehri Garhwal",
            "Udham Singh Nagar", "Uttarkashi"
        ),
        "West Bengal" to listOf(
            "Alipurduar", "Bankura", "Birbhum", "Cooch Behar", "Dakshin Dinajpur",
            "Darjeeling", "Hooghly", "Howrah", "Jalpaiguri", "Jhargram", "Kalimpong",
            "Kolkata", "Malda", "Murshidabad", "Nadia", "North 24 Parganas",
            "Paschim Bardhaman", "Paschim Medinipur", "Purba Bardhaman", "Purba Medinipur",
            "Purulia", "South 24 Parganas", "Uttar Dinajpur"
        )
    )

    val states = statesWithDistricts.keys.sorted()

    fun getDistrictsForState(state: String): List<String> {
        return statesWithDistricts[state]?.sorted() ?: emptyList()
    }
}