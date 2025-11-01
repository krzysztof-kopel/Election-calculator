class Election(parties: List<Party>, private val numberOfSeats: Int=460, minThreshold: Double=0.05) {
    private val eligibleParties: List<Party>
    init {
        val totalNumberOfVotes = parties.sumOf{it.numberOfVotes}

        this.eligibleParties = parties.filter{it.numberOfVotes >= totalNumberOfVotes * minThreshold || it.minority}
    }

    fun resultsDHondt(): HashMap<Party, Int> {
        val seats = this.eligibleParties.associateWith{0}.toMutableMap()
        val quotients = this.eligibleParties.associateWith{it.numberOfVotes}.toMutableMap()

        var seatsTaken = 0
        while (seatsTaken != this.numberOfSeats) {
            val partyToGainSeat = quotients.maxByOrNull{it.value}
            when (partyToGainSeat) {
                is Map.Entry<Party, Int> -> {
                    seats[partyToGainSeat.key] = seats[partyToGainSeat.key]!! + 1
                    seatsTaken++
                    quotients[partyToGainSeat.key] = partyToGainSeat.key.numberOfVotes / (seats[partyToGainSeat.key]!! + 1)
                }
                else -> return HashMap(seats)
            }
        }

        return HashMap(seats)
    }

}