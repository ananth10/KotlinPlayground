package com.ananth.kotlinplayground.annotations_reflections

import com.ananth.kotlinplayground.joinToString
import kotlin.reflect.KCallable
import kotlin.reflect.KFunction
import kotlin.reflect.KFunction2
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

fun main(){
    check()
    check1()
    check2()
    check4()
}

//10.2. Reflection: introspecting Kotlin objects at runtime
/**
 * ->Reflection is, simply put, a way to access properties and methods of objects dynamically at runtime,
 * without knowing in advance what those properties are.
 *
 * -> Normally, when you access a method or a property of an object, the source code of your program references a specific declaration,
 * and the compiler statically resolves the reference and ensures that the declaration exists
 * But sometimes you need to write code that can work with objects of any type,
 * or where the names of methods and properties to be accessed are only known at runtime.
 *  The JSON serialization library is a great example of such code:  it needs to be able to serialize any object to JSON
 *  so it can’t reference specific classes and properties. This is where reflection comes into play.
 *
 * ->When working with reflection in Kotlin, you deal with two different reflection APIs
 * 1. The first is the standard Java reflection, defined in the java.lang.reflect package.
 * Because Kotlin classes are compiled to regular Java bytecode, the Java reflection API supports them perfectly well.
 *  In particular, this means Java libraries that use the reflection API are fully compatible with Kotlin code
 *
 *  2. The second is the Kotlin reflection API, defined in the kotlin.reflect package.
 *   It gives you access to concepts that don’t exist in the Java world, such as properties and nullable types.
 *   But at this time it doesn’t provide a comprehensive replacement for the Java reflection API,
 *   there are cases where you need to fall back to Java reflection.
 *   An important note is that the Kotlin reflection API isn’t restricted to Kotlin classes: you can use the same API to access classes written in any JVM language.
 * */

//10.2.1. The Kotlin reflection API: KClass, KCallable, KFunction, and KProperty

/**
 * ->The main entry point of the Kotlin reflection API is KClass,which represents a class.
 *  KClass is the counterpart of java.lang.Class,
 *  -> and you can use it to enumerate and access all the declarations contained in the class, its superclasses, and so on.
 *  -> You get an instance of KClass by writing MyClass::class.
 *  ->To get the class of an object at runtime, first you obtain its Java class using the javaClass property,
 *  which is a direct equivalent to Java’s java.lang.Object.getClass().
 *  Then you access the .kotlin extension property to move from Java to Kotlin reflection API:
 *
 *  ->This simple example prints the name of the class and the names of its properties and uses
 *  .memberProperties to collect all non-extension properties defined in the class, as well as in all of its superclasses.
 * */

private class Person2(val name: String, val age: Int)

fun check(){
    val person = Person2("Alice", 29)
    val kClass = person.javaClass.kotlin //return the instance of KClass
    println(kClass.simpleName)
}

//KClass in kotlin
interface KClass<T : Any> {
    val simpleName: String?
    val qualifiedName: String?
    val members: Collection<KCallable<*>>
    val constructors: Collection<KFunction<T>>
    val nestedClasses: Collection<KClass<*>>
}

/**
 * ->You may have noticed that the list of all members of a class is a collection of KCallable instances.
 * ->KCallable is a superinterface for functions and properties.
 * It declares the call method, which allows you to call the corresponding function or the getter of the property:
 * */
//KCallable in kotlin
interface KCallable<out R> {
    fun call(vararg args: Any?): R

}
//You provide the function arguments in a vararg list.
// The following code demonstrates how you can use call to call a function through reflection:
//

/**
 * ->You saw the ::foo syntax in section 5.1.5, and now you can see that the value of this expression is an instance of the KFunction class from the reflection API.
 * ->To call the referenced function, you use the KCallable.call method.
 * In this case, you need to provide a single argument, 42.
 *  If you try to call the function with an incorrect number of arguments, such as kFunction.call(), it will throw a runtime exception: “IllegalArgumentException: Callable expects 1 arguments, but 0 were provided.”
 *
 *  ->In this case, however, you can use a more specific method to call the function.
 *  ->The type of the ::foo expression is KFunction1<Int, Unit>, which contains information about parameter and return types.
 *  ->The 1 denotes that this function takes one parameter. To call the function through this interface, you use the invoke method.
 *   It accepts a fixed number of arguments (1 in this case), and their types correspond to the type parameters of the KFunction1 interface.
 *   You can also call kFunction directly:[1]
 *
 *   ->Therefore, if you have a KFunction of a specific type, with known parameters and return type, it’s preferable to use its invoke method.
 *   ->The call method is a generic approach that works for all types of functions but doesn’t provide type safety
 *
 * */
