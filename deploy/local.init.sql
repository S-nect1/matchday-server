INSERT INTO club (id, created_date, updated_date, activity_area, age_range, club_category, club_password, explanation, gender, introduction, sports_type, main_uniform_color, match_count, member_count, profile_img_path, schedule_count, sub_uniform_color, title, university)
VALUES (default, null, null, 'SEOUL', 'TWENTIES', 'SCHOOL_GROUP', 'club_password', 'explanation', 'MAN', 'introduction', 'SOCCER', 'main_uniform_color', 2, 5, null, 2, 'sub_uniform_color', 'title nothing', '한양대학교');
INSERT INTO club (id, created_date, updated_date, activity_area, age_range, club_category, club_password, explanation, gender, introduction, sports_type, main_uniform_color, match_count, member_count, profile_img_path, schedule_count, sub_uniform_color, title, university)
VALUES (default, null, null, 'SEOUL', 'TWENTIES', 'SCHOOL_GROUP', 'club_password', 'explanation', 'WOMAN', 'introduction', 'SOCCER', 'main_uniform_color', 2, 5, null, 2, 'sub_uniform_color', 'titlenothing', '서울대학교');
INSERT INTO club (id, created_date, updated_date, activity_area, age_range, club_category, club_password, explanation, gender, introduction, sports_type, main_uniform_color, match_count, member_count, profile_img_path, schedule_count, sub_uniform_color, title, university)
VALUES (default, null, null, 'GYEONGGI', 'THIRTIES', 'SOCIAL_GROUP', 'club_password', 'explanation', 'UNISEX', 'introduction', 'FUTSAL', 'main_uniform_color', 2, 5, null, 2, 'sub_uniform_color', 'title nothing', '연세대학교');

INSERT INTO CLUB_SEARCH (CLUB_ID, CREATED_DATE, UPDATED_DATE, TITLE_NO_SPACE, INTRO_NO_SPACE, EXP_NO_SPACE, ALL_FIELDS_CONCAT)
VALUES (1, null, null, 'titlenothing', 'introduction', 'explanation', 'titlenothing|introduction|explanation');
INSERT INTO CLUB_SEARCH (CLUB_ID, CREATED_DATE, UPDATED_DATE, TITLE_NO_SPACE, INTRO_NO_SPACE, EXP_NO_SPACE, ALL_FIELDS_CONCAT)
VALUES (2, null, null, 'titlenothing', 'introduction', 'explanation', 'titlenothing|introduction|explanation');
INSERT INTO CLUB_SEARCH (CLUB_ID, CREATED_DATE, UPDATED_DATE, TITLE_NO_SPACE, INTRO_NO_SPACE, EXP_NO_SPACE, ALL_FIELDS_CONCAT)
VALUES (3, null, null, 'titlenothing', 'introduction', 'explanation', 'titlenothing|introduction|explanation');

INSERT INTO users (user_id, created_date, updated_date, activity_area, birthday, email, fcm_token, gender, height, img_path, main_foot, main_position, name, password, phone, refresh_token, role, sub_position, weight)
VALUES (default, null, null, 'SEOUL', 'birthday', 'email', 'fcm_token', 'WOMAN', 180, null, 'RIGHT', 'ST', 'name', 'password', 'phone', 'refresh_token123456', 'USER', 'LM', 70);
INSERT INTO users (user_id, created_date, updated_date, activity_area, birthday, email, fcm_token, gender, height, img_path, main_foot, main_position, name, password, phone, refresh_token, role, sub_position, weight)
VALUES (default, null, null, 'SEOUL', 'birthday', 'email', 'fcm_token', 'WOMAN', 180, null, 'LEFT', 'LM', 'name', 'password', 'phone', 'refresh_token12345678', 'USER', 'ST', 70);
INSERT INTO users (user_id, created_date, updated_date, activity_area, birthday, email, fcm_token, gender, height, img_path, main_foot, main_position, name, password, phone, refresh_token, role, sub_position, weight)
VALUES (default, null, null, 'GYEONGGI', 'birthday', 'email', 'fcm_token', 'MAN', 180, null, 'RIGHT', 'GK', 'name', 'password', 'phone', 'refresh_token123456789', 'USER', 'LM', 70);
INSERT INTO users (user_id, created_date, updated_date, activity_area, birthday, email, fcm_token, gender, height, img_path, main_foot, main_position, name, password, phone, refresh_token, role, sub_position, weight)
VALUES (default, null, null, 'GYEONGGI', 'birthday', 'email', 'fcm_token', 'MAN', 180, null, 'BOTH', 'MANAGER', 'name', 'password', 'phone', 'refresh_token12345678910', 'USER', 'GK', 70);

INSERT INTO user_club (id, created_date, updated_date, club_role, join_date, match_count, schedule_count, club_id, user_id)
VALUES (default, null, null, 'STAFF', '2024-12-11', 2, 2, 1, 1);
---- 3번 동아리에 가입한 유저들
INSERT INTO user_club (id, created_date, updated_date, club_role, join_date, match_count, schedule_count, club_id, user_id)
VALUES (default, null, null, 'STAFF', '2024-12-11', 2, 2, 3, 3);
INSERT INTO user_club (id, created_date, updated_date, club_role, join_date, match_count, schedule_count, club_id, user_id)
VALUES (default, null, null, 'STAFF', '2024-12-11', 2, 2, 3, 4);