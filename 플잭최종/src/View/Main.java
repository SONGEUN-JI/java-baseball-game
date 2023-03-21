package View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Controller.Controller;
import Model.DAO;
import Model.DTO;

public class Main {

	public static void main(String[] args) {

		Random rd = new Random();
		Scanner sc = new Scanner(System.in);
		String login_id = null;

		System.out.println("\r\n" + "                                                      _--_     dMb\r\n"
				+ "                                                   __(._  )   d0P\r\n"
				+ "                                                     <  (D)  .MP\r\n"
				+ "                                                   .~ \\ /~```M-.\r\n"
				+ "                                                 .~    V    Mo_ \\\r\n" + "\r\n"
				+ "          -------============((}{)               (   (___. {:)-./\r\n"
				+ "                                                  ~._____.(:}\r\n"
				+ "                                  baseball game    /     .M\\\r\n"
				+ "                                                  /      \"\" \\\r\n"
				+ "                                                  |    /\\   |\r\n"
				+ "                                                  /   /  \\   \\\r\n"
				+ "                                                 /   /    \\   \\\r\n"
				+ "                                                 \\__/      \\__/\r\n"
				+ "                                                 / /        | |\r\n"
				+ "                                                .^V^.      .^V^.\r\n"
				+ "                                                 +-+        +-+");
		while (true) {
			System.out.print("[1]회원가입 [2]로그인 [3]종료 >> ");
			int choice = sc.nextInt();

			if (choice == 1) {
				System.out.println("회원가입");
				System.out.print("id : ");
				String id = sc.next();

				System.out.print("pw : ");
				String pw = sc.next();

//				System.out.print("score : ");
//				int score = sc.next();

				Controller con = new Controller();
				int result = con.con_join(id, pw);

				if (result > 0) {
					System.out.println("회원가입 성공");
				} else {
					System.out.println("회원가입 실패");
				}

			} else if (choice == 2) {
				System.out.println("로그인");

				System.out.print("ID :");
				String id = sc.next();
				System.out.print("PW :");
				String pw = sc.next();

				DAO dao = new DAO();
				String name = dao.login(id, pw);

				// 3. 로그인 성공유무 판단
				if (name != null) {

					// 로그인 성공
					System.out.println(name + "님 환영합니다.");

					login_id = id;

					while (true) {
						System.out.println("[1] 게임시작 [2] 선수 목록 [3] 선수 등록 [4] 로그아웃 ");
						int select = sc.nextInt();
						if (select == 1) {
							// 게임 진행
							DAO dao2 = new DAO();
							ArrayList<DTO> list = dao2.selectAll(id);
							// 결과값을 가지고 아이디 이름 스탯 점수 출력
							System.out.println("이름 \t 스탯");

							for (DTO dto : list) {
								System.out.print(dto.getName() + "\t");
								System.out.print(dto.getStat() + "\t");
								System.out.println();

							}

							System.out.print("선택할 선수의 이름을 입력해주세요: ");
							String playerName = sc.next();
							String selectedPlayer = null;
							for (DTO dto : list) {
								if (dto.getName().equals(playerName)) {
									selectedPlayer = dto.getName();
									break;
								}
							}
							Controller con = new Controller();
							if (selectedPlayer == null) {
								System.out.println("선택한 선수가 없습니다.");
							} else {
								System.out.println(selectedPlayer + " 선수로 게임을 시작합니다.");
								con.playGame(selectedPlayer);
							}

//					        System.out.print("선택할 선수의 이름을 입력해주세요: ");
//					        String playerName = sc.next();
//					        DTO selectedPlayer = null;
//					        for (DTO dto : list) {
//					        	if (dto.getName().equals(playerName)) {
//					        		selectedPlayer = dto;
//					        		break;
//					        	}
//					        }
//					        Controller con = new Controller();
//					        if (selectedPlayer == null) {
//					        	System.out.println("선택한 선수가 없습니다.");
//					        } else {
//					        	System.out.println(selectedPlayer.getName() + " 선수로 게임을 시작합니다.");
//					        	con.playGame(selectedPlayer.getId());
//					        	
//					        }	
						} else if (select == 2) {
							// 선수 목록 열람

							DAO dao2 = new DAO();
							ArrayList<DTO> list = dao2.selectAll(id);
							// 결과값을 가지고 아이디 이름 스탯 점수 출력
							System.out.println("ID \t 이름 \t 스탯 \t 홈런 \t 스트라이크 \t 히트");

							for (DTO dto : list) {
								System.out.print(dto.getId() + "\t");
								System.out.print(dto.getName() + "\t");
								System.out.print(dto.getStat() + "\t");
								System.out.print(dto.getHomerun() + "\t");
								System.out.print(dto.getStrike() + "\t");
								System.out.print(dto.getHit() + "\t");
								System.out.println();
							}

						} else if (select == 3) {

							Controller con = new Controller();

							id = login_id;
							System.out.print("생성할 선수 이름 : ");
							name = sc.next();
							int stat = rd.nextInt(99) + 1;

							int result = con.AddPlayer(id, name, stat);

							if (result > 0) {
								System.out.println("선수 추가 성공.");
							} else {
								System.out.println("메뉴로 돌아갑니다.");
							}

						} else {
							login_id = null;
							System.out.println("로그아웃 완료. 처음메뉴로 돌아갑니다.");
							break;
						}

					}

				} else {
					// 로그인 실패
					System.out.println("아이디나 비밀번호를 다시 한번 확인해주세요.");
				}
			} else

			{
				System.out.println("게임을 종료합니다.");
				break;
			}
		}
	}

	private static String selectAll(String login_id) {
		// TODO Auto-generated method stub
		return null;
	}

	// 이범준 제작 show Result
	public void showResult(String result, int score) {
		if (result.equals("스트라이크")) {
			System.out.println(result + "입니다!");
		} else if (result.equals("안타") || result.equals("홈런")) {
			System.out.println(result + "입니다!");
			System.out.println("현재 점수는 " + score + "점입니다.");
		} else if (result.equals("아웃")) {
			System.out.println("아웃!");
		} else if (result.equals("승리")) {
			System.out.println("승리!");
		}

	}
}
