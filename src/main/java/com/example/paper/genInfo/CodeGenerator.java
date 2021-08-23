package com.example.paper.genInfo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 赵培辰
 */
public class CodeGenerator {
    public static void main(String[] args) {
        //创建代码生成器实例
        AutoGenerator mpg = new AutoGenerator();
        //配置生成策略

        //1.全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");//获取当前系统的目录
        gc.setOutputDir(projectPath+"/src/main/java"); //生成代码的路径
        gc.setAuthor("test");
        gc.setOpen(false);//生成后是否打开文件
        gc.setFileOverride(false);//是否覆盖
        gc.setServiceImplName("%sServiceImpl");//去service的I前缀
        gc.setServiceName("%sService");
        gc.setMapperName("%sDao");
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc); //全局配置

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
        dsc.setDriverName("oracle.jdbc.driver.OracleDriver");
        dsc.setUsername("LUNWEN");
        dsc.setPassword("root");
        dsc.setDbType(DbType.ORACLE);  //数据库类型
        mpg.setDataSource(dsc); //配置数据源

        //配置包
        PackageConfig pc = new PackageConfig();//设置包管理
        pc.setModuleName("test");
        pc.setParent("com.example");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setController("controller");
        pc.setMapper("dao");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);

        //策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        mpg.setStrategy(strategy);
        mpg.execute();

    }

}

