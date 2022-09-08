package staticanalysis.test.src

import staticanalysis.*

class InterpreterTest {
    private val interpreter = Interpreter()

    // @Test
    fun test1() {
        val program = Integer(1)
        val actual = interpreter.evaluate(program, State.empty()).first
//        assertThat(1, actual)
    }

    // @Test
    fun test2() {
        val program = Addition(Integer(2), Integer(3))
        val actual = interpreter.evaluate(program, State.empty()).first
//        assertThat(5, actual)
    }

    // @Test
    fun test3() {
        val program =
            Addition(
                Addition(
                    Integer(2),
                    Integer(3)
                ),
                Addition(
                    Integer(5),
                    Integer(7)
                )
            )
        val actual = interpreter.evaluate(program, State.empty()).first
//        assertThat(12, actual)
    }

    // @Test
    fun test4() {
        val program =
            Statement(
                VariableAssignment(
                    Variable("x"),
                    Integer(3)
                ),
                Addition(
                    Variable("x"),
                    Integer(2)
                )
            )
        val actual = interpreter.evaluate(program, State.empty()).first
//        assertThat(5, actual)
    }

    // @Test
    fun test5() {
        val program =
            Statement(
                FunctionAssignment(
                    Variable("f"),
                    listOf(Variable("x")),
                    Variable("x")
                ),
                FunctionCall(
                    Variable("f"),
                    listOf(Integer(5))
                )
            )
        val actual = interpreter.evaluate(program, State.empty()).first
//        assertThat(5, actual)
    }

    // @Test
    fun test6() {
        val program =
            Statement(
                FunctionAssignment(
                    Variable("nonstop"),
                    listOf(),
                    FunctionCall(
                        Variable("nonstop"),
                        listOf()
                    )
                ),
                FunctionCall(Variable("nonstop"), listOf())
            )
        val actual = interpreter.evaluate(program, State.empty()).first
//        assert that it does not terminate
    }

    // @Test
    fun test7() {
        val program =
            Statement(
                FunctionAssignment(
                    Variable("nonstopwithargument"),
                    listOf(Variable("x")),
                    FunctionCall(
                        Variable("nonstopwithargument"),
                        listOf(Variable("x"))
                    )
                ),
                FunctionCall(Variable("nonstopwithargument"), listOf(Integer(5)))
            )
        val actual = interpreter.evaluate(program, State.empty()).first
//        assert that it does not terminate
    }

    // @Test
    fun test8() {
        val program =
            Statement(
                FunctionAssignment(
                    Variable("factorial"),
                    listOf(Variable("x")),
                    If(
                        LessThanOrEqual(
                            Variable("x"),
                            Integer(0)
                        ),
                        Integer(1),
                        Multiplication(
                            Variable("x"),
                            FunctionCall(
                                Variable("factorial"),
                                listOf(
                                    Subtraction(
                                        Variable("x"),
                                        Integer(1)
                                    )
                                )
                            )
                        )
                    )
                ),
                FunctionCall(Variable("factorial"), listOf(Integer(5)))
            )
        val actual = interpreter.evaluate(program, State.empty()).first
//        assertThat(120, actual)
    }

    // @Test
    fun test9() {
        val program =
            Statement(
                Statement(
                    FunctionAssignment(
                        Variable("factorial"),
                        listOf(Variable("x")),
                        If(
                            LessThanOrEqual(
                                Variable("x"),
                                Integer(0)
                            ),
                            Integer(1),
                            Multiplication(
                                Variable("x"),
                                FunctionCall(
                                    Variable("factorial"),
                                    listOf(
                                        Subtraction(
                                            Variable("x"),
                                            Integer(1)
                                        )
                                    )
                                )
                            )
                        )
                    ),
                    VariableAssignment(
                        Variable("x"),
                        FunctionCall(
                            Variable("factorial"),
                            listOf(Integer(5))
                        )
                    )
                ),
                Addition(
                    Variable("x"),
                    Integer(2)
                )
            )
        val actual = interpreter.evaluate(program, State.empty()).first
//        assertThat(122, actual)
    }
}
