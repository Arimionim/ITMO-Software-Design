package entity

enum class Currency {
    EUR,
    USD,
    RUB;
}

fun Currency.toRoubles() = when (this) {
    Currency.EUR -> 90
    Currency.USD -> 83
    Currency.RUB -> 1
}

fun Currency.toChar() = when (this) {
    Currency.EUR -> '€'
    Currency.USD -> '$'
    Currency.RUB -> '₽'
}

fun Double.convert(src: Currency, dst: Currency): Double {
    return this * src.toRoubles() / dst.toRoubles()
}
