package visitor

import tokenizer.token.BraceToken
import tokenizer.token.NumberToken
import tokenizer.token.OperationToken

interface TokenVisitor {
    fun visit(token: NumberToken)
    fun visit(token: BraceToken)
    fun visit(token: OperationToken)
}