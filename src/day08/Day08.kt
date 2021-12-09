package day08

import readInput

fun main() {
    val uniquePatternPredicate: (String) -> Boolean = {
        it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7
    }

    fun part1(inputs: List<String>): Int {
        var sum = 0
        for (input in inputs) {
            sum += input.split("|").map { it.trim() }[1]
                .split(" ").count { uniquePatternPredicate(it) }
        }
        return sum
    }

    fun part2(inputs: List<String>): Int {

        var sum = 0

        for (input in inputs){
            val (signalPatterns, outputValue) = input.split("|").map { it.trim() }

            val segCounterFromPattern = Array(10) { "" }

            val (validPattern, unknownPattern) =
                signalPatterns.split(" ")
                    .map {
                        it.toSortedSet().joinToString("")
                    }
                    .partition {
                        uniquePatternPredicate(it)
                    }

            validPattern.forEach {
                when(it.length){
                    2 -> segCounterFromPattern[1] = it
                    3 -> segCounterFromPattern[7] = it
                    4 -> segCounterFromPattern[4] = it
                    7 -> segCounterFromPattern[8] = it
                }
            }

            unknownPattern.forEach {
                when(it.length){
                    6 -> {
                        if (it.toList().union(segCounterFromPattern[1].toList()).count() == 7){
                            segCounterFromPattern[6] = it
                        }else if (it.toList().union(segCounterFromPattern[4].toList()).count() == 7){
                            segCounterFromPattern[0] = it
                        }else{
                            segCounterFromPattern[9] = it
                        }

                    }

                    5 -> {
                        if (it.toList().intersect(segCounterFromPattern[7].toList().toSet()).count() == 3) {
                            segCounterFromPattern[3] = it
                        } else if ((segCounterFromPattern[4].toList()).minus(it.toList().toSet()).count() == 1) {
                            segCounterFromPattern[5] = it
                        } else {
                            segCounterFromPattern[2] = it
                        }
                    }

                }
            }



            val hashMap = hashMapOf<String,Int>()
            segCounterFromPattern.forEachIndexed { index, s ->
                hashMap[s] = index
            }

            val result = StringBuilder()
            outputValue.split(" ")
                .map {
                    it.toSortedSet().joinToString("")
                }.forEach {
                    result.append(hashMap[it])
                }

            sum += result.toString().toInt()
        }

        return sum
    }

    val testInput = readInput("/day08/Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("/day08/Day08")
    check(part1(input) == 349)
    check(part2(input) == 1070957)
    println(part1(input))
    println(part2(input))
}
