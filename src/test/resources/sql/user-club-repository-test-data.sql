ALTER TABLE USERS DROP CONSTRAINT CONSTRAINT_4;
ALTER TABLE USERS DROP CONSTRAINT CONSTRAINT_4D4;

insert into `club` (`id`, `title`, `explanation`, `introduction`, `category`, `university`, `gender`, `activity_area`, `main_event`, `age_range`, `club_password`, `profile_img_path`, `main_uniform_color`, `sub_uniform_color`, `member_count`, `schedule_count`, `match_count`)
values (1, 'title', 'explanation', 'introduction', 'category', 'university', 'gender', 'activity_area', 'main_event', 'age_range', 'club_password', 'profile_img_path', 'main_uniform_color', 'sub_uniform_color', 5, 2, 2);

insert into `club` (`id`, `title`, `explanation`, `introduction`, `category`, `university`, `gender`, `activity_area`, `main_event`, `age_range`, `club_password`, `profile_img_path`, `main_uniform_color`, `sub_uniform_color`, `member_count`, `schedule_count`, `match_count`)
values (2, 'title', 'explanation', 'introduction', 'category', 'university', 'gender', 'activity_area', 'main_event', 'age_range', 'club_password', 'profile_img_path', 'main_uniform_color', 'sub_uniform_color', 5, 2, 2);

insert into `club` (`id`, `title`, `explanation`, `introduction`, `category`, `university`, `gender`, `activity_area`, `main_event`, `age_range`, `club_password`, `profile_img_path`, `main_uniform_color`, `sub_uniform_color`, `member_count`, `schedule_count`, `match_count`)
values (3, 'title', 'explanation', 'introduction', 'category', 'university', 'gender', 'activity_area', 'main_event', 'age_range', 'club_password', 'profile_img_path', 'main_uniform_color', 'sub_uniform_color', 5, 2, 2);

insert into `users` (`user_id`, `email`, `password`, `name`, `birthday`, `gender`, `phone`, `img_path`, `role`, `activity_area`, `height`, `weight`, `main_foot`, `main_position`, `sub_position`, `refresh_token`, `fcm_token`)
values (1, 'email', 'password', 'name', 'birthday', 'WOMAN', 'phone', 'img_path', 'USER', 'activity_area', 180, 70, 'main_foot', 'main_position', 'sub_position', 'refresh_token123456', 'fcm_token');

insert into `users` (`user_id`, `email`, `password`, `name`, `birthday`, `gender`, `phone`, `img_path`, `role`, `activity_area`, `height`, `weight`, `main_foot`, `main_position`, `sub_position`, `refresh_token`, `fcm_token`)
values (2, 'email', 'password', 'name', 'birthday', 'WOMAN', 'phone', 'img_path', 'USER', 'activity_area', 180, 70, 'main_foot', 'main_position', 'sub_position', 'refresh_token12345678', 'fcm_token');

insert into `users` (`user_id`, `email`, `password`, `name`, `birthday`, `gender`, `phone`, `img_path`, `role`, `activity_area`, `height`, `weight`, `main_foot`, `main_position`, `sub_position`, `refresh_token`, `fcm_token`)
values (3, 'email', 'password', 'name', 'birthday', 'WOMAN', 'phone', 'img_path', 'USER', 'activity_area', 180, 70, 'main_foot', 'main_position', 'sub_position', 'refresh_token123456789', 'fcm_token');

insert into `users` (`user_id`, `email`, `password`, `name`, `birthday`, `gender`, `phone`, `img_path`, `role`, `activity_area`, `height`, `weight`, `main_foot`, `main_position`, `sub_position`, `refresh_token`, `fcm_token`)
values (4, 'email', 'password', 'name', 'birthday', 'WOMAN', 'phone', 'img_path', 'USER', 'activity_area', 180, 70, 'main_foot', 'main_position', 'sub_position', 'refresh_token12345678910', 'fcm_token');

insert into `user_club` (`id`, `user_id`, `club_id`, `position`, `join_date`, `category`, `schedule_count`, `match_count`)
values (1, 1, 1, 'position', '2024-12-11', 'creator', 2, 2);
-- 3번 동아리에 가입한 유저들
insert into `user_club` (`id`, `user_id`, `club_id`, `position`, `join_date`, `category`, `schedule_count`, `match_count`)
values (2, 3, 3, 'position', '2024-12-11', 'admin', 2, 2);
insert into `user_club` (`id`, `user_id`, `club_id`, `position`, `join_date`, `category`, `schedule_count`, `match_count`)
values (3, 4, 3, 'position', '2024-12-11', 'creator', 2, 2);