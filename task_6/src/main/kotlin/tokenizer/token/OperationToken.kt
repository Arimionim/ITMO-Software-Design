package tokenizer.token

import visitor.TokenVisitor

class OperationToken(val op: OperationType) : Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

enum class OperationType(val symb: Char, val priority: Int) {
    DIV('/', 8),
    MUL('*', 8),
    ADD('+', 7),
    SUB('-', 7)
}
