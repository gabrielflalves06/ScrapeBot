# ScrapeBot

Este projeto realiza **web scraping** para extrair dados de um site e gerar automaticamente um arquivo **Excel (.xlsx)** com as informações coletadas.

---

## Tecnologias Utilizadas

- **Java** - Linguagem principal do projeto
- **Selenium** - Automação de navegação web
- **Microsoft Edge WebDriver** - Controle do navegador
- **Apache POI** - Geração do arquivo Excel

---

## Como Funciona?

1. O script abre o navegador Microsoft Edge usando Selenium.
2. Lê o arquivo `itens.txt` para buscar os produtos desejados.
3. Acessa a Amazon Brasil e pesquisa cada produto da lista.
4. Extrai informações dos produtos, incluindo nome e preço.
5. Organiza os dados e gera um arquivo Excel chamado `Produtos.xlsx`.

> **Nota:** Dependendo do site alvo, pode ser necessário ajustes.

---

## Como Instalar e Rodar o Projeto

1. **Clone este repositório**
   ```sh
   git clone https://github.com/seu-usuario/webscraping-xlsx.git
   ```
2. **Acesse a pasta do projeto**
   ```sh
   cd webscraping-xlsx
   ```
3. **Baixe e configure o Microsoft Edge WebDriver**
   - Faça o download do [Edge WebDriver](https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/) e coloque o executável na pasta `resources/`.
4. **Compile o projeto**
   ```sh
   javac -cp .:libs/* src/controller/WebScraping.java -d bin
   ```
5. **Crie o arquivo `itens.txt` com os produtos a serem pesquisados**
   - Cada linha do arquivo deve conter um nome de produto.
6. **Execute o script**
   ```sh
   java -cp bin:libs/* controller.WebScraping
   ```

O arquivo **Produtos.xlsx** será gerado na pasta do projeto.

---