fun foo(x: Int) = println(x)

fun check1(){
    val kFunction = ::foo
    kFunction.call(34)
}

fun sum(x: Int, y: Int) = x + y

fun check2(){
    val KFunction: KFunction2<Int,Int,Int> = ::sum
    println(KFunction.invoke(2,3))
//    println(KFunction.invoke(2)) //Now you can’t call the invoke method on kFunction with an incorrect number of arguments: it won’t compile
}

//HOW AND WHERE ARE KFUNCTIONN INTERFACES DEFINED?
/**
 * ->Types such as KFunction1 represent functions with different numbers of parameters.
 * Each type extends KFunction and adds one additional member invoke with the appropriate number of parameters.
 * ->For example, KFunction2 declares operator fun invoke(p1: P1, p2: P2): R, where P1 and P2 represent the function parameter types and R represents the return type.
 *
 * These function types are synthetic compiler-generated types, and you won’t find their declarations in the kotlin.reflect package.
 * That means you can use an interface for a function with any number of parameters.
 *  The synthetic-types approach reduces the size of kotlin-runtime.jar  and avoids artificial restrictions on the possible number of function-type parameters.
 * */

/**
 * ->You can invoke the call method on a KProperty instance as well,
 * and it will call the getter of the property.
 * But the property interface gives you a better way to obtain the property value: the get method.
 * ->To access the get method, you need to use the correct interface for the property, depending on how it’s declared.
 * ->Top-level properties are represented by instances of the KProperty0 interface, which has a no-argument get method:
 * */

var count = 0

fun check4(){
    val KProperty = ::count

    KProperty.setter.call(21)
    println(KProperty.get())
}

/**
 * ->A member property is represented by an instance of KProperty1, which has a one-argument get method.
 * ->To access its value, you must provide the object instance for which you need the value.
 * The following example stores a reference to the property in a memberProperty variable;
 * -> then you call memberProperty.get(person) to obtain the value of this property for the specific person instance.
 * So if a memberProperty refers to the age property of the Person class, memberProperty.get(person) is a way to dynamically get the value of person.age:
 * */
private data class Person3(val name:String,val age:Int)

fun accessMemberProperty(){
    val person = Person3("Alice",34);
    val memberProperty = Person3::age
    println(memberProperty.get(person))
}

/**
 * ->Note that KProperty1 is a generic class. The memberProperty variable has the type KProperty<Person, Int>,
 * ->where the first type parameter denotes the type of the receiver and the second type parameter stands for the property type.
 * Thus you can call its get method only with a receiver of the right type; the call member-Property.get("Alice") won’t compile.
 *
 * ->Also note that you can only use reflection to access properties defined at the top level or in a class, but not local variables of a function.
 * If you define a local variable x and try to get a reference to it using ::x, you’ll get a compilation error saying that “References to variables aren’t supported yet”.
 * */

/**
 * shows a hierarchy of interfaces that you can use to access source code elements at runtime.
 * ->Because all declarations can be annotated,
 * -> the interfaces that represent declaration at runtime, such as KClass, KFunction, and KParameter, all extend KAnnotatedElement.
 *
 * -> KClass is used to represent both classes and objects.
 * ->KProperty can represent any property, whereas its subclass, KMutableProperty, represents a mutable property,  which you declare with var.
 * You can use the special interfaces Getter and Setter declared in Property and KMutableProperty to work with property accessors as functions—for example, if you need to retrieve their annotations.  Both interfaces for accessors extend KFunction.
 *
 *              KAnnotatedProperty
 *              /       |         \
 *             KClass  KCallable   KParameter
 *                      /     \
 *                KFunction  KProperty
 *                \             \
 *                KFunction1    KMutableProperty
 *                        \  \
 *    KMutableProperty.Setter  KProperty.Getter
 *
 * */

//10.2.2. Implementing object serialization using reflection

//first recall the declaration of serialize function in JKid
fun serialize(obj: Any): String = buildString { serializeObject(obj) }

