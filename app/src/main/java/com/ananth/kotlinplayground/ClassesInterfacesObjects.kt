package com.ananth.kotlinplayground

import android.content.Context
import java.lang.IllegalArgumentException
import java.util.jar.Attributes

fun main(){

    val button1 = Button1()
    button1.click()
    button1.showOff()
    button1.setFocus(true)
    println(InstaUser("Jack@gmail.com").nickName)

    val counter = LengthCounter()
    counter.addWord("HIIIII")
    println(counter.counter)
    checkHash()
    countingTest()
}

/**Interface in kotlin*/
//Kotlin interfaces are similar to those of Java 8: they can contains definitions of abstract method as well as implementation of non-abstract methods(similar to the Java 8 default methods) but they cannot contain any state.
//declaring a simple interface

interface Clickable{
    fun click()
}
//All non-abstract classes implementing the interface need to provide an implementation of this method.
//Implementing a simple interface.

class Button : Clickable, Clickable1, Focusable {
    override fun click() {
        println("Click")
    }

    override fun showOff() {

    }

}
//Kotlin uses the colon after the class name to replace both the extends and implements keywords used in Java. As in Java.
//a class can implement as many interfaces as it wants, but it can extends only one class.
//override  modifier is mandatory in kotlin.

//Defining a method with a body in an interface

interface Clickable1{

    fun click()
    fun showOff() = println("I am clickable")  //Method with default implementation
}

//If you implement above interface, you need to provide an implementation for click.
//You can redefine the behavior of the showOff method., or you can omit it if you are fine with the default behaviour.

interface Focusable{

    fun setFocus(b:Boolean) = println("I ${if(b) "got" else "lost"} focus.")

    fun showOff() = println("I am focusable")
}

//Invoking an inherited Interface method implementation.

class Button1 : Focusable, Clickable1 {
    override fun click() {
       println("I was clicked")
    }

    override fun showOff(){    //You must provide an explicit implementation if more than one implementation for the same member is inherited.
        super<Clickable1>.showOff()
        super<Focusable>.showOff()
    }
}

/** Open, Final, and abstract modifiers: final by default*/
//Declaring an open class with an open method.

open class RichButton : Clickable { //This class is open so others can inherit it.
   override fun click() {     //This function overrides an open function and is open as well
        println("I am clickable")
    }

    open fun animate(){}// This function is open you may override it in your subclass

    fun disable(){} //This function is final: you can't override it in subclass
}

class RichButton1 : RichButton() {

    override fun animate() {
        super.animate()
    }

    override fun click() {
        super.click()
    }
}

//Declaring an abstract class

abstract class Animated{   //This class is abstract: you cannot create an instance of it.

    abstract fun animate() //This function is abstract: it does not have an implementation and must be overridden in subclasses

    open fun stopAnimation(){}

    open fun animateTwice(){}//non-abstract functions in abstract classes are not open by default but can be marked an open.

    fun animateFast(){}//this is non-abstract function and its default in final, so it cannot be override in subclasses.

}

class Animated1 : Animated(){
    override fun animate() {
        println("this is animated")
    }

    override fun stopAnimation() {
        super.stopAnimation()
    }

    override fun animateTwice() {
        super.animateTwice()
    }

}

/**Visibility modifiers: public by default*/
//Help to control access to declaration in your code.by restricting the visibility of a class's implementation details.
//if you omit the modifier, the declaration becomes public. The default visibility in Java, package-private, is not present in kotlin.
//Kotlin uses packaging only as way of organizing code in namespaces; it does not use them for visibility control.
//As an alternative, Kotlin offers a new visibility modifier, "internal" which means "visible inside a module."

//Kotlin offers a new modifier, "internal", which means "visible inside a module. A module is a set of kotlin files complied together."
//Advantage of internal visibility is that it provides real encapsulation for the implementation details of your module.

internal open class TalkativeButton : Focusable {

    private fun yell() = println("Hey")

    protected  fun whisper () = println("Lets talk")
}

//fun TalkativeButton.giveSpeech(){  // Error: "public" member exposes its "internal" receiver type TalkactiveButton
//
//    yell()  // Error: cannot access "yell": it is "private" in "TalkactiveButton"

//    whisper() // Error: cannot access "whisper": it is "protected" in "TalkactiveButton"
//}

/** Inner and nested classes: nested by default*/
//As in Java, in Kotlin you can declare a class in another class. Doing so can be useful for encapsulating a helper class or placing the code closer to where it is used.
//The difference is that kotlin nested classes don't have access to the outer class instance. unless you specifically request that.
//Nested classes don't reference their outer class, whereas inner classes do.
//The syntax to reference an instance of an outer class in kotlin also different from Java.
//You write this@Outer to access the Outer class from the Inner class:

