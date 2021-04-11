package array.expression.examples

import array.expression.structure.CallChain
import array.expression.structure.callchain.CallChainImpl
import array.expression.structure.callchain.call.Filter
import array.expression.structure.callchain.call.Mapper
import array.expression.structure.expression.BinaryExpression
import array.expression.structure.expression.Constant
import array.expression.structure.expression.Element
import array.expression.structure.operators.implement.NumbersToBoolOperatorImpl
import array.expression.structure.operators.implement.NumbersToNumberOperatorImpl
import java.math.BigInteger

class EasyExamples {

    // examples: List<Pair<CallChain, String>> contains:
// pairs with equals callchain and callchain's toString()
    val easyExamples: List<Pair<CallChain, String>> = listOf(
            Pair(CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.LESS_THAN, Element(), Element())), CallChainImpl(Mapper(Constant(BigInteger("-6641849157192254023"))), Mapper(BinaryExpression(NumbersToNumberOperatorImpl.MULTIPLY, Constant(BigInteger("-3711132188216687009")), Element())))),
                    "filter{(element<element)}%>%map{-6641849157192254023}%>%map{(-3711132188216687009*element)}"),
            Pair(CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.MORE_THAN, Constant(BigInteger("605613220738032425")), Element())), CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Element(), Constant(BigInteger("-3104336864636073749")))), Mapper(Constant(BigInteger("-9164101171279437131"))))),
                    "filter{(605613220738032425>element)}%>%filter{(element=-3104336864636073749)}%>%map{-9164101171279437131}"),
            Pair(CallChainImpl(Mapper(Constant(BigInteger("2619924829012818875"))), CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Element(), Constant(BigInteger("8211661635828948923")))), Mapper(Constant(BigInteger("4362819833674321318"))))),
                    "map{2619924829012818875}%>%filter{(element=8211661635828948923)}%>%map{4362819833674321318}"),
            Pair(CallChainImpl(Mapper(Constant(BigInteger("4946555178474214325"))), CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Constant(BigInteger("3299500085372830107")), Element())), Filter(BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Element(), Element())))),
                    "map{4946555178474214325}%>%filter{(3299500085372830107=element)}%>%filter{(element=element)}"),
            Pair(CallChainImpl(Mapper(BinaryExpression(NumbersToNumberOperatorImpl.SUBTRACT, Element(), Element())), CallChainImpl(Mapper(BinaryExpression(NumbersToNumberOperatorImpl.SUBTRACT, Element(), Constant(BigInteger("-605272835938492183")))), Mapper(Constant(BigInteger("-7950753862706049061"))))),
                    "map{(element-element)}%>%map{(element--605272835938492183)}%>%map{-7950753862706049061}"),
            Pair(CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Element(), Element())), CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.LESS_THAN, Element(), Element())), Filter(BinaryExpression(NumbersToBoolOperatorImpl.MORE_THAN, Constant(BigInteger("4595752336043432313")), Element())))),
                    "filter{(element=element)}%>%filter{(element<element)}%>%filter{(4595752336043432313>element)}"),
            Pair(CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.LESS_THAN, Element(), Constant(BigInteger("-1927695177376177922")))), CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Element(), Constant(BigInteger("6899403544001071481")))), Mapper(BinaryExpression(NumbersToNumberOperatorImpl.MULTIPLY, Element(), Element())))),
                    "filter{(element<-1927695177376177922)}%>%filter{(element=6899403544001071481)}%>%map{(element*element)}"),
            Pair(CallChainImpl(Mapper(Element()), CallChainImpl(Mapper(Constant(BigInteger("6677818175895881385"))), Mapper(Element()))),
                    "map{element}%>%map{6677818175895881385}%>%map{element}"),
            Pair(CallChainImpl(Mapper(BinaryExpression(NumbersToNumberOperatorImpl.MULTIPLY, Element(), Constant(BigInteger("-1748975985928300240")))), CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Constant(BigInteger("-5379759707642999802")), Constant(BigInteger("352665512903617752")))), Filter(BinaryExpression(NumbersToBoolOperatorImpl.LESS_THAN, Element(), Constant(BigInteger("-1314583102241227267")))))),
                    "map{(element*-1748975985928300240)}%>%filter{(-5379759707642999802=352665512903617752)}%>%filter{(element<-1314583102241227267)}"),
            Pair(CallChainImpl(Mapper(BinaryExpression(NumbersToNumberOperatorImpl.MULTIPLY, Element(), Element())), CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.LESS_THAN, Constant(BigInteger("-6543022210216003672")), Element())), Mapper(BinaryExpression(NumbersToNumberOperatorImpl.PLUS, Constant(BigInteger("4568336594332980021")), Element())))),
                    "map{(element*element)}%>%filter{(-6543022210216003672<element)}%>%map{(4568336594332980021+element)}"),
            Pair(CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Constant(BigInteger("-119717673998280558")), Constant(BigInteger("2844545924498587101")))), CallChainImpl(Mapper(Constant(BigInteger("-5607962724919381360"))), Mapper(BinaryExpression(NumbersToNumberOperatorImpl.MULTIPLY, Constant(BigInteger("4674727950018836139")), Constant(BigInteger("3476213062587117830")))))),
                    "filter{(-119717673998280558=2844545924498587101)}%>%map{-5607962724919381360}%>%map{(4674727950018836139*3476213062587117830)}")
    )
}