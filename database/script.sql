-- MySQL Script generated by MySQL Workbench
-- Tue Nov 21 16:56:04 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Warehouse2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Warehouse2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Warehouse2` DEFAULT CHARACTER SET utf8 ;
USE `Warehouse2` ;

-- -----------------------------------------------------
-- Table `Warehouse2`.`Customers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Warehouse2`.`Customers` (
  `CustomerId` INT NOT NULL AUTO_INCREMENT,
  `CustomerName` VARCHAR(45) NOT NULL,
  `ContactPerson` VARCHAR(45) NOT NULL,
  `Address` VARCHAR(45) NOT NULL,
  `City` VARCHAR(45) NOT NULL,
  `PostCode` VARCHAR(45) NOT NULL,
  `Country` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`CustomerId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Warehouse2`.`Employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Warehouse2`.`Employees` (
  `EmployeeId` INT NOT NULL AUTO_INCREMENT,
  `LastName` VARCHAR(45) NOT NULL,
  `FirstName` VARCHAR(45) NOT NULL,
  `BirthDate` DATE NOT NULL,
  PRIMARY KEY (`EmployeeId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Warehouse2`.`Suppliers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Warehouse2`.`Suppliers` (
  `SupplierId` INT NOT NULL AUTO_INCREMENT,
  `SupplierName` VARCHAR(45) NOT NULL,
  `ContactPerson` VARCHAR(45) NOT NULL,
  `Address` VARCHAR(45) NOT NULL,
  `City` VARCHAR(45) NOT NULL,
  `PostCode` VARCHAR(45) NOT NULL,
  `Country` VARCHAR(45) NOT NULL,
  `Phone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`SupplierId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Warehouse2`.`Shippers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Warehouse2`.`Shippers` (
  `ShipperId` INT NOT NULL AUTO_INCREMENT,
  `ShipperName` VARCHAR(45) NOT NULL,
  `Phone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ShipperId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Warehouse2`.`Products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Warehouse2`.`Products` (
  `ProductId` INT NOT NULL AUTO_INCREMENT,
  `ProductName` VARCHAR(45) NOT NULL,
  `SupplierID` INT NOT NULL,
  `ProductCategory` VARCHAR(45) NOT NULL,
  `PricePerUnit` FLOAT NOT NULL,
  PRIMARY KEY (`ProductId`),
  INDEX `SupplierID_idx` (`SupplierID` ASC) VISIBLE,
  CONSTRAINT `SupplierID`
    FOREIGN KEY (`SupplierID`)
    REFERENCES `Warehouse2`.`Suppliers` (`SupplierId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Warehouse2`.`Orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Warehouse2`.`Orders` (
  `OrderId` INT NOT NULL AUTO_INCREMENT,
  `OrderDate` DATE NOT NULL,
  `CustomerId` INT NOT NULL,
  `EmployeeId` INT NOT NULL,
  `ShipperId` INT NOT NULL,
  PRIMARY KEY (`OrderId`),
  INDEX `CustomerId_idx` (`CustomerId` ASC) VISIBLE,
  INDEX `EmployeeId_idx` (`EmployeeId` ASC) VISIBLE,
  INDEX `ShipperId_idx` (`ShipperId` ASC) VISIBLE,
  CONSTRAINT `CustomerId`
    FOREIGN KEY (`CustomerId`)
    REFERENCES `Warehouse2`.`Customers` (`CustomerId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `EmployeeId`
    FOREIGN KEY (`EmployeeId`)
    REFERENCES `Warehouse2`.`Employees` (`EmployeeId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ShipperId`
    FOREIGN KEY (`ShipperId`)
    REFERENCES `Warehouse2`.`Shippers` (`ShipperId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Warehouse2`.`OrderDetails`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Warehouse2`.`OrderDetails` (
  `OrderDetailsId` INT NOT NULL AUTO_INCREMENT,
  `OrderId` INT NOT NULL,
  `ProductId` INT NOT NULL,
  `Quantity` INT NOT NULL,
  PRIMARY KEY (`OrderDetailsId`),
  INDEX `OrderId_idx` (`OrderId` ASC) VISIBLE,
  INDEX `ProductId_idx` (`ProductId` ASC) VISIBLE,
  CONSTRAINT `OrderId`
    FOREIGN KEY (`OrderId`)
    REFERENCES `Warehouse2`.`Orders` (`OrderId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ProductId`
    FOREIGN KEY (`ProductId`)
    REFERENCES `Warehouse2`.`Products` (`ProductId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
