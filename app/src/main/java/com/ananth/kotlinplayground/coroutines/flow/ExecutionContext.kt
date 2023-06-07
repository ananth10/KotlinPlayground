package com.ananth.kotlinplayground.coroutines.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

fun main(){

}

//Execution Context
/**
 * Have you ever tried to change coroutine dispatcher while creating a flow?
 * If you collect this flow you will face an exception with such message:
 * Flow invariant is violated: Flow was collected in [CoroutineId(2), “coroutine#2”:StandaloneCoroutine{Active}@4756c09e, Dispatchers.Default], but emission happened in
 *
 * The execution context of a kotlin Flow is crucial to understanding why you cannot withContext inside a flow to change the coroutine context in which an item is emitted.
 * The execution context of flow refers to  the coroutine context in which flow was created and determines the thread pool and dispatcher that are used for the execution of each flow.
 *
 * Consider an android app that wants to  update the UI with data from a flow. in this case, the flow needs to emit items on the UI thread to avoid concurrency issues.
 * if we use withContext inside flow to change the coroutine context and emit items on a background thread. the UI update
 * may occur on a different thread than the one on which the UI was created. leading to exceptions and crashes.
 *
 * Additionally, if you allows flows to emit items in different coroutine contexts, every flow collector would need to write boilerplate code to ensure that its block of execution happens in the correct context.
 * Alternatively, you would need to establish project-wide rules and limitations on the context
 * in which elements of the flow are allowed to be emitted, which can be cumbersome and difficult to enforce.
 *
 * To address these issues, you should use the flowOn operator to change the execution context of a flow.
 *
 * When using the flowOn operator to change the dispatcher of the upstream emitter in a flow,
 * a ChannelCoroutine is added in the middle of the collector and the flow.
 * This ChannelCoroutine has the specified dispatcher as its coroutine context element.
 * When calling the Flow.collect { } function, data is received from this channel,
 *  ensuring that the downstream collector runs in the specified coroutine context while preserving the execution context of the flow.
 *  This enables you to control the coroutine context in which downstream processing occurs while ensuring that the flow remains exception-transparent and concurrency-safe.
 * */

suspend fun defaultContext() {
    flow {
        withContext(Dispatchers.Default) {
            while (isActive) {
                emit(1)
            }
        }
    }
}

//using flowOn operator

suspend fun useFlowOn(){
    val flow = flowOf(1,2,3,4)
    flow.flowOn(Dispatchers.IO).collect{ println(it) }
}