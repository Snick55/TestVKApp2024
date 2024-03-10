package com.snick55.testvkapp2024.data

interface Loader<T>{

   suspend fun load(pageIndex: Int,pageSize: Int):List<T>


}