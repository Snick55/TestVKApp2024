package com.snick55.testvkapp2024.core

open class AppException(errorMessage: String): java.lang.Exception(errorMessage)

class NoInternetException: AppException("No Internet connection")
class GenericException: AppException("Something went wrong")