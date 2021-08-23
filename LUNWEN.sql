/*
 Navicat Oracle Data Transfer

 Source Server         : lunwen
 Source Server Type    : Oracle
 Source Server Version : 110200
 Source Host           : 127.0.0.1:1521
 Source Schema         : LUNWEN

 Target Server Type    : Oracle
 Target Server Version : 110200
 File Encoding         : 65001

 Date: 23/08/2021 21:02:37
*/


-- ----------------------------
-- Table structure for paper
-- ----------------------------
DROP TABLE "LUNWEN"."paper";
CREATE TABLE "LUNWEN"."paper" (
  "id" NUMBER(11,0) NOT NULL,
  "s_id" NUMBER(11,0),
  "paper_name" VARCHAR2(50 BYTE),
  "paper_file" VARCHAR2(80 BYTE),
  "source" VARCHAR2(255 BYTE),
  "words" NUMBER(9,0),
  "field" VARCHAR2(20 BYTE),
  "instructor" NUMBER(11,0),
  "phenomenon" VARCHAR2(30 BYTE),
  "expert" NUMBER(11,0),
  "type" VARCHAR2(2 BYTE),
  "addtime" DATE,
  "re_time" DATE
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "LUNWEN"."paper"."s_id" IS '学生id';
COMMENT ON COLUMN "LUNWEN"."paper"."paper_name" IS '论文题目';
COMMENT ON COLUMN "LUNWEN"."paper"."paper_file" IS '论文路径';
COMMENT ON COLUMN "LUNWEN"."paper"."source" IS '来源';
COMMENT ON COLUMN "LUNWEN"."paper"."words" IS '字数';
COMMENT ON COLUMN "LUNWEN"."paper"."field" IS '领域';
COMMENT ON COLUMN "LUNWEN"."paper"."instructor" IS '指导老师';
COMMENT ON COLUMN "LUNWEN"."paper"."phenomenon" IS '现象';
COMMENT ON COLUMN "LUNWEN"."paper"."expert" IS '专家';
COMMENT ON COLUMN "LUNWEN"."paper"."type" IS '论文状态（结论） 1待检测  2 审核通过 3 审核不通过 4 待审核';
COMMENT ON COLUMN "LUNWEN"."paper"."addtime" IS '上传时间';
COMMENT ON COLUMN "LUNWEN"."paper"."re_time" IS '评审时间';
COMMENT ON TABLE "LUNWEN"."paper" IS '论文';

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE "LUNWEN"."student";
CREATE TABLE "LUNWEN"."student" (
  "id" NUMBER(11,0) NOT NULL,
  "username" VARCHAR2(20 BYTE),
  "password" VARCHAR2(20 BYTE),
  "sno" VARCHAR2(20 BYTE),
  "department" VARCHAR2(50 BYTE),
  "addtime" DATE,
  "name" VARCHAR2(20 BYTE),
  "sex" VARCHAR2(2 BYTE)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE "LUNWEN"."teacher";
CREATE TABLE "LUNWEN"."teacher" (
  "id" NUMBER(11,0) NOT NULL,
  "username" VARCHAR2(20 BYTE),
  "password" VARCHAR2(20 BYTE),
  "role" VARCHAR2(2 BYTE),
  "name" VARCHAR2(20 BYTE),
  "department" VARCHAR2(50 BYTE),
  "sex" VARCHAR2(2 BYTE),
  "addtime" DATE
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "LUNWEN"."teacher"."username" IS '账户名';
COMMENT ON COLUMN "LUNWEN"."teacher"."password" IS '密码';
COMMENT ON COLUMN "LUNWEN"."teacher"."role" IS '角色 1指导老师 2 专家';
COMMENT ON COLUMN "LUNWEN"."teacher"."name" IS '名称';
COMMENT ON COLUMN "LUNWEN"."teacher"."department" IS '院系';
COMMENT ON COLUMN "LUNWEN"."teacher"."sex" IS '性别 1 男 2 女';
COMMENT ON COLUMN "LUNWEN"."teacher"."addtime" IS '添加时间';
COMMENT ON TABLE "LUNWEN"."teacher" IS '老师与专家';

-- ----------------------------
-- Sequence structure for SEQ_BASE_PAPER_INFO
-- ----------------------------
DROP SEQUENCE "LUNWEN"."SEQ_BASE_PAPER_INFO";
CREATE SEQUENCE "LUNWEN"."SEQ_BASE_PAPER_INFO" MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 CACHE 20;

-- ----------------------------
-- Sequence structure for SEQ_BASE_PARPER_INFO
-- ----------------------------
DROP SEQUENCE "LUNWEN"."SEQ_BASE_PARPER_INFO";
CREATE SEQUENCE "LUNWEN"."SEQ_BASE_PARPER_INFO" MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 CACHE 20;

-- ----------------------------
-- Sequence structure for SEQ_BASE_STUDENT_INFO
-- ----------------------------
DROP SEQUENCE "LUNWEN"."SEQ_BASE_STUDENT_INFO";
CREATE SEQUENCE "LUNWEN"."SEQ_BASE_STUDENT_INFO" MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 CACHE 20;

-- ----------------------------
-- Sequence structure for SEQ_BASE_TEACHER_INFO
-- ----------------------------
DROP SEQUENCE "LUNWEN"."SEQ_BASE_TEACHER_INFO";
CREATE SEQUENCE "LUNWEN"."SEQ_BASE_TEACHER_INFO" MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 CACHE 20;

-- ----------------------------
-- Primary Key structure for table paper
-- ----------------------------
ALTER TABLE "LUNWEN"."paper" ADD CONSTRAINT "SYS_C0011058" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table paper
-- ----------------------------
ALTER TABLE "LUNWEN"."paper" ADD CONSTRAINT "SYS_C0011057" CHECK ("id" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "LUNWEN"."paper" ADD CONSTRAINT "SYS_C007158" CHECK ("id" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table student
-- ----------------------------
ALTER TABLE "LUNWEN"."student" ADD CONSTRAINT "SYS_C0011106" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table student
-- ----------------------------
ALTER TABLE "LUNWEN"."student" ADD CONSTRAINT "SYS_C007159" CHECK ("id" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table teacher
-- ----------------------------
ALTER TABLE "LUNWEN"."teacher" ADD CONSTRAINT "SYS_C0011056" PRIMARY KEY ("id");

-- ----------------------------
-- Checks structure for table teacher
-- ----------------------------
ALTER TABLE "LUNWEN"."teacher" ADD CONSTRAINT "SYS_C0011055" CHECK ("id" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "LUNWEN"."teacher" ADD CONSTRAINT "SYS_C007160" CHECK ("id" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
