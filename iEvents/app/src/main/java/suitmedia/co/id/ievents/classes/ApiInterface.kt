package suitmedia.co.id.ievents.classes

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("users")
    fun getGuests(@Query("page") page: String, @Query("per_page") perPage: String) : Call<ModelResult>

    companion object {
        var BASE_URL = "https://reqres.in/api/"
        fun create() : ApiInterface {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}