package tokenizer.token

import visitor.TokenVisitor

interface Token {
    fun accept(visitor: TokenVisitor)
}