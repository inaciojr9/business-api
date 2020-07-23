CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `perfil` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ind_usuario_email` (`email`);


-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--



INSERT INTO `usuario` (`email`, `perfil`, `senha`) 
VALUES ('inaciojr9@gmail.com', 'ROLE_ADMIN', '$2a$10$BRuBVxg6ErrLYu9.AoVIEuBQyc5m4H0QoOnwkQIITEqlbzahUmHkK');

