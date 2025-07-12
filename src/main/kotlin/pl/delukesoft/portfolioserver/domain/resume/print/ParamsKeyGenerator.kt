package pl.delukesoft.portfolioserver.domain.resume.print

import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Component("paramsKeyGenerator")
class ParamsKeyGenerator: KeyGenerator {

  override fun generate(target: Any, method: Method, vararg params: Any?): Any {
    return method.name + params.contentDeepHashCode()
  }


}