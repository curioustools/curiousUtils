package work.curioustools.curiousutils.core_droidjet.arch

import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

abstract class BaseCommonActivity: AppCompatActivity(){

    val activityHandler: Handler by lazy {
        val looper = Looper.getMainLooper()
        Handler(looper)
    }
}

