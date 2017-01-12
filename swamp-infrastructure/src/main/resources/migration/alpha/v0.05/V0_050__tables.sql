-- -----------------------------------------------------
-- Table `container`
-- -----------------------------------------------------
ALTER TABLE `container` DROP `name`;

-- -----------------------------------------------------
-- Table `container_aliases`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `container_aliases` (
  `container_id` varchar(255) NOT NULL,
  `aliases` varchar(255) NULL,
  INDEX `fk_container_aliases_idx` (`container_id` ASC),
  CONSTRAINT `fk_container_aliases`
    FOREIGN KEY (`container_id`)
    REFERENCES `container` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION )
ENGINE=InnoDB;