/**
 * The above function takes an object and returns its JSON representation as string.
 * It will build up the resulting JSON in a StringBuilder instance.
 * As it serialize object properties and their values, it will append them to this StringBuilder object.
 * To make the append calls more concise, lets put the implementation in an extension function tp StringBuilder.
 * That way, you can conveniently calls the append method without qualifier
 *
 *
 * */


private fun StringBuilder.serializeObject(obj:Any){
    val kClass = obj.javaClass.kotlin
    val memberProperties = kClass.memberProperties
    memberProperties.joinToString(this, prefix = "{", postfix = "}"){prop->
//        serializeString(prop.name)
        append(":")
//        serializePropertyValue(prop.get(obj))
    }
    append(obj)
}
//Consequently, the serialize function delegates all the work to serialize-Object:
/**
 * As you saw in section 5.5.2, buildString creates a StringBuilder and lets you fill it with content in a lambda. In this case, the content is provided by the call to serialize-Object(obj).
 * ->Now let’s discuss the behavior of the serialization function.
 * By default, it will serialize all properties of the object.
 * ->Primitive types and strings will be serialized as JSON number, boolean, and string values, as appropriate.
 * ->Collections will be serialized as JSON arrays.
 * ->Properties of other types will be serialized as nested objects.
 * As we discussed in the previous section, this behavior can be customized through annotations.
 *
 * ->The implementation of this serializeObject function should be clear: you serialize each property of the class, one after another.
 * ->The resulting JSON will look like this: { prop1: value1, prop2: value2 }.
 * ->The joinToStringBuilder function ensures that properties are separated with commas.
 * ->The serializeString function escapes special characters as required by the JSON format.
 * ->serializePropertyValue function checks whether a value is a primitive value, string, collection, or nested object, and serializes its content accordingly.
 *
 * ->we discussed a way to obtain the value of the KProperty instance: the get method.
 * ->In that case, you worked with the member reference Person::age of the type KProperty1<Person, Int>,
 * ->which lets the compiler know the exact types of the receiver and the property value.
 * ->In this example, however, the exact types are unknown, because you enumerate all the properties of an object’s class.
 * ->Therefore, the prop variable has the type KProperty1<Any, *>, and prop.get(obj) returns a value of Any type.
 * You don’t get any compile-time checks for the receiver type, but because you’re passing the same object from which you obtained the list of properties,
 * the receiver type will be correct. Next, let’s see how the annotations that tune up serialization are implemented.
 * */
//Let’s look at the implementation of serializeObject, where you can observe the reflection API in a real scenario.


//10.2.3. Customizing serialization with annotations
/**
 * ->Earlier in this chapter, you saw the definitions of annotations that let you customize the process of JSON serialization.
 * ->In particular, we discussed the @JsonExclude, @JsonName, and @CustomSerializer annotations.
 * Now it’s time to see how these annotations can be handled by the serializeObject function.
 * ->We’ll start with @JsonExclude. This annotation allows you to exclude some properties from serialization.
 * Let’s investigate how you should change the implementation of the serializeObject function to support that.
 *
 * ->Recall that to get all member properties of the class,
 * you use the extension property memberProperties on the KClass instance.
 * -> But now the task gets more complicated: properties annotated with @JsonExclude need to be filtered out. Let’s see how this is done.
 *
 * ->The KAnnotatedElement interface defines the property annotations,
 *  a collection of instances of all annotations (with runtime retention) applied to the element in the source code.
 *  Because KProperty extends KAnnotatedElement,  you can access all annotations for a property by saying property.annotations.
 *
 *  But here the filtering doesn’t use all annotations; it needs to find a specific one. The helper function findAnnotation does the job:
 *
 *  ->The findAnnotation function returns an annotation of a type specified as an argument if such an annotation is present.
 *
 *  ->It uses the pattern  and makes the type parameter reified in order to pass the annotation class as the type argument.
 * */

inline fun <reified T> KAnnotatedElement.findAnnotation(): T?
        = annotations.filterIsInstance<T>().firstOrNull()

//Now you can use findAnnotation together with the filter standard library function to filter out the properties annotated with @JsonExclude:

val properties = kClass.memberProperties
    .filter { it.findAnnotation<JsonExclude>() == null }

val jsonNameAnn=prop.findAnnotation<JsonName>()
val propName=jsonNameAnn?.name?:prop.name


