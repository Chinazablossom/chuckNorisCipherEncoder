package encoder

import kotlin.system.exitProcess

fun main() {

    do {
        println("Please input operation (encode/decode/exit):")
        val userInput = readln()

        when (userInput) {
            "encode" -> encode()
            "decode" -> decode()
            "exit" -> exit()
            else -> println("There is no '$userInput' operation\n")
        }
    } while (userInput != "exit")

}

fun exit() {
    println("Bye!")
    exitProcess(0)
}

fun encode() {

    println("Input string:")
    val word = readln()
    println("Encoded string:")
    var binaryFormat = ""

    for (i in word) {
        binaryFormat += String.format("%07d", i.code.toString(2).toInt())
    }

    var first = binaryFormat[0]
    var result = ""
    var counter = 0
    for (i in binaryFormat.indices) {
        if (binaryFormat[i] == first) {
            counter++
            if (i != binaryFormat.lastIndex) continue
        }
        val rep = if (first == '1') "0" else "00"
        result += " $rep ${MutableList(counter) { 0 }.joinToString("")}"

        counter = 1
        if (i == binaryFormat.lastIndex && binaryFormat[i] != first) {
            val reps = if (binaryFormat[i] == '1') "0" else "00"
            result += " $reps ${MutableList(counter) { 0 }.joinToString("")}"
        }
        first = binaryFormat[i]
    }
    println("${result.trim()}\n")

}

fun decode() {
    try {
        var code = ""
        println("Input encoded string:")
        val word = readln().split(" ")
        for (i in 0 until word.size - 1 step 2) {
            if (word[i] == "0") {
                code += "1".repeat(word[i + 1].length)
            } else if (word[i] == "00") {
                code += "0".repeat(word[i + 1].length)
            }
        }
        var result = ""
        for (i in code.indices step 7) {
            result += code.substring(startIndex = i, endIndex = i + 7).toInt(2).toChar()
        }

        if (decodeable(word, code)) {
            println("Decoded string: \n$result\n")
        } else println("Encoded string is not valid.\n")

    } catch (e: Exception) {
        println("Encoded string is not valid.\n")
    }

}

fun decodeable(word: List<String>, code: String): Boolean {
    var rem = 0
    var blocks = 0
    for (i in word.indices) {
        val checks = word[i].toInt() % 2
        rem += if (checks == 0) {
            checks
        } else checks
        blocks++

    }

    return (word.contains("0") || word.contains(" ")) && ((word[0] == "0" || word[0] == "00") && word[0].isNotEmpty()) && blocks % 2 == 0 && rem == 0 && code.length % 7 == 0

}