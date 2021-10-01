/**
 * Copyright 2021 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.lzco.cache.prop;

import com.github.lzco.cache.constant.CacheConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 项目通用配置
 *
 * @author lzc
 * @date 2021/04/28 10:53
 */
@ConfigurationProperties(prefix = CacheConstant.PROJECT_PREFIX)
public class ProjectProperties {

    private String name = "default";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}