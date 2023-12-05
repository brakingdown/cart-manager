package develop;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;



public class UserController {
    public static String userFilePath = "./src/develop/User.csv";

    Scanner scanner = new Scanner(System.in);
    CsvManager userFile = new CsvManager(userFilePath);

    public User setUser(Integer Id, String username, Integer vipLevel, String signDate, Float money, String phoneNum, String email, String password){
        User user = new User();
        user.setId(Id);
        user.setUsername(username);
        user.setVipLevel(vipLevel);
        user.setSignDate(signDate);
        user.setMoney(money);
        user.setPhoneNum(phoneNum);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public String[] getUserString(User user){
        String[] userString = new String[8];
        userString[0] = String.valueOf(user.getId());
        userString[1] = user.getUsername();
        userString[2] = user.getVipLevelDetail();
        userString[3] = user.getSignDate();
        userString[4] = String.valueOf(user.getMoney());
        userString[5] = user.getPhoneNum();
        userString[6] = user.getEmail();
        userString[7] = user.getPassword();
        return userString;
    }

    public Integer getVipLevel(String vipLevelDetail){
        if(vipLevelDetail.equals("铜牌客户")){
            return 0;
        }else if(vipLevelDetail.equals("银牌客户")){
            return 1;
        }else if(vipLevelDetail.equals("金牌客户")){
            return 2;
        }
        return -1;
    }

    public User getUser(String username, String password) throws IOException {

        ArrayList<String[]> userList = userFile.read();
        User user = null;
        for (int row = 1; row < userList.size(); row++) {
            if (username.equals(userList.get(row)[1]) && password.equals(userList.get(row)[7])) {

                user = setUser(Integer.valueOf(userList.get(row)[0]),userList.get(row)[1], getVipLevel(userList.get(row)[2]), userList.get(row)[3],Float.valueOf(userList.get(row)[4]),userList.get(row)[5],userList.get(row)[6],userList.get(row)[7]);

            }

        }
        return user;
    }

    public User getUserByPhoneNum(String phoneNum) throws IOException {
        ArrayList<String[]> userList = userFile.read();
        User user = null;
        for (int row = 1; row < userList.size(); row++) {
            if (phoneNum.equals(userList.get(row)[5])) {

                user = setUser(Integer.valueOf(userList.get(row)[0]),userList.get(row)[1], getVipLevel(userList.get(row)[2]),userList.get(row)[3],Float.valueOf(userList.get(row)[4]),userList.get(row)[5],userList.get(row)[6],userList.get(row)[7]);

            }

        }
        return user;
    }


    public User getUserById(String id) throws IOException{
        ArrayList<String[]> userList = userFile.read();
        User user = null;
        for (int row = 1; row < userList.size(); row++) {
            if (id.equals(userList.get(row)[0])) {
                user = setUser(Integer.valueOf(userList.get(row)[0]),userList.get(row)[1], getVipLevel(userList.get(row)[2]),userList.get(row)[3],Float.valueOf(userList.get(row)[4]),userList.get(row)[5],userList.get(row)[6],userList.get(row)[7]);
            }

        }
        return user;
    }


    public User getUserByUsername(String username) throws IOException{
        ArrayList<String[]> userList = userFile.read();
        User user = null;
        for (int row = 1; row < userList.size(); row++) {
            if (username.equals(userList.get(row)[1])) {

                user = setUser(Integer.valueOf(userList.get(row)[0]),userList.get(row)[1], getVipLevel(userList.get(row)[2]),userList.get(row)[3],Float.valueOf(userList.get(row)[4]),userList.get(row)[5],userList.get(row)[6],userList.get(row)[7]);

            }

        }
        return user;
    }

    public User getUserByUsernameAndEmail(String username, String email) throws IOException{
        ArrayList<String[]> userList = userFile.read();
        User user = null;
        for (int row = 1; row < userList.size(); row++) {
            if (username.equals(userList.get(row)[1]) && email.equals(userList.get(row)[6])) {

                user = setUser(Integer.valueOf(userList.get(row)[0]),userList.get(row)[1], getVipLevel(userList.get(row)[2]),userList.get(row)[3],Float.valueOf(userList.get(row)[4]),userList.get(row)[5],userList.get(row)[6],userList.get(row)[7]);

            }

        }
        return user;
    }

    public ArrayList<User> getAllUsers() throws IOException {
        ArrayList<String[]> userList = userFile.read();
        ArrayList<User> users = new ArrayList<>();
        for (int row = 1; row < userList.size(); row++) {

            User user = setUser(Integer.valueOf(userList.get(row)[0]),userList.get(row)[1], getVipLevel(userList.get(row)[2]),userList.get(row)[3],Float.parseFloat(userList.get(row)[4]),userList.get(row)[5],userList.get(row)[6],userList.get(row)[7]);

            users.add(user);

        }
        return users;
    }

    public void showUser(User user){
        System.out.println();
        System.out.println(user.getId()+" "+user.getUsername()+" "+user.getVipLevelDetail()+" "+user.getSignDate()+" "+user.getMoney()+" "+user.getPhoneNum()+ " "+user.getEmail());
    }

    public void showAllUsers() throws IOException {
        ArrayList<User> users = getAllUsers();

        userFile.showHeader();
        for(User user:users){
            System.out.println(user.getId()+" "+user.getUsername()+" "+user.getVipLevelDetail()+" "+user.getSignDate()+" "+user.getMoney()+" "+user.getPhoneNum()+ " "+user.getEmail());
        }
    }


    public void deleteUser(String phoneNum) throws IOException {
        ArrayList<String[]> userList = userFile.read();

        for(int row = 1; row < userList.size(); row++){
            if(phoneNum.equals(userList.get(row)[5])){
                System.out.println("请确认是否删除以下客户(y/n)");
                showUser(getUserByPhoneNum(phoneNum));
                String judge = scanner.next();
                if(judge.equals("y") || judge.equals("Y")){
                    userList.remove(row);
                    userFile.rewrite(userList);
                    System.out.println("删除成功");
                    return;
                } else if(judge.equals("n") || judge.equals("N")){
                    System.out.println("取消删除");
                    return;
                } else{
                    System.out.println("输入错误");
                    return;
                }

            }
        }
        System.out.println("用户不存在");
    }

    public void userRegister() throws IOException {
        ArrayList<String[]> userList = userFile.read();

        System.out.println("请输入用户名:");
        String username = scanner.next();

        int id = 0;
        for(int row = 1; row < userList.size(); row++){
            if(username.equals(userList.get(row)[1])){
                System.out.println("用户名已存在");
                return;
            }
            id = Integer.parseInt(userList.get(row)[0]);
        }
        id = id + 1;

        System.out.println();
        System.out.println("请输入密码（大于8位且包含大小写字母与特殊符号）:");
        String password;
        while(true){
            password = scanner.next();
            if (!new Password().checkPassword(password)){
                System.out.println("密码要求大于8位且包含大小写字母与特殊符号,请重新输入:");
            }
            else break;
        }
        System.out.println();
        System.out.println("请输入手机号:");
        String phoneNum = scanner.next();
        System.out.println();
        System.out.println("请输入邮箱:");
        String email = scanner.next();
        System.out.println();

        //获取当前时间
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String signDate = df.format(new Date());

        userFile.write(getUserString(setUser(id, username,0, signDate, 0f, phoneNum, email, password)));
        System.out.println("注册成功");
    }

    public void modifyUser(String[] modifiedUser) throws IOException{
        String id = modifiedUser[0];
        ArrayList<String[]> userList = userFile.read();
        for(int row = 1; row < userList.size(); row++){
            if(id.equals(userList.get(row)[0])){
                userList.set(row, modifiedUser);
                userFile.rewrite(userList);
                return;
            }
        }
    }

}

