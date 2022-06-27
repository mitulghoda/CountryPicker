# CountryPicker
Country picker

Installation

Step 1. Add the JitPack repository to your build file

allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
}


Step 2. Add the dependency

implementation 'com.github.mitulghoda:CountryPicker:v1.2'

Step 3. How to use

            val bottomSheetCountry = BottomSheetCountry(true, true, true)
            bottomSheetCountry.setCountryObjectCallback(object : IObjectCallback<CountryModel> {
                override fun response(data: CountryModel?) {
                    if (data == null) return
                    //Get all the data related to country in callback varialble data
                   val countryModel = data
                }
            })
            bottomSheetCountry.show(this.supportFragmentManager, "select_country")
            
            


