package com.ananth.kotlinplayground

import android.os.Build
import androidx.annotation.RequiresApi
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.lang.IndexOutOfBoundsException
import java.time.LocalDate
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

@RequiresApi(Build.VERSION_CODES.O)
fun main(){

    val p1 = Point(10,20)
    val p2 = Point(30,40)

    println(p1.plus(p2))
    println(p1.times(2.5))

    println('a'*3)

    compoundAssignment()

    val p = Point(10,20)
    println(-p)

    val p3 = Person1("Alice","Smith")
    val p4 = Person1("Bob","Johnson")

    println(p3<p4)

    val p5 = Point(12,23)
    println(p5[0])

    //set
    val p6 = MutablePoint(0,0)
    p6[0]=10
    p6[1]=20
    println(p6)

    //In

    val rect = Rectangle(Point(10,20), Point(50,50))
    println(Point(20,30) in rect)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        dateRanges()
    }

//    (0..10).forEach { println(it*2) }

    val(name, ext)= splitFileName("test.ppt")
    println("$name.$ext")

    val map = mapOf("Oracle" to "Java", "JetBrains" to "Kotlin")
    printEntries(map)

    checkPropertyChange()
    checkPropertyChange1()
    checkPropertyChange2()
    checkPropertyOnMap()

    testInOperatorOnCustomDateRange()
}

//Defining the plus operator

data class Point(val x:Int, val y:Int){

//    operator fun plus(other:Point):Point{
//        return Point(x+other.x, y+other.y)
//    }
}

// Defining an operator as extension function

operator fun Point.plus(other: Point): Point {
    return Point(x+other.x,y+other.y)
}

// defining an operator with different operand types

operator fun Point.times(scale:Double): Point {
    return Point((x*scale).toInt(),(y*scale).toInt())
}

// Defining an operator with different result type

operator fun Char.times(count:Int):String{
    return toString().repeat(count)
}

//Compound assignment operator

fun compoundAssignment(){
    var point = Point(1,2)
    point+= Point(2,3)
    println(point)
}

// plusAssign on mutable collection

operator fun <T> MutableCollection<T>.plusAssign(element:T){
    this.add(element)
}

// Defining a unary operator

operator fun Point.unaryMinus(): Point {

    return Point(-x,-y)
}

//Implementing the equals method

class Point1(val x: Int, val y:Int){

    override fun equals(obj: Any?): Boolean {
        if(obj===this) return true
        if(obj !is Point1) return false
        return obj.x==x && obj.y==y
    }
}

//Implementing the compareTo method

class Person1(private val firstName:String, private val lastName:String):Comparable<Person1>{
    override fun compareTo(other: Person1): Int {
        return compareValuesBy(this,other, Person1::lastName, Person1::firstName)
    }

}

//Implementing the get convention

operator fun Point.get(index:Int):Int{
    return when(index){
        0->x
        1->y
        else-> throw IndexOutOfBoundsException("Invalid coordinate $index")
    }
}

