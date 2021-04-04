package parser.structure.callchain

import parser.structure.CallChain
import java.math.BigInteger

class CallChainImpl(private val call: Call, private val callChain: CallChain) : CallChain {
    override fun evaluate(arr: List<BigInteger>): List<BigInteger> {
        return callChain.evaluate(call.evaluate(arr))
    }

    override fun toString(): String {
        return "$call%>%$callChain"
    }
}