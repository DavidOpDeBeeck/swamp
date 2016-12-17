-- -----------------------------------------------------
-- Table `domain_event_entry`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `domain_event_entry` (
  `aggregate_identifier` varchar(255) NOT NULL,
  `sequence_number` bigint(20) NOT NULL,
  `type` varchar(255) NOT NULL,
  `event_identifier` varchar(255) NOT NULL,
  `meta_data` longblob,
  `payload` longblob NOT NULL,
  `payload_revision` varchar(255) DEFAULT NULL,
  `payload_type` varchar(255) NOT NULL,
  `time_stamp` varchar(255) NOT NULL,
  PRIMARY KEY (`aggregate_identifier`,`sequence_number`,`type`),
  UNIQUE KEY `UK_event_identifier_1` (`event_identifier`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `domain_event_entry`
-- -----------------------------------------------------
CREATE TABLE `snapshot_event_entry` (
  `aggregate_identifier` varchar(255) NOT NULL,
  `sequence_number` bigint(20) NOT NULL,
  `type` varchar(255) NOT NULL,
  `event_identifier` varchar(255) NOT NULL,
  `meta_data` longblob,
  `payload` longblob NOT NULL,
  `payload_revision` varchar(255) DEFAULT NULL,
  `payload_type` varchar(255) NOT NULL,
  `time_stamp` varchar(255) NOT NULL,
  PRIMARY KEY (`aggregate_identifier`,`sequence_number`,`type`),
  UNIQUE KEY `UK_event_identifier_2` (`event_identifier`))
ENGINE=InnoDB;