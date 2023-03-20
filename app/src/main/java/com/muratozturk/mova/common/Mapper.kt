package com.muratozturk.mova.common

interface Mapper<Input, Output> {
    fun map(input: Input): Output
}
