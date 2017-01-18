-- -----------------------------------------------------
-- Table `container_instance_warning_view`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `container_instance_warning_view` (
  `container_instance_view_id` varchar(255) NOT NULL,
  `warnings` varchar(255) NULL,
  INDEX `fk_container_instance_view_view_idx` (`container_instance_view_id` ASC),
  CONSTRAINT `fk_container_instance_view_view`
    FOREIGN KEY (`container_instance_view_id`)
    REFERENCES `container_instance_view` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION )
ENGINE=InnoDB;