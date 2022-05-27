INSERT INTO CATEGORY (ID, CREATE_DATE, UPDATE_DATE, DESCRIPTION, TITLE) VALUES (35, '2022-05-24 12:13:21.048000', null, 'description', 'cat1');
INSERT INTO CATEGORY (ID, CREATE_DATE, UPDATE_DATE, DESCRIPTION, TITLE) VALUES (39, '2022-05-24 12:14:41.044000', null, 'description', 'cat2');


INSERT INTO MEMBER (ID, CREATE_DATE, UPDATE_DATE, FIRST_NAME, GENDER, LAST_NAME, PASSWORD, USERNAME, ADDRESS, MOBILE_NUMBER) VALUES (33, '2022-05-24 12:05:06.588000', null, 'farid', 0, 'raad', '12434', 'f3', null, null);
INSERT INTO MEMBER (ID, CREATE_DATE, UPDATE_DATE, FIRST_NAME, GENDER, LAST_NAME, PASSWORD, USERNAME, ADDRESS, MOBILE_NUMBER) VALUES (64, '2022-05-24 14:02:05.715000', null, 'farhad', 0, 'feri', '12434', '4', null, null);
INSERT INTO MEMBER (ID, CREATE_DATE, UPDATE_DATE, FIRST_NAME, GENDER, LAST_NAME, PASSWORD, USERNAME, ADDRESS, MOBILE_NUMBER) VALUES (67, '2022-05-24 14:06:07.437000', null, 'farhad', 0, 'feri', '12434', 'f4', null, null);
INSERT INTO MEMBER (ID, CREATE_DATE, UPDATE_DATE, FIRST_NAME, GENDER, LAST_NAME, PASSWORD, USERNAME, ADDRESS, MOBILE_NUMBER) VALUES (97, '2022-05-26 14:53:54.868000', null, 'farhad', 0, 'feri', '12434', 'f3@hh.com', null, null);
INSERT INTO MEMBER (ID, CREATE_DATE, UPDATE_DATE, FIRST_NAME, GENDER, LAST_NAME, PASSWORD, USERNAME, ADDRESS, MOBILE_NUMBER) VALUES (98, '2022-05-26 16:12:53.172000', null, 'farhad', 0, 'feri', 't20I60XsJuw=', 'farid.raad@gmail.com', 'ddddd', '09127374074');


INSERT INTO PRODUCT (ID, CREATE_DATE, UPDATE_DATE, COMMENT_IS_ENABLE, COMMENT_IS_PUBLIC, DESCRIPTION, IMAGE, IS_SHOW, TITLE, VOTE_IS_ENABLE, VOTE_IS_PUBLIC, CATEGORY_ID) VALUES (36, '2022-05-24 12:14:18.117000', null, true, true, 'product desc', 'img.png,img2.png', true, 'pro1', true, true, 35);
INSERT INTO PRODUCT (ID, CREATE_DATE, UPDATE_DATE, COMMENT_IS_ENABLE, COMMENT_IS_PUBLIC, DESCRIPTION, IMAGE, IS_SHOW, TITLE, VOTE_IS_ENABLE, VOTE_IS_PUBLIC, CATEGORY_ID) VALUES (37, '2022-05-24 12:14:26.607000', null, true, true, 'product desc', 'img.png,img2.png', true, 'pro2', true, true, 35);
INSERT INTO PRODUCT (ID, CREATE_DATE, UPDATE_DATE, COMMENT_IS_ENABLE, COMMENT_IS_PUBLIC, DESCRIPTION, IMAGE, IS_SHOW, TITLE, VOTE_IS_ENABLE, VOTE_IS_PUBLIC, CATEGORY_ID) VALUES (38, '2022-05-24 12:14:33.836000', null, true, true, 'product desc', 'img.png,img2.png', true, 'pro3', true, true, 35);
INSERT INTO PRODUCT (ID, CREATE_DATE, UPDATE_DATE, COMMENT_IS_ENABLE, COMMENT_IS_PUBLIC, DESCRIPTION, IMAGE, IS_SHOW, TITLE, VOTE_IS_ENABLE, VOTE_IS_PUBLIC, CATEGORY_ID) VALUES (40, '2022-05-24 12:14:59.685000', null, true, true, 'product desc', 'img.png,img2.png', true, 'pro4', true, true, 39);
INSERT INTO PRODUCT (ID, CREATE_DATE, UPDATE_DATE, COMMENT_IS_ENABLE, COMMENT_IS_PUBLIC, DESCRIPTION, IMAGE, IS_SHOW, TITLE, VOTE_IS_ENABLE, VOTE_IS_PUBLIC, CATEGORY_ID) VALUES (41, '2022-05-24 12:15:02.952000', null, true, true, 'product desc', 'img.png,img2.png', true, 'pro5', true, true, 39);
INSERT INTO PRODUCT (ID, CREATE_DATE, UPDATE_DATE, COMMENT_IS_ENABLE, COMMENT_IS_PUBLIC, DESCRIPTION, IMAGE, IS_SHOW, TITLE, VOTE_IS_ENABLE, VOTE_IS_PUBLIC, CATEGORY_ID) VALUES (42, '2022-05-24 12:15:07.507000', null, true, true, 'product desc', 'img.png,img2.png', true, 'pro6', true, true, 39);

