package tokenizer

import tokenizer.token.*

class Tokenizer {
    private var currentState: TokenizerState = StartState()
    val res = ArrayList<Token>()

    fun process(c: Char) {
        if (Character.isWhitespace(c)) {
            return
        }

        when (currentState) {
            is StartState -> {
                when {
                    Character.isDigit(c) -> {
                        currentState = NumberState().apply {
                            add(c)
                        }
                    }

                    c == BraceType.LEFT.symb -> res.add(BraceToken(BraceType.LEFT))
                    c == BraceType.RIGHT.symb -> res.add(BraceToken(BraceType.RIGHT))
                    c == OperationType.ADD.symb -> res.add(OperationToken(OperationType.ADD))
                    c == OperationType.SUB.symb -> res.add(OperationToken(OperationType.SUB))
                    c == OperationType.MUL.symb -> res.add(OperationToken(OperationType.MUL))
                    c == OperationType.DIV.symb -> res.add(OperationToken(OperationType.DIV))

                    else -> {
                        currentState = ErrorState()
                        throw RuntimeException("Unknown symbol: $c")
                    }
                }
            }
            is NumberState -> {
                if (Character.isDigit(c)) {
                    (currentState as NumberState).add(c)
                } else {
                    res.add(NumberToken((currentState as NumberState).get()))
                    currentState = StartState()
                    process(c)
                }
            }
            is ErrorState -> {
                throw IllegalArgumentException("Can't parse characters in error state.")
            }
            is EndState -> {
                throw IllegalArgumentException("Can't parse characters after EOF.")
            }
        }
    }

    fun finishProcessing() {
        if (currentState is NumberState) {
            res.add(NumberToken((currentState as NumberState).get()))
        }
        currentState = EndState()
    }
}
