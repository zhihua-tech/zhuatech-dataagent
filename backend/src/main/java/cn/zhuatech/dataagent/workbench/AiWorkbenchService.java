/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.dataagent.workbench;

import static cn.zhuatech.dataagent.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);
    keys(r, "values", "zThreshold");
    var values = list(r, "values", 1, 500);
    double threshold = decimal(r, "zThreshold", 1, 10).doubleValue();
    List<Double> numbers = new ArrayList<>();
    int missing = 0;
    for (Object v : values) {
      if (v == null) {
        missing++;
        continue;
      }
      numbers.add(decimal(Map.of("v", v), "v", -1e12, 1e12).doubleValue());
    }
    if (numbers.isEmpty()) throw new IllegalArgumentException("至少提供一个有效数值");
    double mean = numbers.stream().mapToDouble(x -> x).average().orElseThrow();
    double
        variance =
            numbers.stream().mapToDouble(x -> (x - mean) * (x - mean)).sum() / numbers.size(),
        sd = Math.sqrt(variance);
    var sorted = numbers.stream().sorted().toList();
    int n = sorted.size();
    double median =
        n % 2 == 1 ? sorted.get(n / 2) : (sorted.get(n / 2 - 1) + sorted.get(n / 2)) / 2;
    List<Map<String, Object>> out = new ArrayList<>();
    for (int i = 0; i < values.size(); i++)
      if (values.get(i) != null && sd > 0) {
        double value = ((Number) values.get(i)).doubleValue(), z = (value - mean) / sd;
        if (Math.abs(z) > threshold)
          out.add(Map.of("row", i + 1, "value", round(value), "zScore", round(z)));
      }
    return Map.of(
        "count",
        values.size(),
        "missing",
        missing,
        "distinct",
        new HashSet<>(numbers).size(),
        "mean",
        round(mean),
        "median",
        round(median),
        "standardDeviation",
        round(sd),
        "outliers",
        out);
  }
}
