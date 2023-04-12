package com.ananth.kotlinplayground

fun main(){

}

/** Core Coroutine Concepts
 * 1. Coroutine Scope
 * 2. Job
 * 3. CoroutineContext
 * */

/** Coroutine Scope
 * - Coroutine Scope keeps track of any coroutine we create using launch or async(These are extension functions on CoroutinesScope).
 * - The ongoing coroutine work(running coroutine) can be canceled by calling scope.cancel() at any point of time.
 * - We should create a CoroutineScope whenever want to start and control the lifecycle of coroutines in particular layer of our app.
 * - KTX libraries that already provide a CoroutineScope in certain lifecycle classes such as viewModelScope and lifecycleScope.
 * - When creating a CoroutineScope it takes CoroutineContext as a parameter to its constructor
 * */

fun createCoroutineScope(){
    // Job and Dispatcher are combined into a CoroutineContext which
// will be discussed shortly

//    val scope = CoroutineScope(Job() + Dispatchers.Main)
//    val job = scope.launch {
//        // new coroutine
//    }
}

/** Job
 * - A Job is a handle to a coroutine. For every coroutine that we create using(by launch or async),
 * - it returns a Job instance that uniquely identifies coroutine and manage its lifecycle.
 * - As we see above, you can also pass a Job to a CoroutineScope to keep handle on its lifecycle
 * */

/** CoroutineContext
 * - The CoroutineContext is a set of elements that define the behavior of a coroutine.
 * - Its made of
 * 1. Job - controls the lifecycle of coroutine
 * 2. CoroutineDispatcher - dispatches work to appropriate thread.
 * 3. CoroutineName - name of coroutine , useful for debugging.
 * 4. CoroutineException - handles uncaught exceptions,
 * - Whats the CoroutineContext of new coroutine? , we already know that new instance of the Job will be created.
 * - Allowing us to control lifecycle of coroutine. the rest of elements will be inherited from the CoroutineContext of its parent(either another coroutine or the CoroutineScope, where it was created)
 * - Since CoroutineScope can create coroutines and we can create more coroutines inside a coroutine, an implicit task hierarchy is created.
* */

fun createNewCoroutineInsideCoroutine(){

    /*val scope = CoroutineScope(Job() + Dispatchers.Main)

    val job = scope.launch {
        // New coroutine that has CoroutineScope as a parent
        val result = async {
            // New coroutine that has the coroutine started by
            // launch as a parent
        }.await()
    }*/
}

/** Job lifecycle
 * - A Job can go through set of states: New, Active, Completing, Completed, Cancelling, Canceled
 * - While we dont have access to the states themselves, we can access properties of a Job, isActive, isCancelled, isCompleted
 * - refer job_lifecycle.png in drawable folder
 * - If coroutine is an active state, the failure of coroutine or calling job.cancel() will move job in cancelling state(isCancelled = true , isActive = false)
 * - Once all children have completed their work the coroutine will go in the cancelled state and isCompleted =true
 * */

/** Parent CoroutineContext Explained
 * - In the Task Hierarchy, each coroutine has a parent that can be either CoroutineScope or another Coroutine.
 * - However, the resulting parent CoroutineContext of Coroutine can be different from the CoroutineContext of the parent since its calculated based on this function.
 * - Parent context = Defaults + inherited CoroutineContext + arguments
 * - Where some elements have default values: Dispatchers.default is the default of CoroutineDispatcher and "coroutine" the default of Coroutine name
 * - The inherited CoroutineContext is the CoroutineContext of the CoroutineScope or coroutine that created it.
 * - Arguments passed in the coroutine builder will take precedence over those elements in the inherited context.
 * - CoroutineContext can be combined using the + operator. As the CoroutineContext is a set of elements
 * - refer parent_context.png
 * - new CoroutineContext = parent CoroutineContext + Job()
 * - If with new CoroutineScope , we create a new Coroutine like this
 * - val job = scope.launch(Dispatchers.IO) {
      // new coroutine
     }
 - refer new_coroutine_parent_context.png from drawable
 - The job in the CoroutineContext and in the parent context will never be same instance as new Coroutine always get a new instance of a job
 * */

/** Cancelling Coroutines
 * - It can be pain to keep track of all coroutines or cancel individually, rather we can rely on cancelling the entire scope coroutines are launched into as this will cancel all child coroutines
 * */

// assume we have a scope defined for this layer of the app

/*
val job1 = scope.launch { … }
val job2 = scope.launch { … }
scope.cancel()
*/

/** Cancelling the scope cancels its childrens
 * - Sometimes need to cancel only one coroutine
 * - May be as reaction to a user input, Calling job1.cancel() ensures that only that specific coroutine gets cancelled and all other siblings are affected.
 * */
// assume we have a scope defined for this layer of the app

/*
val job1 = scope.launch { … }
val job2 = scope.launch { … }
// First coroutine will be cancelled and the other one won’t be affected
job1.cancel()
*/

/** Cancelled child does not affect other siblings
 * - Coroutines handle cancellation by throwing a special exception : CancellationException
 * */