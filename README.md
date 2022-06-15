# CountryPicker
Country picker
Add below line of code in any of youe views click 

val bottomSheetCountry = BottomSheetCountry(true,true,true)

            bottomSheetCountry.setCountryObjectCallback(object : IObjectCallback<CountryModel> {
            
                override fun response(data: CountryModel?) {
                
                    if (data == null) return
                    mainBinding.tvCountry.text = Gson().toJson(data)
                    
                }
                
            })
            
            bottomSheetCountry.show(this.supportFragmentManager, "select_country")

Retunrning whole country object so you can use ad per your reuirements
