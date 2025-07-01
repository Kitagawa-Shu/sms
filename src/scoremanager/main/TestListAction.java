package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.TestListStudent;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッション情報から学校コード取得
        HttpSession session = req.getSession();
        String school_cd = (String) session.getAttribute("school_cd");

        // 入力パラメータ取得
        String ent_year = req.getParameter("f1");
        String class_num = req.getParameter("f2");
        String subject_cd = req.getParameter("f3");
        String student_no = req.getParameter("f4");

        // Schoolオブジェクト生成
        School school = new School();
        school.setCd(school_cd);

        // 科目一覧取得（セレクトボックス表示用）
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subject_list = subjectDao.filter(school);
        req.setAttribute("subject_list", subject_list);

        // 成績リスト用リスト
        List<TestListStudent> test_list = new ArrayList<>();
        TestListStudentDao testListStudentDao = new TestListStudentDao();

        if (student_no != null && !student_no.isEmpty()) {
            // 学生番号が指定されている場合、その学生の成績を取得
            Student student = new Student();
            student.setNo(student_no);
            List<TestListStudent> result = testListStudentDao.filter(student);
            if (result != null) test_list.addAll(result);

            if (test_list.isEmpty()) {
                req.setAttribute("message", "該当する成績がありません。");
            }

            // 他の検索条件はクリア
            req.setAttribute("f1", null);
            req.setAttribute("f2", null);
            req.setAttribute("f3", null);
            req.setAttribute("f4", student_no);

        } else if (ent_year != null && !ent_year.isEmpty()
                && class_num != null && !class_num.isEmpty()
                && subject_cd != null && !subject_cd.isEmpty()) {

            // クラス・年度・在学中の学生一覧を取得
            StudentDao studentDao = new StudentDao();
            List<Student> students = studentDao.filter(school, Integer.parseInt(ent_year), class_num, true);

            for (Student student : students) {
                List<TestListStudent> result = testListStudentDao.filter(student);
                for (TestListStudent tls : result) {
                    if (tls.getSubjectCd() != null &&
                        tls.getSubjectCd().trim().equals(subject_cd.trim())) {
                        test_list.add(tls);
                    }
                }
            }

            if (test_list.isEmpty()) {
                req.setAttribute("message", "該当する成績がありません。");
            }

            // 検索条件保持
            req.setAttribute("f1", ent_year);
            req.setAttribute("f2", class_num);
            req.setAttribute("f3", subject_cd);
            req.setAttribute("f4", null);
        }

        // 成績リストをセット
        req.setAttribute("test_list_student", test_list);

        // JSP へフォワード
        req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
    }
}
