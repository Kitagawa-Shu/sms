package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {
//
//    // データベース接続情報（自分の環境にあわせて書き換えてください）
//    private static final String URL = "jdbc:h2:tcp://localhost/~/exam";
//    private static final String USER = "sa";
//    private static final String PASSWORD = "";
//
//    // JDBCドライバの読み込み（初回実行時に一度だけ）
//    static {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQLドライバ
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    // ResultSet を List<TestListStudent> に変換する
    private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
        List<TestListStudent> list = new ArrayList<>();

        while (rSet.next()) {
            TestListStudent test = new TestListStudent();
            test.setSubjectName(rSet.getString("subject_name"));
            test.setSubjectCd(rSet.getString("subject_cd"));
            test.setNum(rSet.getInt("no"));
            test.setPoint(rSet.getInt("point"));
            list.add(test);
        }

        return list;
    }

    // 学生情報をもとに成績リストを取得する
    public List<TestListStudent> filter(Student student) throws Exception {
        List<TestListStudent> list;

        String sql =
            "SELECT s.name AS subject_name, s.cd AS subject_cd, t.no, t.point "+
            "FROM test AS t "+
            "JOIN subject AS s ON t.subject_cd = s.cd "+
            "WHERE t.student_no = ? "+
            "ORDER BY t.no ";

        try (
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            stmt.setString(1, student.getNo());

            try (ResultSet rSet = stmt.executeQuery()) {
                list = postFilter(rSet);
            }
        }

        return list;
    }
}
