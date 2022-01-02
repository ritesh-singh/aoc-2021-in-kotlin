package day24

import readInput
import java.lang.StringBuilder
import kotlin.math.floor

fun main() {

    fun solution(inputs: List<String>, largestNum: Boolean): String {
        val possibleNumbers = mutableListOf<String>()
        if (largestNum) {
            var number = "9999999"
            possibleNumbers.add(number)
            while (number.toInt() != 1111111) {
                number = (number.toInt() - 1).toString()
                if (!number.contains("0")) {
                    possibleNumbers.add(number)
                }
            }
        } else {
            var number = "1111111"
            possibleNumbers.add(number)
            while (number.toInt() != 9999999) {
                number = (number.toInt() + 1).toString()
                if (!number.contains("0")) {
                    possibleNumbers.add(number)
                }
            }
        }

        val pairs = mutableListOf<Pair<Int, Int>>()
        inputs.chunked(18).forEach {
            val first = it[5].split(" ")[2].toInt()
            val second = it[15].split(" ")[2].toInt()
            pairs.add(Pair(first, second))
        }

        val result = StringBuilder()

        fun findNumber(modelNum:String):Boolean {
            var counter = 0
            var z  = 0
            result.clear()
            pairs.forEach {
                if (it.first < 0){
                    val num = (z % 26) + it.first
                    if (num !in 1..9)
                        return false
                    result.append(num)
                    z = floor(z / 26.0).toInt()
                } else {
                    result.append(modelNum[counter])
                    z = 26 * z + modelNum[counter].digitToInt() + it.second
                    counter++
                }
            }

            if (z == 0) return true

            return false
        }

        possibleNumbers.forEach {
            if (findNumber(it))
                return result.toString()
        }

        return "Not found"
    }

    val input = readInput("/day24/Day24")
    println(solution(input,largestNum = true))
    println(solution(input,largestNum = false))
}

