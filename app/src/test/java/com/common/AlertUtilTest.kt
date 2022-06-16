package com.common


import com.example.cryptotrackerapp.common.AlertUtil
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class AlertUtilTest {

    @Test
    fun `empty maximum price returns false`() {
        val result = AlertUtil.validateRegistrationInput(
            "",
            "123",
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty minimum price returns false`() {
        val result = AlertUtil.validateRegistrationInput(
            "123",
            "",
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid same min and max values returns false`() {
        val result = AlertUtil.validateRegistrationInput(
            "123",
            "123",
        )
        assertThat(result).isFalse()
    }


    @Test
    fun `more than 8 digit password returns false`() {
        val result = AlertUtil.validateRegistrationInput(
            "1234",
            "1234",
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `min greater than max returns false`() {
        val result = AlertUtil.validateRegistrationInput(
            "12345",
            "1234",
        )
        assertThat(result).isFalse()
    }
}