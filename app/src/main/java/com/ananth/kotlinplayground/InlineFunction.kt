package com.ananth.kotlinplayground

fun main(){

    println(people.filter { it.age<30 })

    //Filtering a collection manually

    val result = mutableListOf<Person>()

    for (person in people){
        if(person.age>30)
           result.add(person)
    }

    lookForAlice(people)
    lookForBob(people)
    lookForAlice1(people)
    lookForAlice2(people)
    ageBelow30(people)
}

//Filtering a collection using a lambda

data class Person(val name:String, val age:Int)

val people = listOf(Person("Alice",29), Person("Bob",31))

//Using return in regular loop

fun lookForAlice(people : List<Person>){
    for (person in people){
        if(person.name=="Alice"){
            println("Alice Found")
            return
        }
    }
    println("Alice is not found")
}

//Using return in a lambda passed in forEach

fun lookForBob(people: List<Person>){
    people.forEach {
        if(it.name=="Bob"){
            println("Found")
            return
        }
    }

    println("Bob is not found")
}


//Using a local return with a lable

fun lookForAlice1(people: List<Person>){
    people.forEach lable@{
        if(it.name=="Alice") return@lable
    }
    println("Alice might be somewhere")
}

//Using a function name as return lable

fun lookForAlice2(people: List<Person>){
    people.forEach {
        if(it.name=="Alice") return@forEach
    }
    println("Alice might be somewhere")
}

//Using return in an anonymous function

fun lookForAlice3(people: List<Person>){
    people.forEach (fun (person){
        if(person.name=="Alice") return
        println("${person.name} is not Alice")
    })
}

//Anonymous function with an expression body

fun ageBelow30(people: List<Person>){
    people.filter (fun (person) = person.age<30)
}