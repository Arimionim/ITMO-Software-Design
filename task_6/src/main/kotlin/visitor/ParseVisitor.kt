package visitor

import tokenizer.token.*

class ParseVisitor : TokenVisitor {
    private val postfixNotation: MutableList<Token> = mutableListOf()
    private val toBeUsed: MutableList<Token> = mutableListOf()

    override fun visit(token: NumberToken) {
        postfixNotation.add(token)
    }

    override fun visit(token: BraceToken) {
        when (token.type) {
            BraceType.LEFT -> toBeUsed.add(token)
            BraceType.RIGHT -> {
                while (true) {
                    if (toBeUsed.size == 0) {
                        throw RuntimeException("There is extra right bracket!")
                    }

                    when (val next = toBeUsed.removeLast()) {
                        is BraceToken -> break
                        else -> postfixNotation.add(next)
                    }
                }
            }
        }
    }

    override fun visit(token: OperationToken) {
        while (toBeUsed.isNotEmpty()) {
            val top = toBeUsed.last()

            if (top !is OperationToken) {
                break
            }

            if (top.op.priority >= token.op.priority) {
                postfixNotation.add(toBeUsed.removeLast())
            } else {
                break
            }
        }

        toBeUsed.add(token)
    }

    fun getResult(): List<Token> {
        while (toBeUsed.isNotEmpty()) {
            when (val top = toBeUsed.removeLast()) {
                is OperationToken -> postfixNotation.add(top)
                else -> throw RuntimeException("There is no right bracket!")
            }
        }

        return postfixNotation
    }
}