package com.ananth.kotlinplayground.higher_order_function

//filter char from string

fun String.filterChar(predicate: (Char) -> Boolean) {
    val sb = StringBuilder()
    for (index in indices) {
        val char = get(index)
        if (predicate(char)) {
            sb.append(char)
        }
    }
}

val onlyChar = "a23bnm23vfe".filterChar { c: Char -> c in 'a'..'z' }


