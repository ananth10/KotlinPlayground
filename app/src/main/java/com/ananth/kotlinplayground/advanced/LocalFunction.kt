package com.ananth.kotlinplayground.advanced

fun main() {
    println("Properties:${properties(24.0, 24.0)}")

    val trout = Fish()
    trout.makeSound()

}

fun properties(length: Double, width: Double): Pair<Double, Double> {
    fun area() = length * width
    fun perimeter() = 2 * (length + width)
    return Pair(area(), perimeter())
}

fun maxSum(a: Int?, b: Int?): Int {
    fun isValid(): Boolean = a != null && b != null
    return if (isValid()) Int.MAX_VALUE else 0 //local function can be only used after that function defined e.g isValid() defined before used in this line
}

//drawbacks of using local function
/**
 * 1. Reduced the readability
 * 2. Limited visibility
 * 3. Performance overhead
 * 4. local functions are tightly coupled
 * */


//another example

abstract class Animal(private val name: String) {
    //properties
    abstract val limb: Int
    abstract val isColdBlooded: Boolean
    abstract val sound: String?
    abstract val gestation:Double

    //behaviors
    open fun makeSound() {
        fun customSound(): String? {
            return if (sound.isNullOrBlank())
                "The $name does not make sound"
            else
                "The $name is $sound"
        }
        println(customSound())
    }

    open fun move(direction:String){
        println("""The $name is move $direction direction""".trimIndent())
    }

    infix fun cross(breed:Animal){
        fun result():Double{
            return if(breed.name.equals(name, ignoreCase = true))
                breed.gestation
            else
                breed.gestation + gestation

        }
        println("The ${breed.name} and $name will be offspring after ${result()}")
    }
}
class Fish() : Animal("Fish"){
    override val limb: Int
        get() = 0
    override val isColdBlooded: Boolean
        get() = true
    override val sound: String?
        get() = null
    override val gestation: Double
        get() = 3.0

}

class Mammal():Animal("Mammal"){
    override val limb: Int
        get() = 4
    override val isColdBlooded: Boolean
        get() = false
    override val sound: String?
        get() = "Moo"
    override val gestation: Double
        get() = 9.0

}