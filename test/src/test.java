import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);
        LocalDateTime ldt = LocalDateTime.parse("2021-02-21 17:07:05", df);
        System.out.println("LocalDateTime转成String类型的时间：" + localTime);
        System.out.println("String类型的时间转成LocalDateTime：" + ldt.getDayOfWeek().getValue());
    }
}
