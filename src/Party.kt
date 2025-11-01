class Party(val name: String, val numberOfVotes: Int, val minority: Boolean=false) {
    override fun hashCode(): Int = name.hashCode()

    override fun equals(other: Any?): Boolean = when (other) {
        is Party -> other.name == this.name
        else -> false
    }
}