-- -----------------------------------------------------
-- Table `port_mapping`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `port_mapping` (
  `value` VARCHAR(255) NOT NULL,
  `internal` INT(5) NULL,
  `external` INT(5) NULL,
  `container_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`value`),
  INDEX `fk_port_mapping_container1_idx` (`container_id` ASC),
  CONSTRAINT `fk_port_mapping_container1`
    FOREIGN KEY (`container_id`)
    REFERENCES `container` (`value`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `environment_variable`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `environment_variable` (
  `value` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `value` VARCHAR(255) NOT NULL,
  `container_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`value`),
  INDEX `fk_port_mapping_container1_idx` (`container_id` ASC),
  CONSTRAINT `fk_port_mapping_container10`
    FOREIGN KEY (`container_id`)
    REFERENCES `container` (`value`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;