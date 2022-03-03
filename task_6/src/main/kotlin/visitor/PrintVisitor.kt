package visitor

import tokenizer.token.BraceToken
import tokenizer.token.NumberToken
import tokenizer.token.OperationToken

class PrintVisitor : TokenVisitor {
    override fun visit(token: NumberToken) {
        print("${token.value} ")
    }

    override fun visit(token: OperationToken) {
        print("${token.op.symb} ")
    }

    override fun visit(token: BraceToken) {
        print("${token.type.symb} ")
    }
}