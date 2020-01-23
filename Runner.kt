package staticanalysis

fun main() {
    val interpreter = Interpreter()
    val program1 = Integer(1)
    val program2 = Addition(Integer(2), Integer(3))
    val program3 =
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
    val program4 =
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
    val program5 =
        Statement(
            FunctionAssignment(
                Variable("id"),
                listOf(Variable("x")),
                Variable("x")
            ),
            FunctionCall(
                Variable("id"),
                listOf(Integer(5))
            )
        )
    val program6 =
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
    val program7 =
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
    val program8 =
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
    val program9 =
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
    println(interpreter.evaluate(program1, State.empty()).first)
    println(interpreter.evaluate(program2, State.empty()).first)
    println(interpreter.evaluate(program3, State.empty()).first)
    println(interpreter.evaluate(program4, State.empty()).first)
    println(interpreter.evaluate(program5, State.empty()).first)
//    println(interpreter.evaluate(program6, State.empty()).first)
//    println(interpreter.evaluate(program7, State.empty()).first)
    println(interpreter.evaluate(program8, State.empty()).first)
    println(interpreter.evaluate(program9, State.empty()).first)
}