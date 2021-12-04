fun main() {

    data class Cell(
        val number: Int,
        var isMarked: Boolean = false,
        var sum: Int = 0,
        var winPos: Int = 0,
        var randomNumber:Int = 0
    )

    fun getFirstWinnerFinalScore(randomNumbers: List<Int>, boards: List<Array<Array<Cell?>>>): Int {
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

    fun getLastWinnerFinalScore(randomNumbers: List<Int>, boards: List<Array<Array<Cell?>>>): Int {
        var winPos = 0
        label1@ for (randomNumber in randomNumbers){
            label@ for (board in boards) {
                var winner = false
                var sum = board[0][0]!!.sum
                for (row in 0..4) {
                    for (col in 0..4) {
                        if (board[row][col]!!.number == randomNumber) { // if found
                            board[row][col]!!.isMarked = true //mark it
                            // check row and col from current position

                            if (board[0][0]!!.winPos <= 0){
                                sum -= board[row][col]!!.number
                                board[0][0]!!.sum = sum

                                for (i in 0..4) {
                                    if (!board[row][i]!!.isMarked) { // check in row
                                        winner = false
                                        break
                                    }
                                    if (i == 4) {
                                        board[0][0]!!.winPos = ++winPos
                                        board[0][0]!!.randomNumber = randomNumber
                                    }
                                }

                                if (!winner) { // check in column
                                    for (i in 0..4) {
                                        if (!board[i][col]!!.isMarked) {
                                            break
                                        }
                                        if (i == 4) {
                                            board[0][0]!!.winPos = ++winPos
                                            board[0][0]!!.randomNumber = randomNumber
                                        }
                                    }
                                }
                            }


                            continue@label
                        }
                    }
                }
            }
        }

        return boards.sortedByDescending {
            it[0][0]!!.winPos
        }[0][0][0].let {
            it!!.sum * it.randomNumber
        }
    }

    fun initBoards(input: List<String>, boards: MutableList<Array<Array<Cell?>>>) {
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
    }

    fun part1(input: List<String>): Int {
        // Initialise stuff
        val randomNumbers: List<Int> = input[0].trim().split(",").map { it.toInt() }
        val boards = mutableListOf<Array<Array<Cell?>>>()
        initBoards(input,boards)
        return getFirstWinnerFinalScore(randomNumbers,boards)
    }

    fun part2(input: List<String>): Int {
        // Initialise stuff
        val randomNumbers: List<Int> = input[0].trim().split(",").map { it.toInt() }
        val boards = mutableListOf<Array<Array<Cell?>>>()
        initBoards(input,boards)

        return getLastWinnerFinalScore(randomNumbers,boards)
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
