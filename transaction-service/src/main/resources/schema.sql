CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    voucher VARCHAR(11)  NOT NULL,
    type VARCHAR(10)  NOT NULL,
    source_account VARCHAR(10) NOT NULL,
    destination_account VARCHAR(10) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    date_time DATETIME NOT NULL
);