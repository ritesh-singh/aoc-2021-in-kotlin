package day06

import readInput

fun main(){

    fun getTotalNumberOfLanternFish(noOfTimes:Int,inputs: List<String>):Long {
        val lanternFish = inputs[0].filter { it!=',' }
        val hashMap = mutableMapOf<Int,Long>()
        lanternFish.forEach {
            if (hashMap.containsKey(it.digitToInt())){
                hashMap[it.digitToInt()] = hashMap[it.digitToInt()]!!.plus(1L)
            }else{
                hashMap[it.digitToInt()] = 1L
            }
        }

        repeat(noOfTimes){
            val newHasMap = mutableMapOf<Int,Long>()
            for (key in hashMap.keys){
                if (key == 0){
                    newHasMap[6] = if (newHasMap.containsKey(6)) (newHasMap[6]!! + hashMap[key]!!) else hashMap[key]!!
                    newHasMap[8] = if (newHasMap.containsKey(8)) (newHasMap[8]!! + hashMap[key]!!) else hashMap[key]!!
                }else{
                    newHasMap[key-1] = if (newHasMap.containsKey(key-1)) (newHasMap[key - 1]!! + hashMap[key]!!) else hashMap[key]!!
                }
            }
            hashMap.clear()
            hashMap.putAll(newHasMap)
        }

        return hashMap.values.sum()
    }

    fun part1(inputs: List<String>): Int {
        return getTotalNumberOfLanternFish(80,inputs).toInt()
    }

    fun part2(inputs: List<String>): Long {
        return getTotalNumberOfLanternFish(256,inputs)
    }

    val testInput = readInput("/day06/Day06_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539)

    val input = readInput("/day06/Day06")
    println(part1(input))
    println(part2(input))
}