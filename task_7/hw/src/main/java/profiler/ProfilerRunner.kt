package profiler

fun main(args: Array<String>) {

    try {
        ProfilerAspect.profiledPackage = args[0]
        val mainClassName = args[1]

        Class.forName(mainClassName)
            .getMethod("main", Array<String>::class.java)
            .invoke(null, args)

        ProfilerAspect.profiler.printStatistics()
    } catch (e: Exception) {
        println("Usage: <Package to profile> <File to Run>")
    }
}