DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `check_reservation_update`(IN param_id bigint(20), IN param_begin_date datetime, IN param_end_date datetime)
BEGIN
 
  DECLARE valid boolean default 1;
  DECLARE overlapping_rows int default 0;
     
  select
	count(id) into overlapping_rows
  from 
	reservations as r
  where  
	r.id <> param_id
	and param_begin_date  <= r.end_date 
    and param_end_date >= r.begin_date
    and r.status = 0;
                   
   IF overlapping_rows > 0 THEN
	 SIGNAL SQLSTATE '45001'
		 SET MESSAGE_TEXT = 'Reservation overlaps with other reservation';
   END IF;     
END ;;
DELIMITER ;