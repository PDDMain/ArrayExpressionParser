package array.expression.structure.operators

@FunctionalInterface
interface Operator<T, R> {
    fun apply(a: T, b: T): R
    override fun toString(): String
}