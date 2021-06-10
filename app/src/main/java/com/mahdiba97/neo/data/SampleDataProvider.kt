package com.mahdiba97.neo.data

import java.util.*

class SampleDataProvider {
    companion object {
        val smapleText1 = "A simple text"
        val smapleText2 = "A note a\nline feed"
        val smapleText3 = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis et libero elit. Praesent accumsan ornare quam. Integer vehicula posuere est placerat malesuada. Proin odio magna, aliquam id mauris sit amet, fermentum rutrum urna. Phasellus volutpat diam quis mauris ornare bibendum. Sed eu tempus ipsum. Maecenas suscipit condimentum magna eu dapibus. Duis lacus ipsum, rhoncus a imperdiet a, finibus et sapien. Suspendisse eget elementum mauris. In a imperdiet risus. Quisque nec accumsan risus.

            Suspendisse potenti. Morbi a enim pulvinar est imperdiet mollis gravida sit amet magna. Mauris mi est, feugiat sed suscipit et, scelerisque quis velit. Suspendisse laoreet, metus nec mollis iaculis, eros ante vehicula augue, quis finibus tellus eros ut libero. Phasellus porta dignissim feugiat. Quisque maximus condimentum massa, vitae aliquet nibh egestas sodales. Sed ut aliquet quam. Nam ultrices, nisl id eleifend consectetur, leo metus posuere ligula, et condimentum est nisl a mauris. Ut a arcu efficitur, mattis nisi non, pulvinar nibh. Fusce ut tellus ex. Phasellus est libero, tincidunt et varius in, luctus sed lacus. Quisque pulvinar orci posuere auctor fermentum. Vivamus volutpat nisi et mi commodo ultricies at id ante.
        """.trimIndent()


        private fun priority(diff: Int): Int {
            return (Date().time + diff).toInt()
        }

        fun getNotes() = arrayListOf(
            NoteEntity(priority(0), smapleText1),
            NoteEntity(priority(1), smapleText2),
            NoteEntity(priority(2), smapleText3)
        )
    }
}