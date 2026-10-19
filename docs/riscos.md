# Registro de riscos

| # | Risco | Chance (baixa/média/alta) | Impacto (baixo/médio/alto) | O que faremos |
|---|---|---|---|---|
| 1 | A migração de arquivos de texto (.txt) para um banco de dados real pode consumir mais tempo do que o previsto. | Alta | Alto | Fazer a migração como uma história isolada na Iteração 1, testando-a separadamente antes de adicionar outras funcionalidades. |
| 2 | As senhas atualmente são geradas e exibidas em texto puro, sem criptografia, o que pode gerar um problema de segurança e ser cobrado na avaliação do sistema. | Alta | Alto | Implementar hash de senha já na história de login e cadastro da API. |
| 3 | O método `enviarDemanda()` pode lançar uma exceção quando não existe funcionário do cargo escolhido (`random.nextInt(0)`). | Média | Médio | Corrigir a validação antes de disponibilizar o método como endpoint da API. |
| 4 | Há falta de validação de campos obrigatórios, inclusive com a existência de um funcionário salvo com o nome em branco nos dados de teste. | Média | Médio | Adicionar validações de entrada na API para campos obrigatórios, como nome, telefone, cargo, entre outros. |
| 5 | O sistema não mantém histórico das demandas, pois a opção de concluir atualmente apaga a demanda de forma definitiva e não existe um campo de status. | Média | Médio | Definir com a equipe se será utilizado um campo de status (pendente/concluída) em vez da remoção definitiva da demanda. |
| 6 | Nenhum integrante domina completamente o framework web escolhido, APIs ou programação web de forma geral. | Alta | Alto | Escolher uma tecnologia já estudada em Programação Web, estudar os fundamentos de API REST e programação web e realizar um tutorial/spike de um dia na Iteração 1, dividindo o estudo entre os integrantes e compartilhando os conhecimentos adquiridos. |
| 7 | Existe o risco de não conseguir publicar a aplicação (API + interface) em um serviço de hospedagem gratuito. | Média | Alto | Testar a publicação já na Iteração 1, utilizando inicialmente uma tela simples para validar o processo antecipadamente. |
