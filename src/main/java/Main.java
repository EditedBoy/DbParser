import com.edited.util.JDBCUtil;

public class Main {

    public static void main(String[] args) {
        JDBCUtil instance = JDBCUtil.getInstance();
        instance.getDataFromDB();
    }
}
