package parser.parser

import parser.structure.operators.implement.BoolsToBoolOperatorImpl
import java.lang.StringBuilder
import java.math.BigInteger
import parser.structure.operators.Operator as Operator

class ArrayParserSource(val line: String) {
    private var pos = 0

    fun hasNext(): Boolean {
        return pos < line.length
    }

    fun test(test: String): Boolean {
        if (pos + test.length > line.length) {
            return false
        }
        for (i in test.indices) {
            if (line[i + pos] != test[i]) {
                return false
            }
        }
        pos += test.length
        return true
    }

    fun nextNumber(): String {
        val sb = StringBuilder()
        if (hasNext() && line[pos] == '-') {
            sb.append("-")
            pos++
        }
        while (hasNext() && line[pos].isDigit()) {
            sb.append(line[pos++])
        }
        if (sb.toString() == "-") {
            pos--
            return ""
        }
        return sb.toString()
    }

    fun isBoolToBools(): Boolean {
        var bracketsNum = 1
        var ind = pos + 1
        while (ind < line.length && bracketsNum > 0) {
            if (line[ind] == '(') {
                bracketsNum++
            } else if (line[ind] == ')') {
                bracketsNum--
            } else if (bracketsNum == 1) {
                val c = line[ind]
                if (c == '&' || c == '|') {
                    return true
                } else if (c == '=' || c == '<' || c == '>') {
                    return false
                }
            }
            ind++
        }
        return false
    }
}
