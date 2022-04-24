package core

import org.testcontainers.containers.GenericContainer

class StockServerContainer : GenericContainer<StockServerContainer>("arimionim/sd-stock")
