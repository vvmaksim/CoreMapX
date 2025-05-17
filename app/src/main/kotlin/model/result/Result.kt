package model.result

sealed class Result<out T> {
    data class Success<out T>(
        val data: T,
    ) : Result<T>()

    data class Error(
        val error: model.result.Error,
    ) : Result<Nothing>()
}
