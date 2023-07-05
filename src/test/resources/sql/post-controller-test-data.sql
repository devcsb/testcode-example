insert into `users`(`id`, `email`, `nickname`, `address`, `certification_code`, `status`, `last_login_at`)
values ('1', 'devcsb119@gmail.com', 'tester', 'seoul', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa', 'ACTIVE', 0);
insert into `users`(`id`, `email`, `nickname`, `address`, `certification_code`, `status`, `last_login_at`)
values ('2', 'devcsb1@gmail.com', 'tester1', 'seoul', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaab', 'PENDING', 0);

insert into `posts`(`id`,`content`,`created_at`,`modified_at`,`user_id`)
values ('1','helloTest',1678530679919,0,1)
