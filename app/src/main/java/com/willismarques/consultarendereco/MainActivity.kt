package com.willismarques.consultarendereco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isInvisible
import com.willismarques.consultarendereco.api.RetrofitService
import com.willismarques.consultarendereco.api.ViaCepAPI
import com.willismarques.consultarendereco.databinding.ActivityMainBinding
import com.willismarques.consultarendereco.model.RespostaCep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        RetrofitService.retrofit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        esconderTexto()

        binding.btnPesquisar.setOnClickListener {
            var cepRecuperado = binding.TextInputCep.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                recuperarCep(cepRecuperado)
            }
        }

    }

    private suspend fun recuperarCep(cepRecuperado: String) {


        var resposta: Response<RespostaCep>? = null

        try {
            val viaCepAPI = retrofit.create(ViaCepAPI::class.java)
            resposta = viaCepAPI.recuperarCep(cepRecuperado)

        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_cep", "Erro na comunicação com a api ${resposta?.code()}")
        }

        if (resposta != null){

            if (resposta.isSuccessful){
                val respostaCEP = resposta.body()

                withContext(Dispatchers.Main){
                    exibirTexto()

                    binding.TextInputCep.text = null
                    binding.textCep.text = respostaCEP?.cep
                    binding.textLogradouro.text = respostaCEP?.logradouro
                    binding.textBairro.text = respostaCEP?.bairro
                    binding.textLocalidade.text = respostaCEP?.localidade
                    binding.textUf.text = respostaCEP?.uf
                    binding.textEstado.text = respostaCEP?.estado
                    binding.textRegiao.text = respostaCEP?.regiao
                    binding.textDdd.text = respostaCEP?.ddd

                }
            }

        }else{
            Log.i("info_cep", "Erro ao recuperar CEP ${resposta?.code()}")
        }
    }

    private fun esconderTexto(){

        //Elementos Estáticos
        binding.textEstaticoCep.isInvisible = true
        binding.textEstaticoLogradouro.isInvisible = true
        binding.textEstaticoBairro.isInvisible = true
        binding.textEstaticoLocalidade.isInvisible = true
        binding.textEstaticoUf.isInvisible = true
        binding.textEstaticoEstado.isInvisible = true
        binding.textEstaticoRegiao.isInvisible = true
        binding.textEstaticoDdd.isInvisible = true

        //Elementos resposta API
        binding.textCep.isInvisible = true
        binding.textLogradouro.isInvisible = true
        binding.textBairro.isInvisible = true
        binding.textLocalidade.isInvisible = true
        binding.textUf.isInvisible = true
        binding.textEstado.isInvisible = true
        binding.textRegiao.isInvisible = true
        binding.textDdd.isInvisible = true

    }

    private fun exibirTexto(){

        //Elementos Estáticos
        binding.textEstaticoCep.isInvisible = false
        binding.textEstaticoLogradouro.isInvisible = false
        binding.textEstaticoBairro.isInvisible = false
        binding.textEstaticoLocalidade.isInvisible = false
        binding.textEstaticoUf.isInvisible = false
        binding.textEstaticoEstado.isInvisible = false
        binding.textEstaticoRegiao.isInvisible = false
        binding.textEstaticoDdd.isInvisible = false

        //Elementos resposta API
        binding.textCep.isInvisible = false
        binding.textLogradouro.isInvisible = false
        binding.textBairro.isInvisible = false
        binding.textLocalidade.isInvisible = false
        binding.textUf.isInvisible = false
        binding.textEstado.isInvisible = false
        binding.textRegiao.isInvisible = false
        binding.textDdd.isInvisible = false
    }
}