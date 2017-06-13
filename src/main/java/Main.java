import com.edited.util.JDBCUtil;

/**
 * Created by taras.zyhmunt on 13.06.2017.
 */
public class Main {

    public static void main(String[] args) {
        JDBCUtil instance = JDBCUtil.getInstance();
        instance.getFromDB();
    }
}
