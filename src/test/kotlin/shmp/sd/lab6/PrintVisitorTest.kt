package shmp.sd.lab6

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PrintVisitorTest {
    private lateinit var printVisitor: PrintVisitor

    @Before
    fun initPrintVisitor() {
        printVisitor = PrintVisitor()
    }

    @Test
    fun noTokensTest() {
        printVisitor.visitAll(listOf())
        assertEquals(
            printVisitor.result,
            ""
        )
    }

    @Test
    fun numberTest() {
        printVisitor.visitAll(listOf(NumberToken(100)))
        assertEquals(
            printVisitor.result,
            "100"
        )
    }

    @Test
    fun bracesTest() {
        printVisitor.visitAll(listOf(Brace(BraceType.Left), NumberToken(1), Brace(BraceType.Right)))
        assertEquals(
            printVisitor.result,
            "(1)"
        )
    }

    @Test
    fun operationTest() {
        printVisitor.visitAll(listOf(NumberToken(1), Operation(OperationType.Plus), NumberToken(1)))
        assertEquals(
            printVisitor.result,
            "1 + 1"
        )
    }
}