package work.curioustools.core_jdk

import work.curioustools.core_jdk.extras.CommonStringRegex

fun Char.isStrictlyALowerCase() = this in 'a'..'z' // will not match characters like ä

fun Char.isStrictlyAnUpperCase() = this in 'A'..'Z' // will not match characters like ä

fun Char.isStrictlyADigit() = this in '0'..'9'

fun Char.isStrictlyAValidSpecialLetter() = this in CommonStringRegex.VALID_SPECIAL_CHARACTERS// does not have escape characters like  \ " '
