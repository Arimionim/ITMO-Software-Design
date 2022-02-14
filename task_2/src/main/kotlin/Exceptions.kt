class BadStatusCodeException(code: Int): Exception("API call caused error code: $code")

class TimeoutException(override val cause: Exception) : Exception("API timeout error: $cause")

class RequestFailedException(
    override val cause: Exception
) : Exception("API call caused Network error: $cause")
