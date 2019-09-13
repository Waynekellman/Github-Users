package wayne.com.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import wayne.com.githubusers.adapters.UserListAdapter
import retrofit2.Retrofit
import wayne.com.githubusers.networking.GithubAPI
import wayne.com.githubusers.networking.User


class MainActivity : AppCompatActivity() {
    val adapter : UserListAdapter = UserListAdapter()
    private val retrofit : Retrofit by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview_users.layoutManager = LinearLayoutManager(this)
        recyclerview_users.adapter = adapter

        val service = retrofit.create(GithubAPI::class.java)

        service.listUsers().enqueue(object : Callback<List<User>>{
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                println(t.localizedMessage)
                Toast.makeText(this@MainActivity,"something went wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    adapter.setList(response.body()!!)
                }
            }

        })

        searchView.isIconified = false
        searchView.clearFocus()
        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
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
