# QR Code Generator

Uma aplicação Spring Boot para gerar QR codes e salvá-los localmente ou no AWS S3.

<h1 align="center">
    <img src="./images/Qrcode.png" width="300"/>

</h1>

## Funcionalidades

- Geração de QR codes a partir de texto
- Armazenamento local de arquivos
- Suporte para AWS S3 (configurável)
- API REST para geração de QR codes
- Servidor de arquivos para acessar os QR codes gerados

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.5.3
- Google ZXing (para geração de QR codes)
- AWS SDK S3 (opcional)
- Maven

## Como Executar

### Pré-requisitos

- Java 21 ou superior
- Maven (ou use o wrapper incluído)

### Execução Local

1. Clone o repositório:
```bash
git clone https://github.com/XxRuanOliveiraxX/QrCode-Generator.git
cd qrcode.generator
```

2. Execute a aplicação:
```bash
# Usando Maven wrapper
./mvnw spring-boot:run

# Ou usando Maven instalado
mvn spring-boot:run
```

3. A aplicação estará disponível em `http://localhost:8080`

### Configuração

As configurações estão no arquivo `src/main/resources/application.properties`:

```properties
# Configuração do aplicativo
spring.application.name=qrcode.generator

# Configuração de armazenamento local
storage.local.path=./uploads
storage.local.base-url=http://localhost:8080/files

# Configuração AWS S3 (opcional)
aws.s3.bucket-name=your-bucket-name
aws.s3.region=us-east-1
aws.access-key-id=
aws.secret-access-key=
```

## Uso da API

### Gerar QR Code

**Endpoint:** `POST /qrcode`

**Corpo da requisição:**
```json
{
    "text": "Texto para gerar o QR code"
}
```

**Resposta:**
```json
{
    "url": "http://localhost:8080/files/arquivo-gerado.png"
}
```

**Exemplo usando curl:**
```bash
curl -X POST http://localhost:8080/qrcode \
  -H "Content-Type: application/json" \
  -d '{"text":"https://www.exemplo.com"}'
```

### Acessar QR Code Gerado

Os QR codes gerados podem ser acessados através da URL retornada na resposta da API.

**Endpoint:** `GET /files/{nome-do-arquivo}`

## Estrutura do Projeto

```
src/main/java/com/ruanoliveira/qrcode/generator/
├── Application.java                 # Classe principal
├── controller/
│   ├── QrCodeController.java       # Controlador da API
│   └── FileController.java         # Servidor de arquivos
├── service/
│   └── QrCodeGeneratorService.java # Serviço de geração
├── adapter/
│   ├── LocalStorageAdapter.java    # Armazenamento local
│   └── S3StorageAdapter.java       # Armazenamento S3
├── ports/
│   └── StoragePort.java           # Interface de armazenamento
├── dto/
│   ├── QrCodeGenerateRequest.java  # DTO de requisição
│   └── QrCodeGenerateResponse.java # DTO de resposta
└── config/
    └── S3Config.java              # Configuração AWS S3
```

## Configuração para Produção

### Usando AWS S3

1. Configure as credenciais AWS no `application.properties`:
```properties
aws.s3.bucket-name=seu-bucket
aws.s3.region=us-east-1
aws.access-key-id=sua-access-key
aws.secret-access-key=sua-secret-key
```

2. Descomente as anotações no `S3StorageAdapter.java` e `S3Config.java`

3. Comente a anotação `@Component` no `LocalStorageAdapter.java`

### Usando Armazenamento Local

1. Mantenha apenas o `LocalStorageAdapter` ativo
2. Configure o diretório de armazenamento no `application.properties`
3. Certifique-se de que o diretório tenha permissões de escrita

## Desenvolvimento

### Executar Testes

```bash
./mvnw test
```

### Compilar

```bash
./mvnw clean compile
```

### Criar JAR Executável

```bash
./mvnw clean package
```

O JAR será criado em `target/qrcode.generator-0.0.1-SNAPSHOT.jar`

## Licença

Este projeto está sob a licença MIT.