/**
 * ->If a property isn’t annotated with @JsonName, then jsonNameAnn is null, and you still use prop.name as the name for the property in JSON. If the property is annotated, you use the specified name instead.
 * ->Let’s look at the serialization of an instance of the Person class declared earlier.
 *   ->During the serialization of the firstName property, jsonNameAnn contains the corresponding instance of the annotation class JsonName. Thus jsonNameAnn?.name returns the non-null value "alias",
 *   which is used as a key in JSON.
 *   -> When the age property is serialized, the annotation isn’t found, so the property name age is used as a key.
 *
 * */

//Serializing an object with property filtering
private fun StringBuilder.serializeObject(obj: Any) {
    obj.javaClass.kotlin.memberProperties
        .filter { it.findAnnotation<JsonExclude>() == null }
        .joinToStringBuilder(this, prefix = "{", postfix = "}") {
            serializeProperty(it, obj)
        }
}
//Now the properties annotated with @JsonExclude are filtered out. We’ve also extracted the logic responsible for property serialization into a separate serialize-Property function.

//Serializing a single property

private fun StringBuilder.serializeProperty(
    prop: KProperty1<Any, *>, obj: Any
) {
    val jsonNameAnn = prop.findAnnotation<JsonName>()
    val propName = jsonNameAnn?.name ?: prop.name
    serializeString(propName)
    append(": ")

    serializePropertyValue(prop.get(obj))
}
//The property name is processed according to the @JsonName annotation discussed earlier.

/**
 * Next, let’s implement the remaining annotation, @CustomSerializer.
 *
 * The implementation is based on the function getSerializer,
 * which returns the ValueSerializer instance registered via the @CustomSerializer annotation.
 *
 * For example, if you declare the Person class as shown next and call getSerializer() when serializing the birthDate property, it will return an instance of DateSerializer:
 * */
data class Person(
    val name: String,
    @CustomSerializer(DateSerializer::class) val birthDate: Date
)
annotation class CustomSerializer(
    val serializerClass: KClass<out ValueSerializer<*>>
)
//. Retrieving the value serializer for a property
fun KProperty<*>.getSerializer(): ValueSerializer<Any?>? {
    val customSerializerAnn = findAnnotation<CustomSerializer>() ?: return null
    val serializerClass = customSerializerAnn.serializerClass

    val valueSerializer = serializerClass.objectInstance
        ?: serializerClass.createInstance()
    @Suppress("UNCHECKED_CAST")
    return valueSerializer as ValueSerializer<Any?>
}

/**
 * It’s an extension function to KProperty, because the property is the primary object handled by the method.
 * -> It calls the findAnnotation function to get an instance of the @CustomSerializer annotation if it exists.
 * -> Its argument, serializerClass, specifies the class for which you need to obtain an instance.
 * ->The most interesting part here is the way you handle both classes and objects (Kotlin’s singletons)
 * as values of the @CustomSerializer annotation. They’re both represented by the KClass class.
 * The difference is that objects have a non-null value of the objectInstance property,
 *  which can be used to access the singleton instance created for the object.
 *
 * For example, DateSerializer is declared as an object,
 * so its objectInstance property stores the singleton DateSerializer instance.
 * You’ll use that instance to serialize all objects, and createInstance won’t be called.
 *
 * If the KClass represents a regular class, you create a new instance by calling createInstance.
 *  This function is similar to java.lang.Class.newInstance.
 *
 *  Finally, you can use getSerializer in the implementation of serialize-Property. Here’s the final version of the function.
 *
 *  serializeProperty uses the serializer to convert the property value to a JSON-compatible format by calling toJsonValue.
 *  If the property doesn’t have a custom serializer, it uses the property value.
 *
 * */
//Serializing a property, with custom serializer support
private fun StringBuilder.serializeProperty(
    prop: KProperty1<Any, *>, obj: Any
) {
    val name = prop.findAnnotation<JsonName>()?.name ?: prop.name
    serializeString(name)
    append(": ")
    val value = prop.get(obj)
    val jsonValue =
        prop.getSerializer()?.toJsonValue(value)
            ?: value
    serializePropertyValue(jsonValue)
}


//10.2.4. JSON parsing and object deserialization
//Let’s start with the second part of the story: implementing the deserialization logic. First, recall that the API, like that used for serialization, consists of a single function:
inline fun <reified T: Any> deserialize(json: String): T

data class Author(val name: String)

