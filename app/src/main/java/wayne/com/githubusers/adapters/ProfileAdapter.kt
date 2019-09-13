package wayne.com.githubusers.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.reponame_item.view.*
import wayne.com.githubusers.GithubWebView
import wayne.com.githubusers.R
import wayne.com.githubusers.networking.Repo

class ProfileAdapter : RecyclerView.Adapter<ProfileViewHolder>() {
    private var repoList : MutableList<Repo> = mutableListOf()
    private var repoListFull : MutableList<Repo> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.reponame_item, parent, false))
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.onBind(repoList[position])
    }

    fun setList(repoList : List<Repo>) {
        this.repoList.addAll(repoList)
        this.repoListFull.addAll(repoList)
        notifyDataSetChanged()
    }
    fun filterList(param : String){
        val filterResults = repoListFull.filter { it.name.contains(param) }
        this.repoList.clear()
        this.repoList.addAll(filterResults)
        notifyDataSetChanged()
    }

}

class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun onBind(repo: Repo){
        itemView.repo_name.text = repo.name
        val forkString = "${repo.forks} forks"
        val starString = "${repo.Stars} stars"
        itemView.forks.text = forkString
        itemView.stars.text = starString
        itemView.setOnClickListener{
            val intent = Intent(itemView.context, GithubWebView::class.java)
            intent.putExtra("url", repo.html_url)
            itemView.context.startActivity(intent)
        }

    }
}
