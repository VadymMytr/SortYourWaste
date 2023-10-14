package vadym.my.wastesorting.utils.extensions

inline fun <FIRST : Any, SECOND : Any, RESULT> letBoth(
    first: FIRST?,
    second: SECOND?,
    onNotNull: (first: FIRST, second: SECOND) -> RESULT,
): RESULT? {
    return if (first != null && second != null) {
        onNotNull(first, second)
    } else {
        null
    }
}
