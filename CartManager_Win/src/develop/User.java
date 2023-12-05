package develop;

public class User extends Admin{
    private Integer id;
    private Integer vipLevel;
    private String signDate;
    private Float money;
    private String phoneNum;
    private String email;

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public String getVipLevelDetail() {

        if(vipLevel == 0){
            return "铜牌客户";
        }else if(vipLevel == 1){
            return "银牌客户";
        }else if(vipLevel == 2){
            return "金牌客户";
        }
        return "";
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }
    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }
    @Override
    public String getPassword() {
        return super.getPassword();
    }

}
