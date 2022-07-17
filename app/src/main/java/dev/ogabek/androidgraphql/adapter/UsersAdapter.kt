package dev.ogabek.androidgraphql.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.ogabek.androidgraphql.R
import dev.ogabek.androidgraphql.UsersLIstQuery

class UsersAdapter(private val users: ArrayList<UsersLIstQuery.User>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onClick: ((id: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UsersViewHolder(view)
    }

    class UsersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val rocket: TextView = view.findViewById(R.id.rocket)
        val time: TextView = view.findViewById(R.id.time)
        val twitter: TextView = view.findViewById(R.id.twitter)
        val root: LinearLayout = view.findViewById(R.id.llRoot)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = users[position]
        if (holder is UsersViewHolder) {
            holder.name.text = "Name: " + user.name
            holder.rocket.text = "Rocket: " + user.rocket
            holder.time.text = "Time: " + user.timestamp.toString()
            holder.twitter.text = "Twitter: " + user.twitter
            holder.root.setOnClickListener {
                onClick?.invoke(user.id as String)
            }
        }
    }

    override fun getItemCount() = users.size

}