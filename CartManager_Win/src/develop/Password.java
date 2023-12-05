package develop;

import java.util.Random;

public class Password {
    //数字
    private static final String REG_NUMBER = ".*\\d+.*";
    //小写字母
    private static final String REG_UPPERCASE = ".*[A-Z]+.*";
    //大写字母
    private static final String REG_LOWERCASE = ".*[a-z]+.*";
    //特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)
    private static final String REG_SYMBOL = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";

    public boolean checkPassword(String password) {

        //密码为空及长度大于8位小于30位判断
        if (password == null || password.length() < 8 || password.length() > 30) return false;

        int i = 0;

        if (password.matches(REG_NUMBER)) i++;
        if (password.matches(REG_LOWERCASE)) i++;
        if (password.matches(REG_UPPERCASE)) i++;
        if (password.matches(REG_SYMBOL)) i++;

        return i >= 4;
    }

    public  String getRandomPassword(int len) {
        String result= this.makeRandomPassword(len);
        if (result.matches(".*[a-z]{1,}.*") && result.matches(".*[A-Z]{1,}.*") && result.matches(".*\\d{1,}.*") && result.matches(".*[~!@#$%^&*\\.?]{1,}.*")) {
            return result;
        }
        return makeRandomPassword(len);
    }

    //产生8位随机数
    public  String makeRandomPassword(int len){
        char charr[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890~!@#$%^&*.?".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int x = 0; x < len; ++x) {
            sb.append(charr[r.nextInt(charr.length)]);
        }
        return sb.toString();

    }

}
