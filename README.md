# ScannerDv — Scanner de Arquivos em Java

O **ScannerDv** é um projeto desenvolvido em Java com o objetivo de analisar arquivos em um diretório e identificar possíveis ameaças digitais utilizando a API do VirusTotal.

O sistema permite informar uma pasta para varredura, calcular o hash SHA-256 dos arquivos, consultar informações sobre possíveis ameaças e mover arquivos identificados como maliciosos para uma pasta de quarentena.

O projeto tem como objetivo aplicar conceitos de programação Java, manipulação de arquivos, integração com APIs e fundamentos de segurança da informação.

## 🚀 Funcionalidades

- **Varredura de diretórios:** lista e processa os arquivos encontrados na pasta informada.
- **Cálculo de hash SHA-256:** gera uma impressão digital do conteúdo de cada arquivo analisado.
- **Análise de arquivos:** integra-se à API do VirusTotal para verificar possíveis ameaças.
- **Identificação de ameaças:** verifica a categoria `malicious` retornada pela análise.
- **Quarentena de arquivos:** move arquivos identificados como maliciosos para uma pasta chamada `Quarentena`.
- **Nova varredura:** permite iniciar uma nova análise sem precisar executar manualmente o programa novamente.
- **Interface via terminal:** interação com o usuário por meio do console.

## 🛠️ Tecnologias utilizadas

- Java
- Maven
- API do VirusTotal
- Apache Commons Codec — cálculo do hash SHA-256
- Java NIO (`java.nio.file`)
- Streams (`java.util.stream`)
- Manipulação de arquivos e diretórios

## 📋 Pré-requisitos

Antes de executar o projeto, você precisará de:

- JDK instalado e configurado.
- Apache Maven instalado.
- Chave de API do VirusTotal.
- Conexão com a internet para realizar as consultas à API.

## ⚙️ Configuração

1. Clone o repositório:

   ```bash
   git clone https://github.com/Gabriel-JesusS/ScannerDv.git
   ```

2. Acesse a pasta do projeto:

   ```bash
   cd ScannerDv
   ```

3. Configure sua chave de API do VirusTotal no arquivo de configuração utilizado pela classe `ApiVirusTotal`, conforme a implementação do projeto.

   **Importante:** não publique sua chave de API no GitHub. Utilize um arquivo local de configuração e adicione-o ao `.gitignore`.

## ▶️ Como executar

Na pasta raiz do projeto, execute:

```bash
mvn compile exec:java '-Dexec.mainClass=resources.desenvolvimento.ScannerDvTerminal'
```

O programa solicitará o caminho do diretório que deseja analisar.

Exemplo:

```text
digite o nome da pasta:
C:\Users\Usuario\Downloads
```

Após informar o diretório, o ScannerDv começará a processar os arquivos encontrados.

> O comando considera que o projeto possui o plugin Maven Exec configurado no `pom.xml` e que a classe principal está no pacote `resources.desenvolvimento`.

## 🔍 Como funciona

O fluxo principal do programa é dividido em etapas:

1. **Entrada do diretório:** o usuário informa o caminho da pasta que deseja verificar.
2. **Listagem dos arquivos:** o método `scanningDirector()` utiliza `Files.list()` para listar os arquivos diretamente no diretório.
3. **Cálculo do hash:** o programa utiliza o algoritmo SHA-256 para gerar uma identificação digital do conteúdo de cada arquivo, utilizando `DigestUtils.sha256Hex()` da biblioteca Apache Commons Codec.
4. **Consulta e análise:** cada arquivo é encaminhado para a classe `ApiVirusTotal`, responsável pela integração com o serviço de análise.
5. **Verificação do resultado:** o método `VerificFile()` verifica se a categoria recebida é `malicious` e se o valor é maior ou igual a 1.
6. **Quarentena:** caso seja identificada uma ameaça, o método `MoveFileQuaretena()` cria a pasta de quarentena, se necessário, e move o arquivo.
7. **Nova varredura:** ao final do processamento, o programa pergunta se o usuário deseja realizar outra análise.

## 🔐 Hash SHA-256

O ScannerDv utiliza o algoritmo criptográfico SHA-256 para calcular o hash dos arquivos analisados. O resultado é uma sequência hexadecimal de 64 caracteres que funciona como uma impressão digital do conteúdo do arquivo.

O cálculo é realizado por meio de `DigestUtils.sha256Hex()`, utilizando um `InputStream` para ler o arquivo.

O hash pode ser utilizado para consultar informações sobre um arquivo no VirusTotal, inclusive para verificar se já existe uma análise associada àquele identificador, dependendo do fluxo implementado na classe `ApiVirusTotal`.

O uso de hashes contribui para:

- **Identificação de arquivos:** permite comparar arquivos pelo conteúdo.
- **Consulta de ameaças conhecidas:** possibilita pesquisar informações associadas ao hash no VirusTotal.
- **Verificação de integridade:** permite identificar alterações no conteúdo de um arquivo por meio da comparação de hashes.

> O hash não determina, por si só, se um arquivo é malicioso. A identificação de ameaças depende dos resultados da análise de segurança. Além disso, arquivos com conteúdos diferentes podem, em teoria, produzir o mesmo hash, embora colisões no SHA-256 sejam extremamente improváveis.

## 📁 Estrutura do projeto

A estrutura simplificada das classes envolvidas é:

```text
ScannerDv/
├── pom.xml
├── src/
│   └── main/
│       └── java/
│           └── resources/
│               └── desenvolvimento/
│                   ├── ScannerDvTerminal.java
│                   ├── ApiVirusTotal.java
│                   └── ApiFileEntry.java
└── README.md
```

*Observação: a estrutura acima é ilustrativa e pode variar conforme a organização atual do repositório.*

## 🔒 Segurança e limitações

- A análise depende da disponibilidade da API do VirusTotal e dos limites de utilização da chave configurada.
- O código apresentado lista apenas os arquivos diretamente dentro do diretório informado, sem percorrer automaticamente as subpastas.
- O resultado `malicious` é utilizado como critério para acionar a quarentena.
- A quarentena implementada move os arquivos para outro diretório; ela não realiza uma desinfecção do arquivo.
- O cálculo do hash identifica o conteúdo do arquivo, mas não substitui a análise de segurança.
- Antes de testar o programa, utilize arquivos de teste e diretórios controlados. Evite analisar pastas importantes sem verificar o comportamento da movimentação dos arquivos.

## 🎯 Objetivo do projeto

Este projeto faz parte dos meus estudos em **Java e Segurança da Informação**, com foco no desenvolvimento de ferramentas para análise de arquivos, integração com serviços externos e automação de tarefas relacionadas à segurança digital.

Durante o desenvolvimento, são praticados conceitos como orientação a objetos, tratamento de exceções, manipulação de caminhos, processamento de arquivos, cálculo de hashes criptográficos e consumo de APIs.

## 📌 Próximas melhorias

- Implementar a varredura recursiva de subdiretórios.
- Criar relatórios com os resultados das análises.
- Melhorar o tratamento de erros e arquivos inacessíveis.
- Implementar uma interface gráfica.
- Aprimorar o gerenciamento e a segurança da quarentena.
- Adicionar testes automatizados.

## 👨‍💻 Autor

**Gabriel de Jesus Silva**

GitHub: [@Gabriel-JesusS](https://github.com/Gabriel-JesusS)

Projeto desenvolvido para fins educacionais e de aprendizado em programação Java e segurança da informação.
