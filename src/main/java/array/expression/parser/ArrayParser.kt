package array.expression.parser

import array.expression.parser.exception.*
import array.expression.structure.CallChain
import array.expression.structure.callchain.Call
import array.expression.structure.callchain.CallChainImpl
import array.expression.structure.callchain.call.Filter
import array.expression.structure.callchain.call.Mapper
import array.expression.structure.expression.BinaryExpression
import array.expression.structure.expression.Constant
import array.expression.structure.expression.Element
import array.expression.structure.expression.Expression
import array.expression.structure.operators.implement.BoolsToBoolOperatorImpl
import array.expression.structure.operators.implement.NumbersToBoolOperatorImpl
import array.expression.structure.operators.implement.NumbersToNumberOperatorImpl
import java.math.BigInteger

class ArrayParser {
    fun parse(line: String): CallChain {
        return Parser(ArrayParserSource(line)).parse()
    }

    class Parser(private val source: ArrayParserSource) {
        fun parse(): CallChain {
            val call: Call
            if (test("map{")) {
                call = parseMapper()
                if (!test("}")) {
                    throw NotCloseParenthesisParserException()
                }
            } else if (test("filter{")) {
                call = parseFilter()
                if (!test("}")) {
                    throw NotCloseParenthesisParserException()
                }
            } else {
                throw NoExpectedParserException("map or filter", source.line)
            }
            if (test("%>%")) {
                return CallChainImpl(call, parse())
            } else if (!source.hasNext()) {
                return call
            }
            throw NoEndParserException()
        }

        private fun parseFilter(): Filter {
            return Filter(parseToBoolExpression())
        }

        private fun parseBoolsToBoolExpression(): Expression<Boolean> {
            if (test("(")) {
                val left = parseToBoolExpression()
                val op = parseBoolsToBoolOperator()
                val right = parseToBoolExpression()
                if (!test(")")) {
                    throw NotCloseParenthesisParserException()
                }
                return BinaryExpression(op, left, right)
            }
            throw SyntaxParserException("'(' not expected")
        }

        private fun parseNumbersToBoolExpression(): Expression<Boolean> {
            if (test("(")) {
                val left = parseNumbersToNumberExpression()
                val op = parseNumbersToBoolOperator()
                val right = parseNumbersToNumberExpression()
                if (!test(")")) {
                    throw NotCloseParenthesisParserException()
                }
                return BinaryExpression(op, left, right)
            }
            throw SyntaxParserException("'(' not expected")
        }

        private fun parseToBoolExpression(): Expression<Boolean> {
            return if (source.isBoolToBools()) {
                parseBoolsToBoolExpression()
            } else {
                parseNumbersToBoolExpression()
            }
        }

        private fun parseMapper(): Mapper {
            return Mapper(parseNumbersToNumberExpression())
        }

        private fun parseNumbersToNumberExpression(): Expression<BigInteger> {
            when {
                test("(") -> {
                    val left = parseNumbersToNumberExpression()
                    val op = parseNumbersToNumberOperator()
                    val right = parseNumbersToNumberExpression()
                    if (!test(")")) {
                        throw NotCloseParenthesisParserException()
                    }
                    return BinaryExpression(op, left, right)
                }
                test("element") -> {
                    return Element()
                }
                else -> {
                    val number = source.nextNumber()
                    if (number == "") {
                        throw SyntaxParserException("number not expected")
                    }
                    return Constant(BigInteger(number))
                }
            }
        }

        private fun parseNumbersToNumberOperator(): NumbersToNumberOperatorImpl {
            return when {
                test("+") -> NumbersToNumberOperatorImpl.PLUS
                test("-") -> NumbersToNumberOperatorImpl.SUBTRACT
                test("*") -> NumbersToNumberOperatorImpl.MULTIPLY
                else -> throw SyntaxParserException("Operation not expected")
            }
        }

        private fun parseBoolsToBoolOperator(): BoolsToBoolOperatorImpl {
            return when {
                test("&") -> BoolsToBoolOperatorImpl.AND
                test("|") -> BoolsToBoolOperatorImpl.OR
                else -> throw SyntaxParserException("Operation not expected")
            }
        }

        private fun parseNumbersToBoolOperator(): NumbersToBoolOperatorImpl {
            return when {
                test("=") -> NumbersToBoolOperatorImpl.EQUALS
                test(">") -> NumbersToBoolOperatorImpl.MORE_THAN
                test("<") ->NumbersToBoolOperatorImpl.LESS_THAN
                else -> throw SyntaxParserException("Operation not expected")
            }
        }

        private fun test(s: String): Boolean {
            return source.test(s)
        }
    }
}
