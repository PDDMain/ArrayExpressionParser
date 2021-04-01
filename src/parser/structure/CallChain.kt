package parser.structure

interface CallChain {
    fun evaluate(arr: List<Int>): List<Int>
    override fun toString(): String
}