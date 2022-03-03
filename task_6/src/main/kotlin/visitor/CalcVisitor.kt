package visitor

import tokenizer.token.BraceToken
import tokenizer.token.NumberToken
import tokenizer.token.OperationToken
import tokenizer.token.OperationType

class CalcVisitor : TokenVisitor {
    private var values = ArrayDeque<Int>()

    fun getResult(): Int {
        if (values.size == 1) {
            return values.first()
        } else {
            throw RuntimeException("Expression is not evaluated yet!")
        }
    }

    override fun visit(token: NumberToken) {
        values.addFirst(token.value)
    }

    override fun visit(token: OperationToken) {
        val b = values.removeFirstOrNull()
        val a = values.removeFirstOrNull()

        if (b == null || a == null) {
            throw RuntimeException("Can not calculate result. Most likely your expression in invalid!")
        }

        val res = when (token.op) {
            OperationType.ADD -> a + b
            OperationType.SUB -> a - b
            OperationType.MUL -> a * b
            OperationType.DIV -> if (b != 0) a / b
            else throw RuntimeException("jajajaja division by zero (kek). Check your expression!")
        }

        values.addFirst(res)
    }

    override fun visit(token: BraceToken) {
        return // we can ignore braces in Reverse Polish notation
    }
}