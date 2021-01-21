fun main() {
    /**
     * Проверка первого задания
     */
    println(agoToText(64)) //был(а) 4 минуты назад
    println(agoToText(74)) //был(а) 14 минут назад
    println(agoToText(61)) //был(а) 1 минуту назад
    println(agoToText(82)) //был(а) 22 минуты назад
    println(agoToText(36)) //был(а) только что
    println(agoToText(4000)) //был(а) 1 час назад
    println(agoToText(7500)) //был(а) 2 часа назад
    println(agoToText(19000)) //был(а) 5 часов назад
    println(agoToText(90000)) //был(а) сегодня
    println(agoToText(180000)) //был(а) вчера
    println(agoToText(300000)) //был(а) давно

    println(comissionForMoneyTransfer(typeOfPayment.VKPAY, 500000, 250000))
    println(comissionForMoneyTransfer(typeOfPayment.VKPAY, 500000, 8000000))
    println(comissionForMoneyTransfer(typeOfPayment.MAESTRO, 500000, 8000000))
}

//Задание 1
private fun agoToText(seconds : Int) : String {
    return when (seconds) {
        in 0..60 -> "был(а) только что"
        in 61..3600 -> "был(а) ${seconds%60} ${wordFormForMinutes(seconds%60)} назад"
        in 3601..60*60*24 -> "был(а) ${seconds/3600} ${wordFormForHours(seconds/3600)} назад"
        in (60*60*24 +1)..60*60*24*2 -> "был(а) сегодня"
        in (60*60*24*2 + 1)..60*60*24*3 -> "был(а) вчера"
        in 60*60*24*3..Int.MAX_VALUE -> "был(а) давно"
        else -> "был(а) давно"
    }
}

private fun wordFormForMinutes(minutes: Int) : String  {
    return when (minutes) {
        1, 21, 31, 41, 51 -> "минуту"
        in 5..20, in 25..30, in 35..40, in 45..50, in 55..60 -> "минут"
        else -> "минуты"
    }
}

private fun wordFormForHours(hours: Int) : String {
    return when(hours) {
        1, 21 -> "час"
        in 2..4, in 22..24 -> "часа"
        else -> "часов"
    }
}


