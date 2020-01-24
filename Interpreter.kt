package staticanalysis

class Interpreter {
    fun evaluate(program: Language, state: State): Pair<Language, State> {
        return when (program) {
            is Integer -> Pair(program, state)
            is Bool -> Pair(program, state)
            is Variable -> Pair(state.lookupVariable(program), state)
            is FunctionCall -> {
                val arguments = ArrayList<Language>()
                var newState = state
                for (argument in program.arguments) {
                    val (evaluatedArgument, evaluatedState) = evaluate(argument, newState)
                    arguments.add(evaluatedArgument)
                    newState = evaluatedState
                }
                val function = state.lookupFunction(program.name)
                val functionState = state.lookupFunctionState(program.name)
                val localState = State.of(state.lookupFunctionParameters(program.name), arguments)

                val state1 = functionState.addState(localState)
                val (result, _) = evaluate(function, state1)
                Pair(result, newState) // Functions don't change state, but its arguments do
            }

            is Addition -> {
                evaluateBinaryFunctions(
                    program.expression1,
                    program.expression2,
                    state
                ) { a, b -> Integer(a + b) }
            }
            is Subtraction -> {
                evaluateBinaryFunctions(
                    program.expression1,
                    program.expression2,
                    state
                ) { a, b -> Integer(a - b) }
            }
            is Multiplication -> {
                evaluateBinaryFunctions(
                    program.expression1,
                    program.expression2,
                    state
                ) { a, b -> Integer(a * b) }
            }
            is Division -> {
                evaluateBinaryFunctions(
                    program.expression1,
                    program.expression2,
                    state
                ) { a, b ->
                    if (b != 0) {
                        Integer(a / b)
                    } else {
                        throw IllegalStateException()
                    }
                }
            }

            is LessThan -> {
                evaluateBinaryFunctions(
                    program.expression1,
                    program.expression2,
                    state
                ) { a, b -> Bool(a < b) }
            }
            is LessThanOrEqual -> {
                evaluateBinaryFunctions(
                    program.expression1,
                    program.expression2,
                    state
                ) { a, b -> Bool(a <= b) }
            }
            is Equal -> {
                evaluateBinaryFunctions(
                    program.expression1,
                    program.expression2,
                    state
                ) { a, b -> Bool(a == b) }
            }

            is VariableAssignment -> {
                val (program1, state1) = evaluate(program.expression, state)
                val state2 = state1.assignVariable(program.variable, program1)
                Pair(program1, state2)
            }
            is FunctionAssignment -> {
                val state1 = state.assignFunction(program.variable, program.parameters, program.expression)
                Pair(program.expression, state1)
            }
            is Statement -> {
                val (_, state1) = evaluate(program.statement, state)
                val (program2, state2) = evaluate(program.expression, state1)
                return Pair(program2, state2)
            }

            is If -> {
                val (condition, state1) = evaluate(program.condition, state)
                when (condition) {
                    is Bool ->
                        if (condition.value) {
                            evaluate(program.expression1, state1)
                        } else {
                            evaluate(program.expression2, state1)
                        }
                    else -> throw IllegalStateException()
                }
            }
        }
    }

    private fun <T> evaluateBinaryFunctions(
        expression1: Language,
        expression2: Language,
        state: State,
        param: (Int, Int) -> T
    ): Pair<T, State> {
        val (a, state1) = evaluate(expression1, state)
        val (b, state2) = evaluate(expression2, state1)
        if (a is Integer && b is Integer) {
            val result = param.invoke(a.value, b.value)
            return Pair(result, state2)
        } else {
            throw IllegalStateException()
        }
    }
}