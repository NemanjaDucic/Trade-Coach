package com.magma.tradecoach.utilities

object CountryUtils {
    val countryNameToCodeMap = mapOf(
        "Afghanistan" to "AF",
        "Albania" to "AL",
        "Algeria" to "DZ",
        "Andorra" to "AD",
        "Angola" to "AO",
        "Argentina" to "AR",
        "Armenia" to "AM",
        "Australia" to "AU",
        "Austria" to "AT",
        "Azerbaijan" to "AZ",
        "Bahamas" to "BS",
        "Bahrain" to "BH",
        "Bangladesh" to "BD",
        "Barbados" to "BB",
        "Belarus" to "BY",
        "Belgium" to "BE",
        "Belize" to "BZ",
        "Benin" to "BJ",
        "Bhutan" to "BT",
        "Bolivia" to "BO",
        "Bosnia and Herzegovina" to "BA",
        "Botswana" to "BW",
        "Brazil" to "BR",
        "Brunei" to "BN",
        "Bulgaria" to "BG",
        "Burkina Faso" to "BF",
        "Burundi" to "BI",
        "Cabo Verde" to "CV",
        "Cambodia" to "KH",
        "Cameroon" to "CM",
        "Canada" to "CA",
        "Central African Republic" to "CF",
        "Chad" to "TD",
        "Chile" to "CL",
        "China" to "CN",
        "Colombia" to "CO",
        "Comoros" to "KM",
        "Congo" to "CG",
        "Congo (DRC)" to "CD",
        "Costa Rica" to "CR",
        "Croatia" to "HR",
        "Cuba" to "CU",
        "Cyprus" to "CY",
        "Czech Republic" to "CZ",
        "Denmark" to "DK",
        "Djibouti" to "DJ",
        "Dominica" to "DM",
        "Dominican Republic" to "DO",
        "Ecuador" to "EC",
        "Egypt" to "EG",
        "El Salvador" to "SV",
        "Equatorial Guinea" to "GQ",
        "Eritrea" to "ER",
        "Estonia" to "EE",
        "Eswatini" to "SZ",
        "Ethiopia" to "ET",
        "Fiji" to "FJ",
        "Finland" to "FI",
        "France" to "FR",
        "Gabon" to "GA",
        "Gambia" to "GM",
        "Georgia" to "GE",
        "Germany" to "DE",
        "Ghana" to "GH",
        "Greece" to "GR",
        "Grenada" to "GD",
        "Guatemala" to "GT",
        "Guinea" to "GN",
        "Guinea-Bissau" to "GW",
        "Guyana" to "GY",
        "Haiti" to "HT",
        "Honduras" to "HN",
        "Hungary" to "HU",
        "Iceland" to "IS",
        "India" to "IN",
        "Indonesia" to "ID",
        "Iran" to "IR",
        "Iraq" to "IQ",
        "Ireland" to "IE",
        "Israel" to "IL",
        "Italy" to "IT",
        "Jamaica" to "JM",
        "Japan" to "JP",
        "Jordan" to "JO",
        "Kazakhstan" to "KZ",
        "Kenya" to "KE",
        "Kiribati" to "KI",
        "Kuwait" to "KW",
        "Kyrgyzstan" to "KG",
        "Laos" to "LA",
        "Latvia" to "LV",
        "Lebanon" to "LB",
        "Lesotho" to "LS",
        "Liberia" to "LR",
        "Libya" to "LY",
        "Liechtenstein" to "LI",
        "Lithuania" to "LT",
        "Luxembourg" to "LU",
        "Madagascar" to "MG",
        "Malawi" to "MW",
        "Malaysia" to "MY",
        "Maldives" to "MV",
        "Mali" to "ML",
        "Malta" to "MT",
        "Marshall Islands" to "MH",
        "Mauritania" to "MR",
        "Mauritius" to "MU",
        "Mexico" to "MX",
        "Micronesia" to "FM",
        "Moldova" to "MD",
        "Monaco" to "MC",
        "Mongolia" to "MN",
        "Montenegro" to "ME",
        "Morocco" to "MA",
        "Mozambique" to "MZ",
        "Myanmar" to "MM",
        "Namibia" to "NA",
        "Nauru" to "NR",
        "Nepal" to "NP",
        "Netherlands" to "NL",
        "New Zealand" to "NZ",
        "Nicaragua" to "NI",
        "Niger" to "NE",
        "Nigeria" to "NG",
        "North Korea" to "KP",
        "North Macedonia" to "MK",
        "Norway" to "NO",
        "Oman" to "OM",
        "Pakistan" to "PK",
        "Palau" to "PW",
        "Panama" to "PA",
        "Papua New Guinea" to "PG",
        "Paraguay" to "PY",
        "Peru" to "PE",
        "Philippines" to "PH",
        "Poland" to "PL",
        "Portugal" to "PT",
        "Qatar" to "QA",
        "Romania" to "RO",
        "Russia" to "RU",
        "Rwanda" to "RW",
        "Saint Kitts and Nevis" to "KN",
        "Saint Lucia" to "LC",
        "Saint Vincent and the Grenadines" to "VC",
        "Samoa" to "WS",
        "San Marino" to "SM",
        "Sao Tome and Principe" to "ST",
        "Saudi Arabia" to "SA",
        "Senegal" to "SN",
        "Serbia" to "RS",
        "Seychelles" to "SC",
        "Sierra Leone" to "SL",
        "Singapore" to "SG",
        "Slovakia" to "SK",
        "Slovenia" to "SI",
        "Solomon Islands" to "SB",
        "Somalia" to "SO",
        "South Africa" to "ZA",
        "South Korea" to "KR",
        "South Sudan" to "SS",
        "Spain" to "ES",
        "Sri Lanka" to "LK",
        "Sudan" to "SD",
        "Suriname" to "SR",
        "Sweden" to "SE",
        "Switzerland" to "CH",
        "Syria" to "SY",
        "Taiwan" to "TW",
        "Tajikistan" to "TJ",
        "Tanzania" to "TZ",
        "Thailand" to "TH",
        "Timor-Leste" to "TL",
        "Togo" to "TG",
        "Tonga" to "TO",
        "Trinidad and Tobago" to "TT",
        "Tunisia" to "TN",
        "Turkey" to "TR",
        "Turkmenistan" to "TM",
        "Tuvalu" to "TV",
        "Uganda" to "UG",
        "Ukraine" to "UA",
        "United Arab Emirates" to "AE",
        "United Kingdom" to "GB",
        "United States" to "US",
        "Uruguay" to "UY",
        "Uzbekistan" to "UZ",
        "Vanuatu" to "VU",
        "Vatican City" to "VA",
        "Venezuela" to "VE",
        "Vietnam" to "VN",
        "Yemen" to "YE",
        "Zambia" to "ZM",
        "Zimbabwe" to "ZW"
    )

    fun getCountryCode(countryName: String): String? {
        return countryNameToCodeMap[countryName]
    }
}
