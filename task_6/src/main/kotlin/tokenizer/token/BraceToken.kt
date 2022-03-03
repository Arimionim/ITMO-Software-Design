package tokenizer.token

import visitor.TokenVisitor

class BraceToken(val type: BraceType) : Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

enum class BraceType(val symb: Char) {
    LEFT('('),
    RIGHT(')')
}