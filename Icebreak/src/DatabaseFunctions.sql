-- -------------------------------------------------------------------------------------
-- Automatically send a birthday message to a user on a given day
-- Returns the total amount of users who were sent messages
-- -------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION birthdayMessage(message text DEFAULT NULL, my_day integer DEFAULT EXTRACT(day FROM
                                                                                                     current_date),
                                           my_month integer DEFAULT EXTRACT(month FROM current_date))
    RETURNS INT AS $total_users$
DECLARE largest_mid INT DEFAULT 0;
    DECLARE total_users INT DEFAULT 0;
    DECLARE C1 CURSOR FOR
            SELECT DISTINCT userid, chatid
            FROM messages
            WHERE EXISTS
                      (
                          SELECT 1
                          FROM users
                          WHERE EXTRACT(month FROM birth_date) = my_month
                            AND extract(day FROM birth_date) = my_day
                            AND userid = messages.userid
                      );
BEGIN
    FOR record in C1 LOOP
            total_users = total_users + 1;
            SELECT MAX(msgid) + 1 INTO largest_mid FROM Messages;
            IF (message IS NULL) THEN
                INSERT INTO Messages VALUES(largest_mid, 'pending', current_date, 'Happy Birthday!',
                                            record.userid, record.chatid, NULL);
            ELSE
                INSERT INTO Messages VALUES(largest_mid, 'pending', current_date, message, record.userid, record
                    .chatid, NULL);
            END IF;
        END LOOP;

    RETURN total_users;
END
$total_users$ LANGUAGE plpgsql;

SELECT *
FROM messages
WHERE EXISTS
          (
              SELECT 1
              FROM users
              WHERE EXTRACT(month FROM birth_date) = '01'
                AND extract(day FROM birth_date) = '08'
                AND userid = messages.userid
          );

SELECT * FROM
    birthdayMessage('Happy Birthday!', 08, 01);
