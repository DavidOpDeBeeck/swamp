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
  `name` VARCHAR(255) NULL,
  `project_id` VARCHAR(255) NULL,
  `run_configuration_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_container_run_configuration1_idx` (`run_configuration_id` ASC),
  INDEX `fk_container_project1_idx` (`project_id` ASC),
  CONSTRAINT `fk_container_run_configuration1`
    FOREIGN KEY (`run_configuration_id`)
    REFERENCES `run_configuration` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_container_project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `project` (`id`)
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
    ON DELETE NO ACTION
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
  `continent_id` VARCHAR(255) NULL,
  INDEX `fk_datacenter_location1_idx` (`id` ASC),
  PRIMARY KEY (`id`),
  INDEX `fk_datacenter_continent1_idx` (`continent_id` ASC),
  CONSTRAINT `fk_datacenter_location1`
    FOREIGN KEY (`id`)
    REFERENCES `location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_datacenter_continent1`
    FOREIGN KEY (`continent_id`)
    REFERENCES `continent` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `server`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `server` (
  `id` VARCHAR(255) NOT NULL,
  `ip` VARCHAR(45) NOT NULL,
  `ca_certificate` TEXT NOT NULL,
  `certificate` TEXT NOT NULL,
  `key` TEXT NOT NULL,
  `datacenter_id` VARCHAR(255) NULL,
  INDEX `fk_server_location1_idx` (`id` ASC),
  PRIMARY KEY (`id`),
  INDEX `fk_server_datacenter1_idx` (`datacenter_id` ASC),
  CONSTRAINT `fk_server_location1`
    FOREIGN KEY (`id`)
    REFERENCES `location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_server_datacenter1`
    FOREIGN KEY (`datacenter_id`)
    REFERENCES `datacenter` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `image_configuration`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `image_configuration` (
  `id` VARCHAR(255) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  INDEX `fk_image_configuration_run_configuration1_idx` (`id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_image_configuration_run_configuration1`
    FOREIGN KEY (`id`)
    REFERENCES `run_configuration` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `git_configuration`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `git_configuration` (
  `id` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `branch` VARCHAR(45) NOT NULL,
  `path` VARCHAR(45) NOT NULL,
  INDEX `fk_image_configuration_run_configuration1_idx` (`id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_image_configuration_run_configuration10`
    FOREIGN KEY (`id`)
    REFERENCES `run_configuration` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dockerfile_configuration`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dockerfile_configuration` (
  `id` VARCHAR(255) NOT NULL,
  `dockerfile` TEXT NOT NULL,
  INDEX `fk_image_configuration_run_configuration1_idx` (`id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_image_configuration_run_configuration11`
    FOREIGN KEY (`id`)
    REFERENCES `run_configuration` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;