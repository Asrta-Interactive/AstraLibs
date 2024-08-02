package ru.astrainteractive.astralibs.command.api.exception

import ru.astrainteractive.astralibs.command.api.argumenttype.ArgumentType

class BadArgumentException(
    wrongArgument: String?,
    type: ArgumentType<*>
) : CommandException("Incompatible type $type for argument $wrongArgument")
