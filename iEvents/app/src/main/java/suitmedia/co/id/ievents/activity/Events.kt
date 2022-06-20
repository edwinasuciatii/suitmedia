package suitmedia.co.id.ievents.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import suitmedia.co.id.ievents.R
import suitmedia.co.id.ievents.classes.ModelEvents
import suitmedia.co.id.ievents.classes.PropertyStatusBar
import suitmedia.co.id.ievents.classes.adapter.ListEvents
import suitmedia.co.id.ievents.databinding.EventsBinding

class Events : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : EventsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EventsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initContent()
    }

    private fun initContent() {
        PropertyStatusBar().changeColor(R.color.orange,this)
        val list = ArrayList<ModelEvents>()
        list.add(ModelEvents("IT Fair","Lorem ipsum dolor sit amet, consectetur adipiscing elit.","15 Jan 2021","9.00 AM"))
        list.add(ModelEvents("Seminar IT","Lorem ipsum dolor sit amet, consectetur adipiscing elit.","16 Jan 2021","10.00 AM"))
        list.add(ModelEvents("Workshop IT","Lorem ipsum dolor sit amet, consectetur adipiscing elit.","17 Jan 2021","11.00 AM"))
        list.add(ModelEvents("Card Title1","Lorem ipsum dolor sit amet, consectetur adipiscing elit.","18 Jan 2021","12.00 AM"))
        list.add(ModelEvents("Card Title2","Lorem ipsum dolor sit amet, consectetur adipiscing elit.","19 Jan 2021","13.00 AM"))
        binding.rvEvents.adapter = ListEvents(list,this)
        binding.rvEvents.layoutManager = LinearLayoutManager(this)
        binding.btnMaps.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.btnMaps.id -> {
                startActivity(Intent(this, MapsDetail::class.java))
            }
        }
    }
}