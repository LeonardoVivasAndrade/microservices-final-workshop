CREATE TABLE IF NOT EXISTS banks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(200) NOT NULL
);

INSERT INTO banks (name, description) VALUES ('Banco de la Nación', 'El banco principal del país, enfocado en servicios gubernamentales y al ciudadano.');
INSERT INTO banks (name, description) VALUES ('Banco Comercial del Pacífico', 'Líder en créditos de consumo y tarjetas de crédito en la región.');
INSERT INTO banks (name, description) VALUES ('Banco de Fomento Agrario', 'Especializado en ofrecer financiamiento y apoyo técnico al sector agrícola.');
INSERT INTO banks (name, description) VALUES ('Inversiones Futuras Bank', 'Banco de inversión centrado en mercados de capitales y gestión de patrimonios.');
INSERT INTO banks (name, description) VALUES ('NeoFintech Digital', 'Banco 100% digital que opera a través de una aplicación móvil, sin sucursales físicas.');
