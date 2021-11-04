package work.curioustools.curiousutils.core_droidjet.arch.vb

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import work.curioustools.curiousutils.core_droidjet.extensions.relaxedError

/**
 * Make sure to use this with Fragment.viewLifecycleOwner.
 * requires library : androidx.lifecycle:lifecycle-runtime:$lifecycle_version
 */
// utility class for providing viewbinding support to any lifecycle owner
interface VBHolder<B : ViewBinding> {
    var bindingHolder: B?

    fun getNullableBinding() = bindingHolder

    fun withBinding(block: B.() -> Unit, isRelaxed:Boolean = false) {
        val localBinding  = bindingHolder
        if (localBinding == null) relaxedError(ERROR_BINDING_IS_NULL,isRelaxed)
        else  block.invoke(localBinding)
    }

    fun usingBinding(block: (binding: B) -> Unit, isRelaxed: Boolean = false) {
        val localBinding  = bindingHolder
        if (localBinding == null) relaxedError(ERROR_BINDING_IS_NULL, isRelaxed)
        else block.invoke(localBinding)
    }


    fun initHolder(binding: B){
        this.bindingHolder = binding
    }

    fun destroyBinding(){
        this.bindingHolder =null
    }


    // A nifty little extension on binding of the children class which will allow  to both set and unset at the same time using lifecycle owner.
    fun B.setContentViewVB(activity: AppCompatActivity) {
        initHolder(this)
        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                owner.lifecycle.removeObserver(this)
                destroyBinding()
            }
        })
        activity.setContentView(this.root)
    }

    // A nifty little extension on binding of the children class which will allow  to both set and unset at the same time using lifecycle owner.
    fun B.getVBView(fragment: Fragment): View {
        initHolder(this)
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                owner.lifecycle.removeObserver(this)
                destroyBinding()
            }
        })
        return this.root
    }


    companion object {
        //// View Binding Error Messages ////
        const val ERROR_BINDING_IS_NULL = "Binding Not Found"
    }
}
