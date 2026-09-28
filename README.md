# ScannerDv 🛡️

Um projeto desenvolvido em **Java** para estudo de Segurança da Informação, com foco em análise de arquivos por meio de **hash SHA-256** e consulta à API do **VirusTotal**.

O projeto foi desenvolvido como uma forma prática de estudar conceitos de segurança, manipulação de arquivos, APIs, JSON e desenvolvimento em Java.

## 📌 Sobre o projeto

O ScannerDv calcula o **SHA-256 de um arquivo** e utiliza o resultado para consultar informações sobre esse arquivo no VirusTotal.

Em vez de enviar o arquivo diretamente para análise, o projeto utiliza seu hash como identificador para consultar informações que já estejam disponíveis na plataforma.

### Fluxo do projeto

```text
Arquivo
   │
   ▼
Leitura do arquivo
   │
   ▼
Cálculo do SHA-256
   │
   ▼
Consulta à API do VirusTotal
   │
   ▼
Resposta JSON
   │
   ▼
Extração das informações
   │
   ▼
Resultado da análise
```

## 🚀 Tecnologias utilizadas

* **Java**
* **Maven**
* **Java HttpClient**
* **Jackson**
* **VirusTotal API v3**
* **SHA-256**
* **JSON**

## 🔐 SHA-256

O projeto utiliza o algoritmo **SHA-256** para gerar uma identificação única baseada no conteúdo do arquivo.

O resultado possui:

```text
256 bits
32 bytes
64 caracteres hexadecimais
```

Exemplo:

```text
275a021bbfb6489e54d471899f7db9d1663fc695ec2fe2a2c4538aabf651fd0f
```

O SHA-256 não determina sozinho se um arquivo é malicioso. Ele é utilizado pelo projeto como uma forma de identificar o arquivo e consultar informações relacionadas a ele no VirusTotal.

## 🌐 VirusTotal API

Depois de gerar o hash, o ScannerDv realiza uma requisição para a API do VirusTotal.

A resposta possui uma estrutura semelhante a:

```text
data
└── attributes
    ├── sha256
    ├── meaningful_name
    ├── names
    ├── size
    ├── type_description
    ├── last_analysis_stats
    └── last_analysis_results
```

O projeto utiliza o **Jackson** para interpretar essa resposta JSON.

## 📊 Resultado da análise

Uma das informações utilizadas pelo projeto é:

```text
last_analysis_stats
```

Ela contém as estatísticas das análises realizadas pelos mecanismos disponíveis no VirusTotal.

Exemplo:

```text
malicious: 0
suspicious: 0
undetected: 56
harmless: 0
timeout: 4
confirmed-timeout: 0
failure: 1
type-unsupported: 13
```

Esses valores permitem visualizar como os mecanismos de análise classificaram o arquivo.

### Principais categorias

| Categoria          | Descrição                                             |
| ------------------ | ----------------------------------------------------- |
| `malicious`        | Mecanismos que classificaram o arquivo como malicioso |
| `suspicious`       | Mecanismos que classificaram o arquivo como suspeito  |
| `undetected`       | Mecanismos que não detectaram uma ameaça              |
| `harmless`         | Classificação como inofensivo                         |
| `timeout`          | A análise não terminou dentro do tempo esperado       |
| `failure`          | Falha durante a análise                               |
| `type-unsupported` | O mecanismo não suporta aquele tipo de arquivo        |

> `undetected` não significa necessariamente que o arquivo foi comprovadamente considerado seguro. Significa que aquele mecanismo não detectou uma ameaça.

## 🧩 Estrutura do JSON

O projeto acessa os dados da API através do caminho:

```java
JsonNode stats = json
        .path("data")
        .path("attributes")
        .path("last_analysis_stats");
```



## 🔎 Informações obtidas

Entre as informações disponibilizadas pela resposta da API estão:

* SHA-256
* SHA-1
* MD5
* Nome significativo do arquivo
* Nomes associados ao arquivo
* Tamanho
* Tipo do arquivo
* Extensão
* Reputação
* Quantidade de submissões
* Data da primeira submissão
* Data da última submissão
* Estatísticas das análises
* Resultados individuais das análises
* Informações de sandbox

## 📦 Dependências

O projeto utiliza **Maven** para gerenciamento das dependências.

A biblioteca **Jackson** é utilizada para trabalhar com o JSON retornado pela API.

Exemplo de utilização:

```java
ObjectMapper mapper = new ObjectMapper();

JsonNode json = mapper.readTree(response.body());
```

## 🔑 Chave da API

Para realizar consultas à API do VirusTotal é necessária uma chave de API.

A chave **não deve ser colocada diretamente no código-fonte ou publicada no GitHub**.

Uma alternativa é utilizar uma variável de ambiente:

```text
VIRUSTOTAL_API_KEY
```

E recuperá-la no Java:

```java
String apiKey = System.getenv("VIRUSTOTAL_API_KEY");
```

## ⚠️ Limitações

Este projeto possui finalidade principalmente **educacional**.

O ScannerDv não deve ser considerado um antivírus completo.

Atualmente, o projeto depende das informações disponibilizadas pelo VirusTotal e utiliza o hash do arquivo para realizar a consulta.

Um arquivo que não possui detecções não deve ser interpretado automaticamente como absolutamente seguro.

## 🎯 Objetivos de aprendizagem

Este projeto foi desenvolvido para praticar:

* Programação Java
* Orientação a objetos
* Manipulação de arquivos
* Geração de hashes
* SHA-256
* Requisições HTTP
* Consumo de APIs REST
* Manipulação de JSON
* Uso do Jackson
* Maven
* Conceitos básicos de Segurança da Informação
* Análise de informações de arquivos

## 🔮 Possíveis melhorias

Algumas funcionalidades que podem ser adicionadas futuramente:

* [ ] Interface gráfica mais completa
* [ ] Melhor tratamento de erros da API
* [ ] Histórico das análises
* [ ] Análise de múltiplos arquivos
* [ ] Exibição gráfica das estatísticas
* [ ] Identificação mais detalhada do tipo de arquivo
* [ ] Sistema de quarentena
* [ ] Melhor organização das classes
* [ ] Testes automatizados
* [ ] Configuração mais segura da API Key

## 📚 Objetivo do projeto

O ScannerDv faz parte dos meus estudos em **Java e Segurança da Informação**.

A ideia principal é aprender, na prática, como diferentes conceitos se conectam:

```text
Java
  ↓
Arquivos
  ↓
Hash
  ↓
SHA-256
  ↓
HTTP
  ↓
API
  ↓
JSON
  ↓
Análise de segurança
```

---

**Projeto desenvolvido para fins educacionais e de aprendizado em desenvolvimento de software e Segurança da Informação.**
