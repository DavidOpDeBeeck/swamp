-- -----------------------------------------------------
-- Table `container_instance_view`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `container_instance_view` (
  `value` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`value`))
ENGINE = InnoDB;