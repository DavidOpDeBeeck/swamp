-- -----------------------------------------------------
-- Table `project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project` (
  `id` VARCHAR(255) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` LONGTEXT NOT NULL,
  `created` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `run_configuration`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `run_configuration` (
  `id` VARCHAR(255) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `container`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `container` (
  `id` VARCHAR(255) NOT NULL,
  `run_configuration_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_container_run_configuration1_idx` (`run_configuration_id` ASC),
  CONSTRAINT `fk_container_run_configuration1`
    FOREIGN KEY (`run_configuration_id`)
    REFERENCES `run_configuration` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `location` (
  `id` VARCHAR(255) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `container_location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `container_location` (
  `container_id` VARCHAR(255) NOT NULL,
  `location_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`container_id`, `location_id`),
  INDEX `fk_container_has_location_location1_idx` (`location_id` ASC),
  INDEX `fk_container_has_location_container1_idx` (`container_id` ASC),
  CONSTRAINT `fk_container_has_location_container1`
    FOREIGN KEY (`container_id`)
    REFERENCES `container` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_container_has_location_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `location` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `continent`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `continent` (
  `id` VARCHAR(255) NOT NULL,
  INDEX `fk_continent_location1_idx` (`id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_continent_location1`
    FOREIGN KEY (`id`)
    REFERENCES `location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `datacenter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `datacenter` (
  `id` VARCHAR(255) NOT NULL,
  INDEX `fk_datacenter_location1_idx` (`id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_datacenter_location1`
    FOREIGN KEY (`id`)
    REFERENCES `location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `server`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `server` (
  `id` VARCHAR(255) NOT NULL,
  `ip` VARCHAR(45) NOT NULL,
  `CACertificate` TEXT NULL,
  `certificate` TEXT NULL,
  `key` TEXT NULL,
  INDEX `fk_server_location1_idx` (`id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_server_location1`
    FOREIGN KEY (`id`)
    REFERENCES `location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `image_configuration`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `image_configuration` (
  `id` VARCHAR(255) NOT NULL,
  `name` VARCHAR(45) NULL,
  INDEX `fk_image_configuration_run_configuration1_idx` (`id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_image_configuration_run_configuration1`
    FOREIGN KEY (`id`)
    REFERENCES `run_configuration` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `project_container`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_container` (
  `project_id` VARCHAR(255) NOT NULL,
  `container_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`project_id`, `container_id`),
  INDEX `fk_project_has_container_container1_idx` (`container_id` ASC),
  INDEX `fk_project_has_container_project1_idx` (`project_id` ASC),
  CONSTRAINT `fk_project_has_container_project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_project_has_container_container1`
    FOREIGN KEY (`container_id`)
    REFERENCES `container` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `continent_datacenter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `continent_datacenter` (
  `continent_id` VARCHAR(255) NOT NULL,
  `datacenter_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`continent_id`, `datacenter_id`),
  INDEX `fk_continent_has_datacenter_datacenter1_idx` (`datacenter_id` ASC),
  INDEX `fk_continent_has_datacenter_continent1_idx` (`continent_id` ASC),
  CONSTRAINT `fk_continent_has_datacenter_continent1`
    FOREIGN KEY (`continent_id`)
    REFERENCES `continent` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_continent_has_datacenter_datacenter1`
    FOREIGN KEY (`datacenter_id`)
    REFERENCES `datacenter` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `datacenter_server`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `datacenter_server` (
  `datacenter_id` VARCHAR(255) NOT NULL,
  `server_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`datacenter_id`, `server_id`),
  INDEX `fk_datacenter_has_server_server1_idx` (`server_id` ASC),
  INDEX `fk_datacenter_has_server_datacenter1_idx` (`datacenter_id` ASC),
  CONSTRAINT `fk_datacenter_has_server_datacenter1`
    FOREIGN KEY (`datacenter_id`)
    REFERENCES `datacenter` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_datacenter_has_server_server1`
    FOREIGN KEY (`server_id`)
    REFERENCES `server` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;