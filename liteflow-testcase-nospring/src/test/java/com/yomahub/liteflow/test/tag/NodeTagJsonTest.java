package com.yomahub.liteflow.test.tag;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.core.FlowExecutorHolder;
import com.yomahub.liteflow.slot.DefaultSlot;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.property.LiteflowConfig;
import com.yomahub.liteflow.test.BaseTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 非spring环境下组件标签的测试
 * @author Bryan.Zhang
 * @since 2.5.0
 */
public class NodeTagJsonTest extends BaseTest {

    private static FlowExecutor flowExecutor;

    @BeforeClass
    public static void init(){
        LiteflowConfig config = new LiteflowConfig();
        config.setRuleSource("tag/flow.json");
        flowExecutor = FlowExecutorHolder.loadInstance(config);
    }

    @Test
    public void testTag1() throws Exception{
        LiteflowResponse<DefaultSlot> response = flowExecutor.execute2Resp("chain1", "arg");
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("123",response.getSlot().getData("test"));
    }

    @Test
    public void testTag2() throws Exception{
        LiteflowResponse<DefaultSlot> response = flowExecutor.execute2Resp("chain2", "arg");
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("a==>a==>a==>c==>e", response.getSlot().getExecuteStepStr());
    }

    //测试多线程when情况下的tag取值是否正确
    //这里循环多次的原因是，因为when多线程，有时候因为凑巧，可能正确。所以多次情况下在2.6.4版本肯定出错
    @Test
    public void testTag3() throws Exception{
        for (int i = 0; i < 50; i++) {
            LiteflowResponse<DefaultSlot> response = flowExecutor.execute2Resp("chain3", "arg");
            Assert.assertTrue(response.isSuccess());
            ConcurrentHashSet<String> testSet = response.getSlot().getData("test");
            Assert.assertEquals(3, testSet.size());
        }
    }

    //测试tag是否能在isAccess中起效
    @Test
    public void testTag4() throws Exception{
        LiteflowResponse<DefaultSlot> response = flowExecutor.execute2Resp("chain4", "arg");
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("g", response.getSlot().getExecuteStepStr());
    }
}
