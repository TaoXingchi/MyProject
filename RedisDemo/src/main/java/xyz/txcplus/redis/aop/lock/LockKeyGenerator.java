package xyz.txcplus.redis.aop.lock;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.sun.deploy.util.ArrayUtil;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import xyz.txcplus.redis.aop.lock.annotation.LockResource;

import java.lang.reflect.Method;

/**
 * 分布式锁Key生成器
 *
 * @version 1.0.0
 * @author: wenhai
 * @date:2019-11-01 10:28
 * @since JDK 1.8
 */

public class LockKeyGenerator {
    private static final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    /**
     * SpEL表达式解析器
     */
    private static final ExpressionParser PARSER = new SpelExpressionParser();

    /**
     * 获取key
     *
     * @param invocation   调用的方法
     * @param lockResource 资源锁信息
     * @return
     */
    public String getKeyName(MethodInvocation invocation, LockResource lockResource) {
        StringBuilder key = new StringBuilder();
        Method method = invocation.getMethod();
        if (StringUtils.isNotBlank(lockResource.resourceName())) {
            // 指定了资源名称采用指定值
            key.append(lockResource.resourceName());
        } else {
            // 未指定资源名称采用 类完整名称+方法名称
            key.append(method.getDeclaringClass().getName()).append(".").append(method.getName());
        }
        if (StringUtils.isNotBlank(lockResource.key())) {
            // 指定key值 采用指定值
            key.append(":");
            key.append(getSpelDefinitionKey(lockResource.key(), method, invocation.getArguments()));
        } else {
            // 未指定值 采用方法所有参数值的MD5加密值
            Object[] arguments = invocation.getArguments();
            if (ArrayUtil.isNotEmpty(arguments)) {
                StringBuilder temporaryKey = new StringBuilder();
                for (Object itm : arguments) {
                    temporaryKey.append(itm);
                }
                key.append(":");
                key.append(DigestUtil.md5Hex(temporaryKey.toString()));
            }
        }
        return key.toString();
    }


    /**
     * 获取SpEL表达式定义的值
     *
     * @param definitionKeys  定义的key值
     * @param method          方法
     * @param parameterValues 方法参数值
     * @return
     */
    private String getSpelDefinitionKey(String definitionKeys, Method method, Object[] parameterValues) {
        EvaluationContext context = new MethodBasedEvaluationContext(null, method, parameterValues, NAME_DISCOVERER);
        return PARSER.parseExpression(definitionKeys).getValue(context).toString();
    }
}
