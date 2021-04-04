package parser.structure

import java.math.BigInteger

interface CallChain {
    fun evaluate(arr: List<BigInteger>): List<BigInteger>
    override fun toString(): String
}