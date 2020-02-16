package cn.wgn.website;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/wuguangnuo";
    private static final String DRIVERNAME = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static boolean FILEOVERRIDE = true;//是否覆盖原文件，要特别注意

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("WuGuangNuo");
        gc.setOpen(false);
        gc.setSwagger2(true);
        gc.setFileOverride(FILEOVERRIDE);//是否覆盖原文件，要特别注意
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(URL);
        // dsc.setSchemaName("public");
        dsc.setDriverName(DRIVERNAME);
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        dsc.setTypeConvert(new MySqlServerTypeConvert());
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("website");
        pc.setParent("cn.wgn");
        pc.setService("baseService");
        pc.setServiceImpl("baseService.impl");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig(templatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });
//
//        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
//         templateConfig.setEntity("templates/entity2.java");
//         templateConfig.setService();
//         templateConfig.setController();

//        templateConfig.setXml(null);
//        templateConfig.setService("/templates/service.java");
        templateConfig.setMapper("/templates/mapper.java");
        templateConfig.setController(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
//        strategy.setSuperServiceImplClass("ffff");
//        strategy.setSuperServiceClass("IBaseService");
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        strategy.setTablePrefix("wu_");

        List<String> tableNames = getAllTableNames();
        strategy.setSkipView(true);

        String[] arr = new String[tableNames.size()];
        tableNames.toArray(arr);
        strategy.setInclude(arr);

//        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    private static Connection getConn() {

        Connection conn = null;
        try {
            Class.forName(DRIVERNAME); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private static List<String> getAllTableNames() {
        Connection conn = getConn();
        String sql = "SELECT table_name FROM information_schema.TABLES WHERE table_schema = 'wuguangnuo'";
        PreparedStatement pstmt;
        List<String> result = new ArrayList<>();
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            System.out.println("============================");
            while (rs.next()) {
                String tableName = rs.getString(1);
                result.add(tableName);
            }
            System.out.println("============================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
