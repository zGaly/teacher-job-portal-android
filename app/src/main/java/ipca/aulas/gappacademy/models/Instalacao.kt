package ipca.aulas.gappacademy.models

data class Instalacao(
    var docId: String? = null,
    var name: String? = null,
    val address: String? = null,
    val local: String? = null,
    val zip: String? = null,
    var imgUrl: String? = null
) {
}