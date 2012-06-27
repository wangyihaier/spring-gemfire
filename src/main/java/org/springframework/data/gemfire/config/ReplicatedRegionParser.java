/*
 * Copyright 2010-2012 the original author or authors.
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

package org.springframework.data.gemfire.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;
import org.w3c.dom.Element;

import com.gemstone.gemfire.cache.DataPolicy;

/**
 * Parser for &lt;replicated-region;gt; definitions.
 * 
 * @author Costin Leau
 * @author David Turanski
 */
class ReplicatedRegionParser extends AbstractRegionParser {
	@Override
	protected void doParseRegion(Element element, ParserContext parserContext, BeanDefinitionBuilder builder,
			boolean subRegion) {

		// set the data policy
		String attr = element.getAttribute("persistent");
		if (Boolean.parseBoolean(attr)) {
			builder.addPropertyValue("dataPolicy", DataPolicy.PERSISTENT_REPLICATE);
		}
		else {
			builder.addPropertyValue("dataPolicy", DataPolicy.REPLICATE);
		}

		ParsingUtils.parseScope(element, builder);

		BeanDefinitionBuilder attrBuilder = subRegion ? builder : BeanDefinitionBuilder
				.genericBeanDefinition(RegionAttributesFactoryBean.class);

		super.doParseRegionCommon(element, parserContext, builder, attrBuilder, subRegion);
		if (!subRegion) {
			builder.addPropertyValue("attributes", attrBuilder.getBeanDefinition());
		}
	}
}