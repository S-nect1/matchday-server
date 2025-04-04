INSERT INTO club (id, created_date, updated_date, activity_area, age_range, club_category, club_password, explanation, gender, introduction, sports_type, main_uniform_color, match_count, member_count, profile_img_path, schedule_count, sub_uniform_color, title, university)
VALUES (default, null, null, 'SEOUL', 'TWENTIES', 'SCHOOL_GROUP', 'club_password', 'explanation', 'MAN', 'introduction', 'SOCCER', 'main_uniform_color', 2, 5, 'profile_img_path', 2, 'sub_uniform_color', 'title', '한양대학교');
INSERT INTO club (id, created_date, updated_date, activity_area, age_range, club_category, club_password, explanation, gender, introduction, sports_type, main_uniform_color, match_count, member_count, profile_img_path, schedule_count, sub_uniform_color, title, university)
VALUES (default, null, null, 'SEOUL', 'TWENTIES', 'SCHOOL_GROUP', 'club_password', 'explanation', 'WOMAN', 'introduction', 'SOCCER', 'main_uniform_color', 2, 5, 'profile_img_path', 2, 'sub_uniform_color', 'title', '서울대학교');
INSERT INTO club (id, created_date, updated_date, activity_area, age_range, club_category, club_password, explanation, gender, introduction, sports_type, main_uniform_color, match_count, member_count, profile_img_path, schedule_count, sub_uniform_color, title, university)
VALUES (default, null, null, 'GYEONGGI', 'THIRTIES', 'SOCIAL_GROUP', 'club_password', 'explanation', 'UNISEX', 'introduction', 'FUTSAL', 'main_uniform_color', 2, 5, 'profile_img_path', 2, 'sub_uniform_color', 'title', '연세대학교');

INSERT INTO users (user_id, created_date, updated_date, activity_area, birthday, email, fcm_token, gender, height, img_path, main_foot, main_position, name, password, phone, refresh_token, role, sub_position, weight)
VALUES (default, null, null, 'SEOUL', 'birthday', 'email', 'fcm_token', 'WOMAN', 180, 'img_path', 'RIGHT', 'ST', 'name', 'password', 'phone', 'refresh_token123456', 'USER', 'LM', 70);
INSERT INTO users (user_id, created_date, updated_date, activity_area, birthday, email, fcm_token, gender, height, img_path, main_foot, main_position, name, password, phone, refresh_token, role, sub_position, weight)
VALUES (default, null, null, 'SEOUL', 'birthday', 'email', 'fcm_token', 'WOMAN', 180, 'img_path', 'LEFT', 'LM', 'name', 'password', 'phone', 'refresh_token12345678', 'USER', 'ST', 70);
INSERT INTO users (user_id, created_date, updated_date, activity_area, birthday, email, fcm_token, gender, height, img_path, main_foot, main_position, name, password, phone, refresh_token, role, sub_position, weight)
VALUES (default, null, null, 'GYEONGGI', 'birthday', 'email', 'fcm_token', 'MAN', 180, 'img_path', 'RIGHT', 'GK', 'name', 'password', 'phone', 'refresh_token123456789', 'USER', 'LM', 70);
INSERT INTO users (user_id, created_date, updated_date, activity_area, birthday, email, fcm_token, gender, height, img_path, main_foot, main_position, name, password, phone, refresh_token, role, sub_position, weight)
VALUES (default, null, null, 'GYEONGGI', 'birthday', 'email', 'fcm_token', 'MAN', 180, 'img_path', 'BOTH', 'MANAGER', 'name', 'password', 'phone', 'refresh_token12345678910', 'USER', 'GK', 70);

INSERT INTO user_club (id, created_date, updated_date, club_role, join_date, match_count, schedule_count, club_id, user_id)
VALUES (default, null, null, 'PRESIDENT', '2024-12-11', 2, 2, 1, 1);
---- 3번 동아리에 가입한 유저들
INSERT INTO user_club (id, created_date, updated_date, club_role, join_date, match_count, schedule_count, club_id, user_id)
VALUES (default, null, null, 'VICE_PRESIDENT', '2024-12-11', 2, 2, 3, 3);
INSERT INTO user_club (id, created_date, updated_date, club_role, join_date, match_count, schedule_count, club_id, user_id)
VALUES (default, null, null, 'PRESIDENT', '2024-12-11', 2, 2, 3, 4);
-- 스케줄 생성
insert into `schedule` (id, club_id, title, location, start_time, end_time, min_people, category, note, attend, non_attend, is_close)
values (1, 3, '운동 매치 스케줄', '서울시 마포구', '2024-03-11 14:30:00', '2024-03-11 17:30:00', 10, 'soccor', 'note', 10, 12, 0);
insert into `schedule` (id, club_id, title, location, start_time, end_time, min_people, category, note, attend, non_attend, is_close)
values (2, 3, '운동 매치 스케줄2', '서울시 마포구', '2024-03-12 14:30:00', '2024-03-12 17:30:00', 10, 'soccor', 'note', 10, 12, 0);
-- 댓글 생성
insert into `comment` (id, user_id, schedule_id, contents)
values (1, 3, 1, '회사 면접이 잡혀있어서 못 갑니다');
-- 투표 생성
insert into `schedule_vote` (id, user_id, schedule_id, attendance)
values (1, 3, 1, 'true');
insert into `schedule_vote` (id, user_id, schedule_id, attendance)
values (2, 4, 1, 'true');
