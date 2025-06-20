# 🚗 Sistema de Vendas de Veículos

Sistema web completo para cadastro e gestão de vendas de veículos, desenvolvido com tecnologias modernas e arquitetura full-stack.

## 📋 Sobre o Projeto

Esta aplicação permite o gerenciamento completo de uma concessionária, incluindo:
- 👥 Cadastro de clientes e vendedores
- 🚙 Gestão de veículos em estoque
- 💰 Controle de vendas com sistema de parcelamento
- 📊 Relatórios e dashboard de vendas

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 21** - Linguagem principal
- **Spring Boot** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Web** - APIs REST
- **Lombok** - Redução de código boilerplate
- **PostgreSQL** - Banco de dados
- **Docker Compose** - Containerização do banco

### Frontend
- **React.js** - Biblioteca JavaScript
- **TypeScript** - Tipagem estática
- **Vite** - Build tool e dev server
- **Tailwind CSS** - Framework CSS
- **ShadCN UI** - Componentes de interface

## 📁 Estrutura do Projeto

```
meu-projeto/
├── backend/                    # Código-fonte do backend
│   ├── src/
│   ├── docker-compose.yml      # Configuração do banco de dados
│   └── build.gradle
├── frontend/                   # Código-fonte do frontend  
│   ├── src/
│   ├── package.json
│   └── vite.config.ts
└── README.md
```

## 🚀 Como Executar o Projeto

### Pré-requisitos
- ☕ Java 21+
- 🐳 Docker e Docker Compose
- 📦 Node.js 18+
- 🔧 Gradle (ou usar o wrapper incluído)

### 1️⃣ Clone o Repositório
```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```

### 2️⃣ Inicialize o Banco de Dados
```bash
cd backend
docker-compose up -d
```

Verifique se o container está rodando:
```bash
docker ps
```

### 3️⃣ Execute o Backend
**Opção A - Via IDE:**
1. Importe como projeto Gradle
2. Execute `CarDealerApiApplication.java`

**Opção B - Via Terminal:**
```bash
cd backend
./gradlew bootRun
```

✅ Backend disponível em: `http://localhost:8080`

### 4️⃣ Execute o Frontend
Abra um novo terminal:
```bash
cd frontend
npm install
npm run dev
```

✅ Frontend disponível em: `http://localhost:3000`

## 🎯 Testando a Aplicação

1. **Acesse** `http://localhost:3000`
2. **Cadastre** clientes, veículos e vendedores
3. **Registre** vendas com parcelamento personalizado
4. **Visualize** relatórios e dashboard

## ⚙️ Configurações

### Variáveis de Ambiente (Frontend)
Crie um arquivo `.env` na pasta `frontend`:
```env
VITE_API_URL=http://localhost:8080/api
```

### Banco de Dados
O PostgreSQL será configurado automaticamente via Docker com:
- **Host:** localhost:5432
- **Database:** car_dealer
- **User/Password:** Definidos no `docker-compose.yml`

## 🐛 Solução de Problemas

### Backend não conecta ao banco
```bash
# Verifique se o container está rodando
docker ps

# Reinicie o banco se necessário
docker-compose down
docker-compose up -d
```

### Frontend não encontra a API
- Verifique se o backend está rodando na porta 8080
- Confirme a variável `VITE_API_URL` no arquivo `.env`

### Erro de CORS
- O backend já possui configuração CORS para desenvolvimento
- Verifique se as URLs estão corretas

## 📚 Recursos Adicionais

- 📖 [Documentação Spring Boot](https://spring.io/projects/spring-boot)
- ⚛️ [Documentação React](https://react.dev/)
- 🎨 [Tailwind CSS](https://tailwindcss.com/)
- 🧩 [ShadCN UI](https://ui.shadcn.com/)

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request


---

⭐ **Gostou do projeto? Deixe uma estrela!**
