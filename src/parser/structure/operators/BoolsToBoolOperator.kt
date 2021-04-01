package parser.structure.operators

interface BoolsToBoolOperator : Operator {
    fun apply(a: Boolean, b: Boolean): Boolean
}