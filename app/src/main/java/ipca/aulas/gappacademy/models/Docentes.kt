package ipca.aulas.gappacademy.models

data class Docentes(
    var id: String? = null,
    var nome: String? = null,
    var email: String? = null,
    var telefone: String? = null,
    val isAdmin: Boolean? = null,
    var fotoPerfil: String? = null,
    var grauAcademico: String? = null,
    var localidade: String? = null,
    var areasDisciplinares: List<String>? = null,
    var areasFormacao: List<String>? = null
)