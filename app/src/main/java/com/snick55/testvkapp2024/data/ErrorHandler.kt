package com.snick55.testvkapp2024.data

import com.snick55.testvkapp2024.core.AppException
import com.snick55.testvkapp2024.core.GenericException
import com.snick55.testvkapp2024.core.NoInternetException
import java.net.UnknownHostException
import javax.inject.Inject

interface ErrorHandler{

    fun handle(e: Exception): AppException

    class BaseErrorHandler @Inject constructor(): ErrorHandler {
        override fun handle(e: Exception): AppException {
           return when(e){
                is UnknownHostException -> NoInternetException()
                else -> GenericException()
            }
        }
    }

}