create table demanda(
    idDemanda varchar(255) not null primary key,
    titulo varchar(100) not null,
    cargo_responsavel varchar(100) not null,
    descricao varchar(500)
);

create table usuario(
    idUsuario varchar(200) not null primary key,
    senhaUsuario VARCHAR(255) NOT NULL,
    emailUsuario varchar(100) UNIQUE not null,
    nomeUsuario varchar(60) not null,
    idadeUsuario INT NOT NULL,
    cargoUsuario varchar(50) not null,
    telefoneUsuario varchar(15) not null
);
