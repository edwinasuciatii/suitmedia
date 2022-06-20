package suitmedia.co.id.ievents.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import suitmedia.co.id.ievents.R
import suitmedia.co.id.ievents.classes.DataApplication
import suitmedia.co.id.ievents.classes.PropertyStatusBar
import suitmedia.co.id.ievents.databinding.DashboardBinding

class Dashboard : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : DashboardBinding
    private var dataApp = DataApplication()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initContent()
    }
    companion object{
        @SuppressLint("StaticFieldLeak")
        var btnEvent: AppCompatButton? = null
        var btnGuests: AppCompatButton? = null
    }

    private fun initContent() {
        PropertyStatusBar().changeColor(R.color.white,this)
        btnEvent = binding.btnEvent
        btnGuests = binding.btnGuest
        dataApp.session(this)
        binding.txtName.text = dataApp.name
        event()
    }

    private fun event() {
        binding.btnEvent.setOnClickListener(this)
        binding.btnGuest.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.btnEvent.id ->{
                startActivity(Intent(this, Events::class.java))
            }
            binding.btnGuest.id ->{
                startActivity(Intent(this, Guests::class.java))
            }
        }
    }
}