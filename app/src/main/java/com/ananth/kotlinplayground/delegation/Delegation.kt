package com.ananth.kotlinplayground.delegation

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {
    val map = mapOf("region" to  "Europe", "lifeSpan" to 10)
    val bird = Bird(map)
    val chicken = Chicken()

    bird.makeSound()
    chicken.makeSound()
    println(chicken.name)

    println("""
         LifeSpan : ${bird.lifeSpan}
        "Region : ${bird.region}
    """.trimIndent())
    chicken.officialName = "African Chicken"

    chicken.age = 20
    chicken.weight = 1.6;
    println("Weight ${chicken.weight}")
}

interface Animal {
    val name: String
    val canFly: Boolean
    fun makeSound(): String
    fun move(direction: String)
}

class Bird(birdDetails:Map<String,Any> = mapOf()) : Animal {
    override val name: String
        get() = "Bird"
    @Deprecated("Use 'airborne' instead", ReplaceWith("airborne"))
    override val canFly: Boolean by this::airborne

    val airborne:Boolean = true


    val region:String by birdDetails
    val lifeSpan:Int by birdDetails
    override fun makeSound(): String {
        println("this is crow")
        return "Crows"
    }

    override fun move(direction: String) {
        println("This bird moves to this $direction")
    }
}

class BirdName {
    operator fun getValue(classRef: Any?, property: KProperty<*>): String {
        return "Bird:$classRef"
    }

    operator fun setValue(classRef: Any?, property: KProperty<*>, value: String) {
        println("This $value property delegated to $classRef")
    }
}

//function delegation declaration
fun birdNameDelegate(name:String = ""):ReadWriteProperty<Any?,String>{
    return object : ReadWriteProperty<Any?,String>{
        var currentName = name
        override fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return "Bird $currentName"
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
           currentName = value
        }

    }
}

var birdAge = 10

class BirdMeasure(val weight: Double = 1.1)
class Chicken(private val measure: BirdMeasure = BirdMeasure()) : Animal by Bird() {

    override val name by birdNameDelegate() //delegate property to function

    var officialName by BirdName() //delegate property to class

    val canSwim: Boolean by lazy {
        false
    }

    var chickenAge by ::birdAge //delegate to another property

    var age: Int by Delegates.observable(0) { property, oldValue, newValue ->
        println(
            """
            ${property.name}
            Old:$oldValue
            New:$newValue
        """.trimIndent()
        )
    }

    var weight: Double by Delegates.vetoable(1.2) { property: KProperty<*>, oldValue: Double, newValue: Double ->
        oldValue < newValue //old value only be updated when this condition satisfied
    }

    val chickWeight:Double by measure::weight //delegate to member property of Measure class

    override fun makeSound(): String {
        println("this is cok")
        return "cok"
    }

}