/**
 * ClassName: test <br/>
 * Description: <br/>
 * date: 2020/7/24 9:38<br/>
 *
 * @author TXC<br />
 */
public class test {
    private String filterCode(String string) {
        if (string != null) {
            string = string.trim();
            byte[] zero = new byte[1];
            zero[0] = (byte) 0;
            String s = new String(zero);
            string = string.replace(s, "");
        }
        return string;
    }

    public static void main(String[] args) {
        String str = "510010";
        String t = str.replaceAll("(00){1,}$", "");
        System.out.println(t);
    }
}
