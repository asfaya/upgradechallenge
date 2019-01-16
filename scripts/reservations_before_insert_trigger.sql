DELIMITER ;;
CREATE TRIGGER `reservations_before_insert` 
	BEFORE INSERT ON `reservations`
		FOR EACH ROW BEGIN    
			CALL check_reservation_insert(new.begin_date,new.end_date); 
		END;;
DELIMITER ; 