class Outer{
    inner class Inner{
        fun getOuterReference() : Outer = this@Outer
    }
}

/**Sealed classes: defining restricted class hierarchies*/
//Creating a hierarchy containing a limited number of classes

//Expressions as interface implementations

interface Expr

class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

fun eval(e: Expr) : Int =
        when (e){
        is Num -> e.value
        is Sum -> eval(e.left) + eval(e.right)
        else ->
            throw IllegalArgumentException("Unknown expression")
}

//When you evaluate an expression using the "when" construct, the kotlin compiler forces you to check for the default option. In this example , you can't return something meaningful, so you throw an exception.
//Always having to add a default branch is not convenient. whats more, if you add new subclass, the compiler wont detect that something has changed. If you forget to add new branch, the default one will be chosen, which can lead to subtle bugs.
//Kotlin provides a solution to this problem: sealed classes. You mark a superclass with the sealed modifier, and that restricts the possibility of creating subclasses. All the direct subclasses must be nested in the superclasses:

//Expression as sealed classes

sealed class Expr1{

    class Num(val value : Int) : Expr1()
    class Sum(val left : Expr1, val right: Expr1): Expr1()   //List all possible subclasses as nested classes.

}

fun eval1(e: Expr1) : Int =
        when(e){                        //The "when" expression covers all possible cases, so no "else" branch is needed.
            is Expr1.Num ->e.value
            is Expr1.Sum -> eval1(e.right) + eval1(e.left)
        }
/** Declaring a class with nontrivial constructors or properties*/
//In java you can declare one or more constructors. kotlin is similar, with one additional change: it makes a distinction between a primary constructor(which is usually the main,concise way to initialize a class and is declared outside of the class body)
// and a secondary constructor (which is declared in the class body). it also allows you to put additional initialization logic in initializer blocks.

/** Initializing classes: primary constructor and initializer blocks*/

class User(val nickname:String) //This block of code surrounded by parentheses is called primary constructor. it serves 2 purposes: specifying constructor parameters and defining properties that initialized by those parameters.

//unpacked version of above class

class User1 constructor(_nickname:String){ // primary constructor with one parameter, you can also omit the constructor keyword if there are not annotations or visibility modifiers on the primary constructor.

    val nickname: String

    init {                    //initializer block, we can declare several initializer block
        nickname = _nickname
    }

}

//constructor -> keyword begins the declaration of primary or secondary constructor
//init -> keyword introduces an initializer block. this blocks contains initialization code that is executed when the class is created.

class User2(_nickname: String){
    val nickname = _nickname
}

//code can be simplified by adding the val keyword before the parameter.

open class User3(val nickname: String) // this is concise syntax

class User4(val nickname: String, val isSubscribed: Boolean = true) // Provides a default value for the constructor parameter

var user = User4("ananth") //uses default value "true" for the isSubscribed parameter

val user4 = User4("tony",false) // You specify all parameters according to declaration order.

val user5 = User4(nickname = "Bob", isSubscribed = true) // You can explicitly specify names for some constructor arguments.

class TwitterUser(nickname: String) : User3(nickname) {

}
open class User5 // The default constructor without arguments is generated.

//If you want to ensure that your class cannot be instantiated by other code, you have to make constructor private.

class User6 private constructor() //This class has a private constructor

/**Secondary constructors: initializing the superclass in different ways*/
//classes with multiple secondary constructors are much less common in Kotlin code that in Java.
//The majority of situations where you would need overloaded constructors in java are covered by kotlin's support for default parameters values and named argument syntax.
// Tip: Dont declare multiple secondary constructors to overload snf provide default values for arguments, Instead, specify default values directly.

open class View{  //This class does not declare a primary constructor(as you can tell because there are no parentheses after the class name in the class header)

    constructor(ctx: Context){
        //some code
    }

    constructor(ctx: Context, attributes: Attributes){
        //some code
    }
}

class MYButton : View {

    constructor(ctx: Context) : super(ctx){  // delegates to super constructor

    }

    constructor(ctx: Context, attributes: Attributes) : super(ctx, attributes){  //delegates to super constructor

    }


}

/**Implementing properties declared in interfaces*/

//In kotlin, an interface can contain abstract property declarations. Here's  an example of an interface definition with such a declaration.

interface UserInter {

    val nickName : String
}

