package com.gyuchan.assignment2;

import com.gyuchan.assignment2.jdbc.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

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
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";

        // 0. if test 테이블 있으면 드랍하고 시작
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("DROP TABLE IF EXISTS testdb.test");
            stmt.executeUpdate();
        } catch (Exception e) {
            LOG.severe("테이블 드랍 중 오류: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            close(stmt, conn);
        }


        // 1. 테이블 생성
        LOG.info("테이블 생성 시작");
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("CREATE TABLE test(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), num INT)");
            stmt.executeUpdate();
            LOG.info("테이블 생성 완료");
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            close(stmt, conn);
        }

        // 2. 데이터 insert
        LOG.info("테이블 insert 시작");
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("INSERT INTO test(name, num) VALUES(?, ?)");
            stmt.setString(1, "이규찬");
            stmt.setInt(2, 1);
            stmt.executeUpdate();
            LOG.info("테이블 insert 완료");
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            close(stmt, conn);
        }

        // 3. 데이터 update
        LOG.info("테이블 update 시작");
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("UPDATE test SET name = ?, num = ? WHERE id = ?");
            stmt.setString(1, "업데이트");
            stmt.setInt(2, 999);
            stmt.setInt(3, 1);
            stmt.executeUpdate();
            LOG.info("테이블 update 완료");
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            close(stmt, conn);
        }

        // 4. 데이터 insert(PK 중복)
        LOG.info("테이블 insert (PK 중복) 시작");
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("INSERT INTO test(id, name, num) VALUES(?, ?, ?)");
            stmt.setInt(1, 1);
            stmt.setString(2, "중복키 발생");
            stmt.setInt(3, 500);
            int result = stmt.executeUpdate();
            System.out.println("result = " + result);
            LOG.info("예상치 못한 성공 - PK 중복이 발생해야 함");
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            LOG.info("테이블 insert (PK 중복) 완료");
        } finally {
            close(stmt, conn);
        }

    }

    private static void close(PreparedStatement stmt, Connection conn) {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
