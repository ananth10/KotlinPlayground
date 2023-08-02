package com.ananth.kotlinplayground.coroutines.coroutine.structured_concurrency

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay

class MyLifeCycleScopeActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        lifecycleScope.launchWhenCreated {
            delay(1000) //make a network request
        }
    }
}