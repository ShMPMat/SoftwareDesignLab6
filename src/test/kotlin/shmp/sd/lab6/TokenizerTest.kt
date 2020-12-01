package shmp.sd.lab6

import org.junit.Test

import org.junit.Assert.*
import kotlin.test.assertFails

class TokenizerTest {
    private val tokenizer = Tokenizer()

    @Test
    fun tokenizeCorrectInputTest() {

        assertEquals(
            tokenizer.tokenize("1"),
            listOf(NumberToken(1))
        )

        assertEquals(
            tokenizer.tokenize("1 + 2"),
            listOf(NumberToken(1), Operation(OperationType.Plus), NumberToken(2))
        )

        assertEquals(
            tokenizer.tokenize("(1 + 2) * 3 / 1234"),
            listOf(
                Brace(BraceType.Left),
                NumberToken(1),
                Operation(OperationType.Plus),
                NumberToken(2),
                Brace(BraceType.Right),
                Operation(OperationType.Multiply),
                NumberToken(3),
                Operation(OperationType.Divide),
                NumberToken(1234)

            )
        )
    }

    @Test
    fun tokenizeMalformedInputTest() {
        assertEquals(
            tokenizer.tokenize("1 1 + 1"),
            listOf(NumberToken(1), NumberToken(1), Operation(OperationType.Plus), NumberToken(1))
        )

        assertEquals(
            tokenizer.tokenize(") 1 ("),
            listOf(Brace(BraceType.Right), NumberToken(1), Brace(BraceType.Left))
        )
    }

    @Test
    fun tokenizeBadlyFormattedInputTest() {
        assertEquals(
            tokenizer.tokenize("            "),
            emptyList<Token>()
        )

        assertEquals(
            tokenizer.tokenize("     1       "),
            listOf(NumberToken(1))
        )

        assertEquals(
            tokenizer.tokenize("     1 +     \n 2     "),
            listOf(NumberToken(1), Operation(OperationType.Plus), NumberToken(2))
        )
    }

    @Test
    fun unexpectedInputFailTest() {
        assertFails {
            tokenizer.tokenize("a")
        }

        assertFails {
            tokenizer.tokenize("123 + (1 + 1) a")
        }
    }
}