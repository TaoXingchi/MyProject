package thread;

/**
 * @author TXC
 * date: 2021/3/1 14:32
 * @version 1.0
 */
public class MyRunnable extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("正在执行线程" + i);
        }
    }
}
