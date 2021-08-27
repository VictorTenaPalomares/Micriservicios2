INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('victor', '$2a$10$gSlHbFjQ680f/DfhMOClE.c6gUGSsOl20aXtC0f8pNacUpwRptbTa', 1, 'Victor', 'Tena', 'victor.tena@viewnext.com');
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('admin', '$2a$10$Wgmo3108vVUmmu93v6m.ROxXuM9jyQBtDNB2nqwivWeixXV69k7j2', 1, 'John', 'Doe', 'john.doe@viewnext.com');

INSERT INTO `roles` (nombre) VALUES ('ROLE_USER');
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1,1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2,2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2,1); 

