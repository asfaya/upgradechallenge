DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `check_reservation_insert`(IN param_begin_date datetime, IN param_end_date datetime)
BEGIN
 
  DECLARE valid boolean default 1;
    DECLARE overlapping_rows int default 0;
    
	select count(id) into overlapping_rows
	from   reservations as r
	where
		r.status = 0 
        and param_begin_date  <= r.end_date 
		and param_end_date >= r.begin_date;
                  
    IF overlapping_rows > 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Reservation overlaps with other reservation';
    END IF;
    
END ;;
DELIMITER ;