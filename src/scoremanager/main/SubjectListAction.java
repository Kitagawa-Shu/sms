package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TeacherDao;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String subject_cd = "";
        String subject_name = "";
        int subject = 0;

        Subject subjects = new Subject();
        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao  subjectDao = new SubjectDao();
        TeacherDao teacherDao = new TeacherDao();

        System.out.println("debug:" + subjectDao.filter(teacher.getSchool()));

        List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

        req.setAttribute("subjects", subjectList);


        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}
