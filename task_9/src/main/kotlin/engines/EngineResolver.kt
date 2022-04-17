package engines

fun EngineType.resolve(): Class<out BaseEngineActor> = when (this) {
    EngineType.BING -> BingEngineActor::class.java
    EngineType.GOOGLE -> GoogleEngineActor::class.java
    EngineType.YANDEX -> YandexEngineActor::class.java
}