//This means classes implementing the User interface need to provide a way to obtain the value of nickName.
//The interface does not specify whether the value should be stored in a backing field or obtained through a getter. Therefore, the interface itself does not  contain any state, and only classes implementing the interface may store the value if they need to .

//Implementing an interface property

class PrivateUser(override val nickName: String) : UserInter {  //primary constructor property

}

class SubscribingUser(val email: String) : UserInter {    //Primary constructor property
    override val nickName: String
        get() = email.substringBefore('@')  // Custom getter, it does not have backing field to store its value. it has only a getter that calculates a nickname from the email on every invocation.

}

class FacebookUser(val accountId: Int) : UserInter {
    override val nickName = getFbId(accountId)   // Property initializer

    fun getFbId(fbId:Int):String{
        return "1010"
    }

}

interface userInter1{

    val email : String

    val nickName : String
        get() = email.substringBefore('@') // Property does not have a backing field: the result value is computed on each access.
}

class InstaUser(override val email: String) : userInter1 {

    override val nickName: String
        get() = super.nickName

}

/** Accessing a backing field from a getter or setter*/
//you have seen a few examples of two kinds of properties: properties that store values and properties with custom accessors that calculate values on every access.
//now lets see how you can combine the two and implement a property that stores a value and provides additional logic that is executed when the value is accessed or modified.
//To support that, you need to be able to access the property's backing field from its accessors.
//Lets say you want to log any change of data stored in property. you declared a mutable property and execute additional code on each setter access.

//Accessing the backing field in a setter

class UserBackingField(val name:String){

//    val address: String = "unspecified"
//        set(value) {
//            println("Address was changed for" ${name} ":" ${field} "->"${value}.") // reads the backing field value
//            field = value  // updated backing field
//        }
// You change a property value as usual by saying user.address = "new value". which invokes a setter under the hood. in this example setter is redefined, so the additional logging code is executed(above one print it out)
// in getter you only read however in setter you can read and write it.
}

/** Changing accessors visibility*/
//The accessor's visibility by default is the same as the property's. but you can changes this if you need to, by putting a visibility modifier before the get or set keyword.

//Declaring a property with a private setter

class LengthCounter{
    var counter: Int = 0
       private set     // You cannot change this property outside of the class. therefore , you let the compiler generate a getter with the default visibility, and you change the visibility of the setter to private.

    fun addWord(word: String){
        counter += word.length
    }
}

/**Compiler-generated methods: data classes and class delegation*/
//Universal object methods
//all kotlin classes have  several methods you may want to override: toString, equals,  and hashCode.

class Client(val name: String, val postalCode: Int)

//STRING REPRESENTATION: toString()
//provides a way to get a string representation of the class's object. this is primarily used for debugging and logging,

//Implementing toString() for client

class Client1(val name: String, val postalCode: Int){

    override fun toString(): String = "Client(name=$name, postalCode=$postalCode)"
}

//OBJECT EQUALITY: EQUALS()
//All computation with the Client class take place outside of it. this class just stores the data. its meant to be plain and transparent.


fun checkEqual(){

    val client1 = Client("Alice",123456)
    val client2 = Client("Alice",123456)

    println(client1==client2) //In Kotlin, == checks whether the objects are equal, not the references. its compiled to a call of "equals"
}

//Implementing equals() for class

class Client2(val name:String, val postalCode: Int){

    override fun equals(other: Any?): Boolean {   //"Any" is the analogue of java.lang.object: a superclass of all classes in kotlin. The nullable type "Any?" means other can be null
        if(other==null || other !is Client2)    //check whether other is a client and "is" check in kotlin is the analogue of instanceOf in java.
            return false
        return  name==other.name && postalCode==other.postalCode // check whether corresponding properties are equal.
    }

    override fun toString(): String = "Client2(name=$name, postalCode = $postalCode)"
}

//HASH CONTAINER: HASHCODE()
//The hashcode method should always overridden together with "equals". This section explains why.
// Lets create a set with one element: a client named Alice. Then you create a new Client instance containing the same data and check whether its contained in the set. You would expect the check to return True, because the two instances are equal, but in fact it return false

fun checkHash(){
    val processed = hashSetOf(Client1("Alice", 123456))
    println(processed.contains(Client1("Alice",123456)))
}

