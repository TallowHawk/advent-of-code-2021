
fun main() {
    val dayId = "03"

    fun part1(input: List<String>): Int {
        val mostCommon = mutableMapOf<Int, Int>()
        val length = input[0].toCharArray().size
        input.forEach { arr ->
            arr.toCharArray().forEachIndexed { index, value ->
                if (value.digitToIntOrNull() == 1) {
                    mostCommon.compute(index) { _, v ->
                        (v ?: 0) + 1
                    }
                }
            }
        }
        var string = ""
        for (i in 0 until length) {
            val count = mostCommon[i] ?: 0
            string += if (count > input.size / 2) "1"
            else "0"
        }
        val gamma = string.toUInt(2)
        val epsilon = gamma.inv() and CharArray(length) { '1' }.concatToString().toUInt(2)
        return (gamma * epsilon).toInt()
    }

    fun calcSequence(
        input: List<String>,
        bitPosToCheck: Int,
        compareFunction: (positiveIndicesSize: Int, negativeIndicesSize: Int) -> Boolean
    ): Int {
        if (input.size == 1) {
            return input.first().toInt(2)
        }
        val positiveIndices = mutableListOf<String>()
        val negativeIndices = mutableListOf<String>()
        input.forEachIndexed { binaryNumIndex, arr ->
            val bit = arr.toCharArray()[bitPosToCheck]
            if (bit.digitToIntOrNull() == 1) {
                positiveIndices.add(input[binaryNumIndex])
            } else {
                negativeIndices.add(input[binaryNumIndex])
            }
        }

        val listToPass = if (compareFunction(positiveIndices.size, negativeIndices.size)) {
            positiveIndices
        } else negativeIndices

        return calcSequence(
            input = listToPass,
            bitPosToCheck = bitPosToCheck + 1,
            compareFunction = compareFunction
        )
    }

    fun part2(input: List<String>): Int {
        val oxygenGenRating = calcSequence(input, 0) { positiveIndicesSize, negativeIndicesSize ->
            positiveIndicesSize >= negativeIndicesSize
        }
        val co2ScrubbingValue = calcSequence(input, 0) { positiveIndicesSize, negativeIndicesSize ->
            positiveIndicesSize < negativeIndicesSize
        }

        return oxygenGenRating * co2ScrubbingValue
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${dayId}")
//    check(part1(testInput) == 198)

    val input = readInput("Day${dayId}")
    println(part1(input))
    println(part2(input))
}
