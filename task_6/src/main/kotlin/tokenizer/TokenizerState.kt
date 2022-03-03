package tokenizer

interface TokenizerState

class StartState : TokenizerState

class NumberState : TokenizerState {
    private var number: Int = 0

    fun add(digit: Char) {
        number *= 10
        number += (digit.code - 48)
    }

    fun get(): Int {
        return number
    }
}

class EndState : TokenizerState

class ErrorState : TokenizerState