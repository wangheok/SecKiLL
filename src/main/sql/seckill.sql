-- SecKill execution Stored Procedure: Reduce Transaction row lock duration
DELIMITER $$ -- Convert console ; to $$
-- stored procedure definition
-- rowcount() : updated rows
CREATE PROCEDURE `SECKILL`.`EXECUTE_SECKILL`
  (IN v_seckill_id bigint, IN v_mobile bigint,
    IN v_kill_time TIMESTAMP, OUT r_result INT )
  BEGIN
    DECLARE insert_count INT DEFAULT 0;
    START TRANSACTION;
    INSERT ignore INTO success_killed
     (seckill_id, user_mobile, create_time)
      VALUES (v_seckill_id, v_mobile, v_kill_time);
    SELECT ROW_COUNT() INTO insert_count;
    IF (insert_count = 0) THEN
      ROLLBACK;
      SET r_result = -1;
    ELSEIF (insert_count < 0) THEN
      ROLLBACK;
      SET r_result = -2;
    ELSE
      UPDATE seckill set number = number - 1
       WHERE seckill_id = v_seckill_id
       AND end_time > v_kill_time
       AND start_time < v_kill_time
       AND number > 0;
      SELECT ROW_COUNT() INTO insert_count;
      IF (insert_count = 0) THEN
        ROLLBACK;
        SET r_result = 0;
      ELSEIF (insert_count < 0) THEN
        ROLLBACK;
        SET r_result = -2;
      ELSE
        COMMIT;
        SET r_result = 1;
      END IF;
    END IF;
  END;
$$
-- Stored Procedure END
DELIMITER ;
SET @r_result=-3;
-- Stored Procedure execution
call EXECUTE_SECKILL(1003, 13888888888, now(), @r_result);
-- Getting result
SELECT @r_result;
