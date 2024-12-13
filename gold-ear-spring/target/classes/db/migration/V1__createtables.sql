CREATE TABLE disco (
                       id_disco SERIAL PRIMARY KEY,
                       uuid UUID DEFAULT gen_random_uuid(),
                       nome VARCHAR(80) NOT NULL,
                       ano_lancamento INTEGER,
                       capa VARCHAR(300)
);

-- Tabela permissao
CREATE TABLE permissao (
                           id_permissao INTEGER PRIMARY KEY,
                           uuid UUID DEFAULT gen_random_uuid(),
                           nome VARCHAR(50) NOT NULL
);

-- Tabela usuario
CREATE TABLE usuario (
                         id_usuario SERIAL PRIMARY KEY,
                         uuid UUID DEFAULT gen_random_uuid(),
                         nome VARCHAR(50) NOT NULL,
                         cpf VARCHAR(11) UNIQUE NOT NULL,
                         senha VARCHAR(50) NOT NULL,
                         email VARCHAR(50) UNIQUE NOT NULL,
                         id_permissao INTEGER,
                         CONSTRAINT fk_permissao FOREIGN KEY (id_permissao) REFERENCES permissao(id_permissao)
);

-- Tabela membro
CREATE TABLE membro (
                        id_membro INTEGER PRIMARY KEY,
                        CONSTRAINT fk_usuario_membro FOREIGN KEY (id_membro) REFERENCES usuario(id_usuario)
);

-- Tabela funcionario
CREATE TABLE funcionario (
                             id_funcionario INTEGER PRIMARY KEY,
                             CONSTRAINT fk_usuario_funcionario FOREIGN KEY (id_funcionario) REFERENCES usuario(id_usuario)
);

CREATE TABLE artista (
                         id_artista SERIAL PRIMARY KEY,
                         uuid UUID DEFAULT gen_random_uuid(),
                         nome VARCHAR(80) NOT NULL
);

CREATE TABLE artista_disco (
                               artista_id INTEGER NOT NULL,
                               disco_id INTEGER NOT NULL,
                               PRIMARY KEY (artista_id, disco_id),
                               CONSTRAINT fk_artista FOREIGN KEY (artista_id) REFERENCES artista(id_artista),
                               CONSTRAINT fk_disco FOREIGN KEY (disco_id) REFERENCES disco(id_disco)
);