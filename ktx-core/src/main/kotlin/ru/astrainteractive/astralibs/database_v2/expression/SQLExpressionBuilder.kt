package ru.astrainteractive.astralibs.database_v2.expression

import ru.astrainteractive.astralibs.database_v2.Column

object SQLExpressionBuilder {
    infix fun <T> Column<T>.eq(value: T): Expression<Boolean> {
        return EqExpression(this, value)
    }
    infix fun <T> Column<T>.notEq(value: T): Expression<Boolean> {
        return NotEqExpression(this, value)
    }
    infix fun <T> Column<T>.more(value: T): Expression<Boolean> {
        return MoreExpression(this, value)
    }
    infix fun <T> Column<T>.less(value: T): Expression<Boolean> {
        return LessExpression(this, value)
    }


    fun resolveExpression(ep: Expression<*>): String {
        return ep.toString()
    }

}