INSERT INTO PROVIDER (ID, CREATE_DATE, UPDATE_DATE, ADDRESS, PHONE_NUMBER, TITLE) VALUES (43, '2022-05-24 12:42:17.212000', null, 'tehran', '09123', 'provider1');
INSERT INTO PROVIDER (ID, CREATE_DATE, UPDATE_DATE, ADDRESS, PHONE_NUMBER, TITLE) VALUES (44, '2022-05-24 12:42:30.321000', null, 'tehran', '09124', 'provider2');
INSERT INTO PROVIDER (ID, CREATE_DATE, UPDATE_DATE, ADDRESS, PHONE_NUMBER, TITLE) VALUES (45, '2022-05-24 12:42:36.117000', null, 'tehran', '09125', 'provider3');


INSERT INTO PRODUCT_PROVIDER (ID, CREATE_DATE, UPDATE_DATE, PRICE, PRODUCT_ID, PROVIDER_ID) VALUES (1, '2022-05-24 12:14:18.117000', '2022-05-24 12:14:18.117000', 1200.00, 41, 44);
INSERT INTO PRODUCT_PROVIDER (ID, CREATE_DATE, UPDATE_DATE, PRICE, PRODUCT_ID, PROVIDER_ID) VALUES (2, '2022-05-24 12:14:18.117000', '2022-05-24 12:14:18.117000', 1300.00, 41, 43);


INSERT INTO COMMENT (ID, CREATE_DATE, UPDATE_DATE, COMMENT, IS_CONFIRM, MEMBER_ID, PRODUCT_ID, PROVIDER_ID) VALUES (54, '2022-05-24 13:58:32.945000', null, 'comment1', false, 33, 36, 44);
INSERT INTO COMMENT (ID, CREATE_DATE, UPDATE_DATE, COMMENT, IS_CONFIRM, MEMBER_ID, PRODUCT_ID, PROVIDER_ID) VALUES (55, '2022-05-24 13:58:42.114000', null, 'comment2', false, 33, 36, 44);
INSERT INTO COMMENT (ID, CREATE_DATE, UPDATE_DATE, COMMENT, IS_CONFIRM, MEMBER_ID, PRODUCT_ID, PROVIDER_ID) VALUES (56, '2022-05-24 13:58:46.207000', null, 'comment3', false, 33, 36, 44);
INSERT INTO COMMENT (ID, CREATE_DATE, UPDATE_DATE, COMMENT, IS_CONFIRM, MEMBER_ID, PRODUCT_ID, PROVIDER_ID) VALUES (57, '2022-05-24 13:58:49.613000', null, 'comment4', false, 33, 36, 44);
INSERT INTO COMMENT (ID, CREATE_DATE, UPDATE_DATE, COMMENT, IS_CONFIRM, MEMBER_ID, PRODUCT_ID, PROVIDER_ID) VALUES (58, '2022-05-24 13:59:30.759000', null, 'comment5', false, 33, 40, 44);
INSERT INTO COMMENT (ID, CREATE_DATE, UPDATE_DATE, COMMENT, IS_CONFIRM, MEMBER_ID, PRODUCT_ID, PROVIDER_ID) VALUES (59, '2022-05-24 13:59:35.767000', null, 'comment6', false, 33, 40, 44);
INSERT INTO COMMENT (ID, CREATE_DATE, UPDATE_DATE, COMMENT, IS_CONFIRM, MEMBER_ID, PRODUCT_ID, PROVIDER_ID) VALUES (60, '2022-05-24 13:59:38.588000', null, 'comment7', false, 33, 40, 44);
INSERT INTO COMMENT (ID, CREATE_DATE, UPDATE_DATE, COMMENT, IS_CONFIRM, MEMBER_ID, PRODUCT_ID, PROVIDER_ID) VALUES (61, '2022-05-24 13:59:41.851000', null, 'comment8', false, 33, 40, 44);


INSERT INTO VOTE (ID, CREATE_DATE, UPDATE_DATE, IS_CONFIRM, RATE, MEMBER_ID, PRODUCT_ID, PROVIDER_ID) VALUES (78, '2022-05-24 14:59:58.273000', null, false, 7, 64, 41, 44);
INSERT INTO VOTE (ID, CREATE_DATE, UPDATE_DATE, IS_CONFIRM, RATE, MEMBER_ID, PRODUCT_ID, PROVIDER_ID) VALUES (79, '2022-05-24 14:59:59.535000', null, false, 7, 98, 41, 44);
INSERT INTO VOTE (ID, CREATE_DATE, UPDATE_DATE, IS_CONFIRM, RATE, MEMBER_ID, PRODUCT_ID, PROVIDER_ID) VALUES (80, '2022-05-24 15:00:10.161000', null, false, 5, 33, 41, 44);
INSERT INTO VOTE (ID, CREATE_DATE, UPDATE_DATE, IS_CONFIRM, RATE, MEMBER_ID, PRODUCT_ID, PROVIDER_ID) VALUES (81, '2022-05-24 15:00:21.347000', null, false, 2, 33, 40, 44);
