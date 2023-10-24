-- reference_data_service.master_overseas_bank definition

CREATE TABLE `master_overseas_bank` (
  `COUNTRY_CODE` varchar(50) DEFAULT NULL,
  `CURRENCY` varchar(50) DEFAULT NULL,
  `SPEEDSEND_CODE` varchar(50) NOT NULL,
  `SPEEDSEND_FLAG` tinyint(1) DEFAULT NULL,
  `IS_DELETE` tinyint(1) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  `MODIFIED_DATE` timestamp NULL DEFAULT NULL,
  `CREATED_BY` varchar(255) DEFAULT NULL,
  `MODIFIED_BY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`SPEEDSEND_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- reference_data_service.map_non_swift definition

CREATE TABLE `map_non_swift` (
  `COUNTRY_CODE` varchar(50) NOT NULL,
  `CURRENCY` varchar(50) NOT NULL,
  `CORRIDOR_CODE` varchar(50) DEFAULT NULL,
  `IS_DELETE` tinyint(1) DEFAULT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  `MODIFIED_DATE` timestamp NULL DEFAULT NULL,
  `CREATED_BY` varchar(255) DEFAULT NULL,
  `MODIFIED_BY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`COUNTRY_CODE`,`CURRENCY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;