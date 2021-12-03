import java.lang.StringBuilder

fun main() {
    fun part1(input: List<String>): Int {
        val mutableMap = mutableMapOf<Int, MutableList<Char>>()

        input.map {
            it.toList() // List<Char>
        }.map {
            it.mapIndexed { index, c ->
                if (mutableMap[index] != null) {
                    mutableMap[index]!!.add(c)
                    mutableMap.put(index, mutableMap[index]!!)
                } else {
                    mutableMap.put(index, mutableListOf(c))
                }
            }
        }

        val gammaRateStringBuilder = StringBuilder()
        val epsilonRateStringBuilder = StringBuilder()

        mutableMap.values.forEach {
            val count = it.count { char -> char == '0' }
            if ((it.size - count) > count){
                gammaRateStringBuilder.append(1)
                epsilonRateStringBuilder.append(0)
            }else{
                gammaRateStringBuilder.append(0)
                epsilonRateStringBuilder.append(1)
            }
        }

        return gammaRateStringBuilder.toString().toInt(2) * epsilonRateStringBuilder.toString().toInt(2)
    }

    fun part2(input: List<String>): Int {
        val charList: List<List<Char>> = input.map {
            it.toList()
        }

        var index = 0
        val oxygenGeneratorList: MutableList<List<Char>> = arrayListOf()
        oxygenGeneratorList.addAll(charList)

        val co2ScrubberList: MutableList<List<Char>> = arrayListOf()
        co2ScrubberList.addAll(charList)

        repeat(input[0].length) {
            oxygenGeneratorList.mapIndexed { _, list ->
                list[index]
            }.let {
                val (zero, one) = it.partition { char -> char == '0' }
                if (one.count() >= zero.count()) {
                    val list = oxygenGeneratorList.filter { newList -> newList[index] == '1' }
                    if (list.isNotEmpty()){
                        oxygenGeneratorList.clear()
                        oxygenGeneratorList.addAll(list)
                    }
                } else {
                    val list = oxygenGeneratorList.filter { newList -> newList[index] == '0' }
                    if (list.isNotEmpty()){
                        oxygenGeneratorList.clear()
                        oxygenGeneratorList.addAll(list)
                    }
                }
            }
            ++index
        }

        index = 0
        repeat(input[0].length){
            co2ScrubberList.mapIndexed { _, list ->
                list[index]
            }.let {
                val (zero, one) = it.partition { char -> char == '0' }
                if (zero.count() <= one.count()) {
                    val list = co2ScrubberList.filter { newList -> newList[index] == '0' }
                    if (list.isNotEmpty()){
                        co2ScrubberList.clear()
                        co2ScrubberList.addAll(list)
                    }
                } else {
                    val list = co2ScrubberList.filter { newList -> newList[index] == '1' }
                    if (list.isNotEmpty()){
                        co2ScrubberList.clear()
                        co2ScrubberList.addAll(list)
                    }
                }
            }
            ++index
        }


        return oxygenGeneratorList.flatten().joinToString("").toInt(2) *
                co2ScrubberList.flatten().joinToString("").toInt(2)
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
