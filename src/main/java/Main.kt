import array.expression.parser.ArrayParser
import array.expression.simplify.Simplifier

fun main() {
    val s = readLine()
    if (s != null) {
        print(ArrayParser().parse(s).toFilterMapper().toString())
    }
}
