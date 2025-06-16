Sistema de Vendas de Veículos 
Sistema web para cadastro e gestão de vendas de veículos. Projeto desenvolvido com tecnologias modernas tanto no backend quanto no frontend , incluindo Java Spring Boot , React + Vite , e banco de dados rodando via Docker . 

Tecnologias Utilizadas 
Backend 
    Java 21
    Spring Boot 
    Spring Data JPA 
    Spring Web 
    Lombok 
    PostgreSQL
    Docker Compose  para subir o banco de dados localmente
Frontend 
    React.js 
    TypeScript 
    Vite 
    Tailwind CSS 
    ShadCN UI Components 
    
Estrutura do Projeto:
 
meu-projeto/
├── backend/          # Código-fonte do backend em Java Spring Boot
│   └── docker-compose.yml     # Arquivo para iniciar o banco de dados
├── frontend/         # Código-fonte do frontend em React + Vite
└── README.md         # Este arquivo
 
🔧 Como Iniciar o Projeto 
Passo 1: Clonar o repositório 

bash:
git clone https://github.com/seu-usuario/seu-repositorio.git 
cd seu-repositorio
 
 
Passo 2: Iniciar o Banco de Dados com Docker 
Na raiz da pasta backend, execute: 
bash
cd backend
docker-compose up -d / Isso iniciará o serviço do banco de dados PostgreSQL.
     
Verifique se os containers estão rodando: 
bash
docker ps
 
Passo 3: Iniciar o Backend (Java) 
Acesse a pasta do backend: 
bash
cd backend

Com IDE: 
Abra o projeto como projeto Maven.
Execute a classe principal (CarDealerApiApplication.java).
     
Com linha de comando: 
bash
./mvnw spring-boot:run
O backend será acessível em http://localhost:8080. 
     
Passo 4: Iniciar o Frontend (React + Node.js) 

Abra outro terminal e acesse a pasta do frontend: 
bash
cd frontend
 
Instale as dependências: 
bash
npm install
 
Inicie o servidor de desenvolvimento: 
bash
npm run dev
O frontend será acessível em http://localhost:3000. 
     

Testando a Aplicação 
Após iniciar todos os serviços: 
Acesse o sistema via navegador: http://localhost:3000 
Registre clientes, veículos e vendedores
Realize o cadastro de vendas com parcelamento e preço personalizado
     

Observações Importantes 
Certifique-se de ter o Docker  e o Docker Compose  instalados antes de iniciar o backend.
Verifique a URL do backend no frontend (geralmente em src/services/api.ts ou similar).
Se estiver usando variáveis de ambiente, configure-as corretamente no arquivo .env do frontend (VITE_API_URL=http://localhost:8080/api).
O arquivo docker-compose.yml deve conter a configuração correta do banco de dados usado (ex: PostgreSQL ou MySQL).
     

Suporte 
Se tiver dúvidas ou encontrar problemas, abra uma issue no repositório ou entre em contato! 
