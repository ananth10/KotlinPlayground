package com.ananth.kotlinplayground


fun main(){
   val type = 2
   val audiCar = AudiCar()
    audiCar.applyClutch()

    val car: Car = AudiCar();
    car.applyClutch()


    var car1: Car
    if(type==1){
        car1 = AudiCar()
    }else{
        car1 = BMWCar()
    }

//    handleCar(car1) getting compile time error

    handleCar1(car1); //here we are using interface reference as type, so it will accept both audi and bmw instances.
}
interface Car {
    fun applyBreak()

    fun applyClutch()

    fun increaseSpeed(speed:Double)

    fun decreaseSpeed(speed: Double)

}

class AudiCar : Car {
    override fun applyBreak() {
        println("Break Applied for audi")
    }

    override fun applyClutch() {
        println("clutch Applied for audi")
    }

    override fun increaseSpeed(speed: Double) {
        println("Speed increased to $speed for audi")
    }

    override fun decreaseSpeed(speed: Double) {
        println("Speed decreased to $speed for audi")
    }

}

class BMWCar : Car {
    override fun applyBreak() {
        println("Break Applied for BMW")
    }

    override fun applyClutch() {
        println("clutch Applied for BMW")
    }

    override fun increaseSpeed(speed: Double) {
        println("Speed increased to $speed for BMW")
    }

    override fun decreaseSpeed(speed: Double) {
        println("Speed decreased to $speed for BMW")
    }

}

fun handleCar(audiCar: AudiCar){
    audiCar.applyClutch()
}

fun handleCar1(car: Car){
    car.applyClutch()
}