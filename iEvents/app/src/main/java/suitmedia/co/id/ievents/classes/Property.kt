
package suitmedia.co.id.ievents.classes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.view.Window
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.io.ByteArrayOutputStream

class PropertyVector {
    fun vectorToBitmap(context: AppCompatActivity, @DrawableRes id: Int): BitmapDescriptor? {
        val vectorDrawable = ResourcesCompat.getDrawable(context.resources, id, null)!!
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
class PropertyImage{
    @Suppress("DEPRECATION")
    fun getBitmap(context: AppCompatActivity, data: String): Bitmap {
        val mBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(data))
        return Bitmap.createScaledBitmap(flipImage(mBitmap), 712, 750, true)
    }
    private fun flipImage(bitmap: Bitmap): Bitmap {
        //Moustafa: fix issue of image reflection due to front camera settings
        val matrix = Matrix()
        val rotation = orientation(bitmap)
        matrix.postRotate(rotation.toFloat())
        matrix.preScale(1f, 1f)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
    private fun orientation(bitmap: Bitmap): Int {
        return if (bitmap.width > bitmap.height){90} else 0
    }
    fun encodeToBase64(image: Bitmap): String {
        val ba = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, ba)
        val b = ba.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
    fun decodeFromBase64(input: String): Bitmap {
        val decodedByte = Base64.decode(input, 0)
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
    }
}
class PropertyStatusBar{
    @Suppress("DEPRECATION")
    fun changeColor(color: Int, activity: FragmentActivity){
        val window: Window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            window.statusBarColor = ContextCompat.getColor(activity, color)
        }
    }
}
class PropertyToast{
    fun toast(fragmentActivity: FragmentActivity?, setToast: String?){
        return android.widget.Toast.makeText(
                fragmentActivity,
                setToast.toString(),
                android.widget.Toast.LENGTH_SHORT
        ).show()
    }
    fun toastLong(fragmentActivity: FragmentActivity?, setToast: String?){
        return android.widget.Toast.makeText(
                fragmentActivity,
                setToast.toString(),
                android.widget.Toast.LENGTH_LONG
        ).show()
    }
}

