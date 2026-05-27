---
name: java-service-logging
description: Enforce consistent logging structure across all service methods. Every public method must follow entry/exit pattern with structured logging.
---

## Обязательная структура логирования

### 1. Объявление логгера (в каждом классе)
\```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

private static final Logger log = LoggerFactory.getLogger(CurrentClassName.class);
\```

### 2. Шаблон для каждого публичного метода

```java
public ReturnType methodName(ParamType param1, ParamType param2) {
    // ENTRY LOG (INFO)
    log.info("→ methodName started - param1={}, param2={}", param1, param2);
    
    try {
        // BUSINESS LOGIC
        ReturnType result = actualBusinessLogic(param1, param2);
        
        // SUCCESS EXIT LOG (INFO)
        log.info("← methodName completed - result={}", result);
        return result;
        
    } catch (Exception e) {
        // ERROR LOG (ERROR)
        log.error("✗ methodName failed - params: param1={}, param2={}, error={}", 
                  param1, param2, e.getMessage(), e);
        throw e; // or wrap in custom exception
    }
} 