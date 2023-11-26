package com.devhp.mykotlin

fun main() {
    Repository.startFetch()
    getResult(result = Repository.getCurrentState())
    Repository.finishedFetch()
    getResult(result = Repository.getCurrentState())
    Repository.error()
    getResult(result = Repository.getCurrentState())

}

//fun getResult(result: Result) {
//    return when (result) {
//        Result.LOADING -> println("Loading")
//        Result.SUCCESS -> println("Success")
//        Result.FAILURE -> println("Failure")
//        Result.ERROR -> println("Error")
//        else -> {
//            println("idle")
//        }
//    }
//}
//
//object Repository {
//    private var loadState: Result = Result.IDLE
//    private var dataFeched: String? = null
//    fun startFetch() {
//        loadState = Result.LOADING
//        dataFeched = "data"
//    }
//
//    fun finishedFetch() {
//        loadState = Result.SUCCESS
//        dataFeched = null
//    }
//
//    fun error() {
//        loadState = Result.ERROR
//    }
//
//    fun getCurrentState(): Result {
//        return loadState
//    }
//}
//
//
//enum class Result {
//    LOADING,
//    SUCCESS,
//    FAILURE,
//    ERROR,
//    IDLE
//}


sealed class Result {
    data class Success(val dataFetched: String?) : Result()
    data class Error(val exception: Exception) : Result()
    object NotLoading : Result()
    object Loading : Result()
}


object Repository {
    private var loadState: Result = Result.NotLoading
    private var dataFeched: String? = null
    fun startFetch() {
        loadState = Result.Loading
        dataFeched = "data"
    }

    fun finishedFetch() {
        loadState = Result.Success(dataFeched)
        dataFeched = null
    }

    fun error() {
        loadState = Result.Error(Exception("Exception"))
    }

    fun getCurrentState(): Result {
        return loadState
    }
}

fun getResult(result: Result) {
    return when (result) {
        is Result.Loading -> println("Loading...")
        is Result.Success -> println(result.dataFetched ?: "Ensure you start get data?")
        is Result.Error -> println(result.exception)
        is Result.NotLoading -> println("idle")
    }
}
