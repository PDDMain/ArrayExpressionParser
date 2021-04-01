package parser.structure.operators

interface NumbersToNumberOperator : Operator {
    fun apply(a: Int, b: Int): Int
}