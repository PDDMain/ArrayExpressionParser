package parser.structure.operators.implement

import parser.structure.operators.Operator
import java.math.BigInteger

enum class NumbersToNumberOperatorImpl : Operator<BigInteger, BigInteger> {
    PLUS {
        override fun apply(a: BigInteger, b: BigInteger): BigInteger = a + b
        override fun toString(): String = "+"
    },
    SUBTRACT {
        override fun apply(a: BigInteger, b: BigInteger): BigInteger = a - b
        override fun toString(): String = "-"
    },
    MULTIPLY {
        override fun apply(a: BigInteger, b: BigInteger): BigInteger = a * b
        override fun toString(): String = "*"
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