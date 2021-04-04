package parser.parser.exception

class NoExpectedParserException(expected: String, actual: String) : ParserException("expected: '$expected', actual:'$actual'")
