//package dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//import bean.School;
//import bean.Subject;
//
//public class SubjectDao extends Dao {
//
//    /**
//     * 主キーによる検索
//     */
//    public Subject get(String cd, School school) throws Exception {
//        Subject subject = null;
//
//        Connection con = getConnection();
//
//        PreparedStatement st = con.prepareStatement(
//            "SELECT * FROM subject WHERE cd = ? AND school_cd = ?");
//        st.setString(1, cd);
//        st.setString(2, school.getCd());
//        ResultSet rs = st.executeQuery();
//
//        if (rs.next()) {
//            subject = new Subject();
//            subject.setCd(rs.getString("cd"));
//            subject.setName(rs.getString("name"));
//            subject.setSchool(school);
//        } else {
//            subject = null;
//        }
//
//        st.close();
//        con.close();
//        return subject;
//    }
//
//    /**
//     * 学校単位の一覧取得
//     */
//    public List<Subject> filter(School school) throws Exception {
//        List<Subject> list = new ArrayList<>();
//
//        Connection con = getConnection();
//
//        PreparedStatement st = con.prepareStatement(
//            "SELECT * FROM subject WHERE school_cd = ? ORDER BY cd");
//        st.setString(1, school.getCd());
//        ResultSet rs = st.executeQuery();
//
//        while (rs.next()) {
//            Subject subject = new Subject();
//            subject.setCd(rs.getString("cd"));
//            subject.setName(rs.getString("name"));
//            subject.setSchool(school);
//            list.add(subject);
//        }
//
//        st.close();
//        con.close();
//        return list;
//    }
//
//    /**
//     * 登録・更新処理（修正済み）
//     */
//    public boolean save(Subject subject) throws Exception {
//        Connection con = getConnection();
//
//        // 主キー存在確認
//        PreparedStatement st = con.prepareStatement(
//            "SELECT COUNT(*) FROM subject WHERE cd = ? AND school_cd = ?");
//        st.setString(1, subject.getCd());
//        st.setString(2, subject.getSchool().getCd());
//        ResultSet rs = st.executeQuery();
//        rs.next();
//        int count = rs.getInt(1);
//        st.close();
//
//        if (count == 0) {
//            // INSERT
//            st = con.prepareStatement(
//                "INSERT INTO subject (cd, name, school_cd) VALUES (?, ?, ?)");
//            st.setString(1, subject.getCd());
//            st.setString(2, subject.getName());
//            st.setString(3, subject.getSchool().getCd());
//        } else {
//            // UPDATE（パラメータ順を修正）
//            st = con.prepareStatement(
//                "UPDATE subject SET name = ? WHERE cd = ? AND school_cd = ?");
//            st.setString(1, subject.getName());            // name
//            st.setString(2, subject.getCd());              // cd
//            st.setString(3, subject.getSchool().getCd());  // school_cd
//        }
//
//        int result = st.executeUpdate();
//        st.close();
//        con.close();
//        return result > 0;
//    }
//
//    /**
//     * 削除処理
//     */
//    public boolean delete(Subject subject) throws Exception {
//        Connection con = getConnection();
//
//        PreparedStatement st = con.prepareStatement(
//            "DELETE FROM subject WHERE cd = ? AND school_cd = ?");
//        st.setString(1, subject.getCd());
//        st.setString(2, subject.getSchool().getCd());
//        int result = st.executeUpdate();
//
//        st.close();
//        con.close();
//        return result > 0;
//    }
//}



package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    /**
     * 主キーによる検索
     */
    public Subject get(String cd, School school) throws Exception {
        Subject subject = null;

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM subject WHERE cd = ? AND school_cd = ?");
        st.setString(1, cd);
        st.setString(2, school.getCd());
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            subject = new Subject();
            subject.setCd(rs.getString("cd"));
            subject.setName(rs.getString("name"));
            subject.setSchool(school);

        }else{
        	subject=null;
        }

        st.close();
        con.close();
        return subject;
    }

    /**
     * 学校単位の一覧取得
     */
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM subject WHERE school_cd = ? ORDER BY cd");
        st.setString(1, school.getCd());
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Subject subject = new Subject();
            subject.setCd(rs.getString("cd"));
            subject.setName(rs.getString("name"));
            subject.setSchool(school);
            list.add(subject);
        }

        st.close();
        con.close();
        return list;
    }

    /**
     * 登録・更新処理
     */
//    public boolean save(Subject subject) throws Exception {
//        Connection con = getConnection();
//
//        // 主キー存在確認
//        PreparedStatement st = con.prepareStatement(
//            "SELECT COUNT(*) FROM subject WHERE cd = ? AND school_cd = ?");
//        st.setString(1, subject.getCd());
//        st.setString(2, subject.getSchool().getCd());
//        ResultSet rs = st.executeQuery();
//        rs.next();
//        int count = rs.getInt(1);
//        st.close();
//
//        if (count == 0) {
//            // INSERT
//            st = con.prepareStatement(
//                "INSERT INTO subject (cd, name, school_cd) VALUES (?, ?, ?)");
//        } else {
//            // UPDATE
//            st = con.prepareStatement(
//                "UPDATE subject SET name = ? WHERE cd = ? AND school_cd = ?");
//        }
//
//        st.setString(1, subject.getCd());
//        st.setString(2, subject.getName());
//        st.setString(3, subject.getSchool().getCd());
//        int result = st.executeUpdate();
//        st.close();
//        con.close();
//        return result > 0;
//    }

    public boolean save(Subject subject) throws Exception {
        Connection con = getConnection();

        // 主キー存在確認
        PreparedStatement st = con.prepareStatement(
            "SELECT COUNT(*) FROM subject WHERE cd = ? AND school_cd = ?");
        st.setString(1, subject.getCd());
        st.setString(2, subject.getSchool().getCd());
        ResultSet rs = st.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        st.close();

        if (count == 0) {
            // INSERT
            st = con.prepareStatement(
                "INSERT INTO subject (cd, name, school_cd) VALUES (?, ?, ?)");
            st.setString(1, subject.getCd());
            st.setString(2, subject.getName());
            st.setString(3, subject.getSchool().getCd());
        } else {
            // ✅ UPDATE (順序修正)
            st = con.prepareStatement(
                "UPDATE subject SET name = ? WHERE cd = ? AND school_cd = ?");
            st.setString(1, subject.getName());            // name
            st.setString(2, subject.getCd());              // cd
            st.setString(3, subject.getSchool().getCd());  // school_cd
        }

        int result = st.executeUpdate();
        st.close();
        con.close();
        return result > 0;
    }




    /**
     * 削除処理
     */
    public boolean delete(Subject subject) throws Exception {
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "DELETE FROM subject WHERE cd = ? AND school_cd = ?");
        st.setString(1, subject.getCd());
        st.setString(2, subject.getSchool().getCd());
        int result = st.executeUpdate();

        st.close();
        con.close();
        return result > 0;
    }
}
