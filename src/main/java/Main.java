import com.edited.util.JDBCUtil;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        JDBCUtil instance = JDBCUtil.getInstance();
        Map<String, List<String>> dataFromDB = instance.getDataFromDB("D:\\dev\\asdasd1\\main1.db");

        System.out.println(instance.exportToTxt("D:\\dev\\output", dataFromDB));

    }
}
