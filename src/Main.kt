import java.io.File

fun main() {
    val parties = File("resources\\parties.csv")
        .readLines()
        .map{it.split(";")}
        .filter{it.size > 1}
        .map{Party(it[0], if (it[1].toIntOrNull() is Int) it[1].toInt() else 0, if (it.size == 3) it[2].toBoolean() else false)}

    val config = File("resources\\config.csv")
        .readLines()
        .map{it.split(";")}
        .associate{it[0].trim() to it[1]}
    val numberOfSeats = config["NumberOfSeats"].orEmpty().toIntOrNull()
    val minThreshold = config["Threshold"].orEmpty().toDoubleOrNull()

    val election = when(numberOfSeats) {
        is Int -> when (minThreshold) {
            is Double -> Election(parties, numberOfSeats, minThreshold)
            else -> Election(parties, numberOfSeats)
        }
        else -> when (minThreshold) {
            is Double -> Election(parties, minThreshold=minThreshold)
            else -> Election(parties)
        }
    }

    val results = election.resultsDHondt().toList()
        .sortedWith(compareByDescending<Pair<Party, Int>>{it.second}.thenBy{it.first.name})

    println("Komitety, które przekroczyły próg:")
    results.forEach{println(it.first.name.plus(":  ").plus(it.second))}
}