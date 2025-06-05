package com.gyuchan.assignment2;

import com.gyuchan.assignment2.jdbc.DBConnection;
import com.gyuchan.assignment2.jdbc.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Main {

//    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        /**
         * JDBC API 사용 -> local mysql crud 수행 프로그램 구현
         * local mysql -> testdb 데이터베이스에서 진행
         * 1. 테이블 생성
         * 2. 데이터 insert
         * 3. 데이터 update
         * 4. 데이터 insert(PK 중복)
         * - 트랜잭션 보장하기
         */

        System.out.println("Hello World! 과제2");

        Repository repository = new Repository();
        // 테이블 생성
        repository.createTable();
        // 인서트
        repository.insert("이규찬", 1);
        // 업데이트
        repository.update("이규찬", 999, 1);
        // insert(PK 중복)
        repository.insertDuplicate(1, "중복키 발생", 500);
    }

}
