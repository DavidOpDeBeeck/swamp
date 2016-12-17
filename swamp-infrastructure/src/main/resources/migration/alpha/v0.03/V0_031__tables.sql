-- -----------------------------------------------------
-- Table `server_view`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `server_view` (
  `id` varchar(255) NOT NULL,
  `ca_certificate` TEXT NOT NULL,
  `certificate` TEXT NOT NULL,
  `ip` varchar(255) NOT NULL,
  `key` TEXT NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE=InnoDB;


-- -----------------------------------------------------
-- Table `container_instance_view`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `container_instance_view` (
  `id` varchar(255) NOT NULL,
  `container_instance_id` varchar(255) NULL,
  `date_scheduled` datetime NULL,
  `date_created` datetime NULL,
  `date_removed` datetime NULL,
  `date_started` datetime NULL,
  `date_stopped` datetime NULL,
  `internal_container_id` varchar(255) NULL,
  `internal_container_name` varchar(255) NULL,
  `log` TEXT NULL,
  `status` varchar(255) NULL,
  `server_view_id` varchar(255) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_container_instance_view_server_view1_idx` (`server_view_id` ASC),
  CONSTRAINT `fk_container_instance_view_server_view1`
    FOREIGN KEY (`server_view_id`)
    REFERENCES `server_view` (`id`)
      ON DELETE CASCADE
      ON UPDATE NO ACTION )
ENGINE=InnoDB;

