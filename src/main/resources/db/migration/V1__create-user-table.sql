CREATE TABLE usuario_login (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   email VARCHAR(255) NOT NULL,
   nickname VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   data_cadastro DATE NOT NULL,
   data_desativacao DATE,
   status VARCHAR(255) NOT NULL,
   moderador BOOLEAN NOT NULL
);
