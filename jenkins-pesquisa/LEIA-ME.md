# Jenkins + testes automatizados (projeto da pesquisa)

## Estrutura

```
jenkins-pesquisa/
├── docker-compose.yml      -> sobe o Jenkins
├── jenkins/Dockerfile      -> imagem oficial do Jenkins + Python
├── pipeline-local.groovy   -> pipeline que usa a pasta local (opção A)
└── projeto-exemplo/        -> calculadora em Python com 4 testes (pytest)
    ├── calculadora.py
    ├── test_calculadora.py
    ├── requirements.txt
    └── Jenkinsfile         -> pipeline que usa o GitHub (opção B)
```

## Pré-requisito

Docker Desktop instalado e aberto (https://www.docker.com/products/docker-desktop/).

## 1. Subir o Jenkins

Abra o terminal dentro da pasta `jenkins-pesquisa` e rode:

```
docker compose up -d --build
```

A primeira vez demora alguns minutos (download da imagem). Depois acesse http://localhost:8080.

## 2. Desbloquear

Pegue a senha inicial com:

```
docker compose exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

Escolha **Instalar plugins sugeridos** e crie o usuário administrador.
📸 Print: tela de desbloqueio e painel inicial.

## 3. Criar o pipeline

**Nova tarefa** -> dê um nome -> escolha **Pipeline** -> OK. No campo **Script**, cole:

- **Opção A (mais fácil, sem GitHub):** o conteúdo de `pipeline-local.groovy`.
- **Opção B (mais realista):** suba os arquivos de `projeto-exemplo` num repositório **público**
  do GitHub e cole o conteúdo de `projeto-exemplo/Jenkinsfile`, trocando `USUARIO`.

Salve. 📸 Print: configuração do job.

## 4. Rodar

Clique em **Construir agora**. O build deve ficar **verde** com 4 testes aprovados
(abra o build -> **Resultados de teste**). 📸 Print: execução e relatório.

## 5. Fazer um teste falhar (demonstração)

Em `calculadora.py`, troque `return a + b` por `return a - b`
(opção A: edite o arquivo na pasta do computador; opção B: edite no GitHub).
Rode o build de novo: ele fica **amarelo (instável)** e mostra qual teste falhou.
📸 Print: falha e gráfico de tendência. Depois desfaça a alteração e rode novamente.

## Comandos úteis

| O que fazer | Comando |
|---|---|
| Parar o Jenkins | `docker compose stop` |
| Ligar de novo | `docker compose start` |
| Ver os logs | `docker compose logs -f jenkins` |
| Apagar tudo (inclusive configurações) | `docker compose down -v` |

## Problemas comuns

- **Porta 8080 ocupada:** no `docker-compose.yml`, troque `"8080:8080"` por `"9090:8080"` e acesse http://localhost:9090.
- **python3: not found:** a imagem não foi construída com o Python; rode `docker compose up -d --build` de novo.
- **Erro no git (opção B):** o repositório está privado ou o usuário na URL está errado.
