/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class MyBeanDefinitionRegistryPostProcessor implements
        BeanDefinitionRegistryPostProcessor {

    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    public void postProcessBeanDefinitionRegistry(
            BeanDefinitionRegistry registry) throws BeansException {
        List<String> beanNames = Arrays.asList(registry
                .getBeanDefinitionNames());
        BeanDefinitionBuilder beanNamesBldr = BeanDefinitionBuilder
                .rootBeanDefinition(BeanDefinitionNames.class);
        beanNamesBldr.addConstructorArgValue(beanNames);
        registry.registerBeanDefinition(BeanDefinitionNames.class.getName(),
                beanNamesBldr.getBeanDefinition());
    }

}