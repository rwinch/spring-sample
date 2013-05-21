Purpose
=================

When trying to figure out how to modify Bean Definitions using JavaConfig I discovered that BeanDefinitionRegistryPostProcessor's postProcessBeanDefinitionRegistry is not invoked. This is discussed in [SPR-7868](https://jira.springsource.org/browse/SPR-7868) which states that a ImportBeanDefinitionRegistrar should be used. However, there appear to be differences between the ImportBeanDefinitionRegistrar and a BeanDefinitionRegistryPostProcessor. Specifically, the BeanDefinitionRegistryPostProcessor appears to contain all bean names but the ImportBeanDefinitionRegistrar does not.

So the question is how can I modify/add BeanDefinitions based upon other beans that are defined in configuration?

Instructions
=================

Your are likely viewing this because you have submitted a forum question or a JIRA and this is a sample project to illustrate points made in my response. You can either run this from the commandline or an IDE that supports Maven (i.e. Spring Tool Suite).

Running from the commandline
---------------------------------

To run the sample application you must have Maven 3 installed. After doing so you can easily run the tests using the following steps:

* Navigate to the folder on the commandline
* Execute

    mvn clean verify


