package array.expression.parser.exception

import java.lang.Exception

open class SyntaxParserException(override val message: String?) : Exception(message)
