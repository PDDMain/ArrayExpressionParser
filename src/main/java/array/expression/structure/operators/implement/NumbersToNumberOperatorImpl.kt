package array.expression.structure.operators.implement

import array.expression.structure.operators.Operator
import java.math.BigInteger

enum class NumbersToNumberOperatorImpl : Operator<BigInteger, BigInteger> {
    PLUS {
        override fun apply(a: BigInteger, b: BigInteger): BigInteger = a + b
        override fun toString(): String = "+"
        override fun isImplicated(): Boolean = true
    },
    SUBTRACT {
        override fun apply(a: BigInteger, b: BigInteger): BigInteger = a - b
        override fun toString(): String = "-"
        override fun isImplicated(): Boolean = false
    },
    MULTIPLY {
        override fun apply(a: BigInteger, b: BigInteger): BigInteger = a * b
        override fun toString(): String = "*"
        override fun isImplicated(): Boolean = true
    };  //  },
//    DIVIDE {
//        override fun apply(a: BigInteger, b: BigInteger): BigInteger {
//            if (b == 0) {
//                throw DivideException("'$a/$b' cannot divide")
//            }
//            return a / b
//        }
//        override fun toString(): String = "/"
//    };
}