data class Book(val title: String, val author: Author)

fun testDeserialize(){
    val json = """{"title": "Catch-22", "author": {"name": "J. Heller"}}"""
    val book = deserialize<Book>(json)
    println(book)
}

/**
 * ->You pass the type of object to be deserialized as a reified type parameter to the deserialize function and get back a new object instance.
 * ->Deserializing JSON is a more difficult task than serializing,  because it involves parsing the JSON string input in addition to using reflection to access object internals.
 * ->The JSON deserializer in JKid is implemented in a fairly conventional way and consists of three main stages:
 * a lexical analyzer, usually referred to as a lexer;
 * a syntax analyzer, or parser;
 * and the deserialization component itself.
 *
 * ->The lexical analysis splits an input string consisting of characters into a list of tokens.
 * -> There are two kinds of tokens: character tokens, which represent characters with special meanings in the JSON syntax (comma, colon, braces, and brackets);
 *  and value tokens, which correspond to string, number, Boolean, and null constants.
 *  A left brace ({), a string value ("Catch-22"), and an integer value (42) are examples of different tokens.
 *
 * ->The parser is generally responsible for converting a plain list of tokens into a structured representation.
 * Its task in JKid is to understand the higher-level structure of JSON and to convert individual tokens into semantic elements supported in JSON: key-value pairs, objects, and arrays.
 * ->The JsonObject interface keeps track of the object or array currently being deserialized.
 * The parser calls the corresponding methods when it discovers new properties of the current object (simple values, composite properties, or arrays).
 *
 * ->The propertyName parameter in these methods receives the JSON key.
 * ->Thus, when the parser encounters an author property with an object as its value, the create-Object("author") method is called.
 * ->Simple value properties are reported as calls to setSimpleProperty, with the actual token value passed as the value argument.
 * ->The JsonObject implementations are responsible for creating new objects for properties and storing references to them in the outer object.
 *
 * ->The deserializer then provides an implementation for JsonObject that gradually builds a new instance of the corresponding type.
 * It needs to find the correspondence between class properties and JSON keys (title, author, and name in figure 10.7)
 * and build nested object values (an instance of Author);
 * only after that it can create a new instance of the required class (Book).
 *
 * ->The JKid library is intended to be used with data classes, and, as such, it passes all the name-value pairs loaded from the JSON file as parameters to the constructor of the class being deserialized.
 * ->It doesn’t support setting properties on object instances after they’ve been created. This means it needs to store the data somewhere while reading it from JSON and before it can construct the object.
 * ->The requirement to save the components before creating the object looks similar to the traditional Builder pattern,
 * with the difference that builders are generally tailored to creating a specific kind of object, and the solution needs to be completely generic.
 * To avoid being boring, we use the term seed for the implementation. In JSON,
 * you need to build different types of composite structures: objects, collections, and maps.
 * ->The classes ObjectSeed, ObjectListSeed, and ValueListSeed are responsible for building objects and lists of composite objects or simple values appropriately
 * */
//JSON parser callback interface
interface JsonObject {
    fun setSimpleProperty(propertyName: String, value: Any?)

    fun createObject(propertyName: String): JsonObject

    fun createArray(propertyName: String): JsonObject
}

/**
 * ->The basic seed interface extends JsonObject and provides an additional spawn method to get the resulting instance after the building process is finished.
 * ->It also declares the createCompositeProperty method that’s used to create both nested objects and nested lists (they use the same underlying logic to create instances through seeds).
 * ->You may think of spawn as an analogue of build—a method that returns the result value. It returns the constructed object for ObjectSeed and the resulting list for ObjectListSeed or ValueListSeed.
 * */
//Interface for creating objects from JSON data

interface Seed: JsonObject {
    fun spawn(): Any?

    fun createCompositeProperty(
        propertyName: String,
        isList: Boolean
    ): JsonObject
    override fun createObject(propertyName: String) =
        createCompositeProperty(propertyName, false)
    override fun createArray(propertyName: String) =
        createCompositeProperty(propertyName, true)

