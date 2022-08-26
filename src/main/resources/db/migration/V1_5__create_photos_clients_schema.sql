CREATE TABLE IF NOT EXISTS `photos_clients`
(
    `clients_id` bigint not null,
    `photos`      varchar(255)
)
    ENGINE = MyISAM
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_general_ci;
