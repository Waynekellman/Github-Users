package wayne.com.githubusers

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import wayne.com.githubusers.modules.networkingModule

class GithubUsers : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GithubUsers)
            modules(networkingModule)
        }
    }
}