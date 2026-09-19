/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.dataagent.agent;
import org.springframework.stereotype.Component; import java.util.List; import java.util.Map;
/**
 * 企业数据分析智能体平台运行边界；默认演示执行器不连接真实模型、业务系统或外部通信渠道。
 *
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface AgentRuntime {
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 AgentResult run(AgentRequest request);
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 record AgentRequest(String objective,Map<String,String> context){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 record AgentStep(String name,String status,String evidence){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 record AgentResult(String runtime,String summary,List<AgentStep> steps,Map<String,Object> metrics){}
}
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Component class DemoAgentRuntime implements AgentRuntime {
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public AgentResult run(AgentRequest request){
  return new AgentResult("data-readonly-demo","已匹配认证指标并生成只读查询草案，敏感字段与经营结论等待数据负责人确认。",List.of(new AgentStep("指标匹配","COMPLETED","命中 6 个认证指标"),new AgentStep("只读查询","COMPLETED","通过 SQL 安全检查"),new AgentStep("结论发布","PENDING","等待分析师确认")),Map.of("evidenceItems",12,"suggestedActions",3,"objectiveLength",request.objective().length()));
 }
}

