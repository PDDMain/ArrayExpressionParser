package parser.structure

import parser.structure.callchain.CallChainImpl
import parser.structure.callchain.FilterMapper
import parser.structure.callchain.call.Filter
import parser.structure.callchain.call.Mapper
import parser.structure.expression.BinaryExpression
import parser.structure.expression.Constant
import parser.structure.expression.Element
import parser.structure.expression.Expression
import parser.structure.operators.implement.BoolsToBoolOperatorImpl
import parser.structure.operators.implement.NumbersToBoolOperatorImpl
import parser.structure.operators.implement.NumbersToNumberOperatorImpl
import java.math.BigInteger
import java.security.InvalidParameterException
import java.util.*
import kotlin.collections.ArrayList

interface CallChain {
    fun evaluate(arr: List<BigInteger>): List<BigInteger>
    override fun toString(): String

    fun randomEquals(callChain: CallChain, len: Int = 100, random: Random = Random(1)): Boolean {
        val list1: List<BigInteger> = randomList(len, random)
        val list2: List<BigInteger> = list1.toList()
        return equals(this.evaluate(list1), callChain.evaluate(list2))
    }

    private fun equals(o1: List<BigInteger>, o2: List<BigInteger>): Boolean {
        if (o1.size != o2.size) {
            return false
        }
        for (i in o1.indices) {
            if (o1[i] != o2[i]) {
                return false
            }
        }
        return true
    }

    private fun randomList(len: Int, random: Random): ArrayList<BigInteger> {
        val list = ArrayList<BigInteger>()
        for (i in 1 until len) {
            list.add(BigInteger.valueOf(random.nextLong()))
        }
        return list
    }


    fun toFilterMapper(): FilterMapper {
        var filterMapper = FilterMapper(
                Filter(BinaryExpression(
                        NumbersToBoolOperatorImpl.EQUALS, Element(), Element())),
                Mapper(Element()))
        var cc = this
        while (cc is CallChainImpl) {
            filterMapper = nextFilterMapper(cc.call, filterMapper)
            cc = cc.callChain
        }
        filterMapper = nextFilterMapper(cc, filterMapper)
        return filterMapper
    }

    private fun nextFilterMapper(cc: CallChain, filterMapper: FilterMapper): FilterMapper {
        return when (cc) {
            is Mapper -> {
                val expression = cc.expression
                FilterMapper(filterMapper.filter,
                        Mapper(replaceElement(expression, filterMapper.mapper.expression)))
            }
            is Filter -> {
                val expression = cc.expression
                FilterMapper(
                        Filter(BinaryExpression(BoolsToBoolOperatorImpl.AND, filterMapper.filter.expression, replaceBoolsElement(expression, filterMapper.mapper.expression))),
                        filterMapper.mapper)
            }
            else -> {
                throw InvalidParameterException()
            }
        }
    }

    private fun replaceElement(expression: Expression<BigInteger>, element: Expression<BigInteger>): Expression<BigInteger> {
        return when (expression) {
            is Element -> {
                element.clone()
            }
            is Constant -> {
                expression.clone()
            }
            is BinaryExpression<*, *> -> {
                return if (expression.operator is NumbersToNumberOperatorImpl) {
                    BinaryExpression(expression.operator, replaceElement(expression.left as Expression<BigInteger>, element), replaceElement(expression.right as Expression<BigInteger>, element))
                } else {
                    throw InvalidParameterException()
                }
            }
            else -> throw InvalidParameterException()
        }
    }

    private fun replaceBoolsElement(expression: Expression<Boolean>, element: Expression<BigInteger>): Expression<Boolean> {
        if (expression is BinaryExpression<*, *>) {
            return when (expression.operator) {
                is NumbersToBoolOperatorImpl ->
                    BinaryExpression(expression.operator, replaceElement(expression.left as Expression<BigInteger>, element), replaceElement(expression.right as Expression<BigInteger>, element))
                is BoolsToBoolOperatorImpl ->
                    BinaryExpression(expression.operator, replaceBoolsElement(expression.left as Expression<Boolean>, element), replaceBoolsElement(expression.right as Expression<Boolean>, element))
                else -> throw InvalidParameterException()
            }
        }
        throw InvalidParameterException()
    }

}