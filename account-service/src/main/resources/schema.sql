CREATE TABLE IF NOT EXISTS accounts(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(10) UNIQUE NOT NULL,
    type VARCHAR(10)  NOT NULL,
    balance DECIMAL(10,2) NOT NULL,
    bank_id BIGINT NOT NULL,
    created_date DATETIME NOT NULL
);

-- Cuentas para el Banco 1
INSERT INTO accounts (number, type, balance, bank_id, created_date) VALUES ('111222333', 'SAVINGS', 1500.75, 1, NOW());
INSERT INTO accounts (number, type, balance, bank_id, created_date) VALUES ('444555666', 'CHECKING', 850.00, 1, NOW());
INSERT INTO accounts (number, type, balance, bank_id, created_date) VALUES ('777888999', 'SAVINGS', 12345.67, 1, NOW());

-- Cuentas para el Banco 2
INSERT INTO accounts (number, type, balance, bank_id, created_date) VALUES ('123123123', 'SAVINGS', 5000.00, 2, NOW());
INSERT INTO accounts (number, type, balance, bank_id, created_date) VALUES ('456456456', 'CHECKING', 250.50, 2, NOW());

-- Cuentas para el Banco 3
INSERT INTO accounts (number, type, balance, bank_id, created_date) VALUES ('987654321', 'SAVINGS', 9999.99, 3, NOW());
INSERT INTO accounts (number, type, balance, bank_id, created_date) VALUES ('101010101', 'CHECKING', 780.20, 3, NOW());

-- Cuentas para el Banco 4
INSERT INTO accounts (number, type, balance, bank_id, created_date) VALUES ('202020202', 'SAVINGS', 25000.00, 4, NOW());

-- Cuentas para el Banco 5
INSERT INTO accounts (number, type, balance, bank_id, created_date) VALUES ('303030303', 'CHECKING', 1200.00, 5, NOW());
INSERT INTO accounts (number, type, balance, bank_id, created_date) VALUES ('505050505', 'SAVINGS', 7500.45, 5, NOW());