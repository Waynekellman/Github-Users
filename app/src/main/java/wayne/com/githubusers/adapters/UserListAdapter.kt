package wayne.com.githubusers.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_item.view.*
import wayne.com.githubusers.GithubWebView
import wayne.com.githubusers.ProfileView
import wayne.com.githubusers.R
import wayne.com.githubusers.networking.User

class UserListAdapter : RecyclerView.Adapter<UserListViewHolder>() {
    private var usersList : MutableList<User> = mutableListOf()
    private var usersListFull : MutableList<User> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false))
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.onBind(usersList[position])
    }

    fun setList(userList : List<User>){
        this.usersList.addAll(userList)
        this.usersListFull.addAll(userList)
        notifyDataSetChanged()
    }
    fun filterList(param : String){
        val filterResults = usersListFull.filter { it.login.contains(param) }
        this.usersList.clear()
        this.usersList.addAll(filterResults)
        notifyDataSetChanged()
    }
}

class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun onBind(user : User){
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, ProfileView::class.java)
            intent.putExtra("name", user.login)
            itemView.context.startActivity(intent)
        }
        Picasso.get().load(user.avatar_url).into(itemView.user_avatar)
        itemView.user_name.text = user.login
        val repoText = " Repo: ${user.id}"
        itemView.repo_number.text = repoText
    }



}
