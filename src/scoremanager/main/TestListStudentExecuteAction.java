package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import bean.TestListStudent;
import dao.SubjectDao;
import dao.TestDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションから学校・教師・学生情報を取得
        HttpSession session = req.getSession();
        School school = (School) session.getAttribute("school");
        Teacher teacher = (Teacher) session.getAttribute("user");
        Student student = (Student) session.getAttribute("student");

        // パラメータ取得
        String subjectCd = req.getParameter("subject_cd");
        String classCd = req.getParameter("class_cd");
        String testCd = req.getParameter("test_cd");

        // 入力チェック（subject_cd / class_cd / test_cd）
        if (subjectCd == null || subjectCd.isEmpty() ||
            classCd == null || classCd.isEmpty() ||
            testCd == null || testCd.isEmpty()) {

            req.setAttribute("message", "すべてのフィールドを入力してください。");
            req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
            return;
        }

        // 変換
        int testNo = Integer.parseInt(testCd);

        // 科目情報の取得
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(subjectCd, school);

        // テスト情報の取得
        TestDao testDao = new TestDao();
        Test test = testDao.get(student, subject, school, testNo);

        // 学生の成績一覧取得
        TestListStudentDao testListStudentDao = new TestListStudentDao();
        List<TestListStudent> testList = testListStudentDao.filter(student);

        // リクエスト属性へセット
        req.setAttribute("subject", subject);
        req.setAttribute("test", test);
        req.setAttribute("test_list", testList);

        // JSP へフォワード
        req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
    }
}
