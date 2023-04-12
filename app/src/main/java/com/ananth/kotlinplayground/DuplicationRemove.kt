package com.ananth.kotlinplayground

fun main(){

    println(averageWindowsDuration)
    println(log.averageDuration(OS.MAC))
    println(log.averageDurationFor{it.os in setOf(OS.ANDROID, OS.IOS)})
}

data class SiteVisit(val path:String,val duration:Double,val os: OS)

enum class OS{WINDOWS,MAC,LINUX,IOS,ANDROID}


val log = listOf(
    SiteVisit("/",34.2, OS.WINDOWS), SiteVisit("/",22.0, OS.LINUX), SiteVisit("/",12.4,
        OS.IOS
    ), SiteVisit("/",12.0, OS.WINDOWS)
)

val averageWindowsDuration= log.filter { it.os== OS.WINDOWS }.map(SiteVisit::duration).average()

//avoided duplication, extract platform as parameter

fun List<SiteVisit>.averageDuration(os: OS)=filter { it.os==os }.map(SiteVisit::duration).average()

//Removing duplication with a higher-order function

fun List<SiteVisit>.averageDurationFor(predicate:(SiteVisit)->Boolean)=filter(predicate).map(
    SiteVisit::duration).average()
