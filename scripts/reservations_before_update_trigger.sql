DELIMITER ;;
CREATE TRIGGER `reservations_before_update` 
	BEFORE UPDATE ON `reservations` 
		FOR EACH ROW BEGIN    
			CALL check_reservation_update(new.id, new.begin_date,new.end_date); 
		END;;
DELIMITER ; 