package suitmedia.co.id.ievents.classes

import com.google.gson.annotations.SerializedName

class ModelEvents(val title: String, val desc: String, val date: String, val time: String)
class ModelListMapping(val latitude: Double, val longitude: Double,val title: String, val desc: String, val date: String, val time: String)
class ModelResult {
    @SerializedName("data")
    var result: List<ModelGuests>? = null
    @JvmName("getResult1")
    fun getResult(): List<ModelGuests>? {
        return result
    }
}
class ModelGuest(val id: String?, val email: String?, val firstName: String?, val lastName: String?, val avatar: String?)
class ModelGuests{
    @SerializedName("id")
    private var id: String? = null

    @SerializedName("email")
    private var email: String? = null

    @SerializedName("first_name")
    private var firstName: String? = null

    @SerializedName("last_name")
    private var lastName: String? = null
    @SerializedName("avatar")
    private var avatar: String? = null

    fun getId(): String? {
        return id
    }

    fun getEmail(): String? {
        return email
    }

    fun getFirstName(): String? {
        return firstName
    }

    fun getLastName(): String? {
        return lastName
    }

    fun getAvatar(): String? {
        return avatar
    }

}
