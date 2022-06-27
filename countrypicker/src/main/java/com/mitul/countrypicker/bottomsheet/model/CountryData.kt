package com.mitul.countrypicker.bottomsheet.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryData(
    var cca2: String?,
    var cca3: String?,
    var ccn3: String?,
    var cioc: String?,
    var currencies: HashMap<String, Currency>?,
    var continents: List<String?>?,
    var flag: String?,
    var flags: Flags?,
    var idd: Idd?,
    var name: Name?,
    var postalCode: PostalCode?,
    var timezones: List<String?>?
) : Parcelable {
    @Parcelize
    data class Flags(
        var png: String?,
        var svg: String?
    ) : Parcelable

    @Parcelize
    data class Idd(
        var root: String?,
        var suffixes: List<String?>?
    ) : Parcelable

    @Parcelize
    data class Currency(
        var name: String?,
        var symbol: String?
    ) : Parcelable

    @Parcelize
    data class Name(
        var common: String?,
//        var nativeName: NativeName?,
        var nativeName: HashMap<String, Name>?,
        var official: String?
    ) : Parcelable {
        @Parcelize
        data class NativeName(
            var eng: Eng?,
            var hin: Hin?,
            var tam: Tam?
        ) : Parcelable {
            @Parcelize
            data class Eng(
                var common: String?,
                var official: String?
            ) : Parcelable

            @Parcelize
            data class Hin(
                var common: String?,
                var official: String?
            ) : Parcelable

            @Parcelize
            data class Tam(
                var common: String?,
                var official: String?
            ) : Parcelable
        }
    }

    @Parcelize
    data class PostalCode(
        var format: String?,
        var regex: String?
    ) : Parcelable

    fun getCurrency(): String {
        return currencies?.keys?.iterator()?.next() ?: ""
    }
    fun getName2(): String {
        return name?.nativeName?.keys?.iterator()?.next() ?: ""
    }
    fun continetal(): String {
        return continents?.get(0)?: ""
    }

    fun getCountryCode(): String {
        val root = idd?.root ?: ""
        val suffix = idd?.suffixes?.get(0) ?: ""
        return "$root$suffix"
    }
}