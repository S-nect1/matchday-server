ALTER TABLE USERS DROP CONSTRAINT CONSTRAINT_4;
ALTER TABLE USERS DROP CONSTRAINT CONSTRAINT_4D4;

-- 동아리 생성
insert into `club` (`id`, `title`, `explanation`, `introduction`, `category`, `university`, `gender`, `activity_area`, `main_event`, `age_range`, `club_password`, `profile_img_path`, `main_uniform_color`, `sub_uniform_color`, `member_count`, `schedule_count`, `match_count`)
values (1, 'title', 'explanation', 'introduction', 'category', 'university', 'gender', 'activity_area', 'main_event', 'age_range', 'club_password', 'profile_img_path', 'main_uniform_color', 'sub_uniform_color', 5, 2, 2);
insert into `club` (`id`, `title`, `explanation`, `introduction`, `category`, `university`, `gender`, `activity_area`, `main_event`, `age_range`, `club_password`, `profile_img_path`, `main_uniform_color`, `sub_uniform_color`, `member_count`, `schedule_count`, `match_count`)
values (2, 'title', 'explanation', 'introduction', 'category', 'university', 'gender', 'activity_area', 'main_event', 'age_range', 'club_password', 'profile_img_path', 'main_uniform_color', 'sub_uniform_color', 5, 2, 2);
insert into `club` (`id`, `title`, `explanation`, `introduction`, `category`, `university`, `gender`, `activity_area`, `main_event`, `age_range`, `club_password`, `profile_img_path`, `main_uniform_color`, `sub_uniform_color`, `member_count`, `schedule_count`, `match_count`)
values (3, 'title', 'explanation', 'introduction', 'category', 'university', 'gender', 'activity_area', 'main_event', 'age_range', 'club_password', 'profile_img_path', 'main_uniform_color', 'sub_uniform_color', 5, 2, 2);
-- 사용자 생성
insert into `users` (`user_id`, `email`, `password`, `name`, `birthday`, `gender`, `phone`, `img_path`, `role`, `activity_area`, `height`, `weight`, `main_foot`, `main_position`, `sub_position`, `refresh_token`, `fcm_token`)
values (1, 'email', 'password', 'name', 'birthday', 'WOMAN', 'phone', 'img_path', 'USER', 'activity_area', 180, 70, 'main_foot', 'main_position', 'sub_position', 'refresh_token123456', 'fcm_token');
insert into `users` (`user_id`, `email`, `password`, `name`, `birthday`, `gender`, `phone`, `img_path`, `role`, `activity_area`, `height`, `weight`, `main_foot`, `main_position`, `sub_position`, `refresh_token`, `fcm_token`)
values (2, 'email', 'password', 'name', 'birthday', 'WOMAN', 'phone', 'img_path', 'USER', 'activity_area', 180, 70, 'main_foot', 'main_position', 'sub_position', 'refresh_token12345678', 'fcm_token');
insert into `users` (`user_id`, `email`, `password`, `name`, `birthday`, `gender`, `phone`, `img_path`, `role`, `activity_area`, `height`, `weight`, `main_foot`, `main_position`, `sub_position`, `refresh_token`, `fcm_token`)
values (3, 'email', 'password', 'name', 'birthday', 'WOMAN', 'phone', 'img_path', 'USER', 'activity_area', 180, 70, 'main_foot', 'main_position', 'sub_position', 'refresh_token123456789', 'fcm_token');
insert into `users` (`user_id`, `email`, `password`, `name`, `birthday`, `gender`, `phone`, `img_path`, `role`, `activity_area`, `height`, `weight`, `main_foot`, `main_position`, `sub_position`, `refresh_token`, `fcm_token`)
values (4, 'email', 'password', 'name', 'birthday', 'WOMAN', 'phone', 'img_path', 'USER', 'activity_area', 180, 70, 'main_foot', 'main_position', 'sub_position', 'refresh_token12345678910', 'fcm_token');
-- userClub 생성
insert into `user_club` (`id`, `user_id`, `club_id`, `position`, `join_date`, `category`, `schedule_count`, `match_count`)
values (1, 1, 1, 'position', '2024-12-11', 'creator', 2, 2);
-- 3번 동아리에 가입한 유저들
insert into `user_club` (`id`, `user_id`, `club_id`, `position`, `join_date`, `category`, `schedule_count`, `match_count`)
values (2, 3, 3, 'position', '2024-12-11', 'admin', 2, 2);
insert into `user_club` (`id`, `user_id`, `club_id`, `position`, `join_date`, `category`, `schedule_count`, `match_count`)
values (3, 4, 3, 'position', '2024-12-11', 'creator', 2, 2);
-- 스케줄 생성
insert into `schedule` (id, club_id, title, location, start_time, end_time, min_people, category, note, attend, non_attend, is_close)
values (1, 3, '운동 매치 스케줄', '서울시 마포구', '2024-12-11', '2024-12-11', 10, 'soccor', 'note', 10, 12, 0);
insert into `schedule` (id, club_id, title, location, start_time, end_time, min_people, category, note, attend, non_attend, is_close)
values (2, 3, '운동 매치 스케줄', '서울시 마포구', '2024-12-11', '2024-12-11', 10, 'soccor', 'note', 10, 12, 0);
-- 댓글 생성
insert into `comment` (id, user_id, schedule_id, contents)
values (1, 3, 1, '회사 면접이 잡혀있어서 못 갑니다');
