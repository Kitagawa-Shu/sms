package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションからログイン中の教員情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // リクエストパラメータ（科目コードとクラス名）を取得
        String subject_cd = req.getParameter("cd");
        String class_name = req.getParameter("class_name");

        // DAOの生成
        SubjectDao subjectDao = new SubjectDao();

        // 該当の科目情報を取得（存在すれば編集、なければ新規）
        Subject subject = null;
        if (subject_cd != null && !subject_cd.isEmpty()) {
            subject = subjectDao.get(subject_cd, teacher.getSchool());
        }

        // 科目情報を画面に渡す
        if (subject != null) {
            req.setAttribute("cd", subject.getCd());
            req.setAttribute("name", subject.getName());
            req.setAttribute("school", subject.getSchool());
        } else {
            req.setAttribute("cd", "");
            req.setAttribute("name", "");
            req.setAttribute("school", teacher.getSchool());
        }

        // クラス名も渡す（必要なら）
        req.setAttribute("class_name", class_name);

        // 科目登録画面へフォワード
        req.getRequestDispatcher("subject_create.jsp").forward(req, res);
    }
}
