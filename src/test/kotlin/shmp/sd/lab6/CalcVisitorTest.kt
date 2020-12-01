package shmp.sd.lab6

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFails

class CalcVisitorTest {
    private lateinit var calcVisitor: CalcVisitor

    @Before
    fun initPrintVisitor() {
        calcVisitor = CalcVisitor()
    }

    @Test
    fun noTokensExceptionTest() {
        assertFails {
            calcVisitor.visitAll(listOf())
            calcVisitor.result
        }
    }

    @Test
    fun numberTest() {
        calcVisitor.visitAll(listOf(NumberToken(100)))
        assertEquals(
            calcVisitor.result,
            100.0,
            0.0
        )
    }

    @Test
    fun bracesFailTest() {
        assertFails {
            calcVisitor.visitAll(listOf(Brace(BraceType.Left), NumberToken(1), Brace(BraceType.Right)))
        }
    }

    @Test
    fun operationTest() {
        calcVisitor.visitAll(listOf(NumberToken(1), NumberToken(1), Operation(OperationType.Plus)))
        assertEquals(
            calcVisitor.result,
            2.0,
            0.0
        )
    }
}