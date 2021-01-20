package java8;

import java.util.function.Consumer;

/**
 * ClassName: ConsumerTest
 * Description:
 * date: 2020/11/11 15:50
 *
 * @author TXC
 */
public class ConsumerTest {
    private static void test(double money, Consumer<Double> con){
        con.accept(money);
    }

    public static void main(String[] args) {
        double money = 10000;
        Consumer consumer = m->System.out.println("1这次消费了"+m+"元");
        Consumer consumer1 = s->System.out.println("2这次消费了"+s+"元");
        test(money,consumer.andThen(consumer1.andThen(consumer)));
        System.out.println(money);
    }

}
