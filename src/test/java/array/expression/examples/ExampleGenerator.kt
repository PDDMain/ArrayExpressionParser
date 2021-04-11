package array.expression.examples

import java.io.File
import java.lang.StringBuilder
import java.util.*

val r = Random(6)
const val classHead = "import array.expression.structure.CallChain\n" +
        "import array.expression.structure.callchain.CallChainImpl\n" +
        "import array.expression.structure.callchain.call.Filter\n" +
        "import array.expression.structure.callchain.call.Mapper\n" +
        "import array.expression.structure.expression.BinaryExpression\n" +
        "import array.expression.structure.expression.Constant\n" +
        "import array.expression.structure.expression.Element\n" +
        "import array.expression.structure.operators.implement.NumbersToBoolOperatorImpl\n" +
        "import array.expression.structure.operators.implement.NumbersToNumberOperatorImpl\n" +
        "import array.expression.structure.operators.implement.BoolsToBoolOperatorImpl" + "\n" +
        "import java.math.BigInteger\n" +
        "\n" +
        "class array.expression.examples.Examples {\n" +
        "\n" +
        "    // examples: List<Pair<CallChain, String>> contains:\n" +
        "    // pairs with equals callchain and callchain's toString()\n" +
        "    val examples: List<Pair<CallChain, String>> = listOf("

// generate example code for Example.kt
// print generated random callchain: CallChain and print Pair(callchain, callchain.toString())
fun main() {
    val writer = File("tests/array.expression.examples.Examples.kt").bufferedWriter()
    writer.write(classHead)
    for (len in 4..8) {
        for (exprLen in 4..5) {
            for (iter in 0..1) {
                val pair: Pair<String, String> = generateCallChain(len, exprLen)
                writer.write("Pair(" + pair.first + ",\n\"" + pair.second + "\"),\n")
            }
        }
    }
    writer.write("    )\n" +
            "}\n")
    writer.close()
}

fun generateCallChain(len: Int, exprLen: Int): Pair<String, String> {
    return generateCallChain(1, len, exprLen)
}

fun generateCallChain(i: Int, len: Int, exprLen: Int): Pair<String, String> {
    val callChainCode = StringBuilder()
    val callChainToString = StringBuilder()
    if (i == len) {
        if (r.nextBoolean()) {
            val pair = boolsToBoolGenerator(exprLen)
            callChainCode.append("Filter(" + pair.first + ")")
            callChainToString.append("filter{" + pair.second + "}")
        } else {
            val pair = numbersToNumberGenerator(exprLen)
            callChainCode.append("Mapper(" + pair.first + ")")
            callChainToString.append("map{" + pair.second + "}")
        }
    } else {
        if (r.nextBoolean()) {
            val pair = boolsToBoolGenerator(exprLen)
            val next = generateCallChain(i + 1, len, exprLen)
            callChainCode.append("CallChainImpl(Filter(" + pair.first + "), " + next.first + ")")
            callChainToString.append("filter{" + pair.second + "}%>%" + next.second)
        } else {
            val pair = numbersToNumberGenerator(exprLen)
            val next = generateCallChain(i + 1, len, exprLen)
            callChainCode.append("CallChainImpl(Mapper(" + pair.first + "), " + next.first + ")")
            callChainToString.append("map{" + pair.second + "}%>%" + next.second)
        }
    }
    return Pair(callChainCode.toString(), callChainToString.toString())
}

fun boolsToBoolGenerator(exprLen: Int): Pair<String, String> {
    if (exprLen > 3 && r.nextDouble() < 0.7) {
        val opCode: String
        val opToString: String
        if (r.nextBoolean()) {
            opCode = "BoolsToBoolOperatorImpl.AND"
            opToString = "&"
        } else {
            opCode = "BoolsToBoolOperatorImpl.OR"
            opToString = "|"
        }
        return binaryExpressionGenerator(opCode, opToString, boolsToBoolGenerator(exprLen - 1), boolsToBoolGenerator(exprLen - 1))
    } else {
        return numbersToBoolGenerator(exprLen)
    }
}

fun numbersToBoolGenerator(exprLen: Int): Pair<String, String> {
    val opCode: String
    val opToString: String
    if (r.nextDouble() < 0.333) {
        opCode = "NumbersToBoolOperatorImpl.EQUALS"
        opToString = "="
    } else if (r.nextBoolean()) {
        opCode = "NumbersToBoolOperatorImpl.MORE_THAN"
        opToString = ">"
    } else {
        opCode = "NumbersToBoolOperatorImpl.LESS_THAN"
        opToString = "<"
    }
    return binaryExpressionGenerator(opCode, opToString, numbersToNumberGenerator(exprLen - 1), numbersToNumberGenerator(exprLen - 1))
}

fun numbersToNumberGenerator(exprLen: Int): Pair<String, String> {
    if (exprLen > 1 && r.nextBoolean()) {
        val opCode: String
        val opToString: String
        when {
            r.nextDouble() < 0.333 -> {
                opCode = "NumbersToNumberOperatorImpl.PLUS"
                opToString = "+"
            }
            r.nextBoolean() -> {
                opCode = "NumbersToNumberOperatorImpl.SUBTRACT"
                opToString = "-"
            }
            else -> {
                opCode = "NumbersToNumberOperatorImpl.MULTIPLY"
                opToString = "*"
            }
        }
        return binaryExpressionGenerator(opCode, opToString, numbersToNumberGenerator(exprLen - 1), numbersToNumberGenerator(exprLen - 1))
    } else {
        return elementOrConstantGenerator()
    }
}

fun binaryExpressionGenerator(opCode: String, opToString: String, left: Pair<String, String>, right: Pair<String, String>): Pair<String, String> {
    return Pair("BinaryExpression(" + opCode + ", " + left.first + ", " + right.first + ")",
            "(" + left.second + opToString + right.second + ")")
}

fun elementOrConstantGenerator(): Pair<String, String> {
    if (r.nextBoolean()) {
        return Pair("Element()", "element")
    } else {
        val a = r.nextLong()
        return Pair("Constant(BigInteger(\"$a\"))", "$a")
    }
}
