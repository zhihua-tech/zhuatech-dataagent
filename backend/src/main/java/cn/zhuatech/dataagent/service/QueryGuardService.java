/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.dataagent.service;
import jakarta.validation.constraints.*; import org.springframework.stereotype.Service; import java.util.*; import java.util.regex.Pattern;
/** 只读查询安全门；演示实现不连接真实数据库。 */
@Service public class QueryGuardService {
 private static final Pattern MUTATION=Pattern.compile("\\b(insert|update|delete|drop|alter|truncate|grant|revoke|merge|call)\\b",Pattern.CASE_INSENSITIVE);
 public record QueryRequest(@NotBlank String question,@NotBlank String sql,@Min(1) @Max(10000) int maxRows,boolean containsSensitiveFields,boolean dataOwnerApproved){}
 public record QueryDecision(boolean allowed,String route,int rowLimit,List<String> controls){}
 public QueryDecision inspect(QueryRequest r){String normalized=r.sql().strip();boolean readOnly=(normalized.regionMatches(true,0,"select",0,6)||normalized.regionMatches(true,0,"with",0,4))&&!MUTATION.matcher(normalized).find();boolean allowed=readOnly&&(!r.containsSensitiveFields()||r.dataOwnerApproved());String route=!readOnly?"BLOCK_MUTATION":r.containsSensitiveFields()&&!r.dataOwnerApproved()?"OWNER_APPROVAL":"READ_ONLY_EXECUTION";return new QueryDecision(allowed,route,Math.min(r.maxRows(),1000),List.of("应用当前用户行列权限","记录指标口径与 SQL 指纹","结果敏感字段默认脱敏"));}}

