package com.ananth.kotlinplayground.annotations_reflections

fun main(){

}

//Annotation and Reflection
/**
 * ->Annotations and reflection give you the power to go beyond that and to write code that deals with arbitrary classes that aren’t known in advance.
 * You can use annotations to assign library-specific semantics to those classes; and reflection allows you to analyze the structure of the classes at runtime.
 *
 * -> The library uses reflection to access properties of arbitrary Kotlin objects at runtime and also to create objects based on data provided in JSON files.
 * Annotations let you customize how specific classes and properties are serialized and deserialized by the library.
 * */
//10.1.1. Applying annotations
/**
 * ->You use annotations in Kotlin in the same way as in Java.
 * ->To apply an annotation, you put its name, prefixed with the @ character, in the beginning of the declaration you’re annotating.
 * ->You can annotate different code elements, such as functions and classes.
 * */
//for instance
class MyTest {
//    @Test fun testTrue() {
//        Assert.assertTrue(true)
//    }
}
/**
 * ->As a more interesting example, let’s look at the @Deprecated annotation.
 * ->Its meaning in Kotlin is the same as in Java, but Kotlin enhances it with the replaceWith parameter,
 * which lets you provide a replacement pattern to support a smooth transition to a new version of the API.
 * ->The following example shows how you can provide arguments for the annotation (a deprecation message and a replacement pattern)
 * ->The arguments are passed in parentheses, just as in a regular function call.
 *  With this declaration, if someone uses the function remove,
 *  IntelliJ IDEA will not only show what function should be used instead (removeAt in this case) but also offer a quick fix to replace it automatically.
 * */
@Deprecated("Use removeAt(index) instead.", ReplaceWith("removeAt(index)"))
fun remove(index: Int) {  }

fun testRemove(){
    remove(0)
}

/**
 * ->Annotations can have parameters of the following types only:
 * primitive types, strings, enums, class references, other annotation classes, and arrays
 *  thereof. The syntax for specifying annotation arguments is slightly different from Java’s:
 *
 *  ->To specify a class as an annotation argument,  put ::class after the class name:
 *  @MyAnnotation(MyClass::class).
 *
 *  ->To specify another annotation as an argument, don’t put the @ character before the annotation name. For instance, ReplaceWith in the previous example is an annotation,
 *  but you don’t use @ when you specify it as an argument of the Deprecated annotation
 *
 *  ->To specify an array as an argument, use the arrayOf function: @Request-Mapping(path = arrayOf("/foo", "/bar")).
 *   If the annotation class is declared in Java, the parameter named value is automatically converted to a vararg parameter if necessary,
 *
 *   ->If the annotation class is declared in Java, the parameter named value is automatically converted to a vararg parameter if necessary,
 *   so the arguments can be provided without using the arrayOf function.
 *
 *   ->Annotation arguments need to be known at compile time, so you can’t refer to arbitrary properties as arguments.
 *   ->To use a property as an annotation argument, you need to mark it with a const modifier,
 *   which tells the compiler that the property is a compile-time constant.
 *
 *   ->ere’s an example of JUnit’s @Test annotation that specifies the timeout for the test, in milliseconds, using the timeout parameter:
 * */

const val TEST_TIMEOUT = 100L

//@Test(timeout = TEST_TIMEOUT) fun testMethod() {  }


//10.1.2. Annotation targets