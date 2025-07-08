package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションチェック
        HttpSession session = req.getSession(false);


        Teacher teacher = (Teacher) session.getAttribute("user");
        //School school = teacher.getSchool();

        // パラメータ取得
        String studentNo = req.getParameter("f4");

        Map<String, String> errors = new HashMap<>();

        if (studentNo == null || studentNo.isEmpty()) {
            errors.put("studentNo", "学生番号を入力してください");
        }

        StudentDao studentDao = new StudentDao();
        Student student = null;

        if (errors.isEmpty()) {
            student = studentDao.get(studentNo);

            if (student == null) {
                errors.put("studentNotFound", "指定された学生情報が存在しません");
            }
        }

        List<TestListStudent> testList = new ArrayList<>();

        if (errors.isEmpty()) {
            TestListStudentDao testListStudentDao = new TestListStudentDao();
            testList = testListStudentDao.filter(student);
        }

        // 表示に必要な科目情報（検索用セレクトボックス用）
        SubjectDao subjectDao = new SubjectDao();
        req.setAttribute("subject_list", subjectDao.filter(teacher.getSchool()));



        // リクエストスコープへセット
        req.setAttribute("f4", studentNo);
        req.setAttribute("student", student);
        req.setAttribute("test_list", testList);
        req.setAttribute("errors", errors);

        req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
    }
}
