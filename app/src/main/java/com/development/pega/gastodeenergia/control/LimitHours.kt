package com.development.pega.gastodeenergia.control

abstract class LimitHours {

    companion object {
        fun limit24H(text: String): String{

            var num1 = text[0].toInt()
            var num2 = text[1].toInt()
            var num4 = text[3].toInt()
            var num5 = text[4].toInt()

            if(num1 > 2) num1 = 2
            if(num1 == 2 && num2 > 4) num2 = 4
            if(num4 > 5) num4 = 5

            return num1.toString() + num2.toString() + num4.toString() + num5.toString()
        }

        fun convertHourToMinute(text: String): Int {
            var hour = text.substring(0..1)
            var minute = text.substring(3..4)

            return ((hour.toInt() * 60) + minute.toInt())
        }
    }
}