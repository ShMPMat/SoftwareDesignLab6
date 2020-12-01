package shmp.sd.lab6

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFails

class ParserVisitorTest {
    private lateinit var parserVisitor: ParserVisitor

    @Before
    fun initPrintVisitor() {
        parserVisitor = ParserVisitor()
    }

    @Test
    fun noTokensTest() {
        parserVisitor.visitAll(listOf())
        assertEquals(
            parserVisitor.result,
            listOf<Token>()
        )
    }

    @Test
    fun numberTest() {
        parserVisitor.visitAll(listOf(NumberToken(100)))
        assertEquals(
            parserVisitor.result,
            listOf(NumberToken(100))
        )
    }

    @Test
    fun bracesTest() {
        parserVisitor.visitAll(listOf(Brace(BraceType.Left), NumberToken(1), Brace(BraceType.Right)))
        assertEquals(
            parserVisitor.result,
            listOf(NumberToken(1))
        )
    }

    @Test
    fun operationTest() {
        parserVisitor.visitAll(listOf(NumberToken(1), Operation(OperationType.Plus), NumberToken(1)))
        assertEquals(
            parserVisitor.result,
            listOf(NumberToken(1), NumberToken(1), Operation(OperationType.Plus))
        )
    }
}
