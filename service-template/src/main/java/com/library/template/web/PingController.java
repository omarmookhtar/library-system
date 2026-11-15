package com.library.template.web;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** للتجربة بس. امسحه لما تبدأ شغلك الحقيقي. */
@RestController
@RequestMapping("/api/template")
public class PingController {
    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of("status", "UP");
    }
}
