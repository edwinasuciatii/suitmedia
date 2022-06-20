package suitmedia.co.id.ievents.classes.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import suitmedia.co.id.ievents.R
import suitmedia.co.id.ievents.activity.Dashboard
import suitmedia.co.id.ievents.classes.ModelEvents
import suitmedia.co.id.ievents.classes.ModelGuest

class ListEvents(private var data: List<ModelEvents>, private val activity: FragmentActivity):
    RecyclerView.Adapter<ListEvents.ViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val getData: ModelEvents = data[p1]
        p0.title.text = getData.title
        p0.desc.text = getData.desc
        p0.date.text = getData.date
        p0.time.text = getData.time
        p0.itemView.setOnClickListener {
            Dashboard.btnEvent?.text = getData.title
            activity.finish()
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.events_card, p0, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.title)
        var desc: TextView = itemView.findViewById(R.id.desc)
        var date: TextView = itemView.findViewById(R.id.date)
        var time: TextView = itemView.findViewById(R.id.time)
    }
}