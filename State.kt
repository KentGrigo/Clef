package staticanalysis

data class State(
    val variables: Map<Variable, Language>,
    val functions: Map<Variable, Language>,
    val functionToParameters: Map<Variable, List<Variable>>,
    val functionToStates: Map<Variable, State>
) {
    fun assignVariable(variable: Variable, expression: Language): State {
        val newVariables = HashMap<Variable, Language>()
        newVariables.putAll(variables)
        newVariables[variable] = expression
        return State(newVariables, functions, functionToParameters, functionToStates)
    }

    fun assignFunction(functionName: Variable, parameters: List<Variable>, expression: Language): State {
        val newFunctions = HashMap<Variable, Language>()
        newFunctions.putAll(functions)
        newFunctions[functionName] = expression

        val newFunctionToParameters = HashMap<Variable, List<Variable>>()
        newFunctionToParameters.putAll(functionToParameters)
        newFunctionToParameters[functionName] = parameters

        val newFunctionToStates = HashMap<Variable, State>()
        newFunctionToStates.putAll(functionToStates)
        newFunctionToStates[functionName] = State(variables, newFunctions, newFunctionToParameters, newFunctionToStates)

        return newFunctionToStates[functionName]!!
    }

    fun lookupVariable(variable: Variable): Language {
        return variables.getValue(variable)
    }

    fun lookupFunction(variable: Variable): Language {
        return functions.getValue(variable)
    }

    fun lookupFunctionParameters(variable: Variable): List<Variable> {
        return functionToParameters.getValue(variable)
    }

    fun lookupFunctionState(variable: Variable): State {
        return functionToStates.getValue(variable)
    }

    fun addState(state: State): State {
        val newVariables = HashMap<Variable, Language>()
        newVariables.putAll(variables)
        state.variables.forEach { newVariables[it.key] = it.value }

        val newFunctions = HashMap<Variable, Language>()
        newFunctions.putAll(functions)
        state.functions.forEach { newFunctions[it.key] = it.value }

        val newFunctionToParameters = HashMap<Variable, List<Variable>>()
        newFunctionToParameters.putAll(functionToParameters)
        state.functionToParameters.forEach { newFunctionToParameters[it.key] = it.value }

        val newFunctionToStates = HashMap<Variable, State>()
        newFunctionToStates.putAll(functionToStates)
        state.functionToStates.forEach { newFunctionToStates[it.key] = it.value }

        return State(newVariables, newFunctions, newFunctionToParameters, newFunctionToStates)
    }

    companion object {
        fun empty(): State {
            return State(HashMap(), HashMap(), HashMap(), HashMap())
        }

        fun of(parameters: List<Variable>, arguments: List<Language>): State {
            if (parameters.size != arguments.size) {
                throw IllegalStateException()
            }
            var functionState: State = empty()
            for (index in arguments.indices) {
                val parameter = parameters.get(index)
                val argument = arguments.get(index)
                functionState = functionState.assignVariable(parameter, argument)
            }
            return functionState
        }
    }
}