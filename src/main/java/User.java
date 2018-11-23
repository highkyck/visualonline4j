
import com.oracle.javafx.jmx.json.JSONFactory;
import com.oracle.javafx.jmx.json.JSONReader;
import jdk.nashorn.internal.parser.JSONParser;
import jdk.nashorn.internal.runtime.JSONFunctions;
import net.sf.json.JSONArray;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    public static JSONArray getUserGroup(int userid) {

        JSONArray jsonArray = new JSONArray();
        try{

            Connection conn = null;
            Statement stmt = null;

            // 打开链接
            conn = BaseDao.getConnection();

            // 执行查询
            stmt = conn.createStatement();
            String sql = "select group_id,sub_group_id from user where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(userid, 1);

            ResultSet rs = pstmt.executeQuery();
            // 展开结果集数据库
            while(rs.next()){
                String group_id_str  = rs.getString("group_id");
                String sub_group_str = rs.getString("sub_group_id");

                String sql_group = "select group_name from user_group where id in (" + group_id_str + ")";
                PreparedStatement pstmt_group = conn.prepareStatement(sql_group);
                ResultSet rs_grpup = pstmt_group.executeQuery();

                List list1 = new ArrayList();
                while(rs_grpup.next()) {
                    list1.add(rs_grpup.getString("group_name"));
                }

                String sql_sub_group = "select sub_group_name from sub_group where id in (" + sub_group_str + ")";
                PreparedStatement pstmt_subgroup = conn.prepareStatement(sql_sub_group);
                ResultSet rs_subgrpup = pstmt_subgroup.executeQuery();

                List list2 = new ArrayList();
                while(rs_subgrpup.next()) {
                    list2.add(rs_subgrpup.getString("sub_group_name"));
                }

                HashMap map = new HashMap();
                map.put("群聊",list1);
                map.put("分组",list2);

                //返回群聊、分组信息
                jsonArray = JSONArray.fromObject(map);
                return jsonArray;

            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }

        return  jsonArray;
    }
}
