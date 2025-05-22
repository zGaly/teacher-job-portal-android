package ipca.aulas.gappacademy.ui.homescreen.articles.articleDetail

import androidx.lifecycle.ViewModel
import ipca.aulas.gappacademy.R
import ipca.aulas.gappacademy.models.Article

class ArticleDetailViewModel : ViewModel() {

    private val articles = listOf(
        Article(
            1,
            "Ministro visita IPCA",
            "O Ministro da Educação visitou o IPCA para debater inovação e desenvolvimento de novas áreas de ensino. A visita incluiu reuniões com alunos e professores, onde foram discutidos temas como a inclusão de novas tecnologias no ensino superior e o impacto da digitalização no mercado de trabalho. Durante a visita, o ministro teve a oportunidade de conhecer alguns dos projetos desenvolvidos pelos alunos do IPCA, como aplicações inovadoras e soluções de sustentabilidade. O evento foi amplamente elogiado pela comunidade académica pela sua relevância e impacto positivo.",
            R.drawable.article1.toString()
        ),
        Article(
            2,
            "Novo curso lançado",
            "O IPCA lançou um novo curso de Engenharia de Software com foco em desenvolvimento ágil e tecnologias emergentes. Este curso visa formar profissionais altamente qualificados para o mercado de trabalho, com competências em programação, análise de sistemas e gestão de projetos. A formação inclui módulos sobre inteligência artificial, desenvolvimento de aplicações móveis e soluções baseadas na cloud. Além disso, o curso conta com parcerias estratégicas com empresas do setor tecnológico, oferecendo aos estudantes oportunidades únicas de estágios e networking.",
            R.drawable.article2.toString()
        ),
        Article(
            3,
            "Conferência Internacional",
            "A conferência sobre Inteligência Artificial no IPCA atraiu especialistas de diversos países para discutir o futuro da tecnologia. O evento contou com palestras de renome internacional, workshops práticos e painéis de discussão sobre temas como machine learning, ética em IA e automação. Estudantes e professores tiveram a oportunidade de apresentar os seus projetos, mostrando o que de melhor se faz no IPCA nesta área. A conferência reforçou a posição do IPCA como uma instituição líder em investigação e inovação tecnológica.",
            R.drawable.article3.toString()
        ),
        Article(
            4,
            "IPCA atinge novo ranking",
            "O IPCA foi classificado entre as 10 melhores instituições de ensino superior em Portugal. Este reconhecimento é fruto de anos de dedicação à qualidade de ensino, investigação e inovação. O ranking destacou a elevada taxa de empregabilidade dos diplomados do IPCA, bem como os investimentos em infraestruturas modernas e na formação de docentes. Este feito posiciona o IPCA como uma escolha de excelência para os estudantes que procuram uma educação superior de qualidade e alinhada às exigências do mercado.",
            R.drawable.article4.toString()
        ),
        Article(
            5,
            "Feira de emprego",
            "A feira de emprego do IPCA contou com a participação de grandes empresas de tecnologia, oferecendo oportunidades de estágio e emprego. Durante o evento, os estudantes puderam interagir diretamente com representantes das empresas, apresentar os seus currículos e participar em entrevistas de recrutamento. Além disso, foram organizados workshops sobre preparação para o mercado de trabalho, dicas para entrevistas e construção de uma carreira de sucesso. O evento reforça o compromisso do IPCA em apoiar os seus estudantes na transição para o mundo profissional.",
            R.drawable.article5.toString()
        ),
        Article(
            6,
            "Projeto de sustentabilidade",
            "Estudantes do IPCA apresentaram um projeto inovador que promove práticas de sustentabilidade em parceria com empresas locais. O projeto, focado na redução de desperdício e na reutilização de materiais, foi amplamente elogiado pela sua criatividade e impacto ambiental. As empresas parceiras destacaram o potencial da iniciativa para transformar práticas industriais, incentivando a responsabilidade social e ambiental. Este é mais um exemplo do compromisso do IPCA em formar profissionais conscientes e preparados para os desafios globais.",
            R.drawable.article6.toString()
        ),
        Article(
            7,
            "Hackathon de inovação",
            "O Hackathon promovido pelo IPCA atraiu jovens programadores que desenvolveram soluções inovadoras durante um fim de semana. Equipas de estudantes trabalharam intensamente em projetos que abordaram desafios reais, como soluções para mobilidade urbana e otimização de recursos energéticos. O evento contou com mentores experientes, que ofereceram orientação e feedback aos participantes. No final, os melhores projetos foram premiados, e alguns já despertaram o interesse de empresas parceiras para implementação no mercado.",
            R.drawable.article7.toString()
        ),
        Article(
            8,
            "Parceria Internacional",
            "O IPCA firmou parceria com universidades europeias, permitindo intercâmbio de estudantes e projetos de pesquisa conjuntos. Esta colaboração abre portas para os alunos adquirirem experiência internacional e expandirem as suas competências académicas e culturais. Além disso, os projetos de pesquisa em conjunto irão focar-se em temas como sustentabilidade, inovação tecnológica e desenvolvimento social. Esta parceria reforça o compromisso do IPCA com a internacionalização e a qualidade da formação dos seus estudantes.",
            R.drawable.article8.toString()
        ),
        Article(
            9,
            "Semana Académica",
            "Durante a semana académica, o IPCA organizou palestras, workshops e painéis com convidados renomados do setor tecnológico. O evento proporcionou aos estudantes a oportunidade de aprender com profissionais de destaque, ampliar os seus conhecimentos e estabelecer conexões valiosas. A semana académica também incluiu atividades culturais, desportivas e momentos de lazer, promovendo o espírito de comunidade e integração entre alunos de diferentes cursos. Foi um verdadeiro sucesso de organização e participação.",
            R.drawable.article9.toString()
        ),
        Article(
            10,
            "Laboratório de IA",
            "O IPCA inaugurou um novo laboratório de Inteligência Artificial, equipado com as mais recentes tecnologias para pesquisa e desenvolvimento. O espaço inclui computadores de alta performance, acesso a grandes volumes de dados e ferramentas de machine learning. Este laboratório será um centro de investigação, onde estudantes e professores poderão colaborar em projetos inovadores e impactantes. A iniciativa posiciona o IPCA na vanguarda da investigação em IA, destacando-se como um polo de inovação em Portugal.",
            R.drawable.article10.toString()
        )
    )

    fun getArticleById(id: Int): Article? {
        return articles.find { it.articleId == id }
    }
}