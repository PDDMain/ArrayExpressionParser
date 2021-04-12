package array.expression.structure

import array.expression.simplify.Simplifier
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

    fun toSimplifyFilterMapper(): FilterMapper {
        return toFilterMapper().simplify()
    }

    fun toFilterMapper(): FilterMapper {
        var filterMapper = FilterMapper(
                Filter(BinaryExpression(
                        NumbersToBoolOperatorImpl.EQUALS, Constant(BigInteger.valueOf(0)), Constant(BigInteger.valueOf(0)))),
                Mapper(Element()))
        var cc = this
        while (cc is CallChainImpl) {
            filterMapper = nextFilterMapper(cc.call, filterMapper)
            cc = cc.callChain
        }
        filterMapper = nextFilterMapper(cc, filterMapper)
        if (filterMapper.filter.expression is BinaryExpression<*, *> && (filterMapper.filter.expression as BinaryExpression<Boolean, Boolean>).right is Expression<*>) {
            filterMapper.filter.expression = (filterMapper.filter.expression as BinaryExpression<Boolean, Boolean>).right
        }
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