package application

import application.profiled.A
import application.profiled.B

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val a = A()
        val b = B()

        repeat(10) {
            with(a) {
                rec1()
            }

            with(b) {
                method1("")
                method1("", 0)
                method2(0, 0)
                method3("")
            }
        }
    }
}