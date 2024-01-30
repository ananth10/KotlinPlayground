package com.ananth.kotlinplayground.dsl

fun main(){

  //e.g regular lambda call , here referring "it" multiple times
   buildString {
       it.append("hello")
       it.append("world")
   }
  //e.g lambda with receiver, here able to call methods of sb without any qualifier
  val s = buildString1{ //this becomes the receiver object
      append("hello")
      append("world")
  }
}

//e.g regular lambda
fun buildString(buildAction:(StringBuilder)->Unit):String{
    val sb = StringBuilder()
    buildAction(sb)
    return sb.toString()
}

//e.g lambda with receiver
fun buildString1(buildAction: StringBuilder.() -> Unit):String{
    val sb = StringBuilder()
    sb.buildAction()
    return sb.toString()
}

//Storing a lambda with receiver type in a variable

fun storeLambdaInAVariable(){
    val appendExcl : StringBuilder.()->Unit = {this.append("hello")} // appendExcl is a value of an extension function type

    val sb = StringBuilder()
    sb.append("user")
    sb.appendExcl() //call appendExcl as an extension function

    buildString(appendExcl) //we can pass appendExcl as an argument
}


//kotlin htmlBuilder
//fun createSimpleTable() = createHtml().
//    table{
//        tr{
//            td{
//                +"cell"
//            }
//        }
//    }


open class Tag(val name: String) {
    private val children = mutableListOf<Tag>()

    protected fun <T : Tag> doInit(child: T, init: T.() -> Unit) {
        child.init()
        children.add(child)
    }

    override fun toString() =
        "<$name>${children.joinToString("")}</$name>"
}

fun table(init: TABLE.() -> Unit) = TABLE().apply(init)

class TABLE : Tag("table") {
    fun tr(init: TR.() -> Unit) = doInit(TR(), init)
}
class TR : Tag("tr") {
    fun td(init: TD.() -> Unit) = doInit(TD(), init)
}
class TD : Tag("td")

fun createTable() =
    table {
        tr {
            td {
            }
        }
    }

fun main(args: Array<String>) {
    println(createTable())
}