//implementing date range iterator
operator fun ClosedRange<LocalDate>.iterator() : Iterator<LocalDate> =
    object : Iterator<LocalDate>{
        var current = start
        @RequiresApi(Build.VERSION_CODES.O)
        override fun hasNext(): Boolean {
            return current<=endInclusive
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun next(): LocalDate = current.apply {
            current = plusDays(1)
        }
    }


@RequiresApi(Build.VERSION_CODES.O)
fun testInOperatorOnCustomDateRange(){
    val lastYear = LocalDate.ofYearDay(2023,10)
    val currentYear = LocalDate.ofYearDay(2024,10)

    val dateRanges = lastYear..currentYear
    for (day in dateRanges){
        println(day)
    }
}

//Implementing the set convention

data class MutablePoint(var x: Int, var y:Int)

operator fun MutablePoint.set(index: Int, value:Int){
    when(index){
        0 -> x=value
        1-> y=value
        else-> throw IndexOutOfBoundsException("Invalid coordinate $index ")
    }
}

//Implementing the in convention == contains
data class Rectangle(val upperLeft: Point, val lowerRight: Point)

operator fun Rectangle.contains(p: Point):Boolean{
    return p.x in upperLeft.x until lowerRight.x && p.y in upperLeft.y until lowerRight.y
}

//Working with a range of dates

@RequiresApi(Build.VERSION_CODES.O)
fun dateRanges(){
    val now = LocalDate.now()
    val vacation = now..now.plusDays(10) // now.rangeTo(now.plusDays(10))
    println(now.plusWeeks(1) in vacation)
}

/**
 * Destructuring declarations and component functions
 * */

fun DestructureSample(){
    val p = Point(10,20)
    val(x,y)=p
    println(x)

    val person = Person("John",30)
    val(name,age)=person // val name = person.component1, val age = person.component2
    println(name)
}

// Using a destructuring declaration to return multiple values

data class NameComponent(val name : String, val extension:String)

fun splitFileName(fullName: String): NameComponent {
    val result = fullName.split('.', limit = 2)
    return NameComponent(result[0], result[1])
}

// Using destructuring declaration to iterate over a map

fun printEntries(map:Map<String,String>){
    for ((key,value) in map){
        println("$key->$value")
    }
}

/**
 * Delegation
 *
 */

//Delegated properties -
//What is delegation - a design pattern where an object instead of performing a task, delegates that task to another helper object. that helper object called delegate

//Delegated properties: the basics

class Foo{
//    var p : Type by Delegate() // property p delegates logic of accessors to another object. a new instance of delegate class.
                  //| "by" keyword associates a property with a delegate object
   // private val delegate = Delegate(), compiler creates a hidden property , initialized with the instance of the Delegate object. to which initial property p delegates.

//    var p: Type
//      set(value:Type) = delegate.setValue(..., value)
//      get() = delegate.getValue(...)
}

class Delegate{

//    operator fun getValue(..){
//        //
//    }

//      operator  fun setValue(...,value:Type){...}
}

// val foo = Foo()
// val oldValue = foo.p // Accessing a property foo.p calls delegates.getValue() under the hood
// foo.p = newValue // Changing property value calls delegate.setValue(...,newValue) under the hood

/**Using Delegated properties: lazy initialization and "by lazy"()*/
//Lazy initialization is a common pattern that entails creating part of an object on demand. when its accessed for the first time.
//this is helpful when the initialization process requires significant resources and the data is not always required when the object is used

//Implementing lazy initialization using a backing property

class Email(){

}
fun loadEmails(person2: Person2):List<Email>{
    return listOf()
}

class Person2(val name: String){
    private var _emails:List<Email>?=null

    val emails:List<Email>
    get() {
        if(_emails==null){
            _emails = loadEmails(this) //Loads the data on access
        }
        return _emails!!
    }
}

// val p = Person2("Alice")
// p.emails   --> Emails are loaded on first access

//Implementing lazy initialization using a delegated property

fun loadEmails1(person3: Person3):List<Email>{
    return listOf()
}

class Person3(val name: String){
    val emails by lazy { loadEmails1(this) }

    //lazy function returns an object that has a method called getValue with the proper signature, so you can use it together with "by" keyword to create a delegated property

}

/** Implementing delegated property*/
//Helper class for using PropertyChangeSupport-->delegate proeprty using PropertyChangeSupport class from java bean

open class PropertyChangeAware{
    protected  val changeSupport = PropertyChangeSupport(this)

    fun addPropertyChangeListener(listener:PropertyChangeListener){
        changeSupport.addPropertyChangeListener(listener)
    }

    fun removePropertyChangeListener(listener: PropertyChangeListener){
        changeSupport.removePropertyChangeListener(listener)
    }
}

//Manually implementing property change notification
class Person4(val name:String, age:Int,salary:Int): PropertyChangeAware(){

    var age:Int = age
     set(newValue) {
         val oldValue = field
         field = newValue
         changeSupport.firePropertyChange("age",oldValue,newValue)
     }

    var salary: Int =salary
    set(newValue) {
        val oldValue = field
        field = newValue
        changeSupport.firePropertyChange("salary",oldValue,newValue)
    }
}

fun checkPropertyChange(){
    val p = Person4("Alice",24,2000)
    p.addPropertyChangeListener(PropertyChangeListener { event-> println("Property ${event.propertyName} changed" + "from ${event.oldValue} to ${event.newValue}") })
    p.age = 30
    p.salary=4000
}

//Repeated code in the setter in above examples, so lets extract class that will store value of the property and fire the necessary notification

//Implementing property change notification with a helper class

class ObservableProperty(val propName:String, var propValue:Int, val changeSupport:PropertyChangeSupport){

    fun getValue():Int = propValue
    fun setValue(newValue: Int){
        val oldValue = propValue
        propValue = newValue
        changeSupport.firePropertyChange(propName,oldValue,newValue)
    }
}

class  Person5(val name: String, age: Int,salary: Int): PropertyChangeAware(){

    val _age = ObservableProperty("age",age,changeSupport)
    var age:Int
    get() = _age.getValue()
    set(value) {_age.setValue(value)}

    val _salary = ObservableProperty("salary",salary,changeSupport)
    var salary : Int
    get() = _salary.getValue()
    set(value) {_salary.setValue(value)}

}

fun checkPropertyChange1(){
    val p = Person5("Bob",23,3000)
    p.addPropertyChangeListener(PropertyChangeListener { event-> println("Property ${event.propertyName} changed" + "from ${event.oldValue} to ${event.newValue}") })
    p.age = 35
    p.salary=5000
}

//kotlin lets you to get rid of boilerplate code that in ObservableProperty class and Person5

//ObservableProperty as a property delegate

class ObservableProperty1(var propValue: Int,val changeSupport: PropertyChangeSupport){

    operator fun getValue(p: Person6, prop:KProperty<*>):Int = propValue

    operator fun setValue(p: Person6, prop: KProperty<*>, newValue: Int){
        val oldValue = propValue
        propValue = newValue
        changeSupport.firePropertyChange(prop.name,oldValue,newValue)
    }
}

//Using delegated properties for property change notification

class  Person6(val name: String, age: Int, salary: Int): PropertyChangeAware(){

    var age: Int by ObservableProperty1(age,changeSupport)
    var salary: Int by ObservableProperty1(salary,changeSupport)
}

//Using Delegates.observable to implement property change notification

class Person7(val name: String, age: Int,salary: Int): PropertyChangeAware(){

    private val observer = {
        prop: KProperty<*>, oldValue: Int, newValue:Int ->
        changeSupport.firePropertyChange(prop.name,oldValue,newValue)
    }

    var age: Int by Delegates.observable(age,observer)
    var salary: Int by Delegates.observable(salary,observer)
}

fun checkPropertyChange2(){
    val p = Person7("Ananth",23,3000)
    p.addPropertyChangeListener(PropertyChangeListener { event-> println("Property ${event.propertyName} changed" + " from ${event.oldValue} to ${event.newValue}") })
    p.age = 32
    p.salary=7000
}

/** Delegated-property translation rules*/

//class Foo(){
////    var c: Type by MyDelegate()
//
//}
// val foo = Foo()

//compiler generated code

//class Foo{
//    private val <delegate> = MyDelegate() //hidden property
//
//    var c: Type
//    get() = <delegate>.getValue(c, <property>)
//    set(value:Type) = <delegate>.setValue(c,<property>,value)
//}

// defining a property that stores its value  in a map

class  Person8{
    private val _attributes = hashMapOf<String,String>()

    fun setAttribute(attrName: String,value:String){
        _attributes[attrName] = value
    }

    val name:String
    get() = _attributes["name"]!!
}

fun checkPropertyOnMap() {
    val p = Person8()
    val data = mapOf("name" to "Ananth", "company" to "Cognizant")
    for ((attrName,value) in data){
        p.setAttribute(attrName,value)
    }

    println(p.name)
}

//Using delegated property which stores its value in a map
class  Person9{
    private val _attributes = hashMapOf<String,String>()

    fun setAttribute(attrName: String,value:String){
        _attributes[attrName] = value
    }

    val name:String by _attributes // uses map as delegated property
}

//invoke operator

object School{
    operator fun invoke(){
        println("You have started going to school")
    }
}

fun callSchool(){
    School()
}