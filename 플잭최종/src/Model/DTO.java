package Model;

public class DTO {

	private String id;
	private String pw;
	private int score;

	private String name;
	private int stat;
	private int homerun;
	private int hit;
	private int strike;

	public DTO() {

	}

	public DTO(String id) {
		this.id = id;
	}

	public DTO(String id, String pw) {
		this.id = id;
		this.pw = pw;

	}

	public void AddPlayer(String id, String name, int stat) {
		this.id = id;
		this.name = name;
		this.stat = stat;
		this.homerun = homerun;
		this.strike = strike;
		this.hit = hit;
	}

	public DTO(String id, String name, int stat) {
		this.id = id;
		this.name = name;
		this.stat = stat;
	}

	public DTO(String id, String name, int stat, int homerun, int strike, int hit) {
		this.id = id;
		this.name = name;
		this.stat = stat;
		this.homerun = homerun;
		this.strike = strike;
		this.hit = hit;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getStat() {
		return stat;
	}

	public void setStat(int stat) {
		this.stat = stat;
	}

	public int getHomerun() {
		return homerun;
	}

	public void setHomerun(int homerun) {
		this.homerun = homerun;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getStrike() {
		return strike;
	}

	public void setStrike(int strike) {
		this.strike = strike;
	}

}
