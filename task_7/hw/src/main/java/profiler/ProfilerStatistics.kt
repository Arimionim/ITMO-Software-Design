package profiler

class ProfilerStatistics {
    private val root = Node(null)
    private var current: Node? = root
    fun enterMethod(methodName: String) {
        current = current?.enterMethod(methodName)
    }

    fun exitMethod(elapsed: Long) {
        current?.increment(elapsed)
        current = current?.parent
    }

    fun printStatistics() {
        System.err.println("================================================================================")
        System.err.println("PROFILER STATISTICS:")
        System.err.printf("%-60s %8s %10s %10s\n", "Method", "Calls", "Total", "Avg")
        root.printStatistics(0)
    }

    private class Node(val parent: Node?) {
        private var callCount = 0
        private var callsElapsed: Long = 0L
        private val children: MutableMap<String, Node>

        init {
            children = LinkedHashMap()
        }

        fun increment(elapsed: Long) {
            callCount++
            callsElapsed += elapsed
        }

        fun enterMethod(methodName: String): Node {
            return children.computeIfAbsent(
                methodName
            ) {
                Node(
                    this
                )
            }
        }

        fun printStatistics(offset: Int) {
            val prefixBuilder = StringBuilder()
            for (i in 0 until offset - 1) {
                prefixBuilder.append("  ")
            }
            if (offset > 0) {
                prefixBuilder.append("- ")
            }
            val prefix = prefixBuilder.toString()
            val maxLength = 60 - 2 * offset
            for (item in children.entries) {
                var methodName = item.key
                val methodInfo = item.value
                if (methodName.length > maxLength) {
                    methodName = methodName.replace(
                        "\\b([a-z])[a-zA-Z_]+\\.".toRegex(),
                        "$1."
                    )
                }
                if (methodName.length > maxLength) {
                    methodName = methodName.substring(maxLength - 3) + "..."
                }
                System.err.printf(
                    """
                        %s%-${maxLength}s %8d %10d %10d
                        
                        """.trimIndent(),
                    prefix,
                    methodName,
                    methodInfo.callCount,
                    methodInfo.callsElapsed,
                    methodInfo.callsElapsed / methodInfo.callCount
                )
                methodInfo.printStatistics(offset + 1)
            }
        }
    }

    companion object {
        val instance = ProfilerStatistics()
    }
}