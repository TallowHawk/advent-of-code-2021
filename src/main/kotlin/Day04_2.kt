
fun main() {
    val dayId = "04"

    val boardRowRegex = """\d+""".toRegex()

    fun String.parseBoardRow(): List<Int> {
        return boardRowRegex.findAll(this).map { it.value.toInt() }.toList()
    }

    data class WinnableRow(val row: List<Int>, var matches: Int = 0)
    class Board(grid: List<List<Int>>) {
        private val winnableRows: List<WinnableRow>
        private val matches = mutableListOf<Int>()
        init {
            winnableRows = grid.mapTo(mutableListOf()) { WinnableRow(it) }
            for (y in 0 until grid[0].size) {
                val row = mutableListOf<Int>()
                for (gridRow in grid) {
                    row.add(gridRow[y])
                }
                winnableRows.add(WinnableRow(row))
            }
        }

        fun addAndReturnIfWinner(num: Int): Int? {
            winnableRows.forEachIndexed { index, winnableRow ->
                if (!winnableRow.row.contains(num)) return@forEachIndexed
                winnableRow.matches += 1
                matches.add(num)
                if (winnableRow.matches == 5) {
                    return index
                }
            }
            return null
        }

        fun calculateWinner(numberCalled: Int): Int {
            val nonMatchesSum = winnableRows
                .take(5) // Last 5 are copied rows
                .flatMap { it.row }
                .filterNot { it in matches }
                .sum()
            return nonMatchesSum * numberCalled
        }
    }

    fun boardFromIterator(iterator: Iterator<String>): Board {
        val board = listOf(
            iterator.next().parseBoardRow(),
            iterator.next().parseBoardRow(),
            iterator.next().parseBoardRow(),
            iterator.next().parseBoardRow(),
            iterator.next().parseBoardRow(),
        )

        return Board(board)
    }

    fun part2(input: List<String>): Int {
        val inputIterator = input.iterator()
        val winningNumbers = inputIterator.next().split(",").map { it.toInt() }

        val boards = mutableListOf<Board>()
        while (inputIterator.hasNext()) {
            inputIterator.next() // blank line

            boards.add(boardFromIterator(inputIterator))
        }

        val winningBoards = mutableSetOf<Int>()
        winningNumbers.forEach { number ->
            boards.forEachIndexed { boardIndex, board ->
                board.addAndReturnIfWinner(number)?.let { index ->
                    if (winningBoards.size == boards.size - 1 && boardIndex !in winningBoards) {
                        return board.calculateWinner(number)
                    } else {
                        winningBoards.add(boardIndex)
                    }
                }
            }
        }

        throw IllegalStateException("Someone should have won")
    }

    val input = readInput("Day${dayId}")
    println(part2(input))
}
