package suitmedia.co.id.ievents.classes

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity

class DataApplication {

    var myPrefs: SharedPreferences? = null

    var name: String? = null
    var photo: String? = null

    fun session(fragmentActivity: FragmentActivity){
        myPrefs = fragmentActivity.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        name = myPrefs!!.getString("NAME", "")
        photo = myPrefs!!.getString("PHOTO", "")
    }
}