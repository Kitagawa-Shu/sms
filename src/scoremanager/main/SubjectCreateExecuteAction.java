package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @SuppressWarnings("null")
	@Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションからログイン中の教員情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // リクエストパラメータ（科目コードとクラス名）を取得
        String subject_cd = req.getParameter("cd");
        String subject_name = req.getParameter("name");

        // DAOの生成
        SubjectDao subjectDao = new SubjectDao();

        // 該当の科目情報を取得（存在すれば編集、なければ新規）
        Subject subject = new Subject();
        if (subject_cd != null && !subject_cd.isEmpty()) {
        	subject.setCd(subject_cd);
			subject.setName(subject_name);
			subject.setSchool(teacher.getSchool());

			// saveメソッドで情報を登録
			subjectDao.save(subject);
        }

        // 科目情報を画面に渡す
        if (subject != null) {
            req.setAttribute("cd", subject.getCd());
            req.setAttribute("name", subject.getName());
            req.setAttribute("school", subject.getSchool());
        } else {
            req.setAttribute("cd", "");
            req.setAttribute("name", "");

        }

        // クラス名も渡す（必要なら）
        req.setAttribute("cd", subject_cd);
        req.setAttribute("name", subject_name);

        // 科目登録画面へフォワード
        req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
    }
}
