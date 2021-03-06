//Задание 2
/**
 *  Константы с комиссиями за перевод В КОПЕЙКАХ!
 */

const val VISA_MIR_COMISSION = 0.0075
const val VISA_MIR_MINIMAL_COMISSION = 3500

const val MASTERCARD_MINIMAL_SUM= 30000
const val MASTERCARD_MAX_SUM_IN_MONTH = 7500000
const val MASTERCARD_ANOTHER_CONDITION_COMISSION = 0.6
const val MASTERCARD_ANOTHER_CONDITION_COMISSION_PLUS = 2000

const val LIMIT_PER_DAY_FOR_CARDS = 15000000
const val LIMIT_PER_MONTH_FOR_CARDS = 60000000

const val VK_PAY_LIMIT_PER_ONCE = 1500000
const val VK_PAY_LIMIT_PER_MONTH = 4000000

enum class typeOfPayment {
    MIR, MASTERCARD, MAESTRO, VISA, VKPAY
}

/**
 * Чуть изменю тз - буду возвращать строку, в которой будет написано, что комиссия столько-то копеек,
 * в случае выхода за пределы лимитов - сообщение об этом
 */
public fun comissionForMoneyTransfer(paymentType: typeOfPayment = typeOfPayment.VKPAY, monthlyTransfer: Int = 0, currentTransfer: Int ) : String {
    return when (paymentType) {
        typeOfPayment.MAESTRO, typeOfPayment.MASTERCARD -> comissionForMaestroAndMC(monthlyTransfer, currentTransfer)
        typeOfPayment.VISA, typeOfPayment.MIR -> comissionForVisaAndMir(monthlyTransfer, currentTransfer)
        else -> comissionForVKPay(monthlyTransfer, currentTransfer)
    }
}


fun comissionForMaestroAndMC (monthlyTransfer: Int = 0, currentTransfer: Int) : String {
    when (monthlyTransfer) {
        in 0..MASTERCARD_MINIMAL_SUM -> when (currentTransfer) {
            in 0..MASTERCARD_MINIMAL_SUM -> return "комиссия в копейках ${(currentTransfer * MASTERCARD_ANOTHER_CONDITION_COMISSION + MASTERCARD_ANOTHER_CONDITION_COMISSION_PLUS).toInt()}"
            in MASTERCARD_MINIMAL_SUM..MASTERCARD_MAX_SUM_IN_MONTH -> return "комиссия не взимается"
            in (MASTERCARD_MAX_SUM_IN_MONTH + 1)..LIMIT_PER_DAY_FOR_CARDS -> return "комиссия в копейках ${
                (
                        (currentTransfer - MASTERCARD_MAX_SUM_IN_MONTH) * MASTERCARD_ANOTHER_CONDITION_COMISSION + MASTERCARD_ANOTHER_CONDITION_COMISSION_PLUS
                        ).toInt()
            }"
            in (LIMIT_PER_DAY_FOR_CARDS..Int.MAX_VALUE) -> return comissionForAllCardsWhenDailyLimitMore ()
        }
        in MASTERCARD_MINIMAL_SUM + 1..MASTERCARD_MAX_SUM_IN_MONTH ->
            if (currentTransfer + monthlyTransfer < MASTERCARD_MAX_SUM_IN_MONTH) {
                return "комиссия не взимается"
            } else if (currentTransfer + monthlyTransfer - MASTERCARD_MAX_SUM_IN_MONTH < LIMIT_PER_DAY_FOR_CARDS) {
                return "комиссия в копейках ${((currentTransfer + monthlyTransfer - MASTERCARD_MAX_SUM_IN_MONTH) * MASTERCARD_ANOTHER_CONDITION_COMISSION + MASTERCARD_ANOTHER_CONDITION_COMISSION_PLUS).toInt()
                }"
            } else return comissionForAllCardsWhenDailyLimitMore ()

        in MASTERCARD_MAX_SUM_IN_MONTH+1..LIMIT_PER_MONTH_FOR_CARDS -> return when (currentTransfer) {
            in 0..LIMIT_PER_DAY_FOR_CARDS -> "комиссия в копейках ${(currentTransfer*MASTERCARD_ANOTHER_CONDITION_COMISSION + MASTERCARD_ANOTHER_CONDITION_COMISSION_PLUS).toInt()}"
            else -> comissionForAllCardsWhenDailyLimitMore ()
        }
        else -> return comissionForAllCardsWhenMonthLimitMore ()
    }
    return ""
}

fun comissionForVisaAndMir (monthlyTransfer: Int = 0, currentTransfer: Int) : String {
    return when (monthlyTransfer) {
        in 0..LIMIT_PER_MONTH_FOR_CARDS -> when (currentTransfer) {
            in 0..LIMIT_PER_DAY_FOR_CARDS -> "комиссия в копейках " +
                    "${if (currentTransfer * VISA_MIR_COMISSION < VISA_MIR_MINIMAL_COMISSION) VISA_MIR_MINIMAL_COMISSION else (currentTransfer * VISA_MIR_COMISSION).toInt()}"
            else -> comissionForAllCardsWhenDailyLimitMore ()
        }
        else -> comissionForAllCardsWhenMonthLimitMore ()
    }
}

fun comissionForVKPay (monthlyTransfer: Int = 0, currentTransfer: Int) : String {
    return when (monthlyTransfer) {
        in 0..VK_PAY_LIMIT_PER_MONTH-> when (currentTransfer){
            in 0..VK_PAY_LIMIT_PER_ONCE-> "комиссия не взимается"
            else -> "Вы не можете перевести более 15000 за один раз с VKPay"
        }
        else -> comissionForAllCardsWhenMonthLimitMore()
    }
}

fun comissionForAllCardsWhenMonthLimitMore () : String {
    return "Превышен лимит по переводу средств в месяц"
}

fun comissionForAllCardsWhenDailyLimitMore () : String {
    return "Превышен дневной лимит"
}

