# 项目说明
该项目基于Spring的@Cacheable，通过自定义注解@TtlCacheable，实现缓存的存活时间配置和自动刷新。

#### 简单说明
@TtlCacheable用法和@Cacheable一样，只是额外加了两个属性：
* ttl，可配置缓存存活时间；
* autoRefreshWithoutUnless，实现自动刷新（原本想命名为autoRefresh，
但由于无法同时实现unless和自动刷新，故改名）。

支持SpringBoot项目自动装配，轻松上手。

注：项目基于SpringBoot 2.3.9.RELEASE开发，经测试在基于2.3.2.RELEASE的项目可成功运行。