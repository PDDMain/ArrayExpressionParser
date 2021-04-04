package parser.structure.callchain

import parser.structure.CallChain

interface Call : CallChain {
    override fun toString(): String
}
