package model.commands.classes

sealed class Result<out T> {
    data class Success<out T>(
        val data: T,
    ) : Result<T>()

    data class Error(
        val error: CommandError,
    ) : Result<Nothing>()
}
