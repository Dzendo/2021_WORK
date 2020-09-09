package com.app4web.asdzendo.todo.database

import androidx.room.TypeConverter
import java.util.*

// @TypeConverters(DatabaseConverters::class)
/**
 * @TypeConverters аннотация должна использоваться, когда мы объявляем свойство,
 * тип которого является пользовательским классом, списком, типом даты или любым другим типом, который Room и SQL не знают, как сериализовать.
 * В этом случае мы используем аннотацию на уровне поля класса, причем только это поле сможет ее использовать.
 * В зависимости от того, где находится аннотация, она будет вести себя по-разному, как описано здесь.
 *
Если вы поставите его на Database, все DAO и сущности в этой базе данных смогут использовать его.
Если вы поставите его на Dao, все методы в Дао смогут использовать его.
Если вы поставите его на Entity, все поля сущности смогут использовать его.
Если вы поместите его на POJO, все поля POJO смогут использовать его.
Если вы поставите его на Entity поле, только это поле сможет его использовать.
Если вы поставите его на Dao метод, все параметры метода будут иметь возможность использовать его.
Если вы поставите его на Dao параметр метода, только это поле сможет его использовать.
 */

/*
Помечает метод как преобразователь типов.
Класс может иметь столько методов @TypeConverter, сколько ему нужно.
Каждый метод конвертера должен получать 1 параметр и иметь тип возврата non-void.
 */
// companion object {
// @TypeConverter @JvmStatic
class DateConverters {
    @TypeConverter fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }
    @TypeConverter fun dateToTimestamp(date: Date?): Long? = date?.time
}

class CalendarConverters {
    @TypeConverter fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis
    @TypeConverter fun datestampToCalendar(value: Long): Calendar =
            Calendar.getInstance().apply { timeInMillis = value }

}

class CharConverters {
    @TypeConverter fun CharToString(char: Char?): String? = char?.toString()
    @TypeConverter fun StringToChar(string: String?): Char? = string?.get(0)?:' '
}
/*class PaemiConverters {
    @TypeConverter fun PaemiToInt(paemi: PAEMI): Int = PAEMI?.
    @TypeConverter fun StringToChar(string: String?): Char? = string?.get(0)?:' '
}*/