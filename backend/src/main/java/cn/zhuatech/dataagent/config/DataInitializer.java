/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.dataagent.config;
import cn.zhuatech.dataagent.model.*; import cn.zhuatech.dataagent.repository.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder; import java.time.LocalDate; import java.util.List;
@Configuration public class DataInitializer {
 @Bean CommandLineRunner seed(OperatingUnitRepository units,WorkRecordRepository records,ResourceRegisterRepository resources,ReviewRecordRepository reviews,UserRepository users,PasswordEncoder encoder){return args->{if(units.count()>0)return;
  OperatingUnit first=units.save(new OperatingUnit("DATA-BIZ","经营分析组","数据与分析中心",2600)),second=units.save(new OperatingUnit("DATA-GOV","数据治理组","数据与分析中心",1400)),third=units.save(new OperatingUnit("DATA-SCM","供应链分析组","运营中心",1200));
  WorkRecord a=records.save(new WorkRecord("ANA-260808-018","MET-RETENTION","华东区域续约率下降原因分析",first,10,6,2,LocalDate.now().plusDays(2),WorkRecord.Status.RELEASED,"METRIC-V6")); WorkRecord b=records.save(new WorkRecord("ANA-260808-012","RPT-MONTHLY","七月经营看板口径核验",second,8,8,0,LocalDate.now().plusDays(0),WorkRecord.Status.COMPLETED,"METRIC-V5")); WorkRecord c=records.save(new WorkRecord("ANA-260808-021","MET-PO-CYCLE","采购交付周期异常分析",third,9,4,1,LocalDate.now().plusDays(3),WorkRecord.Status.RUNNING,"METRIC-V4"));
  resources.saveAll(List.of(new ResourceRegister("METRIC-HUB-01","认证经营指标目录",second,ResourceRegister.Status.RUNNING,97),new ResourceRegister("DATA-LAKE-02","经营数据只读服务",first,ResourceRegister.Status.RUNNING,94),new ResourceRegister("GUARD-SQL-03","SQL 与权限审查器",second,ResourceRegister.Status.ALARM,79)));
  reviews.saveAll(List.of(new ReviewRecord("REV-DA-028",a,"因果表述",18,2,ReviewRecord.Result.PENDING,"唐序"),new ReviewRecord("REV-DA-017",b,"指标准确性",32,0,ReviewRecord.Result.PASSED,"顾遥"),new ReviewRecord("REV-DA-039",c,"数据合规",16,4,ReviewRecord.Result.FAILED,"沈括")));
  String demo=encoder.encode("Demo@2026");
  users.saveAll(List.of(new UserAccount("operator",demo,"顾遥",UserAccount.Role.DOMAIN_USER,"DATA-BIZ"),new UserAccount("planner",demo,"唐序",UserAccount.Role.DOMAIN_OPERATOR,null),new UserAccount("quality",demo,"评测负责人",UserAccount.Role.QUALITY,null),new UserAccount("admin",encoder.encode("ZhuaTech@2026"),"系统管理员",UserAccount.Role.ADMIN,null)));
 };}}

