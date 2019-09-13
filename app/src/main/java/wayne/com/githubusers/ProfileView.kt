package wayne.com.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile_view.*
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import wayne.com.githubusers.adapters.ProfileAdapter
import wayne.com.githubusers.networking.GithubAPI
import wayne.com.githubusers.networking.Repo
import wayne.com.githubusers.networking.UserDetails

class ProfileView : AppCompatActivity() {
    private lateinit var userDetails : UserDetails
    val adapter = ProfileAdapter()
    private val retrofit : Retrofit by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_view)
        recyclerView_profile.layoutManager = LinearLayoutManager(this)
        recyclerView_profile.adapter = adapter
        val Intent = intent
        val profileName = intent.getStringExtra("name")

        val service = retrofit.create(GithubAPI::class.java)
        service.user(profileName).enqueue(object : Callback<UserDetails>{
            override fun onFailure(call: Call<UserDetails>, t: Throwable) {
                println(t.localizedMessage)
                Toast.makeText(this@ProfileView,"something went wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<UserDetails>, response: Response<UserDetails>) {
                if (response.isSuccessful) {
                    userDetails = response.body()!!
                    biography.text = userDetails.bio
                    Picasso.get().load(userDetails.avatar_url).into(user_avatar_profile)
                    username.text = userDetails.name
                    email.text = userDetails.email
                    location.text = userDetails.location
                    join_date.text = userDetails.created_at.substring(0, 10)
                    followers.text = userDetails.followers.toString()
                    following.text = userDetails.following.toString()
                }
            }

        })

        service.listRepos(profileName).enqueue(object : Callback<List<Repo>> {
            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                println(t.localizedMessage)
                Toast.makeText(this@ProfileView,"something went wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                if (response.isSuccessful){
                    adapter.setList(response.body()!!)
                }
            }

        })

        profile_search.isIconified = false
        profile_search.clearFocus()
        profile_search.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.filterList(newText)
                }
                return false
            }

        })

    }
}
