package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.StudentDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションから教師を取得し、学校を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // パラメータ取得
        //String subjectCd = req.getParameter("f3");
        String studentNo = req.getParameter("f4");

//        System.out.println("debug-科目コード:学生番号=" + subjectCd + ":" + studentNo);

        // チェック
        if (studentNo == null || studentNo.isEmpty()) {

            //req.setAttribute("message", "科目コードと学生番号は必須です。");
            req.getRequestDispatcher("test_list.jsp").forward(req, res);
            return;
        }

        // 学生情報
        StudentDao studentDao = new StudentDao();
        Student student=studentDao.get(studentNo);

        // 科目
        //SubjectDao subjectDao = new SubjectDao();
//        Subject subject = subjectDao.get(subjectCd, school);

        // テスト情報（1回目固定）
        //TestDao testDao = new TestDao();
//        Test test = testDao.get(student, subject, school, 1);

        // 成績一覧
        TestListStudentDao testListStudentDao = new TestListStudentDao();
        List<TestListStudent> testList = testListStudentDao.filter(student);

        // 属性をセット（JSPに合わせる）
        req.setAttribute("f4", studentNo);              // 入力値再表示用
        req.setAttribute("student", student);           // 氏名など表示用
//        req.setAttribute("test", test);                 // テスト情報（未使用でもOK）
        req.setAttribute("test_list", testList);        // 成績一覧表示用

        // 遷移先
        req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
    }
}
