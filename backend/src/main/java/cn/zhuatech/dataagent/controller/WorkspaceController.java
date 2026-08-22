/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.dataagent.controller;
import cn.zhuatech.dataagent.agent.AgentRuntime;
import cn.zhuatech.dataagent.common.ApiResponse;
import cn.zhuatech.dataagent.dto.DataAgentDto.*;
import cn.zhuatech.dataagent.service.DataAgentService;
import cn.zhuatech.dataagent.service.QueryGuardService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/shopfloor") @PreAuthorize("hasAnyRole('DOMAIN_USER','ADMIN')")
public class WorkspaceController {
 private final DataAgentService service; private final AgentRuntime runtime; private final QueryGuardService domainAgent;
 public WorkspaceController(DataAgentService service,AgentRuntime runtime,QueryGuardService domainAgent){this.service=service;this.runtime=runtime;this.domainAgent=domainAgent;}
 @GetMapping("/dashboard") public ApiResponse<Dashboard> dashboard(){return ApiResponse.ok(service.shopfloorDashboard());}
 @PostMapping("/work-orders/{id}/reports") public ApiResponse<ReportResult> report(@PathVariable Long id,@Valid @RequestBody ReportRequest request){return ApiResponse.ok("反馈提交成功",service.report(id,request));}
 @PostMapping("/agent-preview") public ApiResponse<AgentRuntime.AgentResult> preview(@RequestBody Map<String,String> body){return ApiResponse.ok(runtime.run(new AgentRuntime.AgentRequest(body.getOrDefault("objective","分析当前业务事项"),Map.of("mode","demo","approval","required"))));}
 @PostMapping("/query-guard") public ApiResponse<QueryGuardService.QueryDecision> domainAction(@Valid @RequestBody QueryGuardService.QueryRequest request){return ApiResponse.ok("数据查询策略检查完成",domainAgent.inspect(request));}
}

