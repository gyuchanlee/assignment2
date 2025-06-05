package com.gyuchan.assignment2.jdbc;

import com.gyuchan.assignment2.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Repository {

    private static final Logger LOG = Logger.getLogger(Repository.class.getName());

    public void createTable() {
        Connection conn = null;
        PreparedStatement stmt = null;

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
    }

    public void insert(String name, int num) {
        Connection conn = null;
        PreparedStatement stmt = null;

        // 2. 데이터 insert
        LOG.info("테이블 insert 시작");
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("INSERT INTO test(name, num) VALUES(?, ?)");
            stmt.setString(1, name);
            stmt.setInt(2, num);
            stmt.executeUpdate();
            LOG.info("테이블 insert 완료");
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            close(stmt, conn);
        }
    }

    public void update(String name, int num, int pk) {
        Connection conn = null;
        PreparedStatement stmt = null;

        // 3. 데이터 update
        LOG.info("테이블 update 시작");
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("UPDATE test SET name = ?, num = ? WHERE id = ?");
            stmt.setString(1, name);
            stmt.setInt(2, num);
            stmt.setInt(3, pk);
            stmt.executeUpdate();
            LOG.info("테이블 update 완료");
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            close(stmt, conn);
        }
    }

    public void insertDuplicate(int pk, String name, int num) {
        Connection conn = null;
        PreparedStatement stmt = null;

        // 4. 데이터 insert(PK 중복)
        LOG.info("테이블 insert (PK 중복) 시작");
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("INSERT INTO test(id, name, num) VALUES(?, ?, ?)");
            stmt.setInt(1, pk);
            stmt.setString(2, name);
            stmt.setInt(3, num);
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

    private void close(PreparedStatement stmt, Connection conn) {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
