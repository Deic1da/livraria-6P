CREATE TABLE LIVRO (
    id UUID PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    isbn VARCHAR(255) NOT NULL,
    ano_publicacao INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    autor_id UUID NOT NULL,
    FOREIGN KEY (autor_id) REFERENCES AUTOR(id),
    categoria_id UUID NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES CATEGORIA(id),
    editora_id UUID NOT NULL,
    FOREIGN KEY (editora_id) REFERENCES EDITORA(id)
)