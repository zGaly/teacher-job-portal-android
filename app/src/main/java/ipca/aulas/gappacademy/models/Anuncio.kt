package ipca.aulas.gappacademy.models

data class Anuncio (
        val local: String? = null,
        val ativo: Boolean? = null,
        val uc: String? = null,
        val nomeCurso: String? = null,
        val regime: String? = null,
        val dataInicio: String? = null,
        val vaga: Boolean? = null,
        val anuncioId: String? = null
    )