package ipca.aulas.gappacademy.ui.homescreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ipca.aulas.gappacademy.R
import ipca.aulas.gappacademy.models.Article

class HomeViewModel : ViewModel() {

    val articles = mutableStateOf(
        listOf(
            Article(
                1,
                "Ministro visita IPCA",
                "Ministro da Educação visita IPCA e elogia infraestrutura.",
                R.drawable.article1.toString()
            ),
            Article(2,
                "Novo curso lançado",
                "IPCA lança novo curso de Engenharia de Software.",
                R.drawable.article2.toString()
            ),
            Article(3,
                "Conferência Internacional",
                "IPCA organiza conferência sobre IA.",
                R.drawable.article3.toString()
            ),
            Article(4,
                "IPCA atinge novo ranking",
                "IPCA é classificado entre as 10 melhores instituições do país.",
                R.drawable.article4.toString()
            ),
            Article(5,
                "Feira de emprego",
                "IPCA realiza feira de emprego com grandes empresas de tecnologia.",
                R.drawable.article5.toString()
            ),
            Article(6,
                "Projeto de sustentabilidade",
                "Estudantes do IPCA desenvolvem projeto sustentável.",
                R.drawable.article6.toString()
            ),
            Article(7,
                "Hackathon de inovação",
                "Hackathon promovido pelo IPCA atrai jovens programadores.",
                R.drawable.article7.toString()
            ),
            Article(8,
                "Parceria Internacional",
                "IPCA firma parceria com universidades europeias.",
                R.drawable.article8.toString()
            ),
            Article(9,
                "Semana Acadêmica",
                "IPCA realiza semana acadêmica com palestras e workshops.",
                R.drawable.article9.toString()

            ),
            Article(10,
                "Laboratório de IA",
                "Novo laboratório de Inteligência Artificial é inaugurado.",
                R.drawable.article10.toString()
            )
        )
    )
}