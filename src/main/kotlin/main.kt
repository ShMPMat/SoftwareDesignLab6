import shmp.sd.lab6.CalcVisitor
import shmp.sd.lab6.ParserVisitor
import shmp.sd.lab6.PrintVisitor
import shmp.sd.lab6.Tokenizer

fun main() {
    val tokens = Tokenizer().tokenize(readLine()!!)
    println(PrintVisitor().let {
        it.visitAll(tokens)
        it.result
    })

    val prnTokens = ParserVisitor().let {
        it.visitAll(tokens)
        it.result
    }

    println(PrintVisitor().let {
        it.visitAll(prnTokens)
        it.result
    })

    println(CalcVisitor().let {
        it.visitAll(prnTokens)
        it.result
    })
}