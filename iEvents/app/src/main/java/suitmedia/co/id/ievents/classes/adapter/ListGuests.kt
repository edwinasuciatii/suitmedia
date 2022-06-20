package suitmedia.co.id.ievents.classes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import suitmedia.co.id.ievents.R
import suitmedia.co.id.ievents.activity.Dashboard
import suitmedia.co.id.ievents.classes.ModelGuest

class ListGuests(private var data: List<ModelGuest>, private val activity: FragmentActivity):
    RecyclerView.Adapter<ListGuests.ViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val getData: ModelGuest = data[p1]
        p0.txtName.text = getData.firstName + " "+ getData.lastName
        val url = getData.avatar
        Glide.with(activity)
            .load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(120)))
            .into(p0.avatar)
        p0.itemView.setOnClickListener {
            Dashboard.btnGuests?.text = getData.firstName + " "+ getData.lastName
            activity.finish()
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.guests_card, p0, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avatar: ImageView = itemView.findViewById(R.id.avatar)
        var txtName: TextView = itemView.findViewById(R.id.txtName)
    }
}