package com.muratozturk.metflix.common

interface Mapper<Input, Output> {
    fun map(input: Input): Output
}
