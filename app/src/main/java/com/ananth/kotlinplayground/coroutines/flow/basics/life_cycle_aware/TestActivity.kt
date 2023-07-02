package com.ananth.kotlinplayground.coroutines.flow.basics.life_cycle_aware

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ananth.kotlinplayground.R
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        //Approach1
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                emitFlow().collect {
                    println(it)
                }
            }
        }

        //Approach 2
        lifecycleScope.launch {
            emitFlow()
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    println(it)
                }
        }


    }

    fun emitFlow() = flowOf(1, 2, 3)
}