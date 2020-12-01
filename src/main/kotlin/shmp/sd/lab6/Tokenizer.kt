package shmp.sd.lab6


class Tokenizer {
    private var state: TokenizerState = StartNextTokenState()

    fun tokenize(str: String): List<Token> = "$str ".flatMap { char ->
        val resultTokens = mutableListOf<Token>()

        do {
            val (newState, newToken, isConsumed) = state.iterate(char)
            state = newState
            newToken?.let { resultTokens.add(it) }
        } while (!isConsumed)

        resultTokens
    }
}


interface TokenizerState {
    fun iterate(char: Char): IterationResult
}

data class IterationResult(val state: TokenizerState, val token: Token?, val isConsumed: Boolean)


class StartNextTokenState : TokenizerState {
    override fun iterate(char: Char): IterationResult =
        when (char) {
            '(' -> IterationResult(this, Brace(BraceType.Left), true)
            ')' -> IterationResult(this, Brace(BraceType.Right), true)
            '+' -> IterationResult(this, Operation(OperationType.Plus), true)
            '-' -> IterationResult(this, Operation(OperationType.Minus), true)
            '*' -> IterationResult(this, Operation(OperationType.Multiply), true)
            '/' -> IterationResult(this, Operation(OperationType.Divide), true)
            else ->
                when {
                    char.isWhitespace() -> IterationResult(this, null, true)
                    char.isDigit() -> IterationResult(ReadNumberState(char.toString().toInt()), null, true)
                    else -> throw TokenizerException("Unexpected symbol '$char'")
                }
        }
}


class ReadNumberState(private val number: Int) : TokenizerState {
    override fun iterate(char: Char): IterationResult =
        if (char.isDigit())
            IterationResult(ReadNumberState(number * 10 + char.toString().toInt()), null, true)
        else
            IterationResult(StartNextTokenState(), NumberToken(number), false)
}
