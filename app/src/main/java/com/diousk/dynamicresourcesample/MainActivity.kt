package com.diousk.dynamicresourcesample

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diousk.dynamicresourcesample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.setImageRes(mipmap("ic_launcher"))
        viewModel.setTextRes(R.string.test_resource)
    }
}

class MainViewModel : ViewModel() {
    val aLiveData = MutableLiveData<Int>()
    val bLiveData = MutableLiveData<Int>()
    fun setImageRes(@DrawableRes resId: Int) {
        aLiveData.value = resId
    }

    fun setTextRes(@StringRes resId: Int) {
        bLiveData.value = resId
    }
}

fun Context.resByName(resName: String?, resType: String): Int {
    resName?.let {
        return resources.getIdentifier(it, resType, packageName)
    }
    throw Resources.NotFoundException()
}

fun Context.drawable(name: String) = resByName(name, "drawable")
fun Context.mipmap(name: String) = resByName(name, "mipmap")