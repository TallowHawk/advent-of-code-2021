enum class Direction {
    FORWARD,
    UP,
    DOWN;

}

fun main() {
    val dayId = "02"

    fun part1(input: List<String>): Int {
        var depth = 0
        var distance = 0
        input.asSequence().map { it.split(" ") }
            .map {
                Pair(
                    Direction.valueOf(it[0].uppercase()),
                    it[1].toInt()
                )
            }.forEach { (direction, l) ->
                when (direction) {
                    Direction.FORWARD -> distance += l
                    Direction.UP -> depth -= l
                    Direction.DOWN -> depth += l
                }
            }


        return depth * distance
    }

    fun part2(input: List<String>): Int {
        var aim = 0
        var depth = 0
        var distance = 0
        input.map { it.split(" ") }
            .map {
                Pair(
                    Direction.valueOf(it[0].uppercase()),
                    it[1].toInt()
                )
            }.forEach { (direction, l) ->
                when (direction) {
                    Direction.FORWARD -> {
                        distance += l
                        depth += l * aim
                    }
                    Direction.UP -> aim -= l
                    Direction.DOWN -> aim += l
                }
            }
        return depth * distance
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${dayId}")
//    println(part1(testInput.map { it.toInt() }))
//    check(part1(testInput) == 1)

    val input = readInput("Day${dayId}")
    println(part1(input))
    println(part2(input))
}
