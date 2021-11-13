package work.curioustools.core_jdk.extras

enum class EmailValidationResponse(val msg: String) {
    VALID("Success"),
    NULL_OR_BLANK("Email must not be empty"),
    IMPROPER_EMAIL("Email does not match the standard email pattern of ${CommonStringRegex.VALID_EMAIL}")
}