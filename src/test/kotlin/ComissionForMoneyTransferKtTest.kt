import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.BeforeClass

class ComissionForMoneyTransferKtTest {

    val monthlyTransferSmall = 500000
    val monthlyTransferBigForMaestroWithoutComission = 5000000
    val monthlyTransferBigForMaestroWithComissionForMoney =  48000000
    val cardDayLimitMore = 17300000
    val cardMonthLimitMore = 62000000
    val VKPayDayLimitMore = 2200000
    val VkPayMonthLimitMore = 6300000
    val tinySum = 15000
    val smallSum = 90000
    val normalSum = 2300000

    @Test
    fun comissionForMoneyTransferMaestroWithTinySumMonthlyNormalLimit() {
        val expected = "комиссия не взимается";
        val result = comissionForMoneyTransfer(typeOfPayment.MAESTRO, monthlyTransferSmall, tinySum)
        assertEquals(expected, result);
    }

    @Test
    fun ComissionForMoneyTransferWithTinySumMonthMoreThanLimit() {
        val expected = "Превышен лимит по переводу средств в месяц"
        val result = comissionForMoneyTransfer(typeOfPayment.MAESTRO, cardMonthLimitMore, tinySum)
        assertEquals(expected, result)
    }

    @Test
    fun ComissionForMoneyTransferVisaWithNormalSumNormalLimits() {
        val expected = "комиссия в копейках 24000"
        val result = comissionForMoneyTransfer(typeOfPayment.VISA, 4600000, 3200000)
        assertEquals(expected, result)

    }

    @Test
    fun ComissionForVisaAndMirTest() {
        val expected = "комиссия в копейках 3500"
        val result = comissionForVisaAndMir(0, 300000)
        assertEquals(expected, result)

    }

    @Test
    fun comissionForAllCardsWhenMonthLimitMoreTest () {
        assertEquals("Превышен лимит по переводу средств в месяц", comissionForAllCardsWhenMonthLimitMore())
    }

    @Test
    fun comissionForAllCardsWhenDailyLimitMoreTest () {
        assertEquals("Превышен дневной лимит", comissionForAllCardsWhenDailyLimitMore())
    }
}