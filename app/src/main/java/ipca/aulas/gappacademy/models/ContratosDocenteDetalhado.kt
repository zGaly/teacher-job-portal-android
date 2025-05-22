package ipca.aulas.gappacademy.models

data class ContratoDocenteDetalhado(
    val userId: String? = null,
    val docenteId: String? = null,
    val tipoContrato: String? = null,
    val horas: String? = null,
    val percentagem: String? = null,
    val anoLetivo: String? = null
)