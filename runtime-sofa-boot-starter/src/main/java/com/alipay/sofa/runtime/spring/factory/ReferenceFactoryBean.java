/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.runtime.spring.factory;

import com.alipay.sofa.runtime.model.InterfaceMode;
import com.alipay.sofa.runtime.service.component.Reference;
import com.alipay.sofa.runtime.service.component.impl.ReferenceImpl;
import com.alipay.sofa.runtime.service.helper.ReferenceRegisterHelper;
import com.alipay.sofa.runtime.spi.service.BindingConverterContext;
import org.springframework.util.Assert;

/**
 * @author xuanbei 18/3/1
 */
public class ReferenceFactoryBean extends AbstractContractFactoryBean {
    private Object proxy;
    /** load balance **/
    private String loadBalance;

    @Override
    protected void doAfterPropertiesSet() throws Exception {
        Reference reference = buildReference();
        Assert.isTrue(bindings.size() == 1, "<sofa:reference/> should have one binding.");

        reference.addBinding(bindings.get(0));
        proxy = ReferenceRegisterHelper.registerReference(reference, sofaRuntimeContext);
    }

    @Override
    protected void setProperties(BindingConverterContext bindingConverterContext) {
        bindingConverterContext.setLoadBalance(loadBalance);
        bindingConverterContext.setBeanId(beanId);
    }

    protected Reference buildReference() {
        return new ReferenceImpl(uniqueId, getInterfaceClass(), InterfaceMode.spring);
    }

    @Override
    public Object getObject() throws Exception {
        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return getInterfaceClass();
    }

    @Override
    protected boolean isInBinding() {
        return true;
    }

    public String getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(String loadBalance) {
        this.loadBalance = loadBalance;
    }
}
