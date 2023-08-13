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
 *   ->there’s an example of JUnit’s @Test annotation that specifies the timeout for the test, in milliseconds, using the timeout parameter:
 * */

const val TEST_TIMEOUT = 100L

//@Test(timeout = TEST_TIMEOUT) fun testMethod() {  }


//10.1.2. Annotation targets
/**
 * ->In many cases, a single declaration in the Kotlin source code corresponds to multiple Java declarations, and each of them can carry annotations.
 * ->For example, a Kotlin property corresponds to a Java field, a getter, and possibly a setter and its parameter.
 * ->A property declared in the primary constructor has one more corresponding element: the constructor parameter.
 * Therefore, it may be necessary to specify which of these elements needs to be annotated.
 *
 * ->You specify the element to be annotated with a use-site target declaration.
 * ->The use-site target is placed between the @ sign and the annotation name and is separated from the name with a colon.
 * ->The word get in figure 10.1 causes the annotation @Rule to be applied to the property getter. @get:Rule
 *
 * ->In JUnit, you can specify a rule to be executed before each test method.
 * ->For instance, the standard TemporaryFolder rule is used to create files and folders that are deleted when the test method finishes.
 * ->To specify a rule, in Java you declare a public field or method annotated with @Rule.
 *  But if you just annotate the property folder in your Kotlin test class with @Rule
 *  you’ll get a JUnit exception: “The @Rule ‘folder’ must be public.” It happens because @Rule is applied to the field,
 *   which is private by default. To apply it to the getter, you need to write that explicitly, @get:Rule, as follows:
 *
 *  ->If you annotate a property with an annotation declared in Java, it’s applied to the corresponding field by default
 *  Kotlin also lets you declare annotations that can be directly applied to properties.
 *
 *  The full list of supported use-site targets is as follows:
 *
 * 1.property—Java annotations can’t be applied with this use-site target.
   2.field—Field generated for the property.
   3.get—Property getter.
   4.set—Property setter.
   5.receiver—Receiver parameter of an extension function or property.
   6.param—Constructor parameter.
   7.setparam—Property setter parameter.
   8,delegate—Field storing the delegate instance for a delegated property.
   9.file—Class containing top-level functions and properties declared in the file.

 ->Any annotation with the file target needs to be placed at the top level of the file,  before the package directive.
One of the annotations commonly applied to files is @JvmName, which changes the name of the corresponding class. Section 3.2.3 showed you an example:
@file:JvmName("StringFunctions").
 *
 * ->Note that unlike Java, Kotlin allows you to apply annotations to arbitrary expressions, not only to class and function declarations or types
 *  The most common example is the @Suppress annotation,
 *  which you can use to suppress a specific compiler warning in the context of the annotated expression
 *
 *  Here’s an example that annotates a local variable declaration to suppress an unchecked cast warning:
 *
 * */
class HasTempFolder {
//    @get:Rule
//    val folder = TemporaryFolder()
//    @Test
//    fun testUsingTempFolder() {
//        val createdFile = folder.newFile("myfile.txt")
//        val createdFolder = folder.newFolder("subfolder")
//        // ...
//    }
}


fun test(list: List<*>) {
    @Suppress("UNCHECKED_CAST")
    val strings = list as List<String>
    // ...
}

//CONTROLLING THE JAVA API WITH ANNOTATIONS
/**
 * ->Kotlin provides a variety of annotations to control how declarations written in Kotlin are compiled to Java bytecode and exposed to Java callers.
 * ->Some of those annotations replace the corresponding keywords of the Java language:
 * ->for example, the @Volatile and @Strictfp annotations serve as direct replacements for Java’s volatile and strictfp keywords.
 * Others are used to change how Kotlin’s declarations are visible to Java callers:
 * 1.@JvmName changes the name of a Java method or field generated from a Kotlin declaration.
 * 2.@JvmStatic can be applied to methods of an object declaration or a companion object to expose them as static Java methods.
 * 3.@JvmOverloads, mentioned in section 3.2.2, instructs the Kotlin compiler to generate overloads for a function that has default parameter values.
 * 4.@JvmField can be applied to a property to expose that property as a public, Java field with no getters or setters.
 * */

//10.1.3. Using annotations to customize JSON serialization
/**
 * ->One of the classic use cases for annotations is customizing object serialization
 * Serialization:
 * Serialization is a process of converting an object to a binary or text representation that can be then stored or sent over the network.
 * The reverse process, deserialization,
 * converts such a representation back to an object.
 * One of the most common formats used for serialization is JSON
 * ->There are many widely used libraries for serializing Java objects to JSON, including Jackson, Gson
 * */

/**
 * ->You can use annotations to customize the way objects are serialized and deserialized.
 * ->When serializing an object to JSON, by default the library tries to serialize all the properties and uses the property names as keys.
 * ->The annotations allow you to change the defaults.
 * ->The @JsonExclude annotation is used to mark a property that should be excluded from serialization and deserialization.
 * ->The @JsonName annotation lets you specify that the key in the key/value pair representing the property should be the given string, not the name of the property.
 * */

//data class Person(
//    @JsonName("alias") val firstName: String,
//    @JsonExclude val age: Int? = null
//)
/**
 * ->You annotate the property firstName to change the key used to represent it in JSON.
 * ->You annotate the property age to exclude it from serialization and deserialization.
 * ->Note that you must specify the default value of the property age. Otherwise, you wouldn’t be able to create a new instance of Person during deserialization.
 * */

//10.1.4. Declaring annotations