import java.io.IOException;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GameOverController {

/*	
	@FXML
	void onGameOverAction(ActionEvent event) {
		try {
			StageDB.getGameOverStage().hide();
			StageDB.getMainSound().stop();
			StageDB.getGameOverSound().stop();
			StageDB.getMainStage().show();
			StageDB.getMainSound().play();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
*/
	@FXML
	void exit(ActionEvent event) {
		try {
			System.exit(0);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}	
}
