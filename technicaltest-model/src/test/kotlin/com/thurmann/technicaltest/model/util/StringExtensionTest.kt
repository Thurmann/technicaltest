package com.thurmann.technicaltest.model.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StringExtensionTest {
    
    @Test
    fun verifySplitInHalf() {
        assertThat("1".splitInHalf()).isEqualTo(Pair("","1"))
        assertThat("12".splitInHalf()).isEqualTo(Pair("1","2"))
        assertThat("123".splitInHalf()).isEqualTo(Pair("1","23"))
        assertThat("1234".splitInHalf()).isEqualTo(Pair("12","34"))
        assertThat("12345".splitInHalf()).isEqualTo(Pair("12","345"))
    }
    
    @Test
    fun verifyRemoveWhitespaces() {
        assertThat("1 2 3".removeWhiteSpaces()).isEqualTo("123")
        assertThat(" 123".removeWhiteSpaces()).isEqualTo("123")
        assertThat("123 ".removeWhiteSpaces()).isEqualTo("123")
        assertThat("123 \t abc \n".removeWhiteSpaces()).isEqualTo("123abc")
    }
}