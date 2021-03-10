package top.solove.convert2pdf.util;



/**
 * @author TXC
 */
public class OSinfoUtils {

    private static String OS = System.getProperty("os.name").toLowerCase();

    private static OSinfoUtils _instance = new OSinfoUtils();

    private EPlatform platform;

    private OSinfoUtils() {
    }

    private static boolean isLinux() {
        return OS.indexOf("linux") >= 0;
    }

    private static boolean isMacOS() {
        return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") < 0;
    }

    private static boolean isMacOSX() {
        return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") > 0;
    }

    private static boolean isWindows() {
        return OS.indexOf("windows") >= 0;
    }

    private static boolean isOS2() {
        return OS.indexOf("os/2") >= 0;
    }

    private static boolean isSolaris() {
        return OS.indexOf("solaris") >= 0;
    }

    private static boolean isSunOS() {
        return OS.indexOf("sunos") >= 0;
    }

    private static boolean isMPEiX() {
        return OS.indexOf("mpe/ix") >= 0;
    }

    private static boolean isHPUX() {
        return OS.indexOf("hp-ux") >= 0;
    }

    private static boolean isAix() {
        return OS.indexOf("aix") >= 0;
    }

    private static boolean isOs390() {
        return OS.indexOf("os/390") >= 0;
    }

    private static boolean isFreeBsd() {
        return OS.indexOf("freebsd") >= 0;
    }

    private static boolean isIrix() {
        return OS.indexOf("irix") >= 0;
    }

    private static boolean isDigitalUnix() {
        return OS.indexOf("digital") >= 0 && OS.indexOf("unix") > 0;
    }

    private static boolean isNetWare() {
        return OS.indexOf("netware") >= 0;
    }

    private static boolean isOsF1() {
        return OS.indexOf("osf1") >= 0;
    }

    private static boolean isOpenVms() {
        return OS.indexOf("openvms") >= 0;
    }

    /**
     * 获取操作系统名字
     *
     * @return 操作系统名
     */
    public static EPlatform getOsName() {
        if (isAix()) {
            _instance.platform = EPlatform.AIX;
        } else if (isDigitalUnix()) {
            _instance.platform = EPlatform.Digital_Unix;
        } else if (isFreeBsd()) {
            _instance.platform = EPlatform.FreeBSD;
        } else if (isHPUX()) {
            _instance.platform = EPlatform.HP_UX;
        } else if (isIrix()) {
            _instance.platform = EPlatform.Irix;
        } else if (isLinux()) {
            _instance.platform = EPlatform.Linux;
        } else if (isMacOS()) {
            _instance.platform = EPlatform.Mac_OS;
        } else if (isMacOSX()) {
            _instance.platform = EPlatform.Mac_OS_X;
        } else if (isMPEiX()) {
            _instance.platform = EPlatform.MPEiX;
        } else if (isNetWare()) {
            _instance.platform = EPlatform.NetWare_411;
        } else if (isOpenVms()) {
            _instance.platform = EPlatform.OpenVMS;
        } else if (isOS2()) {
            _instance.platform = EPlatform.OS2;
        } else if (isOs390()) {
            _instance.platform = EPlatform.OS390;
        } else if (isOsF1()) {
            _instance.platform = EPlatform.OSF1;
        } else if (isSolaris()) {
            _instance.platform = EPlatform.Solaris;
        } else if (isSunOS()) {
            _instance.platform = EPlatform.SunOS;
        } else if (isWindows()) {
            _instance.platform = EPlatform.Windows;
        } else {
            _instance.platform = EPlatform.Others;
        }
        return _instance.platform;
    }

}

