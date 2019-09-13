package wayne.com.githubusers

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_github_web_view.*

class GithubWebView : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_web_view)
        val intent = intent
        val url = intent.getStringExtra("url")
        repo_page.settings.javaScriptEnabled = true
        repo_page.loadUrl(url)
    }
}
