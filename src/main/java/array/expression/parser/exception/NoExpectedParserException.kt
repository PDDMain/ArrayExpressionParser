package array.expression.parser.exception

class NoExpectedParserException(expected: String, actual: String) : ParserException("expected: '$expected', actual:'$actual'")
