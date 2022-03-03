package tokenizer.token

import tokenizer.token.Token
import visitor.TokenVisitor

class NumberToken(val value : Int) : Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}