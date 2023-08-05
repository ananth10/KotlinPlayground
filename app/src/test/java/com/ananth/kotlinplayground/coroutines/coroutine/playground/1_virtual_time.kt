package com.ananth.kotlinplayground.coroutines.coroutine.playground

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class SystemUnderTest{
    suspend fun functionWithDelay():Int{
        delay(1000)
        return 42
    }
}

fun CoroutineScope.functionThatStartNewCoroutine(){

    launch {
        delay(1000)
        println("Coroutine gets completed")
    }
}

class TestClass{
    @Test
    fun `functionWithDelay should return 42`() = runBlockingTest{
        val realTimeStart = System.currentTimeMillis()
        val virtualTimeStart = currentTime

//        val sut = SystemUnderTest()
//        val actualValue = sut.functionWithDelay()
//
//        Assert.assertEquals(42,actualValue)

        functionThatStartNewCoroutine()
        advanceTimeBy(1000) //manually advance the delay time for child coroutine

        val realTimeDuration = System.currentTimeMillis()-realTimeStart
        val virtualTimeDuration = currentTime - virtualTimeStart
        println("realTimeDuration:$realTimeDuration")
        println("virtualTimeDuration:$virtualTimeDuration")
    }
}