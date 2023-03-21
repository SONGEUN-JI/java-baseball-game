package Controller;

import Model.DAO;
import Model.DTO;
import View.Main;

public class Controller {

	DAO con_dao = new DAO();
	DTO con_dto = new DTO();

	String ans = null;

	int result = 0;

	public int con_join(String id, String pw) {
		con_dto = new DTO(id, pw);

		result = con_dao.join(con_dto);

		if (result > 0) {
			ans = "회원가입 성공";
		} else {
			ans = "회원가입 실패";
		}

		return result;

	}

	public int AddPlayer(String id, String name, int stat) {
		con_dto = new DTO(id, name, stat);

		result = con_dao.AddPlayer(id, name, stat);

		if (result > 0) {
			ans = "선수 추가 성공";
		} else {
			ans = "선수 추가 실패";
		}

		return result;
	}

	public void playGame(String id) {
		DTO pitcher = con_dao.getRandomPlayer(); // 랜덤으로 투수 선택
		int batterStatus = con_dao.getStat(id);
		int pitcherStatus = pitcher.getStat(); // 랜덤으로 선택한 투수의 스탯 가져오기

		int difference = batterStatus - pitcherStatus;
		String result = "";
		int score = 0;
		int strikes = 0;
		int hit = 0;
		int homerun = 0;
		String end_result = null;
		while (strikes < 3 && score < 10) {
			if (difference <= 10) { // 수정
				System.out.println("스트라이크!");
				result = "스트라이크";
				con_dao.addResult(id, 0, 0, 1, 0);
				strikes++;
				if (strikes == 3) {
					end_result = "아웃";
					System.out.println(end_result);
				}
			} else if (difference <= 50) { // 수정
				System.out.println("안타!");
				result = "안타";
				score++;
				if (score >= 10) {
					end_result = "승리";
					System.out.println(end_result);
				}
				con_dao.addResult(id, 0, 1, 0, 1);
			} else {
				System.out.println("홈런!");
				result = "홈런";

				score = score + 2;
				if (score >= 10) {
					end_result = "승리";
					System.out.println(end_result);
				}
				con_dao.addResult(id, 1, 0, 0, 2);
			}

//			if (strikes == 3) {
//				result = "아웃";
//			} else if (score >= 10) {
//				result = "승리";
//			}
//
//			if (!result.equals("아웃") && !result.equals("승리")) {
//				difference = con_dao.getStat(id) - pitcher.getStat();
//			}
//		}

//		
//		view.showResult(result, score);
//		
//		if (!result.equals("아웃") && !result.equals("승리")) {
//			difference = con_dao.getStat(id) - pitcher.getStat();
//		}
		}
	}
}
