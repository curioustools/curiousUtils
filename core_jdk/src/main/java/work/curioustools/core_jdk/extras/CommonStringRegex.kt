package work.curioustools.core_jdk.extras

object CommonStringRegex {
    const val VALID_EMAIL = """[a-zA-Z0-9\+\.\_\%\-\+]{1,256}\@[a-zA-Z0-9][a-zA-Z0-9\-]{0,64}(\.[a-zA-Z0-9][a-zA-Z0-9\-]{0,25})+"""

    const val VALID_SPECIAL_CHARACTERS = "~`!@#$%^&*()_-+={[}]|:;<,>.?/"
}