package parser.structure.expression

interface Expression {
    fun evaluateBool(element: Int) : Boolean
    fun evaluateInt(element: Int) : Int
}
