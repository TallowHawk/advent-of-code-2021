
fun main() {
    val dayId = "01"

    fun part1(input: List<Int>): Int {
        var prev = input[0]
        var acc = 0
        for (x in 1 until input.size) {
            if (input[x] > prev) {
                acc += 1
            }
            prev = input[x]
        }
        return acc
    }

    fun part2(input: List<Int>): Int {
        return input.windowed(3).map { it.sum() }.zipWithNext().count { (x, y) -> y > x }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${dayId}")
//    println(part1(testInput.map { it.toInt() }))
//    check(part1(testInput) == 1)

    val input = readInput("Day${dayId}")
//    println(part1(input))
    println(part2(input.map { it.toInt() }))
}
