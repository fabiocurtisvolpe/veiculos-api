INSERT INTO usuarios (email, senha, role, ativo)
VALUES ('admin@tinnova.com',
        '$2a$10$rpktpIziJk6myNKgnHJpY.DSZ4F/w6QZCWbUYxJgWE6FSClbW7bWq', -- admin123
        'ROLE_ADMIN',
        TRUE),
       ('user@tinnova.com',
        '$2a$10$fM.QBx4aLQn2ZgMDAUVJFus4rnMUx8rVCymbRty9dNAN5QtKMUi8y', -- user123
        'ROLE_USER',
        TRUE);