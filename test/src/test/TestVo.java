package test;

/**
 * ClassName: TestVo <br/>
 * Description: <br/>
 * date: 2020/7/24 9:40<br/>
 *
 * @author TXC<br />
 */
public class TestVo {
    public static void main(String[] args) {
        Vo vo = new Vo();
        vo.setDateType(Vo.DateType.YEAR);
        Vo.DateType datetype = vo.getDateType();
        System.out.println(Vo.DateType.YEAR.equals(datetype));
    }
}
