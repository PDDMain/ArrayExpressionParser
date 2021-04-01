package parser.structure.callchain.call

import parser.structure.callchain.Call
import parser.structure.expression.Expression
import java.util.*
import java.util.stream.Collectors

class Filter(private val expression: Expression) : Call {
    override fun evaluate(arr: List<Int>): List<Int> {
        return arr.stream()
                .filter { x -> expression.evaluateBool(x) }
                .collect(Collectors.toList())
    }

    override fun toString(): String {
        return "filter{${expression}}"
    }
}