    // ...
}
//But before that, let’s study the main deserialize function that does all the work of deserializing a value.
//The top-level deserialization function
fun <T: Any> deserialize(json: Reader, targetClass: KClass<T>): T {
    val seed = ObjectSeed(targetClass, ClassInfoCache())
    Parser(json, seed).parse()
    return seed.spawn()
}
/**
 * ->To start the parsing, you create an ObjectSeed to store the properties of the object being deserialized,
 * -> and then you invoke the parser and pass the input stream reader json to it.
 * ->Once you reach the end of the input data, you call the spawn function to build the resulting object.
 *
 * ->Now let’s focus on the implementation of ObjectSeed, which stores the state of an object being constructed.
 * ->ObjectSeed takes a reference to the resulting class and a classInfoCache object containing cached information about the properties of the class.
 * ->This cached information will be used later to create instances of that class.
 *  ClassInfoCache and ClassInfo are helper classes that we’ll discuss in the next section.
 * */

//deserializing an object
class ObjectSeed<out T: Any>(
    targetClass: KClass<T>,
    val classInfoCache: ClassInfoCache
) : Seed {
    private val classInfo: ClassInfo<T> =
        classInfoCache[targetClass]
    private val valueArguments = mutableMapOf<KParameter, Any?>()
    private val seedArguments = mutableMapOf<KParameter, Seed>()
    private val arguments: Map<KParameter, Any?>
        get() = valueArguments +
                seedArguments.mapValues { it.value.spawn() }
    override fun setSimpleProperty(propertyName: String, value: Any?) {
        val param = classInfo.getConstructorParameter(propertyName)
        valueArguments[param] =
            classInfo.deserializeConstructorArgument(param, value)
    }
    override fun createCompositeProperty(
        propertyName: String, isList: Boolean
    ): Seed {
        val param = classInfo.getConstructorParameter(propertyName)
        val deserializeAs =
            classInfo.getDeserializeClass(propertyName)
        val seed = createSeedForType(
            deserializeAs ?: param.type.javaType, isList)
        return seed.apply { seedArguments[param] = this }
    }
    override fun spawn(): T =
        classInfo.createInstance(arguments)
}

//10.2.5. Final deserialization step: callBy() and creating objects using reflection
/**
 * ->The last part you need to understand is the ClassInfo class that builds the resulting instance and caches information about constructor parameters.
 * It is used in ObjectSeed.
 * ->But before we dive into the implementation details,
 * let’s look at the APIs that you use to create objects through reflection.
 *
 * ->You’ve already seen the KCallable.call method, which calls a function or a constructor by taking a list of arguments.
 * ->This method works great in many cases, but it has a restriction:
 * it doesn’t support default parameter values.
 *  ->In this case, if a user is trying to deserialize an object with a constructor that has default parameter values, you definitely don’t want to require those arguments to be specified in the JSON.
 *  ->Therefore, you need to use another method, which does support default parameter values: KCallable.callBy.
 * */

interface KCallable<out R> {
    fun callBy(args: Map<KParameter, Any?>): R
    ...
}
/**
 * ->The method takes a map of parameters to their corresponding values that will be passed as arguments.
 * If a parameter is missing from the map, its default value will be used if possible.
 *  It’s also nice that you don’t have to put the parameters in the correct order; you can read the name-value pairs from JSON,
 *  find the parameter corresponding to each argument name, and put its value in the map.
 *
 * ->One thing you do need to take care of is getting the types right. The type of the value in the args map needs to match the constructor parameter type;
 * otherwise, you’ll get an IllegalArgumentException.
 * ->This is particularly important for numeric types: you need to know whether the parameter takes an Int, a Long, a Double, or another primitive type, and to convert the numeric value coming from JSON to the correct type
 *  To do that, you use the KParameter.type property.
 * ->The type conversion works through the same ValueSerializer interface used for custom serialization.
 *  If a property doesn’t have an @CustomSerializer annotation, you retrieve a standard implementation based on its type.
 * */

//Getting a serializer based on value type

fun serializerForType(type: Type): ValueSerializer<out Any?>? =
    when(type) {
        Byte::class.java -> ByteSerializer
        Int::class.java -> IntSerializer
        Boolean::class.java -> BooleanSerializer
        // ...
        else -> null
    }

//The corresponding ValueSerializer implementations perform the necessary type checking or conversion.

// Serializer for Boolean values
object BooleanSerializer : ValueSerializer<Boolean> {
    override fun fromJsonValue(jsonValue: Any?): Boolean {
        if (jsonValue !is Boolean) throw JKidException("Boolean expected")
        return jsonValue
    }

    override fun toJsonValue(value: Boolean) = value
}



