-- -----------------------------------------------------
-- Table `credentials`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `credentials` (
  `id` VARCHAR(255) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `username_password_credentials`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `username_password_credentials` (
  `id` VARCHAR(255) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `git_configuration`
-- -----------------------------------------------------
DROP TABLE `git_configuration`;

CREATE TABLE IF NOT EXISTS `git_configuration` (
  `id` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `branch` VARCHAR(45) NOT NULL,
  `path` VARCHAR(45) NOT NULL,
  `credentials_id` VARCHAR(255) NULL,
  INDEX `fk_git_configuration_credentials1_idx` (`credentials_id` ASC),
  INDEX `fk_git_configuration_run_configuration2_idx` (`id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_git_configuration_credentials1`
    FOREIGN KEY (`credentials_id`)
    REFERENCES `credentials` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_image_configuration_run_configuration10`
    FOREIGN KEY (`id`)
    REFERENCES `run_configuration` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


