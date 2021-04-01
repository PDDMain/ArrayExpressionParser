package parser.structure.callchain

import parser.structure.CallChain

class CallChainImpl(private val call: Call, private val callChain: CallChain) : CallChain {
    override fun evaluate(arr: List<Int>): List<Int> {
        return callChain.evaluate(call.evaluate(arr))
    }

    override fun toString(): String {
        return "$call%>%$callChain"
    }
}