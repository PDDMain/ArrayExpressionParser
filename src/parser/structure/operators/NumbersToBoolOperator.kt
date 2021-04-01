package parser.structure.operators

interface NumbersToBoolOperator : Operator {
    fun apply(a: Int, b: Int): Boolean
}