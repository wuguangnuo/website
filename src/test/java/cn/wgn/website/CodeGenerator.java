package cn.wgn.website;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * CodeGenerator
 * 自动代码生成器
 * 功能已完善
 * </p>
 *
 * @author WuGuangNuo
 */
public class CodeGenerator {

    private static final String DB_NAME = "wuguangnuo";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/" + DB_NAME + "?serverTimezone=UTC";

    private static final String[] TABLE_PREFIX = {"bot_"}; // 需要生成的表前缀
    private static final Boolean CONTENT_CUD = false; // 是否生成默认 Post,Put,Delete 方法(Get,Count已选)
    private static final Boolean FILE_OVERRIDE = true; // 是否覆盖原文件
    private static final Boolean TEST_MODEL = true; // 测试模式

    public static void main(String[] args) {
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setTypeConvert(new MySqlServerTypeConvert());
        dsc.setUrl(URL);
        dsc.setDriverName(DRIVER_NAME);
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);

        // 数据库表配置
        StrategyConfig sc = new StrategyConfig();
        sc.setCapitalMode(false); // 是否大写命名，默认false
        sc.setSkipView(true); // 是否跳过视图，默认false
        sc.setNaming(NamingStrategy.underline_to_camel); // 数据库表映射到实体的命名策略
        sc.setColumnNaming(NamingStrategy.underline_to_camel); // 数据库表字段映射到实体的命名策略
        sc.setTablePrefix(TABLE_PREFIX); // 表前缀
//        sc.setFieldPrefix(); // 字段前缀
        sc.setSuperEntityClass(cn.wgn.framework.web.entity.BaseEntity.class); // 自定义继承的Entity类全称
//        sc.setSuperEntityColumns(); // 自定义基础的Entity类
//        sc.setSuperMapperClass(); // 自定义继承的Mapper类全称
        sc.setSuperServiceClass("cn.wgn.framework.web.service.IBaseService"); // 自定义继承的Service类全称
        sc.setSuperServiceImplClass("cn.wgn.framework.web.service.BaseServiceImpl"); // 自定义继承的ServiceImpl类全称
        sc.setSuperControllerClass(cn.wgn.framework.web.controller.BaseController.class); // 自定义继承的Controller类全称
        sc.setEnableSqlFilter(false); // 激活进行sql模糊表名匹配
        sc.setInclude(getTables()); // 需要包含的表名，当enableSqlFilter为false时，允许正则表达式
        sc.setEntityLombokModel(true); // 实体是否为lombok模型，默认false
        sc.setRestControllerStyle(true); // 生成 @RestController 控制器
        sc.setEntityTableFieldAnnotationEnable(false); // 是否生成实体时，生成字段注解
        sc.setVersionFieldName("version"); // 乐观锁属性名称
        sc.setLogicDeleteFieldName("delete"); // 逻辑删除属性名称

        // 包名配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("cn.wgn");
        pc.setModuleName("website"); // 其余包名按照默认配置即可

        // 模板配置
        TemplateConfig tc = new TemplateConfig(); // 其余使用默认模板
        tc.setMapper("/templates/mapper.java");
        tc.setController("/templates/controller.java");

        // 全局策略配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(TEST_MODEL ? "D://" : System.getProperty("user.dir") + "/src/main/java"); // 生成文件的输出目录
        gc.setFileOverride(FILE_OVERRIDE); // 是否覆盖已有文件
        gc.setOpen(false); // 是否打开输出目录
        gc.setEnableCache(false); // 是否在xml中添加二级缓存配置
        gc.setAuthor("WuGuangNuo"); // 开发人员
        gc.setKotlin(false); // 开启 Kotlin 模式
        gc.setSwagger2(true); // 开启 swagger2 模式
        gc.setActiveRecord(true); // 开启 ActiveRecord 模式
        gc.setBaseResultMap(true); // 开启 BaseResultMap
        gc.setBaseColumnList(true); // 开启 baseColumnList
        gc.setEntityName("%sEntity"); // 实体命名方式
        gc.setIdType(IdType.AUTO); // 指定生成的主键的ID类型

        // 注入配置
        InjectionConfig ic = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                // CONTENT_CUD=true  生成CRUD四个方法，CONTENT_CUD=false 则只生成Get方法
                map.put("contentCud", CONTENT_CUD);
                this.setMap(map);
            }
        };

        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setDataSource(dsc);
        autoGenerator.setStrategy(sc);
        autoGenerator.setPackageInfo(pc);
        autoGenerator.setTemplate(tc);
        autoGenerator.setGlobalConfig(gc);
        autoGenerator.setCfg(ic);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());

        autoGenerator.execute();
    }

    /**
     * 需要自动生成代码的数据表
     *
     * @return The Tables We Need Generate
     */
    private static String[] getTables() {
        List<String> tableNames = getAllTableNames();
        List<String> tableList = new ArrayList<>();
        for (String table : tableNames) {
            for (String prefix : TABLE_PREFIX) {
                if (table.startsWith(prefix)) {
                    tableList.add(table);
                    break;
                }
            }
        }
        String[] arr = new String[tableList.size()];
        tableList.toArray(arr);
        return arr;
    }

    /**
     * 获取数据库连接
     *
     * @return DataBase Connection
     */
    private static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 获取所有的数据表
     *
     * @return All the Tables in DataBase
     */
    private static List<String> getAllTableNames() {
        Connection conn = getConn();
        String sql = "SELECT table_name FROM information_schema.TABLES WHERE table_schema = '" + DB_NAME + "'";
        PreparedStatement pstmt;
        List<String> result = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String tableName = rs.getString(1);
                result.add(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
