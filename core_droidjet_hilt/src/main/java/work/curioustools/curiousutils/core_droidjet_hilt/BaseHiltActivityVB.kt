package work.curioustools.curiousutils.core_droidjet_hilt

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import work.curioustools.curiousutils.core_droidjet.arch.BaseCommonActivity
import work.curioustools.curiousutils.core_droidjet.vb_helpers.VBHolder
import work.curioustools.curiousutils.core_droidjet.vb_helpers.VBHolderImpl

@AndroidEntryPoint
abstract class BaseHiltActivity: BaseCommonActivity()


abstract class BaseHiltActivityVB<VB: ViewBinding>: BaseHiltActivity(), VBHolder<VB> by VBHolderImpl() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBindingForComponent(layoutInflater).setContentViewFor(this)
        setup()
    }

    abstract fun getBindingForComponent(layoutInflater: LayoutInflater):VB
    abstract fun setup()

}