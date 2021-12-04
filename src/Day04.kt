fun main() {

    data class Cell(
        val number: Int,
        var isMarked: Boolean = false,
        var sum: Int = 0
    )


    fun checkWinnerInPart1(randomNumbers: List<Int>, boards: List<Array<Array<Cell?>>>): Int {
        for (randomNumber in randomNumbers){
            label@ for (board in boards) {
                var winner = false
                var sum = board[0][0]!!.sum
                for (row in 0..4) {
                    for (col in 0..4) {
                        if (board[row][col]!!.number == randomNumber) { // if found
                            board[row][col]!!.isMarked = true //mark it
                            // check row and col from current position

                            sum -= board[row][col]!!.number
                            board[0][0]!!.sum = sum

                            for (i in 0..4) {
                                if (!board[row][i]!!.isMarked) { // check in row
                                    winner = false
                                    break
                                }
                                if (i == 4) {
                                    return randomNumber * sum
                                }
                            }

                            if (!winner) { // check in column
                                for (i in 0..4) {
                                    if (!board[i][col]!!.isMarked) {
                                        winner = false
                                        break
                                    }
                                    if (i == 4) {
                                        return randomNumber * sum
                                    }
                                }
                            }

                            continue@label
                        }
                    }
                }
            }
        }
        return 0
    }

    fun part1(input: List<String>): Int {
        // Initialise stuff
        val randomNumbers: List<Int> = input[0].trim().split(",").map { it.toInt() }
        val boards = mutableListOf<Array<Array<Cell?>>>()
        var cell: Array<Array<Cell?>> = Array(5) { arrayOfNulls(5) }
        var index = 2
        while (index < input.size) {
            if (input[index].isNotBlank()) {
                var counter = index
                for (row in 0..4) {
                    val inputValue = input[counter++].split(" ").filter { it.isNotBlank() }.map { it.toInt() }
                    for (col in 0..4) {
                        cell[row][col] = Cell(number = inputValue[col])
                        cell[0][0]!!.sum += inputValue[col]
                    }
                }
                index += 5
                boards.add(cell)
                cell = Array(5) { arrayOfNulls(5) }
            } else {
                ++index
            }
        }

        return checkWinnerInPart1(randomNumbers,boards)
    }


    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
//    check(part2(testInput) == 230)

    val input = readInput("Day04")
    println(part1(input))
//    println(part2(input))
}