//The reason is that the Client1 class is missing the hashCode method. it violates the general hashCode contract: if two objects are equal, they must have same hashcode.
//The processed set is a HashSet. Values in a HashSet are compared in an optimized way: at first their hash codes are compared,then only if they are equal, the actual values are compared.
//The hash codes are different for two different instances of Client1 class in the previous example, so the set decides that it does not contain the second object, even though "equals" would return true.
//Therefore, if the rule is not followed, the HashSet cannot work correctly with such objects.
//To fix that, you can add the implementation of hashCode to the classes.

//implementing hashcode for client class

class Client5(val name:String, val postalCode: Int){

    override fun hashCode(): Int = name.hashCode()*31+postalCode //but notice how much code you have had to write, Fortunately, the Kotlin compiler can help you by generating all of those methods automatically, Lets see how you can ask it to do that.
}

/** Data classes: autogenerated implementations of universal methods*/
//If you want your class to be convenient holder for your data, you need to override these methods: toString, equals, and hashCode.
//IDE can help you to generate them automatically and verify that they are implemented correctly and consistently.
//The good news is, you do not have to generate all of these in kotlin, if you add modifier data to your class, the necessary methods are automatically generated for you.
//Client class as data class

data class Client6(val name: String, val postalCode: Int)

//Easy Right? Now you have a class that overrides all the standard Java methods:
//equals-> for comparing instances
//hashCode-> for using them as keys in hash-based containers such as HashMap.
//toString-> for generating string representation showing all the fields in declaration orders.

//Data classes And Immutability: The COPY() method.//
//To make it even easier to use data classes as immutable objects, the kotlin compiler generates one more method for them: a method that allows you to copy the instances of your classes, changing the values of some properties.
//Creating a copy is usually a good alternative to modifying the instance in place: the copy has a separate lifecycle and cannot affect the places in the code that refer to the original instance.

class Client7(val name: String, val postalCode: Int){

    fun copy(name: String = this.name, postalCode: Int = this.postalCode) = Client7(name,postalCode)
}

/** Class Delegation: using the "by" keyword*/

//A common problem in the design of large object-oriented systems is fragility caused by implementation inheritance. when you extend a class and override some of its methods, your code becomes dependent on the implementation details of the class you are extending.
//When the system evolves and the implementation of the base class changes or new methods are added to it, the assumptions about its behavior that you have made in your class can become invalid, so your code may end up not behaving properly.
//But often you need to add behavior to another class, even if it was not designed to be extended. A commonly used way to implement this is known as the Decorator pattern.
//The essence of the pattern is that a new class is created, implementing the same interface as the original class and storing the instance of the original class as a field.
//Methods in which the behavior of the original class does not need to be modified are forwarded to the original class instance.
//One downside of this approach is that it requires a fairly large amount of boiler code.

class DelegatingCollection<T> : Collection<T>{

    private val innerList = ArrayList<T>()
    override val size: Int
        get() = innerList.size

    override fun contains(element: T): Boolean = innerList.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = innerList.containsAll(elements)

    override fun isEmpty(): Boolean = innerList.isEmpty()

    override fun iterator(): Iterator<T> = innerList.iterator()

}
//The good news is that Kotlin includes first-class support for delegation as a language feature: Whenever you are implementing an interface, you can say that you are delegating the implementation of the interface to another object. using the by keyword.
//Here is how you can use this approach to rewrite the previous example:

class DelegatingCollectionUsingBy<T>(innerList: Collection<T> = ArrayList<T>()) : Collection<T> by innerList {

    //All the method implementations in the class are gone. the compiler will generate them.
    //the implementation is similar to that in the DelegatingCollection example. Because there is little interesting content in the code. there is no point in writing i manually when the compiler can do the same job for you automatically.
    //Now, when you need to change the behavior of some methods, you can override them, and your code will be called instead of the generated methods, you can leave out methods for which you are satisfied with the default implementation of delegating to the underlying instance.

}

//Lets see how you can use this technique to implement a collection that counts the number of attempts to add an elements to it.
//For example , if you are performing some kind of deduplication, you can use such a collection to measure how efficient the process is, by comparing the numbers of attempts to add an element with the resulting size of the collection.

//Using class delegation

class CountingSet<T>(val innerSet: MutableCollection<T> = HashSet<T>()) : MutableCollection<T> by innerSet{  //Delegates the MutableCollection implementation to innerSet

    var objectAdded = 0

    override fun add(element: T): Boolean {
        objectAdded++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectAdded +=elements.size
        return innerSet.addAll(elements)
    }
}

fun countingTest(){
    val cset = CountingSet<Int> ()
    cset.addAll(listOf(1,1,2))
    println("${cset.objectAdded} objects were added, ${cset.size} remain")
}

