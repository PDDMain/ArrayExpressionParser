package array.expression.parser.exception

import java.lang.Exception

open class ParserException(override val message: String?): Exception("Parser exception : $message")
