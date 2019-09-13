package wayne.com.githubusers.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GithubAPI {
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users")
    fun listUsers(): Call<List<User>>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users/{username}")
    fun user(@Path ("username") username : String) : Call<UserDetails>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users/{username}/repos")
    fun listRepos(@Path ("username") username : String) : Call<List<Repo>>
}