// As you see, you override the add and addAll methods to increment the count, and  you delegate the rest of the implementation of the MutableCollection interface to the container you are wrapping.
//The important part is that you are not introducing any dependency on how the underlying collection is implemented.
//For example, you do not care whether that collection implements addAll by calling add in a loop, or if it uses a different implementation optimized for a particular case.
//You have full control over what happens when the client code  calls your class, and you rely only on the documented API of the underlying collection to implement your operation, so you can reply on it continuing to work.

/** The "object" keyword: declaring a class and creating an instance, combined*/
//The object keyword comes up in a number of cases, but they all share the same core idea: the keyword defines a class and creates an instance (in other words, an object) of that class at the same time.
//Lets look at the different situations when its used:
/**
 * -> Object declaration is a way to define a singleton.
 * -> Companion object can contain factory methods and other methods that are related to this class but do not require a class instance to be called. Their members can be accessed via class name.
 * -> Object expression is used instead of Java's anonymous inner class.
 * */

//Object declarations: singletons made easy//
//A fairly common occurrence in the design of object-oriented system is a class for which you need only one instance, in Java , this is usually implemented using the Singleton pattern: you define a class with a private constructor and a static field holding the only existing instance of the class.
//Kotlin provides first-class language support for this using the object declaration feature. The object declaration combines a declaration and a declaration of a single instance of that class.
//For example , you can use an object declaration to represent the payroll of an organization. you probably do not have multiple payrolls, so using an object for this sounds reasonable.

object Payroll{  //Object declaration are introduced with the object keyword. An object declaration effectively defines a class and variable of that class in a single statement.

    val allEmployees = arrayListOf<Person>()

    fun calculateSalary(){

        for(person in allEmployees){
            //
        }
    }
}

/** Companion objects as regular objects*/
//A companion object is a regular object that is declared in a class. It can be named, implement an interface, or have extension function or properties.
//Suppose you are working on a web service for a company's payroll, and you need to serialize and deserialize objects as JSON. you can place the serialization logic in a companion object.

//Declaring named companion object

class  Person20(val name: String){

    companion object Loader{
        fun fromJSON(jsonText : String) : Person20 {
            return Person20("ananth")
        }
    }
}

//Implementing interface in companion object

interface JSONFactory<T>{

    fun fromJSON(jsonText:String) : T
}

class  Person21(val name: String){

    companion object : JSONFactory<Person20> {

        override fun fromJSON(jsonText: String): Person20 {
            return Person20("ananth")
        }
    }
}

/** Companion-object extensions*/
//extension function allow you to define methods that can be called on instances of a class defined elsewhere in the codebase.
//What if you need to define functions that can be called on the class itself. like companion-object methods or java static methods? if the class has a companion object, you can do so by defining extension functions on it.
//If class C has a companion object, and you define an extension function func on C.Companion, you can call it as C.func()

//Defining extension function for a companion object

class  Person22(val firstName: String, val lastName: String){

    companion object{  //Declares an empty companion object

    }
}

fun Person22.Companion.fromJSON(jsonText: String) : Person22 {  //Declare an extension function

    return Person22("ananth","babu")
}

fun testCall(){
    Person22.fromJSON("");
}

/** Object expressions: anonymous inner rephrased*/
//The object keyword can be used not only for declaring named singleton-like objects, but also for declaring anonymous objects,
//Anonymous objects replace Java's use of anonymous inner classes,

//Implementing an event listener with an anonymous object

/** Summary
 *
 * -> Interfaces in kotlin are similar to Java's but can contain default implementations (which Java suuports only since version 8) and properties.
 * -> All declarations are final and public by default.
 * -> To make a declaration non-final , mark it as open.
 * -> Internal declarations are visible in the same module.
 * -> Nested classes are not inner by default. Use the keyword inner to store a reference to the outer class.
 * -> A sealed class can only have subclasses nested in its declaration(Kotlin 1.1 will allow placing them anywhere in the same file)
 * -> Initializer blocks and secondary constructors provide flexibility for initializing class instance.
 * -> You use the field identifier to reference a property backing field from the accessor body.
 * -> Data classes provide compiler-generated equals, hashCode, toString, copy and other methods.
 * -> Class delegation helps to avoid many similar delegating methods in your code.
 * -> Object declaration is Kotlin's way to define a singleton class.
 * -> Companion objects (along with package-level functions and properties) replace Java's static method and field definitions.
 * -> Object expressions are kotlin's replacement for Java's anonymous inner classes, with added power such as the ability to implement multiple interfaces and to modify the variable defined in the scope where the object is created.
 * */
