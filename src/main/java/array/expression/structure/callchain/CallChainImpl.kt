package array.expression.structure.callchain

import array.expression.structure.CallChain
import java.math.BigInteger

class CallChainImpl(val call: Call, val callChain: CallChain) : CallChain {
    override fun evaluate(arr: List<BigInteger>): List<BigInteger> {
        return callChain.evaluate(call.evaluate(arr))
    }

    override fun toString(): String {
        return "$call%>%$callChain"
    }
}