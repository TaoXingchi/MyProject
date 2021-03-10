package thread;

/**
 * @author TXC
 * date: 2021/3/1 14:09
 * @version 1.0
 */
public class demo1 {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("正在执行线程");
            }
        };
        new Thread(runnable).start();
    }
}
