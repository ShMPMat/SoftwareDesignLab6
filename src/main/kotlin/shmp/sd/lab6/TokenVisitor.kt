package shmp.sd.lab6

import java.util.*


interface TokenVisitor {
    fun visit(token: NumberToken)
    fun visit(token: Brace)
    fun visit(token: Operation)

    fun visitAll(list: List<Token>) =
        list.forEach { it.accept(this) }
}

class ParserVisitor() : TokenVisitor {
    private val stack = Stack<Token>()
    private val _result = mutableListOf<Token>()

    public val result: List<Token>
        get() = _result + stack.toList()

    override fun visit(token: NumberToken) {
        _result.add(token)
    }

    override fun visit(token: Brace) {
        when (token.type) {
            BraceType.Left -> stack.add(token)
            BraceType.Right -> {
                while (stack.peek().let { it !is Brace || it.type == BraceType.Right }) {
                    _result.add(stack.pop())
                }
                if (stack.isNotEmpty())
                    stack.pop()
            }
        }
    }

    override fun visit(token: Operation) {
        if (stack.isEmpty())
            stack.add(token)
        else {
            while (
                stack.isNotEmpty()
                && token.type in listOf(OperationType.Plus, OperationType.Minus)
                && stack.peek().let {
                    it is Operation && it.type in listOf(OperationType.Multiply, OperationType.Divide)
                }
            ) {
                _result.add(stack.pop())
            }
            stack.add(token)
        }
    }
}

class PrintVisitor() : TokenVisitor {
    public var result = ""
        private set

    override fun visit(token: NumberToken) {
        addSpace()
        result += token.value.toString()
    }

    override fun visit(token: Brace) {
        result += when (token.type) {
            BraceType.Left -> {
                addSpace()
                "("
            }
            BraceType.Right -> ")"
        }
    }

    override fun visit(token: Operation) {
        addSpace()
        result += when (token.type) {
            OperationType.Plus -> "+"
            OperationType.Minus -> "-"
            OperationType.Multiply -> "*"
            OperationType.Divide -> "/"
        }
    }

    private fun addSpace() {
        if (result.isNotEmpty() && result.last() !in listOf(' ', '('))
            result += " "
    }
}

class CalcVisitor() : TokenVisitor {
    private val stack = Stack<Double>()

    public val result: Double
        get() {
            if (stack.size > 1)
                throw TokenVisitorException("Malformed expression: there are unused numbers left on stack")
            else if (stack.isEmpty())
                throw TokenVisitorException("Malformed expression: no numbers left on stack")

            return stack.peek()
        }

    override fun visit(token: NumberToken) {
        stack.add(token.value.toDouble())
    }

    override fun visit(token: Brace) {
        throw TokenVisitorException("No brackets allowed in RPN")
    }

    override fun visit(token: Operation) {
        val secondOperand = stack.pop()
        val firstOperand = stack.pop()
        val result = when (token.type) {
            OperationType.Plus -> firstOperand + secondOperand
            OperationType.Minus -> firstOperand - secondOperand
            OperationType.Multiply -> firstOperand * secondOperand
            OperationType.Divide -> firstOperand / +secondOperand
        }

        stack.push(result)
    }
}
