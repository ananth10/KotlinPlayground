package com.ananth.kotlinplayground.annotations_reflections

import java.util.Date
import kotlin.reflect.KClass

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
/**
 * ->The syntax looks like a regular class declaration, with the added annotation modifier before the class keyword.
 * ->Because annotation classes are only used to define the structure of metadata associated with declarations and expressions,they can’t contain any code.
 * Therefore, the compiler prohibits specifying a body for an annotation class.
 * ->For annotations that have parameters, the parameters are declared in the primary constructor of the class:
 * ->You use the regular primary constructor declaration syntax. The val keyword is mandatory for all parameters of an annotation class.
 * */

//e.g - simple annotation
annotation class JsonExclude
//with patamter
//annotation class JsonName(val name: String)

//For comparison, here’s how you’d declare the same annotation in Java:
/* Java */
//public @interface JsonName {
//    String value();
//}
/**
 * ->Note how the Java annotation has a method called value, whereas the Kotlin annotation has a name property.
 * ->The value method is special in Java: when you apply an annotation, you need to provide explicit names for all attributes you’re specifying except value.
 * ->n Kotlin, on the other hand, applying an annotation is a regular constructor call.
 * */

//10.1.5. Meta-annotations: controlling how an annotation is processed
/**
 * ->Just as in Java, a Kotlin annotation class can itself be annotated.
 * ->The annotations that can be applied to annotation classes are called meta-annotations.
 * ->The standard library defines several of them, and they control how the compiler processes annotations.
 * Other frameworks use meta-annotations as well—for example,
 * ->for example, many dependency-injection libraries use meta-annotations to mark annotations used to identify different injectable objects of the same type.
 *
 * ->Of the meta-annotations defined in the standard library, the most common is @Target.
 * ->The declarations of JsonExclude and JsonName in JKid use it to specify the valid targets for those annotations.
 *
 * ->The @Target meta-annotation specifies the types of elements to which the annotation can be applied.
 * -> If you don’t use it, the annotation will be applicable to all declarations.
 * ->That wouldn’t make sense for JKid, because the library processes only property annotations.
 * */


//Target
/**
 * ->The list of values of the AnnotationTarget enum gives the full range of possible targets for an annotation.
 * ->It includes classes, files, functions, properties, property accessors, types, all expressions, and so on.
 * ->You can declare multiple targets if you need to:
 * @Target(AnnotationTarget.CLASS, AnnotationTarget.METHOD).
 *
 * ->To declare your own meta-annotation, use ANNOTATION_CLASS as its target:
 * */

@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class BindingAnnotation
@BindingAnnotation //meta-annoation
annotation class MyBinding

//THE @RETENTION ANNOTATION
//In Java, you’ve probably seen another important meta-annotation,
// @Retention. You can use it to specify whether the annotation you declare will be stored in the .class file and whether it will be accessible at runtime through reflection. Java by default retains annotations in .class files
// but doesn’t make them accessible at runtime. Most annotations do need to be present at runtime,
// so in Kotlin the default is different: annotations have RUNTIME retention. Therefore, the JKid annotations do not have an explicitly specified retention.

//10.1.6. Classes as annotation parameters

/**
 * ->Below example we passed CompanyImpl as argument because we cannot create instance of interface directly.
 * ->Whenever JKid reads a nested company object for a Person instance, it creates and deserializes an instance of CompanyImpl and stores it in the company property.
 * ->
 * */

interface Company {
    val name: String
}

data class CompanyImpl(override val name: String) : Company

data class Person(
    val name: String,
    @DeserializeInterface(CompanyImpl::class) val company: Company
)
annotation class DeserializeInterface(val targetClass: KClass<out Any>)

/**
 * ->The KClass type is Kotlin’s counterpart to Java’s java.lang.Class type.
 * ->It’s used to hold references to Kotlin classes;
 * ->The type parameter of KClass specifies which Kotlin classes can be referred to by this reference.
 * For instance, CompanyImpl::class has a type KClass<CompanyImpl>,
 * which is a subtype of the annotation parameter type
 * ->If you wrote KClass<Any> without the out modifier, you wouldn’t be able to pass CompanyImpl::class as an argument
 * the only allowed argument would be Any::class.
 *
 * ->The out keyword specifies that you’re allowed to refer to classes that extend Any, not just to Any itself.
 * */

//10.1.7. Generic classes as annotation parameters
/**
 * ->By default, JKid serializes properties of nonprimitive types as nested objects. But you can change this behavior and provide your own serialization logic for some values.
 * ->The @CustomSerializer annotation takes a reference to a custom serializer class as an argument.
 * The serializer class should implement the ValueSerializer interface:
 *
 * ->Suppose you need to support serialization of dates, and you’ve created your own DateSerializer class for that, implementing the ValueSerializer<Date> interface.
 * -> The Value-Serializer class is generic and defines a type parameter,  so you need to provide a type argument value whenever you refer to the type.
 * -> Because you know nothing about the types of properties with which this annotation will be used, you can use a star projection (discussed in section 9.3.6) as the argument:
 * */

interface ValueSerializer<T> {
    fun toJsonValue(value: T): Any?
    fun fromJsonValue(jsonValue: Any?): T
}

data class Person1(
    val name: String,
//    @CustomSerializer(DateSerializer::class) val birthDate: Date
)

/**
 * ->You need to ensure that the annotation can only refer to classes that implement the ValueSerializer interface.
 * ->For instance, writing @CustomSerializer(Date::class) should be prohibited, because Date doesn’t implement the ValueSerializer interface.
 *
 * -> You can write KClass<out YourClassName>, and if YourClassName has its own type arguments, replace them with *.
 *
 * You’ve now seen all the important aspects of declaring and applying annotations in Kotlin.
 * The next step is to find out how to access the data stored in the annotations. For this, you need to use reflection.
 * */
annotation class CustomSerializer(
    val serializerClass: KClass<out ValueSerializer<*>>
    //out -> Accept any class implementing ValueSerializer, not only ValueSerializer::class
    //* -> Allows ValueSerializer to serialize any value
)
