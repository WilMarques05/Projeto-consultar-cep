package com.willismarques.consultarendereco.api

import com.willismarques.consultarendereco.model.RespostaCep
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepAPI {
    @GET("{cep}/json/")
    suspend fun recuperarCep(
        @Path("cep") cep : String
    ): Response<RespostaCep>
}