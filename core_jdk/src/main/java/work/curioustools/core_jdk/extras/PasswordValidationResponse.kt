package work.curioustools.core_jdk.extras

sealed class PasswordValidationResponse(open val msg: String) {
    object VALID : PasswordValidationResponse("Success")
    object NULL_OR_BLANK : PasswordValidationResponse("Password must not be empty")
    data class NO_MIN_UPPERCASE_LETTERS(val minCount: Int = 1) : PasswordValidationResponse("Password must have at least $minCount Capital letter(s)")
    data class NO_LOWERCASE_LETTERS(val minCount: Int = 1) : PasswordValidationResponse("Password must have at least $minCount Lowercase letter(s)")
    data class NO_MIN_Special_LETTERS(val minCount: Int = 1) : PasswordValidationResponse("Password must have at least $minCount letter(s) from ${CommonStringRegex.VALID_SPECIAL_CHARACTERS}")
    data class NO_MIN_DIGITS(val minCount: Int = 1) : PasswordValidationResponse("Password must have at least $minCount digit(s)")
    data class OUT_OF_BOUNDS_ERROR(val min: Int, val max: Int) : PasswordValidationResponse("Password must have $min to $max characters")
    data class ILLEGAL_CHARACTER(val c: Char) : PasswordValidationResponse(" '$c ' is not a valid character")


}