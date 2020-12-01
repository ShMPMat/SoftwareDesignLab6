package shmp.sd.lab6

interface Token {
    fun accept(visitor: TokenVisitor)
}

data class NumberToken(val value: Int): Token {
    override fun accept(visitor: TokenVisitor) =
        visitor.visit(this)
}

data class Brace(val type: BraceType): Token {
    override fun accept(visitor: TokenVisitor) =
        visitor.visit(this)
}

data class Operation(val type: OperationType): Token {
    override fun accept(visitor: TokenVisitor) =
        visitor.visit(this)
}

enum class BraceType {
    Left,
    Right
}

enum class OperationType {
    Plus,
    Minus,
    Multiply,
    Divide
}
