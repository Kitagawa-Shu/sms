package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.TestListStudent;
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
        List<TestListStudent> test_list = null;

        if (student_no != null && !student_no.isEmpty()) {
            // 学生番号が指定されていれば、その学生の成績を全件取得
            Student student = new Student();
            student.setNo(student_no);

            TestListStudentDao testListStudentDao = new TestListStudentDao();
            test_list = testListStudentDao.filter(student);

            if (test_list == null || test_list.isEmpty()) {
                req.setAttribute("message", "該当する成績がありません。");
            }

            // 他の検索条件はクリア
            req.setAttribute("f1", null);
            req.setAttribute("f2", null);
            req.setAttribute("f3", null);

        } else if (class_num != null && !class_num.isEmpty()
                && subject_cd != null && !subject_cd.isEmpty()
                && ent_year != null && !ent_year.isEmpty()) {
            // 入学年度＋クラス＋科目指定での成績取得
            // （必要に応じて、TestDaoのfilterなどを使う実装をここに追加）

            req.setAttribute("message", "この条件での成績取得は未実装です。");

        } else {
            req.setAttribute("message", "検索条件を指定してください。");
        }

        // 検索条件保持
        req.setAttribute("f1", ent_year);
        req.setAttribute("f2", class_num);
        req.setAttribute("f3", subject_cd);
        req.setAttribute("f4", student_no);

        // 成績リストをセット
        req.setAttribute("test_list_student", test_list);


        req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
    }
}
