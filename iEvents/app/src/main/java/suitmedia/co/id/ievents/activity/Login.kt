package suitmedia.co.id.ievents.activity

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import suitmedia.co.id.ievents.R
import suitmedia.co.id.ievents.classes.DataApplication
import suitmedia.co.id.ievents.classes.PropertyImage
import suitmedia.co.id.ievents.classes.PropertyStatusBar
import suitmedia.co.id.ievents.classes.PropertyToast
import suitmedia.co.id.ievents.databinding.AlertChoosePhotoBinding
import suitmedia.co.id.ievents.databinding.LoginBinding
import java.io.IOException

@Suppress("DEPRECATION")
class Login : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : LoginBinding
    private var imageUri: Uri? = null
    private var photo: String? = null
    private var imageCaptureCode: Int = 1
    private var imageGalleryCode: Int = 2
    private var dataApp = DataApplication()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initContent()
    }

    private fun initContent() {
        PropertyStatusBar().changeColor(R.color.orange, this)
        dataApp.myPrefs = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        event()
    }

    private fun event() {
        binding.btnCheck.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
        binding.btnEditPhoto.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.btnCheck.id -> {
                if (checkPalindrome(binding.txtPolindrome.text.toString())){
                    PropertyToast().toast(this, "Palindrome")
                }else{
                    PropertyToast().toast(this, "Bukan Palindrome")
                }
            }
            binding.btnNext.id -> {
                if (binding.txtName.text.isNullOrEmpty() or binding.txtPolindrome.text.isNullOrEmpty()) {
                    errorText()
                } else {
                    saveData()
                }
            }
            binding.btnEditPhoto.id -> {
                showDialogPhoto()
            }
        }
    }

    @Suppress("DEPRECATED_IDENTITY_EQUALS")
    private fun checkPalindrome(inputString: String): Boolean {
        val value: Int = inputString.length
        var count = 0
        for (i in 0 until inputString.length / 2) {
            if (inputString[i] === inputString[value - i - 1]) {
                count++
            }
        }
        return count === value / 2 //true when count=val/2
    }

    private fun errorText() {
        if (binding.txtName.text.isNullOrEmpty()){
            binding.txtName.background = resources.getDrawable(R.drawable.background_text_error)
            textChangedListener(binding.txtName)
        }
        if (binding.txtPolindrome.text.isNullOrEmpty()){
            binding.txtPolindrome.background = resources.getDrawable(R.drawable.background_text_error)
            textChangedListener(binding.txtPolindrome)
        }
    }
    private fun textChangedListener(text: TextView){
        text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!binding.txtName.text.isNullOrEmpty()) {
                    binding.txtName.background = resources.getDrawable(R.drawable.background_text)
                }
                if (!binding.txtPolindrome.text.isNullOrEmpty()) {
                    binding.txtPolindrome.background = resources.getDrawable(R.drawable.background_text)
                }
            }
        })
    }

    private fun saveData() {
        val editor =  dataApp.myPrefs?.edit()
        editor?.putString("NAME", binding.txtName.text.toString())
        editor?.putString("PHOTO", photo)
        editor?.apply()
        startActivity(Intent(this, Dashboard::class.java))
    }

    private fun showDialogPhoto() {
        val bindingAlert : AlertChoosePhotoBinding = AlertChoosePhotoBinding.inflate(layoutInflater)
        val dialogBottom = BottomSheetDialog(this)
        dialogBottom.setContentView(bindingAlert.root)
        bindingAlert.btnCamera.setOnClickListener {
            openCamera()
            dialogBottom.dismiss()
        }
        bindingAlert.btnGallery.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, imageGalleryCode)
            dialogBottom.dismiss()
        }
        dialogBottom.show()
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, imageCaptureCode)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, dataIntent: Intent?) {
        if (requestCode == imageCaptureCode) {
            if (resultCode == Activity.RESULT_OK) {
                photo = imageUri.toString()
                val bitmap = PropertyImage().getBitmap(this, photo.toString())
                photo = PropertyImage().encodeToBase64(PropertyImage().getBitmap(this, photo.toString()))
                binding.btnEditPhoto.setImageBitmap(bitmap)
            }
        }else if (requestCode == imageGalleryCode){
            if (dataIntent != null) {
                try {
                    imageUri = dataIntent.data
                    photo = imageUri.toString()
                    val bitmap = PropertyImage().getBitmap(this, photo.toString())
                    photo = PropertyImage().encodeToBase64(PropertyImage().getBitmap(this, photo.toString()))
                    binding.btnEditPhoto.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, dataIntent)
    }
}