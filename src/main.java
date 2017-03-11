import com.SCGEOrientaIA.business.DecisionTreeController;
import com.SCGEOrientaIA.dao.util.jdbc.SQLServerPergunta;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Object o = new DecisionTreeController().answerQuestion(new SQLServerPergunta().retrive(null).get(20), 10, false);
		
	}

}
